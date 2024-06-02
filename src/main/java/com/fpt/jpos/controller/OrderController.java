package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.CustomerRequestDTO;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.pojo.Payment;
import com.fpt.jpos.pojo.enums.OrderStatus;
import com.fpt.jpos.service.IFileUploadService;
import com.fpt.jpos.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final IOrderService orderService;

    private final IFileUploadService fileUploadService;


    @Autowired
    public OrderController(IOrderService orderService, IFileUploadService fileUploadService) {
        this.orderService = orderService;
        this.fileUploadService = fileUploadService;
    }

    //Test - get all Orders
    @CrossOrigin
    @GetMapping("/order/all")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    //User uploads image
    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file) {
        try {
            return ResponseEntity.ok(fileUploadService.upload(file));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    // Customer send request - 1st flow
    @CrossOrigin
    @PostMapping("/send-request")
    public ResponseEntity<Order> saveCustomerRequest(@RequestBody CustomerRequestDTO customerRequestDTO) {

        Order newOrder = orderService.insertOrder(customerRequestDTO);

        return ResponseEntity.ok(newOrder);
    }

    // Sale staff view orders list
    @CrossOrigin
    @GetMapping("/sales/orders/{staffId}")
    public ResponseEntity<?> getAllOrdersForSaleStaff(@PathVariable int staffId) {

        List<Order> requestList = orderService.getOrdersByStatusAndStaffs(staffId);

        if (requestList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(requestList);
        }
    }

    // Manager get quotation from staff
    @CrossOrigin
    @PostMapping("/sales/orders/{staffId}/{productId}")
    public ResponseEntity<?> retrieveQuotationFromStaff(@RequestBody Order order, @PathVariable Integer productId, @PathVariable int staffId) {
        try {
            Order quotedOrder = orderService.retrieveQuotationFromStaff(order, productId, staffId);
            return ResponseEntity.ok(quotedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build();
        }
    }

    // Manager accept or decline quotation
    @CrossOrigin
    @PostMapping("/{id}/manager-response")
    public ResponseEntity<String> getManagerResponse(@PathVariable Integer id, @RequestParam boolean managerApproval) {
        String status = orderService.handleManagerResponse(id, managerApproval);
        return ResponseEntity.ok(status);
    }

    // After manager accepted, sale staff forward quotation to customer
    @PostMapping("/{id}/forward-quotation")
    public ResponseEntity<String> forwardQuotation(@PathVariable Integer id) {
        OrderStatus status = orderService.forwardQuotation(id);
        return ResponseEntity.ok(status.toString());
    }

    // Customer accept quotation
    @PutMapping("/{id}/accept")
    public Order acceptOrder(@PathVariable Integer id) {
        return orderService.acceptOrder(id);
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

    // Update order status to designing after confirming deposit
    @PutMapping("/sales/orders/{id}/confirm-deposit")
    public ResponseEntity<?> confirmDeposit(@PathVariable int id, @RequestBody Payment payment) {
        Order order = orderService.updateOrderStatusDesigning(id, payment);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
        return ResponseEntity.ok(order);

    }

    // Upload file by design staff
    @CrossOrigin
    @PostMapping("/designs/upload/{staffId}/{orderId}")
    public ResponseEntity<?> uploadDesign(@RequestParam("file") MultipartFile file, @PathVariable Integer orderId, @PathVariable Integer staffId) throws IOException {

        String imageURL = fileUploadService.uploadModelDesignFile(file, orderId, staffId);

        if (imageURL == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image is not found");
        } else {
            return ResponseEntity.ok(imageURL);
        }
    }

    // Customer accept design
    @PostMapping("/customers/{orderId}/acceptDesign")
    public ResponseEntity<?> acceptDesign(@PathVariable Integer orderId) {
        Order theOrder = orderService.updateOrderStatusProduction(orderId);
        if (theOrder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
        return ResponseEntity.ok(theOrder);
    }
    @PostMapping("/{id}/complete-product")
    public ResponseEntity<Order> completeProduct(@PathVariable Integer id, @RequestParam String imageUrl, @RequestParam Integer productionStaffId) {
        Order order = orderService.completeProduct(id, imageUrl, productionStaffId);
        return ResponseEntity.ok(order);
    }
}
