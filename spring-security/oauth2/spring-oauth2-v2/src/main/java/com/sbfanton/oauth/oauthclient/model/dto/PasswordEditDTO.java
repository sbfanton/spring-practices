package com.sbfanton.oauth.oauthclient.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class PasswordEditDTO {

    private String currentPassword;

    @NotBlank(message = "Debe enviar la nueva contraseña")
    private String newPassword;

    @NotBlank(message = "Debe enviar la confirmación de la nueva contraseña")
    private String confirmPassword;

    private Boolean isFirstPassword;
}
