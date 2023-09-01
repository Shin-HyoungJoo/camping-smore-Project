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
    @Column(updatable = false, columnDefinition = "BIGINT UNSIGNED", length = 20)
    @NotNull
    private Long ireview;

    @JoinColumn(name = "iuser")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @JoinColumn(name = "iorder")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity orderEntity;

    @JoinColumn(name = "iitem")
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemEntity itemEntity;

    @Column(length = 200)
    @NotNull
    private String reviewCtnt;

    @Column(length = 200)
    private String pic;

    @Column(columnDefinition = "TINYINT", length = 1)
    @NotNull
    @Size(min = 0, max = 5)
    @ColumnDefault("5")
    private Integer starRating;

    @Column(columnDefinition = "INT UNSIGNED", length = 10)
    private Integer reviewLike;

    @Column(columnDefinition = "TINYINT", length = 1)
    @NotNull
    @Size(min = 0, max = 1)
    @ColumnDefault("1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer delYn;

}