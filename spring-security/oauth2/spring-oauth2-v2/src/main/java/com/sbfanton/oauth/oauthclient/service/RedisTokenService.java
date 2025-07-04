package com.sbfanton.oauth.oauthclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;


}

