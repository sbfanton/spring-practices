package com.demokafka.producer.config;


import com.demokafka.producer.model.RiderLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.function.Supplier;

@Configuration
public class ProducerStream {

    /*
    * Los suppliers en spring cloud streams son utilizados para enviar
    * al broker un stream continuo de mensajes. Es decir, automaticamente enviara,
    * de forma continua, los mensajes al broker con el que se hizo el binding
    * en el .properties o el yml
    * */
    @Bean
    public Supplier<RiderLocation> sendRiderLocation() {
        Random random = new Random(); // la aleatoriedad hara que se envien a partciones diferentes porque el hash cambiara
        return () -> {
            String riderId = "rider" + random.nextInt(20);
            RiderLocation riderLocation = new RiderLocation(riderId, 61.5, 75.8);
            System.out.println("Sending: " + riderLocation.getRiderId());
            return riderLocation;
        };
    }


    @Bean
    public Supplier<String> sendRiderStatus() {
        Random random = new Random(); // la aleatoriedad hara que se envien a partciones diferentes porque el hash cambiara
        return () -> {
            String riderId = "rider" + random.nextInt(20);
            String status = random.nextBoolean() ? "completed" : "started";
            String riderStatus = riderId + " - Status: " + status;
            System.out.println("Sending: " + riderStatus);
            return riderStatus;
        };
    }

}
