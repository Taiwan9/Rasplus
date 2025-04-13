package com.client_ws.rasmooplus.service.impl;

import com.client_ws.rasmooplus.dto.LoginDto;
import com.client_ws.rasmooplus.dto.TokenDto;
import com.client_ws.rasmooplus.model.UserCredentials;
import com.client_ws.rasmooplus.service.AuthenticationService;
import com.client_ws.rasmooplus.service.TokenService;
import com.client_ws.rasmooplus.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenService tokenService;

    @Override
    public TokenDto auth(LoginDto dto) {
        try {
            UserCredentials userCredentials = userDetailsService.loadUserByUsernameAndPass(dto.getUsername(), dto.getPassword());
            String token = tokenService.getToken(userCredentials.getId());
            return TokenDto.builder().token(token).type("Bearer").build();
        } catch (Exception e) {
            throw new BadRequestException("Erro ao formatar token - "+e.getMessage());
        }
    }
}
