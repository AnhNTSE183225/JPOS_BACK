package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.repository.ICustomerRepository;
import com.fpt.jpos.service.ICustomerService;
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
    @PostMapping("/orders")
    public ResponseEntity<Order> saveCustomerRequest(@RequestBody Order theOrder, @RequestParam(name = "id") int customerId) {

        // set id = 0 -> add new
        theOrder.setId(0);

        Order newOrder = orderService.insertOrder(
                customerId,
                theOrder.getDesignFile(),
                theOrder.getBudget(),
                theOrder.getDescription()
        );


        return ResponseEntity.ok(newOrder);
    }
}
