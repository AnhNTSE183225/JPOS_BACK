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
            order.setStatus(OrderStatus.manager_approved.name());
        } else {
            order.setStatus(OrderStatus.wait_manager.name());
        }

        orderRepository.save(order);
        return order.getStatus();
    }

    @Override
    public List<Order> getOrdersByStatusAndStaffs(int id) {
        return orderRepository.findAllByStatusAndStaff(id);
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
        theOrder.setStatus(OrderStatus.wait_sale_staff.name());
        theOrder.setCustomer(customer);

        orderRepository.save(theOrder);
        return theOrder;
    }

}
