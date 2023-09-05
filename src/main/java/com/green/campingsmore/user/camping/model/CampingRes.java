package com.green.campingsmore.user.camping.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CampingRes {
    private Long icamp;
    private Integer capacity;
    private Integer quantity;
    private String address;
    private String campPhone;
    private Long inationwide;
    private String mainPic;
    private String name;
    private String note;
    private Integer delyn;
    private Integer price;
    private LocalDate reservation;
}
