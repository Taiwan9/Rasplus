package com.client_ws.rasmooplus.service.impl;

import com.client_ws.rasmooplus.dto.PaymentProcessDto;
import com.client_ws.rasmooplus.exception.BusinessException;
import com.client_ws.rasmooplus.exception.NotFoundException;
import com.client_ws.rasmooplus.model.User;
import com.client_ws.rasmooplus.repository.UserRepository;
import com.client_ws.rasmooplus.service.PaymentInfoService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    private final UserRepository userRepository;

    PaymentInfoServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public Boolean process(PaymentProcessDto dto) {

        //Verificar usuario por id verifica se existe assinatura
        var userOpt = userRepository.findById(dto.getUserPaymentInfo().getId());
        if(userOpt.isEmpty()){
            throw new NotFoundException("Usuario não encontrado");
        }
        User user = userOpt.get();
        if(Objects.nonNull(user.getSubscriptionType())){
            throw new BusinessException("Usuario já possui assinatura")
        }
        //salvar as informações de pagament
        //criar ou atualizar usuario raspay
        //criar o pedido de pagamento
        //processar o pagamento
        //enviar email de criacao de conta
        //retorna o sucesso ou nao do pagamento
        return null;
    }
}
