package com.client_ws.rasmooplus.service.impl;

import com.client_ws.rasmooplus.exception.NotFoundException;
import com.client_ws.rasmooplus.model.UserCredentials;
import com.client_ws.rasmooplus.repository.UserDetailsRepository;
import com.client_ws.rasmooplus.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserCredentials loadUserByUsernameAndPass(String username, String pass) {
        var userDetailsOpt = userDetailsRepository.findByUsername(username);

        if(userDetailsOpt.isPresent()){
            return userDetailsOpt.get();
        }
        throw new NotFoundException("Usuario não encontrado");
    }
}
