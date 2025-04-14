package com.client_ws.rasmooplus.mapper;

import com.client_ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client_ws.rasmooplus.model.jpa.SubscriptionType;

public class SubscriptionTypeMapper {
    public  static SubscriptionType fromDtoEntity(SubscriptionTypeDto dto){
        return SubscriptionType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .accessMonth(dto.getAccessMonth())
                .price(dto.getPrice())
                .productKey(dto.getProductKey())
                .build();
    }
}
