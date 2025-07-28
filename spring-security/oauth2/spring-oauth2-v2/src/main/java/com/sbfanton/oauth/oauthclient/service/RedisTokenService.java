package com.sbfanton.oauth.oauthclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisTokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String EMAIL_PREFIX = "email_verification:";

    public void saveEmailToken(String token, String email) {
        redisTemplate.opsForValue().set(
                EMAIL_PREFIX + token,
                email,
                //Duration.ofHours(24));
                Duration.ofSeconds(20));
    }

    public String getEmailByToken(String token) {
        return redisTemplate.opsForValue().get(EMAIL_PREFIX + token);
    }

    public void deleteEmailToken(String token) {
        redisTemplate.delete(EMAIL_PREFIX + token);
    }
}

