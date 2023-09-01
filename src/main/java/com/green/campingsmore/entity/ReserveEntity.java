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
    @NotNull
    private Long ireserve;


    @NotNull
    private LocalDate reservation;

    @NotNull
    @Size(min = 2)
    private String name;

    @Column(length = 11)
    @NotNull
    @Size(min = 11)
    private String phone;


    @NotNull
    private Integer price;

    @Column(name = "pay_type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Column(name = "pay_status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @ManyToOne
    @JoinColumn(name = "icamp")
    @NotNull
    private CampEntity campEntity;

    @ManyToOne
    @JoinColumn(name = "iuser")
    @NotNull
    private UserEntity userEntity;


}
