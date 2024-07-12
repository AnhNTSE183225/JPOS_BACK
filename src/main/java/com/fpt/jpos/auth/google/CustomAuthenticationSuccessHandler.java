package com.fpt.jpos.auth.google;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.jpos.auth.AuthenticationRequest;
import com.fpt.jpos.dto.CustomerRegistrationDTO;
import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.enums.Provider;
import com.fpt.jpos.repository.IAccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    //private final JwtService jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final IAccountRepository accountRepository;
    //private final ICustomerService customerService;
    private final GoogleCallbackConfig googleCallbackConfig;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomOAuth2User authUser = (CustomOAuth2User) authentication.getPrincipal();

        String email = authUser.getEmail();
        String name = email.split("@")[0];
        String username = "GOOGLE_" + email.split("@")[0];
        String password = "GOOGLE_" + email;
        String method;

        Account user = accountRepository.findOneByEmail(email);
        String json;

        if (user != null && user.getProvider().equals(Provider.GOOGLE) && user.getStatus()) {

            AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build();
            method = "L";
            json = objectMapper.writeValueAsString(authenticationRequest);


        } else {

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


//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setContentType("application/json");
//        objectMapper.writeValue(response.getWriter(), authenticationResponse);
    }
}
