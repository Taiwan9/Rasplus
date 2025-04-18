package com.client_ws.rasmooplus.integration;

import com.client_ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client_ws.rasmooplus.integration.impl.WsRaspayIntegrationImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WsRaspayIntegrationTest {

    private static HttpHeaders headers;

    @InjectMocks
    private WsRaspayIntegrationImpl wsRaspayIntegration;

    @Mock
    private RestTemplate restTemplate;

    @BeforeAll
    static void loadHeaders() {
        headers = getHttpHeaders();
    }

    @Test
    void given_createCustomer_when_apiResponseIs201Created_then_returnCustomerDto() {
        ReflectionTestUtils.setField(wsRaspayIntegration, "raspayHost", "https://raspay-api-61f5fa5fc34c.herokuapp.com/ws-raspay");
        ReflectionTestUtils.setField(wsRaspayIntegration, "customerUrl", "/v1/customer");
        ReflectionTestUtils.setField(wsRaspayIntegration, "restTemplate", restTemplate);


        CustomerDto dto = new CustomerDto();
        dto.setFirstName("taiwan");
        dto.setLastName("marinho");
        dto.setCpf("15993880732");

        when(restTemplate.exchange(
                eq("https://raspay-api-61f5fa5fc34c.herokuapp.com/ws-raspay/v1/customer"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(CustomerDto.class)
        )).thenReturn(ResponseEntity.of(Optional.of(dto)));

        CustomerDto result = wsRaspayIntegration.createCustomer(dto);

        assertNotNull(result);
        assertEquals("taiwan", result.getFirstName());
        verify(restTemplate, times(1)).exchange(
                eq("https://raspay-api-61f5fa5fc34c.herokuapp.com/ws-raspay/v1/customer"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(CustomerDto.class)
        );
    }

    @Test
    void given_createCustomer_when_apiResponseIs400BadRequest_then_returnNull() {
        // Mesma configuração de host/URL/injeção
        ReflectionTestUtils.setField(wsRaspayIntegration, "raspayHost", "https://raspay-api-61f5fa5fc34c.herokuapp.com/ws-raspay");
        ReflectionTestUtils.setField(wsRaspayIntegration, "customerUrl", "/v1/customer");
        ReflectionTestUtils.setField(wsRaspayIntegration, "restTemplate", restTemplate);

        CustomerDto dto = new CustomerDto();
        dto.setCpf("122345678911");

        when(restTemplate.exchange(
                eq("https://raspay-api-61f5fa5fc34c.herokuapp.com/ws-raspay/v1/customer"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(CustomerDto.class)
        )).thenReturn(ResponseEntity.badRequest().build());

        CustomerDto result = wsRaspayIntegration.createCustomer(dto);

        assertNull(result);
        verify(restTemplate, times(1)).exchange(
                eq("https://raspay-api-61f5fa5fc34c.herokuapp.com/ws-raspay/v1/customer"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(CustomerDto.class)
        );
    }

    @Test
    void given_createCustomer_when_apiResponseGeThrows_then_returnHttpClientException() {
        // configurações de host/URL/injeção
        ReflectionTestUtils.setField(wsRaspayIntegration, "raspayHost",
                "https://raspay-api-61f5fa5fc34c.herokuapp.com/ws-raspay");
        ReflectionTestUtils.setField(wsRaspayIntegration, "customerUrl", "/v1/customer");
        ReflectionTestUtils.setField(wsRaspayIntegration, "restTemplate", restTemplate);

        CustomerDto dto = new CustomerDto();
        dto.setCpf("122345678911");

        when(restTemplate.exchange(
                eq("https://raspay-api-61f5fa5fc34c.herokuapp.com/ws-raspay/v1/customer"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(CustomerDto.class)
        )).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        assertThrows(RuntimeException.class,
                () -> wsRaspayIntegration.createCustomer(dto));

        verify(restTemplate, times(1)).exchange(
                eq("https://raspay-api-61f5fa5fc34c.herokuapp.com/ws-raspay/v1/customer"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(CustomerDto.class)
        );
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
