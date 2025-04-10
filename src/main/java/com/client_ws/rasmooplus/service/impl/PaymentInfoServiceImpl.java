package com.client_ws.rasmooplus.service.impl;

import com.client_ws.rasmooplus.dto.PaymentProcessDto;
import com.client_ws.rasmooplus.service.PaymentInfoService;
import org.springframework.stereotype.Service;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    PaymentInfoServiceImpl(){}


    @Override
    public Boolean process(PaymentProcessDto dto) {
        //Verificar usuario por id
        //salvar as informações de pagament
        //criar ou atualizar usuario raspay
        //criar o pedido de pagamento
        //processar o pagamento
        //enviar email de criacao de conta
        //retorna o sucesso ou nao do pagamento
        return null;
    }
}
