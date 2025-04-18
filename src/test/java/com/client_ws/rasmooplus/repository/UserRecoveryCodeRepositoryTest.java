package com.client_ws.rasmooplus.repository;

import com.client_ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client_ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureDataRedis
@AutoConfigureTestDatabase
@WebMvcTest(UserRecoveryCodeRepository.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRecoveryCodeRepositoryTest {
    @Autowired
    private  UserRecoveryCodeRepository userRecoveryCodeRepository;

    @BeforeAll
    void loadSubscriptions(){
        List<UserRecoveryCode> recoveryCodes = new ArrayList<>();

        UserRecoveryCode recoveryCode = new UserRecoveryCode();
        recoveryCode.setEmail("usuario@usuario.com");
        recoveryCode.setCode("1234");
        recoveryCodes.add(recoveryCode);

        UserRecoveryCode recoveryCode2 = new UserRecoveryCode();
        recoveryCode2.setEmail("usuario2@usuario.com");
        recoveryCode2.setCode("4561");
        recoveryCodes.add(recoveryCode2);

        UserRecoveryCode recoveryCode3 = new UserRecoveryCode();
        recoveryCode3.setEmail("usuario3@usuario.com");
        recoveryCode3.setCode("8765");
        recoveryCodes.add(recoveryCode3);

        userRecoveryCodeRepository.saveAll(recoveryCodes);
    }

    @AfterAll
    void dropDataBase(){
        userRecoveryCodeRepository.deleteAll();
    }

    @Test
    void given_findByEmail_when_getByEmail_then_returnCorrectUserRecoveryCode(){
        assertEquals("1234", userRecoveryCodeRepository.findByEmail("usuario@usuario.com")
                .get().getCode()
        );
        assertEquals("4561", userRecoveryCodeRepository.findByEmail("usuario2@usuario.com")
                .get().getCode()
        );
        assertEquals("8765", userRecoveryCodeRepository.findByEmail("usuario3@usuario.com")
                .get().getCode()
        );

    }

}