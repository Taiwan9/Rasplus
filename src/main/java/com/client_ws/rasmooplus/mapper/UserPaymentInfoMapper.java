package com.client_ws.rasmooplus.mapper;

import com.client_ws.rasmooplus.dto.UserPaymentInfoDto;
import com.client_ws.rasmooplus.model.jpa.User;
import com.client_ws.rasmooplus.model.jpa.UserPaymentInfo;

public class UserPaymentInfoMapper {

    public static UserPaymentInfo fromDtoToEntity(UserPaymentInfoDto dto, User user){
        return UserPaymentInfo.builder()
                .id(dto.getId())
                .cardNumber(dto.getCardNumber())
                .cardExpirationMonth(dto.getCardExpirationMonth())
                .cardExpirationYear(dto.getCardExpirationYear())
                .cardSecurityCode(dto.getCardSecurityCode())
                .price(dto.getPrice())
                .dtPayment(dto.getDtPayment())
                .user(user)
                .installments(dto.getInstallments())
                .build();
    }
}
