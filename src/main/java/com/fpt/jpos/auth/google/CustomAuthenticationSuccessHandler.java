package com.fpt.jpos.auth.google;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.jpos.auth.AuthenticationResponse;
import com.fpt.jpos.auth.JwtService;
import com.fpt.jpos.dto.CustomerRegistrationDTO;
import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.service.ICustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final IAccountRepository accountRepository;
    private final GoogleCallbackConfig googleCallbackConfig;
    private final ICustomerService customerService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomOAuth2User authUser = (CustomOAuth2User) authentication.getPrincipal();

        String email = authUser.getEmail();
        String name = email.split("@")[0];
        String username = "GOOGLE_" + email.split("@")[0];
        String password = "GOOGLE_" + email;
        String method;

        Optional<Account> user = accountRepository.findAccountByUsername(username);
        String json;

        if (user.isPresent()) {
            Account account = user.get();
            if(account.getStatus()) {
                Customer customer = customerService.loginCustomer(account);
                String token = jwtTokenProvider.generateToken(account);
                AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                        .account(customer)
                        .token(token)
                        .build();
                method = "L";
                json = objectMapper.writeValueAsString(authenticationResponse);
            } else {
                method = "F";
                json = "";
            }
        } else {
            System.out.println("ACCOUNT FIRST TIME REGISTER");
            CustomerRegistrationDTO customerRegistrationDTO = CustomerRegistrationDTO.builder()
                    .name(name)
                    .address("*")
                    .username(username)
                    .email(email)
                    .password(password)
                    .build();
            method = "S";
            json = objectMapper.writeValueAsString(customerRegistrationDTO);

        }
        String encodedAuthResponse = Base64.getUrlEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        response.sendRedirect(googleCallbackConfig.getGoogleCallbackUrl() + method + encodedAuthResponse);
    }
}
