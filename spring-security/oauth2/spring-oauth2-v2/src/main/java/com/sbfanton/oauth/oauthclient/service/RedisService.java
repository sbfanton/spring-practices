package com.sbfanton.oauth.oauthclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void guardar(String clave, String valor) {
        redisTemplate.opsForValue().set(clave, valor);
    }

    public String obtener(String clave) {
        return redisTemplate.opsForValue().get(clave);
    }
}

