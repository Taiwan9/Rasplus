package com.client_ws.rasmooplus.integration;

import com.client_ws.rasmooplus.dto.wsraspay.CreditCardDto;
import com.client_ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client_ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client_ws.rasmooplus.dto.wsraspay.PaymentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
 class WsRaspayIntegrationTest {

    @Autowired
    private WsRaspayIntegration wsRaspayIntegration;

    @Test
    void createCustomerWhenDtoOk(){
        CustomerDto dto = new CustomerDto(null, "97179125059", "teste@teste.com", "teste", "customerDto");
        var valor = wsRaspayIntegration.createCustomer(dto);
        System.out.println(valor);
    }

    @Test
    void createOrderWhenDtoOk(){
        OrderDto dto = new OrderDto(null,"6436c69416869c357e9017b6", BigDecimal.ZERO,"MONTH50");
        var valor = wsRaspayIntegration.createOrder(dto);
        System.out.println(valor);
    }

    @Test
    void createPaymentWhenDtoOk(){
        CreditCardDto creditCardDto = new CreditCardDto(123L, "44635762025", 0L,06L, "1234123412341234",2032L);
        PaymentDto paymentDto = new PaymentDto(creditCardDto,"6436c69416869c357e9017b6","67f80b7512e337494cad4551");
        var valor = wsRaspayIntegration.processPayment(paymentDto);
        System.out.println(valor);
    }
}
