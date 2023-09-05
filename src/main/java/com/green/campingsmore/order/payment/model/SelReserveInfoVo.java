package com.green.campingsmore.order.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelReserveInfoVo {
    private Long icamp;
    private String name;
    private String address;
    private String mainPic;
    private String campPhone;
    private LocalDate reservation;
}