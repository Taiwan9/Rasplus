package com.client_ws.rasmooplus.service;

import com.client_ws.rasmooplus.exception.BadRequestException;
import com.client_ws.rasmooplus.exception.NotFoundException;
import com.client_ws.rasmooplus.integration.MailIntegration;
import com.client_ws.rasmooplus.model.jpa.UserCredentials;
import com.client_ws.rasmooplus.model.jpa.UserType;
import com.client_ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client_ws.rasmooplus.repository.jpa.UserDetailsRepository;
import com.client_ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client_ws.rasmooplus.service.impl.CustomUserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    private static final String USERNAME_ALUNO = "felipe@email.com";
    private static final String PASSWORD_ALUNO = "senha123";
    private static final String RECOVERY_CODE_ALUNO = "4065";

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @Mock
    private MailIntegration mailIntegration;

    @InjectMocks
    private CustomUserDetailsServiceImpl userDetailsService;

    @Test
    void given_loadUserByUsernameAndPass_when_thereIsUsername_then_returnUserCredentials() {
        UserCredentials userCredentials = getUserCredentials();
        when(userDetailsRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.of(userCredentials));
        assertEquals(userCredentials, userDetailsService.loadUserByUsernameAndPass(USERNAME_ALUNO, PASSWORD_ALUNO));
        verify(userDetailsRepository, times(1)).findByUsername(USERNAME_ALUNO);
    }


    @Test
    void given_loadUserByUsernameAndPass_when_thereIsNoUsername_then_throwNotFoundException() {
        when(userDetailsRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.empty());
        assertEquals("Usuário não encontrado",
                assertThrows(NotFoundException.class,
                        () -> userDetailsService.loadUserByUsernameAndPass(USERNAME_ALUNO, PASSWORD_ALUNO)
                ).getLocalizedMessage());
        verify(userDetailsRepository, times(1)).findByUsername(USERNAME_ALUNO);
    }

    @Test
    void given_loadUserByUsernameAndPass_when_passIsNotCorrect_then_throwBadRequestException() {
        UserCredentials userCredentials = getUserCredentials();
        when(userDetailsRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.of(userCredentials));
        assertEquals("Usuário ou senha inválido",
                assertThrows(BadRequestException.class,
                        () -> userDetailsService.loadUserByUsernameAndPass(USERNAME_ALUNO, "pass")
                ).getLocalizedMessage());
        verify(userDetailsRepository, times(1)).findByUsername(USERNAME_ALUNO);
    }

    @Test
    void given_sendRecoveryCode_when_userRecoveryCodeIsFound_then_updateUserAndSendEmail() {
        UserRecoveryCode userRecoveryCode = new UserRecoveryCode(UUID.randomUUID().toString(), USERNAME_ALUNO, "4065", LocalDateTime.now());
        when(userRecoveryCodeRepository.findByEmail(USERNAME_ALUNO)).thenReturn(Optional.of(userRecoveryCode));
        userDetailsService.sendRecoveryCode(USERNAME_ALUNO);
        verify(userRecoveryCodeRepository, times(1)).save(any());
        verify(mailIntegration, times(1)).send(any(), any(), any());
    }

    @Test
    void given_sendRecoveryCode_when_userRecoveryCodeIsNotFound_then_SaveUserAndSendEmail() {
        when(userRecoveryCodeRepository.findByEmail(USERNAME_ALUNO)).thenReturn(Optional.empty());
        when(userDetailsRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.of(getUserCredentials()));
        userDetailsService.sendRecoveryCode(USERNAME_ALUNO);
        verify(userRecoveryCodeRepository, times(1)).save(any());
        verify(mailIntegration, times(1)).send(any(), any(), any());
    }

    @Test
    void given_sendRecoveryCode_when_userRecoveryCodeIsNotFoundAndUserDetailsIsNotFound_then_throwNotFoundException() {
        when(userRecoveryCodeRepository.findByEmail(USERNAME_ALUNO)).thenReturn(Optional.empty());
        when(userDetailsRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.empty());
        try {
            userDetailsService.sendRecoveryCode(USERNAME_ALUNO);
        } catch (Exception e) {
            assertEquals(NotFoundException.class, e.getClass());
            assertEquals("Usuário não encontrado", e.getMessage());
        }
        verify(userRecoveryCodeRepository, times(0)).save(any());
        verify(mailIntegration, times(0)).send(any(), any(), any());
    }

    @Test
    void given_recoveryCodeIsValid_when_userIsFound_then_returnTrue(){
        ReflectionTestUtils.setField(userDetailsService, "recoveryCodeTimeout", "5");
        when(userRecoveryCodeRepository.findByEmail(USERNAME_ALUNO)).thenReturn(Optional.of(getUserRecoveryCode()));
        assertTrue(userDetailsService.recoveryCodeIsValid(RECOVERY_CODE_ALUNO,USERNAME_ALUNO));

        verify(userRecoveryCodeRepository,times(1)).findByEmail(USERNAME_ALUNO);
    }

    private UserCredentials getUserCredentials() {
        UserType userType = new UserType(1L, "aluno", "aluno plataforma");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return new UserCredentials(1L, USERNAME_ALUNO, encoder.encode(PASSWORD_ALUNO), userType);
    }

    private static UserRecoveryCode getUserRecoveryCode() {
        return  new UserRecoveryCode(UUID.randomUUID().toString(), USERNAME_ALUNO, RECOVERY_CODE_ALUNO, LocalDateTime.now());
    }
}