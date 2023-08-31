package com.green.campingsmore.user.camping.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CampingRes {
    private Long icamp;
    private Integer capacity;
    private Integer quantity;
    private String address;
    private String campPhone;
    private String city;
    private String mainPic;
    private String name;
    private String note;
}
