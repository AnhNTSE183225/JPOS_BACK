package com.fpt.jpos.auth.google;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/oauth2/authorization/google/")
public class LoginController {


    private final CustomOAuth2UserService customOAuth2UserService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PutMapping("/register")
    public ResponseEntity<?> finishRegister(@RequestBody GoogleRegistrationDTO registration) {

        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(customOAuth2UserService.finishGoogleRegister(registration));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return response;
    }

}
