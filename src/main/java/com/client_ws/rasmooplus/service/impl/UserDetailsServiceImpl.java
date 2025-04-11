package com.client_ws.rasmooplus.service.impl;

import com.client_ws.rasmooplus.exception.NotFoundException;
import com.client_ws.rasmooplus.repository.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserDetailsRepository userDetailsRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDetailsOpt = userDetailsRepository.findByUsername(username);

        if(userDetailsOpt.isPresent()){
            return userDetailsOpt.get();
        }
        throw new NotFoundException("Usuario não encontrado");
    }
}
