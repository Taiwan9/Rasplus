package com.client_ws.rasmooplus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDatailsDto {

    @Email(message = "inválido !")
    private String email;

    @NotBlank(message = "Atributo invalido")
    private String password;

    private String recoveryCode;
}
