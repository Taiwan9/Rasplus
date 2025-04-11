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
 class MailIntegrationTest {

    @Autowired
    private MailIntegration mailIntegration;

    @Test
    void createCustomerWhenDtoOk(){
      mailIntegration.send("clownser1994@gmail.com", "Olá Gmail");
    }

}
