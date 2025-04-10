package com.client_ws.rasmooplus.service;

import com.client_ws.rasmooplus.dto.PaymentProcessDto;

public interface PaymentInfoService {

    Boolean process(PaymentProcessDto dto);
}
