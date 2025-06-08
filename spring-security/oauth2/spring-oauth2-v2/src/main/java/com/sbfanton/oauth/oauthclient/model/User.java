package com.sbfanton.oauth.oauthclient.model;

import com.sbfanton.oauth.oauthclient.utils.constants.AuthProviderType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails{

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "web")
    private String web;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private AuthProviderType provider;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
