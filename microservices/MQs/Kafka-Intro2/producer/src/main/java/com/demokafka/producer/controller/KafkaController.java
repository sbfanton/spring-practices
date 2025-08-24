package com.demokafka.producer.controller;

import com.demokafka.producer.model.RiderLocation;
import com.demokafka.producer.service.KafkaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class KafkaController {

    private final KafkaService kafkaService;

    public KafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @PostMapping("/message")
    public String sendMessage(
            @RequestParam String topic,
            @RequestParam String message) {
        kafkaService.sendStringMsg(topic, message);
        return "Message sent: " + message;
    }

    @PostMapping("/rider-location")
    public String sendRiderLocation(
            @RequestParam String topic,
            @RequestBody RiderLocation riderLocation) {
        kafkaService.sendRiderLocation(topic, riderLocation);
        return "Message sent: " + riderLocation.toString();
    }
}
