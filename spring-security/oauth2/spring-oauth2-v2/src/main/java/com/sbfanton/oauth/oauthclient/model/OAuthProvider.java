package com.sbfanton.oauth.oauthclient.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Builder
public class OAuthProvider {

    private String clientId;
    private String clientSecret;
    private String authorizationUri;
    private String tokenUri;
    private List<OAuthEndpoint> userInfoUris;
    private String redirectUri;
    private String grantType;
    private List<String> userInfoFields;
}
