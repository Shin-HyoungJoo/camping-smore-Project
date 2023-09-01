package com.green.campingsmore.user.camping.model;

import lombok.Data;

@Data
public class CampingUpdDto {
    private Long icamp;
    private Integer capacity;
    private Integer quantity;
    private String address;
    private String campPhone;
    private Long inationwide;
    private String name;
    private String note;
}
