package com.client_ws.rasmooplus.service;

import com.client_ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client_ws.rasmooplus.model.jpa.SubscriptionType;

import java.util.List;

public interface SubscriptionTypeService {
    List<SubscriptionType> findAll();

    SubscriptionType findById(Long id);

    SubscriptionType create(SubscriptionTypeDto dto);

    SubscriptionType update(Long id, SubscriptionTypeDto subscriptionTypeDto);

    void delete(Long id);
}
