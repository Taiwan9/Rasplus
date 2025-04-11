package com.client_ws.rasmooplus.mapper.wsraspay;

import com.client_ws.rasmooplus.dto.PaymentProcessDto;
import com.client_ws.rasmooplus.dto.wsraspay.OrderDto;

public class OrderMapper {
    public static OrderDto build(String customerId, PaymentProcessDto paymentProcessDto){
       return OrderDto.builder()
                .customerId(customerId)
                .productAcronym(paymentProcessDto.getProductKey())
                .discount(paymentProcessDto.getDiscount())
                .build();
    }
}
