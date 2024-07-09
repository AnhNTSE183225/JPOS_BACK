package com.fpt.jpos.controller;

import com.fpt.jpos.service.ILoginGoogleService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login/google")
public class LoginGoogleController {

    @Autowired
    private ILoginGoogleService loginGoogleService;

    @GetMapping("/LoginAccount")
    public String loginWithGoogle() {
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/success")
    @ResponseBody
    public Map<String, Object> handleGoogleLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            // Giả lập dữ liệu tài khoản cho ví dụ này.
            Map<String, Object> account = new HashMap<>();
            Map<String, Object> accountDetails = new HashMap<>();
            accountDetails.put("username", attributes.get("sub"));
            accountDetails.put("email", attributes.get("email"));
            account.put("account", accountDetails);
            account.put("name", attributes.get("name"));

            response.put("account", account);
        }

        return response;
    }
}

