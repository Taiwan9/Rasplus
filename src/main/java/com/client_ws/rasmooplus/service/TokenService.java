package com.client_ws.rasmooplus.service;

public interface TokenService {

    String getToken(Long userId);

    Boolean isValid(String token);

    Long getUserId(String token);

}
