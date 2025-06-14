package com.sbfanton.oauth.oauthclient.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Data
@Getter
@Setter
@Builder
public class FileDTO {

    private Resource resource;
    private String contentType;
}
