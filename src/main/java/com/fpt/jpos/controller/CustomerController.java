package com.fpt.jpos.controller;

import com.fpt.jpos.dto.CustomerRegistrationDTO;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @CrossOrigin
    @PostMapping("/customer-register")
    public ResponseEntity<Customer> customerRegister(@RequestBody CustomerRegistrationDTO customerRegistrationDTO) {
        Customer newCustomer = customerService.registerCustomer(customerRegistrationDTO);
        if(newCustomer != null) {
            return ResponseEntity.ok(newCustomer);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @CrossOrigin
    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestParam Integer customerId, @RequestParam String email, @RequestParam String name, @RequestParam String address) {
        ResponseEntity<?> responseEntity = ResponseEntity.noContent().build();

        try {
            responseEntity = ResponseEntity.ok(customerService.updateCustomer(customerId, name, email, address));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return responseEntity;
    }
}
