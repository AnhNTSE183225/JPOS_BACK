package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.pojo.CustomerRequest;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.repository.ICustomerRepository;
import com.fpt.jpos.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService implements IOrderService {

    private IOrderRepository orderRepository;

    private ICustomerRepository customerRepository;


    @Autowired
    public OrderService(IOrderRepository theIOrderRepository, ICustomerRepository theICustomerRepository) {
        orderRepository = theIOrderRepository;
        customerRepository = theICustomerRepository;
    }


//    @Override
//    public Order insertOrder(Order theOrder, int customerId) {
//        Order order = new Order();
//
//        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
//
//        order.setCustomer(customer);
//
//        return orderRepository.save(order);
//    }


    @Override
    @Transactional
    public Order insertOrder(CustomerRequest customerRequest) {
        Order theOrder = new Order();

        Customer customer = customerRepository.findById(customerRequest.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));

        theOrder.setDesignFile(customerRequest.getDesignFile());
        theOrder.setBudget(customerRequest.getBudget());
        theOrder.setOrderType("customize");
        theOrder.setDescription(customerRequest.getDescription());
        theOrder.setStatus("wait_sale_staff");
        theOrder.setCustomer(customer);

        orderRepository.save(theOrder);
        return theOrder;
    }
}
