package com.client_ws.rasmooplus.configuration;

import com.client_ws.rasmooplus.repository.UserDetailsRepository;
import com.client_ws.rasmooplus.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private static final String[] AUTH_SWAGGER_LIST = {
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web ->
                web.ignoring()
                        .requestMatchers( AUTH_SWAGGER_LIST)
                        .requestMatchers(HttpMethod.GET, "/subscription-type")
                        .requestMatchers(HttpMethod.GET, "/subscription-type/*")
                        .requestMatchers(HttpMethod.POST, "/user")
                        .requestMatchers(HttpMethod.POST, "/payment/process")
                        .requestMatchers(HttpMethod.POST, "/auth")
                        .requestMatchers( "/auth/recovery-code/*");
    }


}

