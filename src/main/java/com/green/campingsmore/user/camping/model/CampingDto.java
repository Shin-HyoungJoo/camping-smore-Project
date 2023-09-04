package com.green.campingsmore.user.camping.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class CampingDto {
    private Integer capacity;
    private Integer quantity;
    private LocalDate reservation;
    private Integer price;
    private String address;
    private String campPhone;
    private Long inationwide;
    private String name;
    private String note;
}
