package com.fpt.jpos.auth;

import com.fpt.jpos.dto.CustomerRegistrationDTO;
import com.fpt.jpos.exception.AccountAlreadyExistsException;
import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.pojo.enums.Role;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.repository.ICustomerRepository;
import com.fpt.jpos.service.CustomerService;
import com.fpt.jpos.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IAccountRepository accountRepository;
    private final ICustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StaffService staffService;
    private final CustomerService customerService;

    public AuthenticationResponse register(CustomerRegistrationDTO request) throws AccountAlreadyExistsException {
        var user = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .status(true)
                .role(Role.customer)
                .build();
        if (accountRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new AccountAlreadyExistsException();
        }
        user = accountRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        Customer customer = new Customer();
        customer.setAccount(user);
        customer.setName(request.getName());
        customer.setAddress(request.getAddress());
        customer = customerRepository.save(customer);
        return AuthenticationResponse.builder()
                .account(customer)
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = accountRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        Object authenticatedUser = null;
        if (user.getRole() == Role.customer) {
            authenticatedUser = customerService.loginCustomer(user);
        } else if (user.getRole() == Role.staff) {
            authenticatedUser = staffService.getStaffByAccount(user);
        }
        return AuthenticationResponse.builder()
                .account(authenticatedUser)
                .token(jwtToken)
                .build();
    }


}
