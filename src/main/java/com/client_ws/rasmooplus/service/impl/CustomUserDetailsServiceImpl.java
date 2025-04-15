package com.client_ws.rasmooplus.service.impl;

import com.client_ws.rasmooplus.exception.BadRequestException;
import com.client_ws.rasmooplus.exception.NotFoundException;
import com.client_ws.rasmooplus.integration.MailIntegration;
import com.client_ws.rasmooplus.model.jpa.UserCredentials;
import com.client_ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client_ws.rasmooplus.repository.jpa.UserDetailsRepository;
import com.client_ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client_ws.rasmooplus.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    @Value("${webservices.rasplus.redis.recoverycode.timetou}")
    private String recoveryCodeTimeout;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @Autowired
    private MailIntegration mailIntegration;

    @Override
    public UserCredentials loadUserByUsernameAndPass(String username, String pass) {

        var userCredentialsOpt = userDetailsRepository.findByUsername(username);

        if (userCredentialsOpt.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        UserCredentials userCredentials = userCredentialsOpt.get();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(pass, userCredentials.getPassword())) {
            return userCredentials;
        }

        throw new BadRequestException("Usuário ou senha inválido");
    }

    @Override
    public void sendRecoveryCode(String email) {

        UserRecoveryCode userRecoveryCode;
        String code = String.format("%04d", new Random().nextInt(10000));
        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);

        if (userRecoveryCodeOpt.isEmpty()) {

            var user = userDetailsRepository.findByUsername(email);
            if (user.isEmpty()) {
                throw new NotFoundException("Usuário não encontrado");
            }

            userRecoveryCode = new UserRecoveryCode();
            userRecoveryCode.setEmail(email);

        } else {
            userRecoveryCode = userRecoveryCodeOpt.get();
        }
        userRecoveryCode.setCode(code);
        userRecoveryCode.setCreationTime(LocalDateTime.now());

        userRecoveryCodeRepository.save(userRecoveryCode);
        mailIntegration.send(email, "Código de recuperação de conta: "+code, "Código de recuperação de conta");
    }

//    @Override
//    public boolean recoveryCodeIsValid(String recoveryCode, String email) {
//
//        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);
//
//        if (userRecoveryCodeOpt.isEmpty()) {
//            throw new NotFoundException("Usuário não encontrado");
//        }
//
//        UserRecoveryCode userRecoveryCode = userRecoveryCodeOpt.get();
//
//        LocalDateTime timeout = userRecoveryCode.getCreationDate().plusMinutes(Long.parseLong(recoveryCodeTimeout));
//        LocalDateTime now = LocalDateTime.now();
//
//        return recoveryCode.equals(userRecoveryCode.getCode()) && now.isBefore(timeout);
//    }
}
