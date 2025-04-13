package com.client_ws.rasmooplus.service;

import com.client_ws.rasmooplus.dto.LoginDto;
import com.client_ws.rasmooplus.dto.TokenDto;

public interface AuthenticationService {
    TokenDto auth(LoginDto dto);
}
