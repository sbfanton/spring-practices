package com.demokafka.producer.service;

import com.demokafka.producer.model.RiderLocation;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private final KafkaTemplate<String, String> stringKafkaTemplate;
    private final KafkaTemplate<String, RiderLocation> riderLocationKafkaTemplate;


    public KafkaService(
            KafkaTemplate<String, String> stringKafkaTemplate,
            KafkaTemplate<String, RiderLocation> riderLocationKafkaTemplate) {
        this.stringKafkaTemplate = stringKafkaTemplate;
        this.riderLocationKafkaTemplate = riderLocationKafkaTemplate;
    }

    public void sendStringMsg(String topic, String msg) {
        stringKafkaTemplate.send(topic, msg);
    }

    public void sendRiderLocation(String topic, RiderLocation riderLocation) {
        riderLocationKafkaTemplate.send(topic, riderLocation);
    }
}
