package com.demokafka.producer.service;

import com.demokafka.producer.model.RiderLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class ManualProducerService {

    @Autowired
    private StreamBridge streamBridge;

    public String sendMessage(RiderLocation riderLocation) {
        // 'manualSender-out-0' debe coincidir con el binding configurado
        boolean sent = streamBridge.send("manualSender-out-0", riderLocation);
        return "Mensaje enviado? " + sent;
    }
}
