package com.green.campingsmore.user.camping.model;

import com.green.campingsmore.entity.CampPicEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class CampingDetailList {
    private Long icamp;
    private String name;
    private String campPhone;
    private String address;
    private Integer price;
    private Integer capacity;
    private Integer quantity;
    private String note;
    private List<String> pic; // pic을 List<String>으로 변경

    public CampingDetailList(Long icamp, String name, String campPhone, String address, Integer price, Integer capacity, Integer quantity, String note, String pic) {
        this.icamp = icamp;
        this.name = name;
        this.campPhone = campPhone;
        this.address = address;
        this.price = price;
        this.capacity = capacity;
        this.quantity = quantity;
        this.note = note;
        this.pic = Arrays.asList(pic.split(", ")); // 쉼표와 공백으로 분리하여 리스트로 변환
    }
}




