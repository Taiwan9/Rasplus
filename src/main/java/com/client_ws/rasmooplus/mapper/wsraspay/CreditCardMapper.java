package com.client_ws.rasmooplus.mapper.wsraspay;

import com.client_ws.rasmooplus.dto.UserPaymentInfoDto;
import com.client_ws.rasmooplus.dto.wsraspay.CreditCardDto;

public class CreditCardMapper {
    public static CreditCardDto build(UserPaymentInfoDto dto, String documentNumber){
        return CreditCardDto.builder()
                .documentNumber(documentNumber)
                .cvv(Long.parseLong(dto.getCardSecurityCode()))
                .number(dto.getCardNumber())
                .month(dto.getCardExpirationMonth())
                .year(dto.getCardExpirationYear())
                .installments(dto.getInstallments())
                .build();
    }
}
