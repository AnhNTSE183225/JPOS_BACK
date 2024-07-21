package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CustomerController {
    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer/get-all")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getAllCustomers() {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(customerService.findAll());
        } catch (Exception e) {
            response = ResponseEntity.status(400).build();
        }

        return response;
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('customer','admin','staff')")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        ResponseEntity<?> responseEntity;

        try {
            responseEntity = ResponseEntity.ok(customerService.updateCustomer(customer));
        } catch (Exception ex) {
            ex.printStackTrace();
            responseEntity = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        return responseEntity;
    }

    @DeleteMapping("/customer/delete/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        ResponseEntity<?> response;

        try {
            customerService.delete(id);
            response = ResponseEntity.ok().build();
        } catch (Exception ex) {
            response = ResponseEntity.status(409).build();
        }

        return response;
    }
}
