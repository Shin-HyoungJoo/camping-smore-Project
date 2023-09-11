package com.green.campingsmore.user.camping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CampingDetailList1 {
        private Long icamp;
        private String name;
        private String campPhone;
        private String address;
        private Integer price;
        private Integer capacity;
        private Integer quantity;
        private String note;
        private List<CampingPicList> pic;

    public CampingDetailList1(Long icamp, String name, String campPhone, String address, Integer price, Integer capacity, Integer quantity, String note, List<CampingPicList> pic) {
        this.icamp = icamp;
        this.name = name;
        this.campPhone = campPhone;
        this.address = address;
        this.price = price;
        this.capacity = capacity;
        this.quantity = quantity;
        this.note = note;
        this.pic = pic;
    }
}
