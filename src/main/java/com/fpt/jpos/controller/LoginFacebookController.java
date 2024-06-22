package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.dto.CustomerRegistrationDTO;
import com.fpt.jpos.service.ILoginFacebookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;



@RestController
@RequestMapping("/api")
public class LoginFacebookController {
    private final ILoginFacebookService loginFacebookService;

    @Autowired
    public LoginFacebookController(ILoginFacebookService loginFacebookService) {
        this.loginFacebookService = loginFacebookService;
    }

    @CrossOrigin
    @GetMapping("/get-facebook-account")
    public Map<String, Object> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
    }

    @CrossOrigin
    @PostMapping("/customer-facebook-login")
    public ResponseEntity<Customer> login(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        Account account = new Account();
        account.setUsername((String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("id"));
        account.setPassword("no");
        Customer customer = loginFacebookService.loginFacebookCustomer(account);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(customer);
        }
    }

    @CrossOrigin
    @PostMapping("/customer-facebook-register")
    public ResponseEntity<Customer> customerRegister(@RequestBody CustomerRegistrationDTO customerRegistrationDTO) {
        Customer newCustomer = loginFacebookService.registerCustomer(customerRegistrationDTO);
        if(newCustomer != null) {
            return ResponseEntity.ok(newCustomer);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}