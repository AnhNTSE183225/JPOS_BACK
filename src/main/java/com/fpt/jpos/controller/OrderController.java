package com.fpt.jpos.controller;

import com.fpt.jpos.dto.NoteDTO;
import com.fpt.jpos.dto.PaymentDTO;
import com.fpt.jpos.dto.ProductDesignDTO;
import com.fpt.jpos.dto.CustomerRequestDTO;
import com.fpt.jpos.pojo.Order;
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

    // Get all orders for customer
    @CrossOrigin
    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<?> getOrdersForCustomer(@PathVariable Integer customerId) {
        List<Order> requestList = orderService.getOrdersByCustomerId(customerId);
        if (requestList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(requestList);
        }
    }

    // Sale staff view orders list
    @CrossOrigin
    @GetMapping("/sales/orders/{staffId}")
    public ResponseEntity<?> getAllOrdersForSaleStaff(@PathVariable int staffId) {

        List<Order> requestList = orderService.getOrderForSalesStaff(staffId);

        if (requestList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(requestList);
        }
    }

    // Staff sends quotation to manager
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

    @CrossOrigin
    @GetMapping("/manager/orders")
    public ResponseEntity<?> getOrderForManager() {
        List<Order> orderList = orderService.getOrderForManager();
        if (orderList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(orderList);
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
    @CrossOrigin
    @PostMapping("/{id}/forward-quotation")
    public ResponseEntity<String> forwardQuotation(@PathVariable Integer id) {
        OrderStatus status = orderService.forwardQuotation(id);
        return ResponseEntity.ok(status.toString());
    }

    // Customer accept quotation
    @CrossOrigin
    @PutMapping("/accept-order")
    public ResponseEntity<?> acceptOrder(@RequestBody Order order) {
        try {
            return ResponseEntity.ok(orderService.acceptOrder(order));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
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

    // Update order status to designing after confirming deposit
    @CrossOrigin
    @PutMapping("/sales/orders/{id}/confirm-deposit")
    public ResponseEntity<?> confirmDeposit(@PathVariable int id, @RequestBody PaymentDTO payment) {
        Order order = orderService.updateOrderStatusDesigning(id, payment);
        if (order == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(order);

    }

    //  design staff view orders list
    @CrossOrigin
    @GetMapping("/designs/orders/{staffId}")
    public ResponseEntity<?> getAllOrdersForDesignStaff(@PathVariable int staffId) {
        List<Order> requestList = orderService.getOrderForDesignStaff(staffId);

        if (requestList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(requestList);
        }
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
    @CrossOrigin
    @PostMapping("/customers/{orderId}/acceptDesign")
    public ResponseEntity<?> acceptDesign(@PathVariable Integer orderId) {
        Order theOrder = orderService.updateOrderStatusProduction(orderId);
        if (theOrder == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(theOrder);
    }

    //Customer refuses design
    @CrossOrigin
    @PostMapping("/customers/{orderId}/refuseDesign")
    public ResponseEntity<?> refuseDesign(@PathVariable Integer orderId, @RequestBody NoteDTO noteDTO) {
        Order theOrder = orderService.updateOrderStatusDesigning(orderId, noteDTO);
        if (theOrder == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(theOrder);
    }

    //  production staff view orders list
    @CrossOrigin
    @GetMapping("/production/orders/{staffId}")
    public ResponseEntity<?> getAllOrdersForProductionStaff(@PathVariable int staffId) {
        List<Order> requestList = orderService.getOrderForProductionStaff(staffId);

        if (requestList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(requestList);
        }
    }

    @CrossOrigin
    @PostMapping("/{id}/complete-product")
    public ResponseEntity<?> completeProduct(@PathVariable Integer id, @RequestParam String imageUrl, @RequestParam Integer productionStaffId) {
        try {
            return ResponseEntity.ok(orderService.completeProduct(id, imageUrl, productionStaffId));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @CrossOrigin
    @PostMapping("/orders/{orderId}/complete")
    public ResponseEntity<Order> completeOrder(@RequestBody PaymentDTO paymentDTO, @PathVariable Integer orderId) {
        Order order = orderService.completeOrder(paymentDTO, orderId);
        return ResponseEntity.ok(order);

    }
//    @PostMapping("/add-product-design")
//    public ResponseEntity<Order> addProductDesignToOrder(@RequestBody ProductDesignDTO productDesignDTO) {
//        Order order = orderService.addProductDesignToOrder(productDesignDTO);
//        return ResponseEntity.ok(order);
//    }

}
