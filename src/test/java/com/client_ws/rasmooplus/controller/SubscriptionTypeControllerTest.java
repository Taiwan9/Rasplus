package com.client_ws.rasmooplus.controller;

import com.client_ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client_ws.rasmooplus.model.jpa.SubscriptionType;
import com.client_ws.rasmooplus.service.SubscriptionTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@WebMvcTest(SubscriptionTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
class SubscriptionTypeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubscriptionTypeService subscriptionTypeService;

    @Test
    void given_findAll_then_returnAllSubscriptionType() throws Exception {
        mockMvc.perform(get("/subscription-type"))
                .andExpect(status().isOk());
    }

    @Test
    void given_findById_whenGetId2_then_returnOneSubscriptionType() throws Exception {
        SubscriptionType subscriptionType = new SubscriptionType(2L, "VITALICIO", null, BigDecimal.valueOf(997),
                "FOR99");
        when(subscriptionTypeService.findById(2L)).thenReturn(subscriptionType);
        mockMvc.perform(get("/subscription-type/2"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(2)));
    }

    @Test
    void given_delete_whenGetId2_then_noReturnAndNoContent() throws Exception {
        mockMvc.perform(delete("/subscription-type/{id}", 2))
                .andExpect(status().isNoContent());

        verify(subscriptionTypeService,times(1)).delete(2L);
    }

    @Test
    void given_create_whenDtoIsOk_then_returnSubscriptionTypeCreated() throws Exception {
        SubscriptionType subscriptionType = new SubscriptionType(2L, "VITALICIO", null, BigDecimal.valueOf(997),
                "FOR99");

        SubscriptionTypeDto dto = new SubscriptionTypeDto(null, "VITALICIO", null, BigDecimal.valueOf(997),
                "FOR99");

        when(subscriptionTypeService.create(dto)).thenReturn(subscriptionType);

        mockMvc.perform(post("/subscription-type")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Is.is(2)))
        ;
    }

    @Test
    void given_create_whenDtoIsMissingValues_then_returnBadRequest() throws Exception {

        SubscriptionTypeDto dto = new SubscriptionTypeDto(null, "", 13L, null,
                "FO");

        mockMvc.perform(post("/subscription-type")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatus", Is.is("BAD_REQUEST")))
                .andExpect(jsonPath("$.statusCode", Is.is(400)))
        ;

        verify(subscriptionTypeService, times(0)).create(any());
    }

    @Test
    void given_update_whenDtoIsOk_then_returnSubscriptionTypeUpdated() throws Exception {
        SubscriptionType subscriptionType = new SubscriptionType(2L, "VITALICIO", null, BigDecimal.valueOf(997),
                "FOR99");

        SubscriptionTypeDto dto = new SubscriptionTypeDto(2L, "VITALICIO", null, BigDecimal.valueOf(997),
                "FOR99");

        when(subscriptionTypeService.update(2L,dto)).thenReturn(subscriptionType);

        mockMvc.perform(put("/subscription-type/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(2)))
        ;
    }

    @Test
    void given_update_whenDtoIsMissingValues_then_returnBadRequest() throws Exception {

        SubscriptionTypeDto dto = new SubscriptionTypeDto(null, "", 13L, null,
                "FO");

        mockMvc.perform(put("/subscription-type/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatus", Is.is("BAD_REQUEST")))
                .andExpect(jsonPath("$.statusCode", Is.is(400)))
        ;

        verify(subscriptionTypeService, times(0)).update(any(),any());
    }

    @Test
    void given_update_whenIdIsNull_then_returnBadRequest() throws Exception {

        SubscriptionTypeDto dto = new SubscriptionTypeDto(null, "", 13L,
                null,"FO");

        mockMvc.perform(put("/subscription-type/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
        ;

        verify(subscriptionTypeService, times(0)).update(any(),any());
    }

}