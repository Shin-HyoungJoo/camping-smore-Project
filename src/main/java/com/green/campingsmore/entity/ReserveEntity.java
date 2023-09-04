package com.green.campingsmore.entity;


import com.green.campingsmore.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "reserve")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ReserveEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,columnDefinition = "BIGINT UNSIGNED")
    private Long ireserve;

    @Column(nullable = false)
    @Size(min = 2)
    private String name;

    @Column(nullable = false,length = 11)
    @Size(min = 11)
    private String phone;


    @Column(name = "pay_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Column(name = "pay_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @ManyToOne
    @JoinColumn(name = "icamp",nullable = false)
    private CampEntity campEntity;

    @ManyToOne
    @JoinColumn(name = "iuser",nullable = false)
    private UserEntity userEntity;


}
