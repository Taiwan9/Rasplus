package com.client_ws.rasmooplus.service.impl;

import com.client_ws.rasmooplus.model.UserType;
import com.client_ws.rasmooplus.repository.UserTypeRepository;
import com.client_ws.rasmooplus.service.UserTypeService;

import java.util.List;

public class UserTypeServiceImpl implements UserTypeService {
    private final UserTypeRepository userTypeRepository;

    UserTypeServiceImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public List<UserType> findAll() {
        return userTypeRepository.findAll();
    }
}
