package com.sbfanton.oauth.oauthclient.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class UserDTO {

    private String username;
    private String email;
    private String avatarUrl;
    private String web;
}
