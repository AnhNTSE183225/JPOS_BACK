package com.fpt.jpos.service;

import java.util.Optional;

import com.fpt.jpos.controller.CustomerRequest;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.repository.ICustomerRequestRepository;
import com.fpt.jpos.repository.IOrderRepository;

import jakarta.transaction.Transactional;

//public class CustomerRequestService implements ICustomerRequestService {
	public class CustomerRequestService {

    private IOrderRepository orderRepository;
	
	private ICustomerRequestRepository customerRequestRepository;
	
	public CustomerRequestService(IOrderRepository theOrderRepository, ICustomerRequestRepository theCustomerRequestRepository) {
		orderRepository = theOrderRepository;
		customerRequestRepository = theCustomerRequestRepository;
	}

//	@Override
//	@Transactional
//	public Order handleRequest(int customerId) {
//		Order customer = customerRequestRepository.findById((int) customerId)
//                .orElseThrow(() -> new RuntimeException("Customer not found"));
//
//        Optional<Order> existingOrder = orderRepository.findByCustomerAndStatus(customer, "wait_sale_staff");
//
//        if (existingOrder.isPresent()) {
//            return existingOrder.get();
//        } else {
//        	return null;
//        }
//	}
}
