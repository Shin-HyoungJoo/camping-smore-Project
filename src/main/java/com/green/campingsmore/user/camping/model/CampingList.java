package com.green.campingsmore.user.camping.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampingList {
    private Long icamp;
    private String name;
    private String address;
    private String campPhone;
    private String mainPic;

    public CampingList(Long icamp, String name, String address, String campPhone, String mainPic, String city, Integer delyn) {
        this.icamp = icamp;
        this.name = name;
        this.address = address;
        this.campPhone = campPhone;
        this.mainPic = mainPic;
        this.city = city;
        this.delyn = delyn;
    }

    private String city;
    private Integer delyn;
}
