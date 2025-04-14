package com.client_ws.rasmooplus.service;

import com.client_ws.rasmooplus.model.jpa.UserType;

import java.util.List;

public interface UserTypeService {
    List<UserType> findAll();
}
