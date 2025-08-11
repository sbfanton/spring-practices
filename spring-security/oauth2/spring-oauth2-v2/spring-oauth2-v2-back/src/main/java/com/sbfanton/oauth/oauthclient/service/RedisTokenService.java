package com.sbfanton.oauth.oauthclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisTokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String EMAIL_PREFIX = "email_verification:";

    public void saveEmailToken(String token, String email) {
        redisTemplate.opsForValue().set(
                EMAIL_PREFIX + token,
                email,
                Duration.ofHours(24));
                //Duration.ofSeconds(20)); para pruebas
    }

    public String getEmailByToken(String token) {
        return redisTemplate.opsForValue().get(EMAIL_PREFIX + token);
    }

    public void deleteEmailToken(String token) {
        redisTemplate.delete(EMAIL_PREFIX + token);
    }

    public String saveRefreshToken(String userId, String refreshToken) {
        String sessionId = UUID.randomUUID().toString();
        String key = "refresh:" + userId + ":" + sessionId;
        redisTemplate.opsForValue().set(key, refreshToken, 2, TimeUnit.HOURS);
        return sessionId;
    }

    public boolean validateRefreshToken(String userId, String refreshToken, String sessionId) {
        String key = "refresh:" + userId + ":" + sessionId;
        String storedToken = redisTemplate.opsForValue().get(key);
        return storedToken != null && storedToken.equals(refreshToken);
    }

    public void deleteRefreshToken(String userId, String sessionId) {
        String key = "refresh:" + userId + ":" + sessionId;
        redisTemplate.delete(key);
    }
}

