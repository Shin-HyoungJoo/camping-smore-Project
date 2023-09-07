package com.green.campingsmore.user.camping.model;

import com.green.campingsmore.entity.CampEntity;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DailyList {
    private Long iday;
    private LocalDate date;

    private Integer dayQuantity;
    private Long icamp;

    public DailyList(Long iday, LocalDate date, Integer dayQuantity, Long icamp) {
        this.iday = iday;
        this.date = date;
        this.dayQuantity = dayQuantity;
        this.icamp = icamp;
    }
}
