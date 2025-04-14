package com.client_ws.rasmooplus.service.impl;

import com.client_ws.rasmooplus.model.jpa.UserType;
import com.client_ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client_ws.rasmooplus.service.UserTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
