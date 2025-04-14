package com.client_ws.rasmooplus.mapper;

import com.client_ws.rasmooplus.dto.UserDto;
import com.client_ws.rasmooplus.model.jpa.SubscriptionType;
import com.client_ws.rasmooplus.model.jpa.User;
import com.client_ws.rasmooplus.model.jpa.UserType;

public class UserMapper {
    public static User fromDtoToEntity(UserDto dto, UserType userType, SubscriptionType subscriptionType){
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .dtSubscription(dto.getDtSubscription())
                .dtExpiration(dto.getDtExpiration())
                .userType(userType)
                .subscriptionType(subscriptionType)
                .build();
    }
}
