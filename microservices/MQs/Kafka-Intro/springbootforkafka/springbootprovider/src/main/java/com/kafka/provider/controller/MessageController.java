package com.kafka.provider.controller;

import com.kafka.provider.model.Message;
import com.kafka.provider.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestParam String topic, @RequestBody Message message) {
        messageService.sendMessage(message, topic);
        return ResponseEntity.ok("Mensaje enviado");
    }
}
