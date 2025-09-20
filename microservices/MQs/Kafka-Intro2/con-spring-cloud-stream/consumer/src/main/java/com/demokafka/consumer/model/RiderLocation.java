package com.demokafka.consumer.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RiderLocation {

    private String riderId;
    private double latitude;
    private double longitude;

}
