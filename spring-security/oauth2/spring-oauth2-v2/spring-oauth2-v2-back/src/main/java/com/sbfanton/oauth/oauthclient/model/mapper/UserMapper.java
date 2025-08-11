package com.sbfanton.oauth.oauthclient.model.mapper;

import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.model.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO userToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatarUrl(user.getAvatarUrl())
                .web(user.getWeb())
                .email(user.getEmail())
                .isEmailVerified(user.getIsEmailVerified())
                .provider(user.getProvider().name())
                .hasPassword(user.getPassword() != null)
                .build();
    }
}
