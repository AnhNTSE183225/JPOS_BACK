package com.fpt.jpos.auth.google;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.jpos.auth.AuthenticationResponse;
import com.fpt.jpos.auth.JwtService;
import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.pojo.enums.Provider;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.service.ICustomerService;
import jakarta.servlet.http.Cookie;
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
        String email = authUser.getEmail();
        System.out.println(email);
        Account user = accountRepository.findOneByEmail(email);
        AuthenticationResponse authenticationResponse = null;

        if (user != null && user.getProvider().equals(Provider.GOOGLE) && user.getStatus()) {
            Customer customer = customerService.loginCustomer(user);
            String token = jwtTokenProvider.generateToken(user);
            authenticationResponse = new AuthenticationResponse(customer, token);

        } else if (user == null) {
            user = userService.processOAuthPostLogin(email);
            String jwtToken = jwtTokenProvider.generateToken(user);
            Customer customer = customerService.createNewCustomer(user, authUser.getName(), "*");
            authenticationResponse = AuthenticationResponse.builder()
                    .account(customer)
                    .token(jwtToken)
                    .build();
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), authenticationResponse);
    }
}
