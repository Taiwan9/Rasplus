package com.client_ws.rasmooplus.integration;

import com.client_ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client_ws.rasmooplus.integration.impl.WsRaspayIntegrationImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class WsRaspayIntegrationTest {

    private static HttpHeaders headers;

    @InjectMocks
    private WsRaspayIntegrationImpl wsRaspayIntegration;

    @Mock
    private  RestTemplate restTemplate;

    @BeforeAll
    static void loadHeaders(){
        headers = getHttpHeaders();
    }

    @Test
    void given_createCustomer_when_apiResponseIs201Created_then_returnCustomerDto() {
        ReflectionTestUtils.setField(wsRaspayIntegration,"raspayHost","http://localhost:8080");
        ReflectionTestUtils.setField(wsRaspayIntegration,"customerUrl","/v1/customer");
        CustomerDto dto = new CustomerDto();
        dto.setCpf("122345678911");
        HttpEntity<CustomerDto> request = new HttpEntity<>(dto, this.headers);
        when(restTemplate.exchange("http://localhost:8080/v1/customer", HttpMethod.POST, request, CustomerDto.class))
                .thenReturn(ResponseEntity.of(Optional.of(dto)));
        wsRaspayIntegration.createCustomer(dto);
        Mockito.verify(restTemplate.exchange("http://localhost:8080/v1/customer", HttpMethod.POST, request, CustomerDto.class));
    }

    @Test
    void createOrder() {
    }

    @Test
    void processPayment() {
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credentials = "rasmooplus:r@sm00";

        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
}