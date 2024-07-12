package com.fpt.jpos.controller;

import com.fpt.jpos.dto.*;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.pojo.enums.OrderStatus;
import com.fpt.jpos.service.IFileUploadService;
import com.fpt.jpos.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@CrossOrigin
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
    @GetMapping("/order/all")
    @PreAuthorize("hasAnyAuthority('staff','admin')")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }


    //User uploads image
    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('customer','admin', 'staff')")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file) {
        try {
            return ResponseEntity.ok(fileUploadService.upload(file));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            return ResponseEntity.noContent().build();
        }
    }

    // Customer send request - 1st flow
    @PostMapping("/send-request")
    @PreAuthorize("hasAnyAuthority('customer','admin', 'staff')")
    public ResponseEntity<Order> saveCustomerRequest(@RequestBody CustomerRequestDTO customerRequestDTO) {

        Order newOrder = orderService.insertOrder(customerRequestDTO);

        return ResponseEntity.ok(newOrder);
    }

    // Get all orders for customer
    @GetMapping("/customers/{customerId}/orders")
    @PreAuthorize("hasAnyAuthority('customer','admin', 'staff')")
    public ResponseEntity<?> getOrdersForCustomer(@PathVariable Integer customerId) {
        List<Order> requestList = orderService.getOrdersByCustomerId(customerId);
        if (requestList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(requestList);
        }
    }

    // Sale staff view orders list
    @GetMapping("/sales/orders/{staffId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('staff')")
    public ResponseEntity<?> getAllOrdersForSaleStaff(@PathVariable int staffId) {

        List<Order> requestList = orderService.getOrderForSalesStaff(staffId);

        if (requestList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(requestList);
        }
    }

    // Staff sends quotation to manager
    @PreAuthorize("hasAuthority('admin') or hasAuthority('staff')")
    @PostMapping("/sales/orders/{staffId}/{productId}")
    public ResponseEntity<?> retrieveQuotationFromStaff(@RequestBody Order order, @PathVariable Integer productId, @PathVariable int staffId) {
        try {
            Order quotedOrder = orderService.retrieveQuotationFromStaff(order, productId, staffId);
            return ResponseEntity.ok(quotedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PreAuthorize("hasAuthority('admin') or hasAuthority('staff')")
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
    @PreAuthorize("hasAuthority('admin') or hasAuthority('staff')")
    @PostMapping("/{id}/manager-response")
    public ResponseEntity<String> getManagerResponse(@PathVariable Integer id, @RequestParam boolean managerApproval, @RequestBody ManagerResponseDTO managerResponseDTO) {
        String status = orderService.handleManagerResponse(id, managerApproval, managerResponseDTO);
        return ResponseEntity.ok(status);
    }

    // After manager accepted, sale staff forward quotation to customer\
    @PreAuthorize("hasAuthority('admin') or hasAuthority('staff')")
    @PostMapping("/{id}/forward-quotation")
    public ResponseEntity<String> forwardQuotation(@PathVariable Integer id) {
        OrderStatus status = orderService.forwardQuotation(id);
        return ResponseEntity.ok(status.toString());
    }

    // Customer accept quotation
    @PreAuthorize("hasAnyAuthority('customer','admin', 'staff')")
    @PutMapping("/accept-quotation")
    public ResponseEntity<?> acceptQuotation(@RequestParam Integer orderId) {
        try {
            return ResponseEntity.ok(orderService.acceptQuotation(orderId));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/sales/order-select/{id}")
    @PreAuthorize("hasAnyAuthority('customer','admin', 'staff')")
    public ResponseEntity<?> findOrderById(@PathVariable int id) {
        Order order = orderService.findById(id);
        if (order == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(order);
        }
    }

    // Update order status to designing after confirming deposit
    @PutMapping("/sales/orders/{id}/confirm-deposit")
    @PreAuthorize("hasAuthority('customer') or hasAuthority('admin') or hasAuthority('staff')")
    public ResponseEntity<?> confirmDeposit(@PathVariable int id, @RequestBody PaymentRestDTO.PaymentRequest payment) {
        Order order = orderService.updateOrderStatusDesigning(id, payment);
        if (order == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(order);

    }

    //  design staff view orders list
    @GetMapping("/designs/orders/{staffId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('staff')")
    public ResponseEntity<?> getAllOrdersForDesignStaff(@PathVariable int staffId) {
        List<Order> requestList = orderService.getOrderForDesignStaff(staffId);

        if (requestList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(requestList);
        }
    }


    // Upload file by design staff
    @PostMapping("/designs/upload/{orderId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('staff')")
    public ResponseEntity<?> uploadDesign(@RequestBody String imageUrls, @PathVariable Integer orderId) {
        ResponseEntity<?> responseEntity = ResponseEntity.noContent().build();

        String decodedUrls = URLDecoder.decode(imageUrls, StandardCharsets.UTF_8);

        if (decodedUrls.endsWith("=")) {
            decodedUrls = decodedUrls.substring(0, decodedUrls.length() - 1);
        }

        try {
            responseEntity = ResponseEntity.ok(orderService.addImage(decodedUrls, orderId));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return responseEntity;
    }

    // Customer accept design
    @PreAuthorize("hasAuthority('customer') or hasAuthority('admin') or hasAuthority('staff')")
    @PostMapping("/customers/{orderId}/acceptDesign")
    public ResponseEntity<?> acceptDesign(@PathVariable Integer orderId) {
        Order theOrder = orderService.updateOrderStatusProduction(orderId);
        if (theOrder == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(theOrder);
    }

    //Customer refuses design
    @PreAuthorize("hasAnyAuthority('admin','customer','staff')")
    @PostMapping("/customers/{orderId}/refuseDesign")
    public ResponseEntity<?> refuseDesign(@PathVariable Integer orderId, @RequestBody NoteDTO noteDTO) {
        Order theOrder = orderService.updateOrderStatusDesigning(orderId, noteDTO);
        if (theOrder == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(theOrder);
    }

    //  production staff view orders list
    @PreAuthorize("hasAuthority('admin') or hasAuthority('staff')")
    @GetMapping("/production/orders/{staffId}")
    public ResponseEntity<?> getAllOrdersForProductionStaff(@PathVariable int staffId) {
        List<Order> requestList = orderService.getOrderForProductionStaff(staffId);

        if (requestList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(requestList);
        }
    }

    @PreAuthorize("hasAuthority('admin') or hasAuthority('staff')")
    @PostMapping("/{id}/complete-product")
    public ResponseEntity<?> completeProduct(@PathVariable Integer id, @RequestBody String imageUrls) {
        try {
            String decodedUrls = URLDecoder.decode(imageUrls, StandardCharsets.UTF_8);

            if (decodedUrls.endsWith("=")) {
                decodedUrls = decodedUrls.substring(0, decodedUrls.length() - 1);
            }

            return ResponseEntity.ok(orderService.completeProduct(id, decodedUrls));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @PreAuthorize("hasAuthority('admin') or hasAuthority('staff')")
    @PostMapping("/orders/{orderId}/complete")
    public ResponseEntity<Order> completeOrder(@PathVariable Integer orderId) {
        Order order = orderService.completeOrder(orderId);
        return ResponseEntity.ok(order);

    }

    @PreAuthorize("hasAuthority('customer') or hasAuthority('admin') or hasAuthority('staff')")
    @PostMapping("/create-order-from-design")
    public ResponseEntity<?> createOrderFromDesign(@RequestBody ProductDesignDTO productDesignDTO) {
        try {
            return ResponseEntity.ok(orderService.createOrderFromDesign(productDesignDTO).getId());
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('customer','admin','staff')")
    @PostMapping("/cancel-order/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Integer orderId) {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(orderService.cancelOrder(orderId));
        } catch (Exception ex) {
            response = ResponseEntity.status(400).build();
        }

        return response;
    }

    @PreAuthorize("hasAuthority('admin') or hasAuthority('staff')")
    @PostMapping("/assign")
    public ResponseEntity<?> assign(@RequestParam int orderId,
                                    @RequestParam(required = false) Integer saleStaffId,
                                    @RequestParam(required = false) Integer designStaffId,
                                    @RequestParam(required = false) Integer productionStaffId) {
        try {
            return ResponseEntity.ok(orderService.assign(orderId, saleStaffId, designStaffId, productionStaffId));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            return ResponseEntity.noContent().build();
        }
    }
}
