package com.green.campingsmore.user.camping.model;

import com.green.campingsmore.entity.CampEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyDto {
    private LocalDate date;
    private Integer dayQuantity;
    private Long icamp;
}
