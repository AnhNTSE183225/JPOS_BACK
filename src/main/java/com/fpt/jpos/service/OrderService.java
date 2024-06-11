package com.fpt.jpos.service;

import com.fpt.jpos.dto.*;
import com.fpt.jpos.pojo.*;
import com.fpt.jpos.pojo.enums.OrderStatus;
import com.fpt.jpos.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;

    private final ICustomerRepository customerRepository;

    private final IStaffRepository staffRepository;

    private final IPaymentRepository paymentRepository;

    private final IProductRepository productRepository;

    private final IProductShellDesignRepository productShellDesignRepository;

    private final IProductDesignRepository productDesignRepository;

    private final IProductShellMaterialRepository productShellMaterialRepository;

    private final IProductMaterialRepository productMaterialRepository;

    private final IDiamondRepository diamondRepository;

    private final IDiamondPriceService diamondPriceService;

    private final IMaterialPriceService materialPriceService;
    private final ProductService productService;

    ModelMapper modelMapper = new ModelMapper();


    @Autowired
    public OrderService(IOrderRepository theIOrderRepository,
                        ICustomerRepository theICustomerRepository,
                        IPaymentRepository theIPaymentRepository,
                        IStaffRepository theIStaffRepository,
                        IProductRepository theIProductRepository,
                        IProductShellDesignRepository productShellDesignRepository,
                        IProductDesignRepository productDesignRepository,
                        IProductShellMaterialRepository productShellMaterialRepository,
                        IProductMaterialRepository productMaterialRepository,
                        IDiamondRepository diamondRepository,
                        IDiamondPriceService diamondPriceService,
                        IMaterialPriceService materialPriceService, ProductService productService) {
        orderRepository = theIOrderRepository;
        customerRepository = theICustomerRepository;
        paymentRepository = theIPaymentRepository;
        staffRepository = theIStaffRepository;
        productRepository = theIProductRepository;
        this.productShellDesignRepository = productShellDesignRepository;
        this.productDesignRepository = productDesignRepository;
        this.productShellMaterialRepository = productShellMaterialRepository;
        this.productMaterialRepository = productMaterialRepository;
        this.diamondRepository = diamondRepository;
        this.diamondPriceService = diamondPriceService;
        this.materialPriceService = materialPriceService;
        this.productService = productService;
    }

    @Override
    @Transactional
    public String handleManagerResponse(Integer id, boolean managerApproval) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));

        if (managerApproval) {
            order.setStatus(OrderStatus.manager_approved);
        } else {
            order.setStatus(OrderStatus.wait_manager);
        }

        orderRepository.save(order);
        return order.getStatus().name();
    }

    @Override
    public List<Order> getOrdersByCustomerId(Integer customerId) {
        return orderRepository.findOrdersForCustomer(customerId);
    }

    @Override
    public List<Order> getOrderForSalesStaff(Integer id) {
        return orderRepository.findOrderForSalesStaff(id);
    }

    @Override
    public List<Order> getOrderForDesignStaff(Integer id) {
        return orderRepository.findOrdersForDesignStaff(id);
    }

    @Override
    public List<Order> getOrderForProductionStaff(Integer id) {
        return orderRepository.findOrdersForProductionStaff(id);
    }

    @Override
    public List<Order> getOrderForManager() {
        return orderRepository.findOrdersForManager();
    }


    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Order updateOrderStatusDesigning(Integer id, PaymentDTO paymentDTO) {
        Optional<Order> theOrder = orderRepository.findById(id);
        Payment payment = modelMapper.map(paymentDTO, Payment.class);

        if (theOrder.isPresent()) {
            Order order = theOrder.get();
            if(order.getOrderType().equals("from_design")) {
                order.setStatus(OrderStatus.production);
            } else {
                order.setStatus(OrderStatus.designing);
            }
            payment.setOrder(order);
            paymentRepository.save(payment);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public Order updateOrderStatusDesigning(Integer id, NoteDTO noteDTO) {
        Optional<Order> theOrder = orderRepository.findById(id);
        if (theOrder.isPresent()) {
            Order order = theOrder.get();
            order.setStatus(OrderStatus.designing);
            order.setModelFeedback(noteDTO.getNote());
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public Order updateOrderStatusProduction(Integer id) {
        Optional<Order> theOrder = orderRepository.findById(id);
        if (theOrder.isPresent()) {
            Order order = theOrder.get();
            order.setStatus(OrderStatus.production);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order insertOrder(CustomerRequestDTO customerRequestDTO) {
        Order theOrder = new Order();

        Customer customer = customerRepository.findById(customerRequestDTO.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));

        theOrder.setOrderDate(new Date());
        theOrder.setDesignFile(customerRequestDTO.getDesignFile());
        theOrder.setBudget(customerRequestDTO.getBudget());
        theOrder.setOrderType("customize");
        theOrder.setDescription(customerRequestDTO.getDescription());
        theOrder.setStatus(OrderStatus.wait_sale_staff);
        theOrder.setCustomer(customer);

        orderRepository.save(theOrder);
        return theOrder;
    }

    @Override
    @Transactional
    public Order retrieveQuotationFromStaff(Order order, Integer productId, Integer staffId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Staff> optionalStaff = staffRepository.findById(staffId);
        if (optionalProduct.isPresent() && optionalStaff.isPresent()) {
            Product product = optionalProduct.get();
            Staff staff = optionalStaff.get();
            order.setProduct(product);
            order.setStatus(OrderStatus.wait_manager);
            order.setSaleStaff(staff);
            order.setQDate(new Date());
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
        }
    }

    @Override
    @Transactional
    public OrderStatus forwardQuotation(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));

        if (!OrderStatus.manager_approved.equals(order.getStatus())) {
            throw new IllegalStateException("Order status must be 'manager_approved' to forward quotation");
        }

        order.setStatus(OrderStatus.wait_customer);
        orderRepository.save(order);
        return order.getStatus();
    }

    @Override
    @Transactional
    public Order acceptOrder(Order order) {
        order.setStatus(OrderStatus.customer_accept);
        order.setODate(new Date());
        return orderRepository.save(order);
    }

    @Override
    public Order completeProduct(Integer id, String imageUrl, Integer productionStaffId) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));

        if (!OrderStatus.production.equals(order.getStatus())) {
            throw new IllegalStateException("Order status must be 'production' to complete the product");
        }

        Staff productionStaff = staffRepository.findById(productionStaffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found for this id :: " + productionStaffId));

        order.setStatus(OrderStatus.delivered);
        order.setProductImage(imageUrl);
        order.setProductionStaff(productionStaff); // Gán productionStaffId vào order
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order completeOrder(PaymentDTO paymentDTO, Integer orderId) {

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        Payment depositPayment = paymentRepository.findPaymentByOrderId(orderId);

        Double depositMoney = 0.0;

        if(depositPayment != null) {
            depositMoney = depositPayment.getAmountPaid();
        }
        double sum = depositMoney + payment.getAmountPaid();

        if (sum == payment.getAmountTotal()) {
            paymentRepository.save(payment);
        }
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(OrderStatus.completed);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
    }

    @Override
    @Transactional
    public Order createOrderFromDesign(ProductDesignDTO productDesignDTO) {
        ProductShellDesign productShellDesign = productShellDesignRepository.findById(productDesignDTO.getProductShellId()).orElseThrow();
        ProductDesign productDesign = productDesignRepository.findById(productDesignDTO.getProductDesignId()).orElseThrow();
        List<ProductShellMaterial> productShellMaterialList = productShellMaterialRepository.findByShellId(productShellDesign.getProductShellDesignId());
        List<Diamond> diamonds = new ArrayList<>();
        Double diamondPrice = 0.0;
        double materialPrice = 0.0;

        Product product = new Product();
        product.setProductName(productDesign.getDesignName() + " - " +  productShellDesign.getShellName());
        product.setProductType(productDesign.getDesignType());
        product.setEDiamondPrice(productShellDesign.getEDiamondPrice());
        product.setEMaterialPrice(productShellDesign.getEMaterialPrice());
        product.setMarkupRate(productShellDesign.getMarkupRate());
        product.setProductionPrice(productShellDesign.getProductionPrice());

        product = productRepository.save(product);

        for (ProductShellMaterial productShellMaterial : productShellMaterialList) {
            Material material = productShellMaterial.getMaterial();

            ProductMaterialId productMaterialId = new ProductMaterialId(product.getProductId(), material.getMaterialId());

            ProductMaterial productMaterial = new ProductMaterial();
            productMaterial.setId(productMaterialId);
            productMaterial.setMaterial(material);
            productMaterial.setProduct(product);
            productMaterial.setWeight(productShellMaterial.getWeight());

            materialPrice += productShellMaterial.getWeight()*materialPriceService.getLatestPriceById(material.getMaterialId());
            productMaterialRepository.save(productMaterial);
        }


        for (Integer id : productDesignDTO.getDiamondIds()) {
            Diamond diamond = diamondRepository.findById(id).orElseThrow();
            diamonds.add(diamond);
            diamondPrice += diamondPriceService.getDiamondPricesBy4C(new Diamond4CDTO(diamond.getColor(),diamond.getClarity(),diamond.getCut(),diamond.getCaratWeight(),diamond.getCaratWeight()));
        }
        product.setDiamonds(diamonds);

        product = productRepository.save(product);

        Order order = new Order();
        order.setProduct(product);
        if(productDesignDTO.getHavePaid()) {
            order.setStatus(OrderStatus.production);
        } else {
            order.setStatus(OrderStatus.customer_accept);
        }
        order.setCustomer(customerRepository.findById(productDesignDTO.getCustomerId()).orElseThrow());
        order.setOrderDate(new Date());
        order.setOrderType("from_design");
        order.setProductionPrice(productShellDesign.getProductionPrice());
        order.setMarkupRate(productShellDesign.getMarkupRate());
        order.setODiamondPrice(diamondPrice);
        order.setOMaterialPrice(materialPrice);
        order.setDesignFile(productDesign.getDesignFile());
        order.setEDiamondPrice(productShellDesign.getEDiamondPrice());
        order.setEMaterialPrice(productShellDesign.getEMaterialPrice());
        order.setTotalAmount((diamondPrice + materialPrice + order.getProductionPrice() + order.getEDiamondPrice() + order.getEMaterialPrice())*order.getMarkupRate());
        return orderRepository.save(order);
    }

//    @Override
//    public Order addProductDesignToOrder(ProductDesignDTO productDesignDTO) {
//        // Get the product shell design
//        Optional<ProductShellDesign> productShellDesignOptional = productShellDesignRepository.findById(productDesignDTO.getProductShellId());
//        if (productShellDesignOptional.isEmpty()) {
//            throw new ResourceNotFoundException("Product shell design not found for this id :: " + productDesignDTO.getProductShellId());
//        }
//        ProductShellDesign productShellDesign = productShellDesignOptional.get();
//
//        // Get diamonds
//        List<Diamond> diamonds = diamondRepository.findAllById(productDesignDTO.getDiamondIds());
//
//        // Create new ProductDesign and set its properties
//        ProductDesign productDesign = ProductDesign.builder()
//                .productShellDesign(productShellDesign)
//                .diamonds(diamonds)
//                .build();
//
//        // Save the product design
//        productDesign = productDesignRepository.save(productDesign);
//
//        // Create new Order and set its properties
//        Order order = new Order();
//        order.setStatus(OrderStatus.wait_sale_staff);
//        order.setOrderDate(new Date());
//        order.setProductDesign(productDesign);
//        // Add other necessary order details
//
//        // Save the order
//        return orderRepository.save(order);
//    }
}
