package com.sbfanton.oauth.oauthclient.repository;

import com.sbfanton.oauth.oauthclient.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    public Optional<User> findByUsername(String username);
}
