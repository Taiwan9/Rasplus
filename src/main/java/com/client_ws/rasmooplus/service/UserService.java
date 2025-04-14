package com.client_ws.rasmooplus.service;

import com.client_ws.rasmooplus.dto.UserDto;
import com.client_ws.rasmooplus.model.jpa.User;

public interface UserService {

    User create(UserDto dto);

    Object sendRecoveryCode(String email);
}
