package com.green.campingsmore.user.camping.model;

import com.green.campingsmore.entity.PayStatus;
import com.green.campingsmore.entity.PayType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReserveDto {
    private LocalDate reservation;
    private String name;
    private String phone;
    private PayType payType;
    private Long icamp;
    private Long iuser;
}
