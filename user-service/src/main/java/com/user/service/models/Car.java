package com.user.service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private String marca;
    private String modelo;

    private Integer userId;
}
