package com.sbfanton.oauth.oauthclient.repository;

import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.utils.constants.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);
    public void deleteByUsername(String username);

    public Optional<User> findByProviderAndProviderId(AuthProviderType provider, String providerId);
}
