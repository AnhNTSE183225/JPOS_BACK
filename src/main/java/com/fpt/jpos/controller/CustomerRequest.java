package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.service.ICustomerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer-requests")
public class CustomerRequest {

    @Autowired
    private ICustomerRequestService customerRequestService;

    @PostMapping("/handle-request")
    public Order handleCustomerRequest(@RequestParam int customerId) {
        return customerRequestService.handleRequest(customerId);
    }
}
