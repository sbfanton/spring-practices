package com.sbfanton.oauth.oauthclient.model;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuthEndpoint {

    private String url;
    private boolean returnsList;
}
