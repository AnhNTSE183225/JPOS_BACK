package com.fpt.jpos.auth.google;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.jpos.auth.AuthenticationResponse;
import com.fpt.jpos.auth.JwtService;
import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.service.ICustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final IAccountRepository accountRepository;
    private final ICustomerService customerService;
    private final UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomOAuth2User authUser = (CustomOAuth2User) authentication.getPrincipal();
        String username = authUser.getEmail();
        System.out.println(username);
        Account user = accountRepository.findOneByEmail(username);
        Customer customer = customerService.loginCustomer(user);


        String token = jwtTokenProvider.generateToken(user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(customer, token);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        //userService.processOAuthPostLogin(authentication.getName());

        objectMapper.writeValue(response.getWriter(), authenticationResponse);
    }
}
