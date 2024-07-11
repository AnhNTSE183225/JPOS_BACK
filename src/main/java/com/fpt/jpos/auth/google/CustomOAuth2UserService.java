package com.fpt.jpos.auth.google;

import com.fpt.jpos.auth.AuthenticationResponse;
import com.fpt.jpos.auth.JwtService;
import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final CustomerService customerService;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        return new CustomOAuth2User(user);
    }


    public AuthenticationResponse finishGoogleRegister(GoogleRegistrationDTO googleRegistrationDTO) {

        Account account = userService.getAccount(googleRegistrationDTO.getEmail());
        Customer customer = customerService.loginCustomer(account);
        customer.setAddress(googleRegistrationDTO.getAddress());
        account.setUsername(googleRegistrationDTO.getUsername());
        userService.saveAccount(account);
        customerService.updateCustomer(customer);

        String token = jwtService.generateToken(account);
        return AuthenticationResponse.builder()
                .token(token)
                .account(customer)
                .build();

    }


}
