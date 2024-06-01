package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.pojo.CustomerRequest;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.pojo.enums.OrderStatus;
import com.fpt.jpos.repository.ICustomerRepository;
import com.fpt.jpos.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private IOrderRepository orderRepository;

    private ICustomerRepository customerRepository;


    @Autowired
    public OrderService(IOrderRepository theIOrderRepository, ICustomerRepository theICustomerRepository) {
        orderRepository = theIOrderRepository;
        customerRepository = theICustomerRepository;
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
    public List<Order> getOrdersByStatusAndStaffs(int id) {
        return orderRepository.findAllByStatusAndStaff(id);
    }

    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order updateOrderStatus(int id, OrderStatus status) {
        Optional<Order> theOrder = orderRepository.findById(id);
        theOrder.ifPresent(order -> order.setStatus(status));
        return orderRepository.save(theOrder.get());
    }

    @Override
    @Transactional
    public Order insertOrder(CustomerRequest customerRequest) {
        Order theOrder = new Order();

        Customer customer = customerRepository.findById(customerRequest.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));

        theOrder.setDesignFile(customerRequest.getDesignFile());
        theOrder.setBudget(customerRequest.getBudget());
        theOrder.setOrderType("customize");
        theOrder.setDescription(customerRequest.getDescription());
        theOrder.setStatus(OrderStatus.wait_sale_staff);
        theOrder.setCustomer(customer);

        orderRepository.save(theOrder);
        return theOrder;
    }

    @Override
    public OrderStatus forwardQuotation(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));

        if (!OrderStatus.manager_approved.equals(order.getStatus())) {
            throw new IllegalStateException("Order status must be 'manager_approved' to forward quotation");
        }

        order.setStatus(OrderStatus.customer_accept);
        orderRepository.save(order);
        return order.getStatus();
    }

    @Override
    public String retrieveQuotationFromStaff(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));

        // Logic to retrieve quotation from staff goes here
   

        order.setStatus(OrderStatus.wait_manager);
        orderRepository.save(order);
        return order.getStatus().name();
    }

    @Override
    public Order acceptOrder(Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(OrderStatus.customer_accept);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }
}
