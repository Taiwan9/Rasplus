package com.client_ws.rasmooplus.service.impl;

import com.client_ws.rasmooplus.dto.PaymentProcessDto;
import com.client_ws.rasmooplus.exception.BusinessException;
import com.client_ws.rasmooplus.exception.NotFoundException;
import com.client_ws.rasmooplus.mapper.UserPaymentInfoMapper;
import com.client_ws.rasmooplus.model.User;
import com.client_ws.rasmooplus.model.UserPaymentInfo;
import com.client_ws.rasmooplus.repository.UserPaymentInfoRepository;
import com.client_ws.rasmooplus.repository.UserRepository;
import com.client_ws.rasmooplus.service.PaymentInfoService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    private final UserRepository userRepository;
    private final UserPaymentInfoRepository userPaymentInfoRepository;

    PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository){
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
    }


    @Override
    public Boolean process(PaymentProcessDto dto) {

        //Verificar usuario por id verifica se existe assinatura
        var userOpt = userRepository.findById(dto.getUserPaymentInfoDto().getId());
        if(userOpt.isEmpty()){
            throw new NotFoundException("Usuario não encontrado");
        }
        User user = userOpt.get();
        if(Objects.nonNull(user.getSubscriptionType())){
            throw new BusinessException("Usuario já possui assinatura");
        }

        //criar ou atualizar usuario raspay
        //criar o pedido de pagamento
        //processar o pagamento
        //salvar as informações de pagament
        UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDto(),user);
        userPaymentInfoRepository.save(userPaymentInfo);
        //enviar email de criacao de conta
        //retorna o sucesso ou nao do pagamento
        return null;
    }
}
