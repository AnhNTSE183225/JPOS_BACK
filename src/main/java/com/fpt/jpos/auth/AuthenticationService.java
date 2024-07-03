package com.fpt.jpos.auth;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.enums.Role;
import com.fpt.jpos.repository.IAccountRepository;
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

    private final IAccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StaffService staffService;
    private final CustomerService customerService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .status(true)
                .role(Role.customer)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        Object authenticatedUser = null;
        if (user.getRole() == Role.customer) {
            authenticatedUser = customerService.loginCustomer(user);
        } else if (user.getRole() == Role.staff) {
            authenticatedUser = staffService.getStaffByAccount(user);
        }
//         else if (user.getRole() == Role.admin) {
//            authenticatedUser = staffService.getStaffByAccount(user);
//        }

        return AuthenticationResponse.builder()
                .account(authenticatedUser)
                .token(jwtToken)
                .build();
    }


}
