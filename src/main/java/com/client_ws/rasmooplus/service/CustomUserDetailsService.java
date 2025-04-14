package com.client_ws.rasmooplus.service;

import com.client_ws.rasmooplus.model.UserCredentials;

public interface CustomUserDetailsService {
    UserCredentials loadUserByUsernameAndPass(String username, String pass);
}
