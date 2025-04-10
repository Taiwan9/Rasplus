package com.client_ws.rasmooplus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProcessDto {

    @NotBlank(message = "Deve ser Informado")
    private String productKey;
    private String discount;

    @NotNull(message = "Dados do pagamento deve ser iformado")
    @JsonProperty("userPaymentInfo")
    private UserPaymentInfoDto userPaymentInfoDto;
}
