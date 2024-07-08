package com.fpt.jpos.controller;

import com.fpt.jpos.service.ILoginGoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login/google")
public class LoginGoogleController {

    @Autowired
    private ILoginGoogleService loginGoogleService;

    @GetMapping
    public String loginWithGoogle() {
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/success")
    public String handleGoogleLogin() {
        // Lấy thông tin người dùng từ Authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

            // In thông tin người dùng
            System.out.println("User's Name: " + oAuth2User.getAttribute("name"));
            System.out.println("User's Email: " + oAuth2User.getAttribute("email"));
            System.out.println("User's Profile Picture: " + oAuth2User.getAttribute("picture"));
        }

        // Chuyển hướng đến trang chủ hoặc trang khác tùy ý
        return "redirect:/";
    }
}