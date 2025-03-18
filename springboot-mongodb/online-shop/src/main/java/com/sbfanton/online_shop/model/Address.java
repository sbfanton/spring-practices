package com.sbfanton.online_shop.model;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    
    @Field("street")
    private String street;
    
    @Field("city")
    private String city;
    
    @Field("number")
    private Integer number;
    
}
