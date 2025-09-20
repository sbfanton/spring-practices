package com.demokafka.producer.controller;

import com.demokafka.producer.model.RiderLocation;
import com.demokafka.producer.service.ManualProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final ManualProducerService manualProducerService;

    public MessageController(ManualProducerService manualProducerService) {
        this.manualProducerService = manualProducerService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody RiderLocation riderLocation) {
        return
                ResponseEntity.ok(
                manualProducerService.sendMessage(riderLocation)
                );
    }
}
