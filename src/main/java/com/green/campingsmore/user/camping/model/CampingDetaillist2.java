package com.green.campingsmore.user.camping.model;

import lombok.*;

import java.util.Arrays;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CampingDetaillist2 {
    private Long icamp;
    private String name;
    private String campPhone;
    private String address;
    private Integer price;
    private Integer capacity;
    private Integer quantity;
    private String note;
    private List<String> pic;




}
