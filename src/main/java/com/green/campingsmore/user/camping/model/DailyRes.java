package com.green.campingsmore.user.camping.model;

import com.green.campingsmore.entity.CampEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class DailyRes {
    private Long iday;
    private LocalDate date;
    private Integer dayQuantity;
    private CampEntity campEntity;
}
