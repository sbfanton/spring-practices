package com.sbfanton.oauth.oauthclient.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class StatusDTO {

    private String message;
    private String code;
    private String timestamp;
}
