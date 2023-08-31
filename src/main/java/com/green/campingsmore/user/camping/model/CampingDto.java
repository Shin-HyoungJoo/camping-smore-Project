package com.green.campingsmore.user.camping.model;

import lombok.Data;

@Data
public class CampingDto {
    private Integer capacity;
    private Integer quantity;
    private String address;
    private String campPhone;
    private String city;
    private String mainPic;
    private String name;
    private String note;
}
