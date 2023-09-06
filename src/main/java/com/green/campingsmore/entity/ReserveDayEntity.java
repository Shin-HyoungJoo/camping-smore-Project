package com.green.campingsmore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "daily")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class ReserveDayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,nullable = false,columnDefinition = "BIGINT UNSIGNED")
    private Long iday;

    private LocalDate date;

    @Column(name = "day_quantity",nullable = false,columnDefinition = "INT UNSIGNED")
    private Integer dayQuantity;

    @ManyToOne
    @JoinColumn(name = "icamp",nullable = false)
    private CampEntity campEntity;

}