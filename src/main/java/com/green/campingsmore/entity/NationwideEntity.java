package com.green.campingsmore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "nationwide")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class NationwideEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,columnDefinition = "BIGINT UNSIGNED")
    @NotNull
    private Long inationwide;

    @Column(updatable = false)
    @NotNull
    private String city;
}
