package com.demokafka.consumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    /*
    * A fines practicos, tenermos aca dos consumers, pero en la vida real,
    * cada consumer estará en una aplciacion, servidor, etc, por separado
    *
    * Por otro lado, cada consumer que pertenece a un mismo grupo y estan suscriptos
    * a un mismo topic, le llegaran mensajes o data posiblemente en particiones distintas, si
    * dicho topic las tuviera
    * */


    // Consumer 1
    @KafkaListener(topics = { "my-topic" }, groupId = "my-group-1")
    public void listen(String message) {
        System.out.println("Received message1: " + message);
    }

    //Consumer 2
    //@KafkaListener(topics = { "my-topic" }, groupId = "my-group-2")
    @KafkaListener(topics = { "my-topic" }, groupId = "my-group-1")
    public void listen2(String message) {
        System.out.println("Received message2: " + message);
    }
}
