package com.green.campingsmore.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.campingsmore.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED", length = 20)
    private Long ireview;

    @JoinColumn(name = "iuser")
    @ManyToOne
    private UserEntity userEntity;

    @JoinColumn(name = "iorder")
    @ManyToOne
    private OrderEntity orderEntity;

    @JoinColumn(name = "iitem")
    @ManyToOne
    private ItemEntity itemEntity;

    @Column(name = "review_ctnt",nullable = false, length = 200)
    private String reviewCtnt;

    private String pic;

    @Column(name = "star_rating", columnDefinition = "TINYINT not null DEFAULT 1 CHECK(star_rating in (0,1,2,3,4,5))", length = 1)
    private Integer starRating;

    @Column(name = "review_like",nullable = false, columnDefinition = "INT UNSIGNED", length = 10)
    private Integer reviewLike;

    @Column(name = "del_yn", columnDefinition = "TINYINT not null DEFAULT 1 CHECK(del_yn in (0,1))", length = 1)
    private Integer delYn;
}