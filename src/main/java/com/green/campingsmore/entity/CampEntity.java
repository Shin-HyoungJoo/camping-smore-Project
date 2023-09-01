package com.green.campingsmore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "camp")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class CampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,nullable = false,columnDefinition = "BIGINT UNSIGNED")
    private Long icamp;


    @NotNull
    private String name;

    @Column(updatable = false)
    @NotNull
    private String address;


    @Column(name = "main_pic")
    @NotNull
    private String mainPic;

    @Column(name = "camp_phone")
    @NotNull
    @Size(min = 9,max = 20)
    private String campPhone;

    @NotNull
    private Integer quantity;

    @Column(length = 2)
    @NotNull
    private Integer capacity;

    @NotNull
    private String note;

    @Column(name = "del_yn",columnDefinition = "TINYINT not null DEFAULT 1 CHECK(del_yn in (0,1))",length = 1)
    private Integer delyn;

    @ManyToOne
    @JoinColumn(name = "inationwide")
    private NationwideEntity nationwideEntity;

}
