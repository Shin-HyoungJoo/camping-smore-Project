package com.green.campingsmore.order.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelReserveInfoVo {
    private Long icamp;
    private Long camp;
    private Long address;
    private Long maicPic;
    private Long campNumber;
    private Long reservation;
}