package com.client_ws.rasmooplus.mapper.wsraspay;

import com.client_ws.rasmooplus.dto.wsraspay.CreditCardDto;
import com.client_ws.rasmooplus.dto.wsraspay.PaymentDto;

public class PaymentMapper {
    public static PaymentDto build(String customerId, String orderId, CreditCardDto dto){
        return PaymentDto.builder()
                .customerId(customerId)
                .orderId(orderId)
                .creditCard(dto)
                .build();

    }
}
