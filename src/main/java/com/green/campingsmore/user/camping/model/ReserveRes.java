package com.green.campingsmore.user.camping.model;

import com.green.campingsmore.entity.CampEntity;
import com.green.campingsmore.entity.PayStatus;
import com.green.campingsmore.entity.PayType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ReserveRes {
    private Long ireserve;
    private String name;
    private String phone;
    private Integer price;
    private PayType payType;
    private PayStatus payStatus;
    private Long icamp;
    private Long iuser;
    private CampEntity campEntity;
}
