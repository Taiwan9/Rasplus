package com.client_ws.rasmooplus.integration.impl;

import com.client_ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client_ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client_ws.rasmooplus.dto.wsraspay.PaymentDto;
import com.client_ws.rasmooplus.integration.WsRaspayIntegration;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntegration {

    private RestTemplate restTemplate;

    public WsRaspayIntegrationImpl() {
        restTemplate = new RestTemplate();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {
        try {
            HttpHeaders headers = getHttpHeaders();

            HttpEntity<CustomerDto> request = new HttpEntity<>(dto, headers);

            ResponseEntity<CustomerDto> response =
                    restTemplate.exchange(
                            "https://raspay-api-61f5fa5fc34c.herokuapp.com/ws-raspay/v1/customer",
                            HttpMethod.POST,
                            request,
                            CustomerDto.class
                    );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public OrderDto createOrder(OrderDto dto) {
        return null;
    }

    @Override
    public Boolean processPayment(PaymentDto dto) {
        return null;
    }

    private static HttpHeaders getHttpHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Adicionando autenticação básica
//        headers.setBasicAuth("rasmooplus", "r@sm00");

        HttpHeaders headers = new HttpHeaders();
        String credentials = "rasmooplus:r@sm00";

        // Usando o Base64 do Java 8+
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
}
