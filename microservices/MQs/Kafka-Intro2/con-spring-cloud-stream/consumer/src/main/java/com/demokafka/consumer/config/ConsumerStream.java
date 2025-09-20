package com.demokafka.consumer.config;

import com.demokafka.consumer.model.RiderLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ConsumerStream {

    @Bean
    public Consumer<RiderLocation> processRiderLocation() {
        return location -> {
            System.out.println("Received: " + location.getRiderId() +
                    "@" + location.getLatitude() + "-" + location.getLongitude());
        };
    }
}
