package com.fpt.jpos.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.jpos.auth.JwtService;
import com.fpt.jpos.auth.google.CustomAuthenticationSuccessHandler;
import com.fpt.jpos.auth.google.CustomOAuth2UserService;
import com.fpt.jpos.auth.google.GoogleCallbackConfig;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableWebMvc
@EnableAutoConfiguration
// Binh
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final GoogleCallbackConfig googleCallbackConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/public/**", "/api/v1/auth/**", "/", "/oauth/**", "/oauth2/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sessionManagementCustomizer ->
                        sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oauthUserService))
                        .successHandler(new CustomAuthenticationSuccessHandler(jwtTokenProvider, objectMapper, accountRepository, googleCallbackConfig, customerService)));
        return http.build();
    }

    private final CustomOAuth2UserService oauthUserService;
    private final JwtService jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final IAccountRepository accountRepository;
    private final ICustomerService customerService;
}




