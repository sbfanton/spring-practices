package com.kafka.provider.service;

import com.kafka.provider.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Message message, String topic) {
        kafkaTemplate.send(topic, message.getMessage());
    }
}
