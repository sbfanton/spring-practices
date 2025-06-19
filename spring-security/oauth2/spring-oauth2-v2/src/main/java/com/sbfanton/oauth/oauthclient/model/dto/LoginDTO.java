package com.sbfanton.oauth.oauthclient.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {

    @NotBlank(message = "No ha enviado un nombre de usuario o email")
    private String identifier;
    @NotBlank(message = "No ha enviado la contraseña")
    private String password;
}
