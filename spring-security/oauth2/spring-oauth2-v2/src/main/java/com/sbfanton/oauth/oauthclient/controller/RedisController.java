package com.sbfanton.oauth.oauthclient.controller;

import com.sbfanton.oauth.oauthclient.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("/guardar")
    public String guardar(@RequestParam String clave, @RequestParam String valor) {
        redisService.guardar(clave, valor);
        return "Guardado en Redis";
    }

    @GetMapping("/obtener")
    public String obtener(@RequestParam String clave) {
        return redisService.obtener(clave);
    }
}
