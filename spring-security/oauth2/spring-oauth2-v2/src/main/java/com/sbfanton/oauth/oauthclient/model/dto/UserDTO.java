package com.sbfanton.oauth.oauthclient.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;

    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "Solo se permiten letras, números y guión bajo, sin espacios")
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;

    @Email
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    private Boolean isEmailVerified;

    private String avatarUrl;

    private String web;

    private String provider;

    private Boolean hasPassword;
}
