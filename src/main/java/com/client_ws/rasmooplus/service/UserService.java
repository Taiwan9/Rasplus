package com.client_ws.rasmooplus.service;

import com.client_ws.rasmooplus.dto.UserDto;
import com.client_ws.rasmooplus.model.User;

public interface UserService {

    User create(UserDto dto);
}
