package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.CustomerRequest;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    private IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    // tạm thời chưa có chức năng login nên để customerId lên trên url nhé, sau này sửa sau. ?
    @CrossOrigin
    @PostMapping("/send-request")
    public ResponseEntity<Order> saveCustomerRequest(@RequestBody CustomerRequest customerRequest) {

        System.out.println(customerRequest.getCustomerId());

        Order newOrder = orderService.insertOrder(customerRequest);

        return ResponseEntity.ok(newOrder);
    }
}
