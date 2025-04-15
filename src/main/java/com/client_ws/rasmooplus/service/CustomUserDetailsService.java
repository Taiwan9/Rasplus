package com.client_ws.rasmooplus.service;

import com.client_ws.rasmooplus.model.jpa.UserCredentials;

public interface CustomUserDetailsService {
    UserCredentials loadUserByUsernameAndPass(String username, String pass);
    void sendRecoveryCode(String email);
   // boolean recoveryCodeIsValid(String recoveryCode, String email);
}
