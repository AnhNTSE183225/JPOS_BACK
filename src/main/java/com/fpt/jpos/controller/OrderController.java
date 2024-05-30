package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.CustomerRequest;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.service.IOrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/{id}/manager-response")
    public ResponseEntity<String> getManagerResponse(@PathVariable int id, @RequestParam boolean managerApproval) {
        String status = orderService.handleManagerResponse(id, managerApproval);
        return ResponseEntity.ok(status);
    }


    // get staff id
    @GetMapping("/sales/orders/{id}")
    public ResponseEntity<List<Order>> getSalesOrders(@PathVariable int id) {

        List<Order> orders = orderService.getOrdersForSaleStaffs(id);

        return ResponseEntity.ok(orders);

    }
}