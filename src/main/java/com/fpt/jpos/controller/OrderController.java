package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.CustomerRequest;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.service.IFileUploadService;
import com.fpt.jpos.service.IOrderService;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private IOrderService orderService;

    private IFileUploadService fileUploadService;

    @Autowired
    public OrderController(IOrderService orderService, IFileUploadService fileUploadService) {
        this.orderService = orderService;
        this.fileUploadService = fileUploadService;
    }

    @CrossOrigin
    @PostMapping("/send-request")
    public ResponseEntity<Order> saveCustomerRequest(@RequestBody CustomerRequest customerRequest) {

        System.out.println(customerRequest.getCustomerId());

        Order newOrder = orderService.insertOrder(customerRequest);

        return ResponseEntity.ok(newOrder);
    }

    @CrossOrigin
    @PostMapping("/{id}/manager-response")
    public ResponseEntity<String> getManagerResponse(@PathVariable Integer id, @RequestParam boolean managerApproval) {
        String status = orderService.handleManagerResponse(id, managerApproval);
        return ResponseEntity.ok(status);
    }

    @CrossOrigin
    @GetMapping("/sales/orders/{id}")
    public ResponseEntity<?> getAllOrdersForSaleStaff(@PathVariable int id) {

        List<Order> requestList = orderService.getOrdersByStatusAndStaffs(id);

        if (requestList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No requests found");
        } else {
            return ResponseEntity.ok(requestList);
        }
    }

    @CrossOrigin
    @GetMapping("/sales/order-select/{id}")
    public ResponseEntity<?> findOrderById(@PathVariable int id) {
        Order order = orderService.findById(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        } else {
            return ResponseEntity.ok(order);
        }
    }


    // Upload file by design staff
    @CrossOrigin
    @PostMapping("/designs/upload/{id}")
    @Transactional
    public ResponseEntity<?> uploadDesign(@RequestParam("file") MultipartFile file, @PathVariable Integer id) throws IOException {

        String imageURL = fileUploadService.uploadFile(file, id);

        if (imageURL == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image is not found");
        } else {
            return ResponseEntity.ok(imageURL);
        }
    }
}
