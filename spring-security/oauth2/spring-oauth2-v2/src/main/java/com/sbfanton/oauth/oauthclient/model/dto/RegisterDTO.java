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
public class RegisterDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String email;

    private String avatarUrl;

    private String web;
}
