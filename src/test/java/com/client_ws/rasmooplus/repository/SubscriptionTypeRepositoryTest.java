package com.client_ws.rasmooplus.repository;

import com.client_ws.rasmooplus.model.jpa.SubscriptionType;
import com.client_ws.rasmooplus.repository.jpa.SubscriptionTypeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@WebMvcTest(SubscriptionTypeRepository.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SubscriptionTypeRepositoryTest {

    @Autowired
    private  SubscriptionTypeRepository subscriptionTypeRepository;

    @BeforeAll
     void loadSubscriptions(){
        List<SubscriptionType> subscriptionTypes = new ArrayList<>();
        SubscriptionType subscriptionType = new SubscriptionType(null, "VITALICIO", null,
                BigDecimal.valueOf(997),"FOR99");
        subscriptionTypes.add(subscriptionType);
        SubscriptionType subscriptionType2 = new SubscriptionType(null, "SEMESTRAL", 6L, BigDecimal.valueOf(547),
                "SEM06");
        subscriptionTypes.add(subscriptionType2);
        SubscriptionType subscriptionType3 = new SubscriptionType(null, "MENSAL", 1L, BigDecimal.valueOf(100),
                "MENSAL030");
        subscriptionTypes.add(subscriptionType3);

        subscriptionTypeRepository.saveAll(subscriptionTypes);
    }

    @Test
    void given_findByProductKey_when_getProductKey_then_returnCorrectSubscription(){
        assertEquals("VITALICIO", subscriptionTypeRepository.findByProductKey("FOR99")
                .get().getName()
        );
        assertEquals("SEMESTRAL", subscriptionTypeRepository.findByProductKey("SEM06")
                .get().getName()
        );
        assertEquals("MENSAL", subscriptionTypeRepository.findByProductKey("MENSAL030")
                .get().getName()
        );

    }

}