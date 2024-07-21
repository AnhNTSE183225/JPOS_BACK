package com.fpt.jpos.service;

import com.fpt.jpos.dto.*;
import com.fpt.jpos.pojo.*;
import com.fpt.jpos.pojo.enums.OrderStatus;
import com.fpt.jpos.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
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

    private final IWarrantyRepository warrantyRepository;

    @Override
    @Transactional
    public String handleManagerResponse(Integer id, boolean managerApproval, ManagerResponseDTO managerResponseDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));

        if (managerApproval) {
            order.setMarkupRate(managerResponseDTO.getMarkupRate());
            order.setTotalAmount(managerResponseDTO.getTotalAmount());
            order.setStatus(OrderStatus.manager_approved);
        } else {
            order.setStatus(OrderStatus.wait_sale_staff);
        }

        orderRepository.save(order);
        return order.getStatus().name();
    }

    //Binh
    @Override
    public List<Order> getOrdersByCustomerId(Integer customerId) {
        return orderRepository.findOrdersForCustomer(customerId);
    }

    @Override
    public List<Order> getOrderForSalesStaff(Integer id) {
        return orderRepository.findOrderForSalesStaff(id);
    }

    //Binh
    @Override
    public List<Order> getOrderForDesignStaff(Integer id) {
        return orderRepository.findOrdersForDesignStaff(id);
    }

    //Binh
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
    //Binh
    public Order updateOrderStatusDesigning(Integer id, PaymentRestDTO.PaymentRequest paymentDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Order> theOrder = orderRepository.findById(id);
        Payment payment = modelMapper.map(paymentDTO, Payment.class);

        if (theOrder.isPresent()) {
            Order order = theOrder.get();
            if (order.getOrderType().equals("from_design")) {
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
    //Binh
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
    public Integer acceptQuotation(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        double oDiamondPrice = 0.0;
        double oMaterialPrice = 0.0;
        for (Diamond diamond : order.getProduct().getDiamonds()) {
            DiamondPriceQueryDTO query = new DiamondPriceQueryDTO(
                    diamond.getOrigin(),
                    diamond.getShape(),
                    diamond.getCaratWeight(),
                    diamond.getColor(),
                    diamond.getClarity(),
                    diamond.getCut()
            );
            oDiamondPrice += diamondPriceService.getSingleDiamondPrice(query);
        }
        for (ProductMaterial material : order.getProduct().getMaterials()) {
            oMaterialPrice += materialPriceService.getLatestPriceById(material.getMaterial().getMaterialId()) * material.getWeight();
        }
        double totalAmount = (oMaterialPrice + oDiamondPrice + order.getEMaterialPrice() + order.getEDiamondPrice() + order.getProductionPrice()) * order.getMarkupRate() * 1.1;
        double taxFee = (oMaterialPrice + oDiamondPrice + order.getEMaterialPrice() + order.getEDiamondPrice() + order.getProductionPrice()) * order.getMarkupRate() * 0.1;

        order.setOrderDate(new Date());
        order.setTaxFee(taxFee);
        order.setTotalAmount(totalAmount);
        order.setODiamondPrice(oDiamondPrice);
        order.setOMaterialPrice(oMaterialPrice);
        order.setStatus(OrderStatus.wait_payment);

        return orderRepository.save(order).getId();
    }

    @Override
    public Order completeProduct(Integer id, String imageUrls) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));

        if (!OrderStatus.production.equals(order.getStatus())) {
            throw new IllegalStateException("Order status must be 'production' to complete the product");
        }

        order.setStatus(OrderStatus.delivered);
        order.setProductImage(imageUrls);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order completeOrder(Integer orderId) {

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.YEAR, 3);
        Date endOfSupportDate = calendar.getTime();

        Order order = orderRepository.findById(orderId).orElseThrow();
        Payment payment = paymentRepository.findPaymentByOrderId(orderId);
        Warranty warranty = Warranty.builder()
                .customer(order.getCustomer())
                .product(order.getProduct())
                .purchaseDate(today)
                .endOfSupportDate(endOfSupportDate)
                .build();
        warrantyRepository.save(warranty);

        payment.setAmountPaid(order.getTotalAmount());
        payment.setPaymentStatus("Fully paid");
        paymentRepository.save(payment);

        order.setStatus(OrderStatus.completed);
        return orderRepository.save(order);
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
        product.setProductName(productDesign.getDesignName() + " - " + productShellDesign.getShellName());
        product.setProductType(productDesign.getDesignType().name());
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

            materialPrice += productShellMaterial.getWeight() * materialPriceService.getLatestPriceById(material.getMaterialId());
            productMaterialRepository.save(productMaterial);
        }


        for (Integer id : productDesignDTO.getDiamondIds()) {
            Diamond diamond = diamondRepository.findById(id).orElseThrow();
            diamond.setActive(false);
            diamond = diamondRepository.save(diamond);
            diamonds.add(diamond);
            DiamondPriceQueryDTO query = new DiamondPriceQueryDTO(
                    diamond.getOrigin(),
                    diamond.getShape(),
                    diamond.getCaratWeight(),
                    diamond.getColor(),
                    diamond.getClarity(),
                    diamond.getCut()
            );
            diamondPrice += diamondPriceService.getSingleDiamondPrice(query);
        }
        product.setDiamonds(diamonds);

        product = productRepository.save(product);

        Order order = new Order();
        order.setProduct(product);
        order.setStatus(OrderStatus.wait_payment);
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
        order.setTaxFee((diamondPrice + materialPrice + order.getProductionPrice() + order.getEDiamondPrice() + order.getEMaterialPrice()) * order.getMarkupRate() * 0.1);
        order.setTotalAmount((diamondPrice + materialPrice + order.getProductionPrice() + order.getEDiamondPrice() + order.getEMaterialPrice()) * order.getMarkupRate() + order.getTaxFee());
        order.setNote(productDesignDTO.getNote());

        return orderRepository.save(order);
    }

    @Override
    public void confirmPaymentSuccess(Integer orderId, String orderType) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentDate(new Date());
        payment.setPaymentStatus("30% Paid");
        payment.setAmountPaid(order.getTotalAmount() * 0.3);
        payment.setAmountTotal(order.getTotalAmount());
        payment.setPaymentMethod("VN-PAY");
        paymentRepository.save(payment);
        if (orderType.equals("from_design")) {
            order.setStatus(OrderStatus.production);
        } else {
            order.setStatus(OrderStatus.designing);
        }
        orderRepository.save(order);
    }

    @Override
    public Integer assign(Integer orderId, Integer saleStaffId, Integer designStaffId, Integer productionStaffId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        if (saleStaffId != null) {
            Staff staff = staffRepository.findById(saleStaffId).orElseThrow();
            order.setSaleStaff(staff);
        }

        if (designStaffId != null) {
            Staff staff = staffRepository.findById(designStaffId).orElseThrow();
            order.setDesignStaff(staff);
        }

        if (productionStaffId != null) {
            Staff staff = staffRepository.findById(productionStaffId).orElseThrow();
            order.setProductionStaff(staff);
        }

        return orderRepository.save(order).getId();
    }

    @Override
    public Order addImage(String imageUrls, Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setModelFile(imageUrls);
        order.setStatus(OrderStatus.pending_design);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(OrderStatus.cancelled);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findCompletedOrders() {
        return orderRepository.findCompletedOrders();
    }

}
