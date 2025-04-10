package com.client_ws.rasmooplus.integration;

import com.client_ws.rasmooplus.dto.wsraspay.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
 class WsRaspayIntegrationTest {

    @Autowired
    private WsRaspayIntegration wsRaspayIntegration;
    @Test
    void createCustomerWhenDtoOk(){
        CustomerDto dto = new CustomerDto(null, "15993880732", "teste@teste.com", "teste", "customerDto");
        wsRaspayIntegration.createCustomer(dto);
    }
}
