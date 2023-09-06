package com.green.campingsmore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "visitor")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@DynamicInsert
public class VisitorEntity {
    // 그냥 방문자 조회해서 넣으면 됨 ㅋ
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 설정
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED", length = 15)
    private Long ivisit;

    @Column(name = "birth_date")
    private String birthDate;


    private Integer gender;
}
