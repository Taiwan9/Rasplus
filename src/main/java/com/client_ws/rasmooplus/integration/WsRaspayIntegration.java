package com.client_ws.rasmooplus.integration;

import com.client_ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client_ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client_ws.rasmooplus.dto.wsraspay.PaymentDto;

public interface WsRaspayIntegration {

    CustomerDto createCustomer(CustomerDto dto);

    OrderDto createOrder(OrderDto dto);

    Boolean processPayment(PaymentDto dto);
}
