package com.green.campingsmore.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.campingsmore.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "\"order\"")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class OrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, columnDefinition = "BIGINT UNSIGNED", length = 20)
    private Long iorder;

    @JoinColumn(name = "iuser")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @JoinColumn(name = "ireserve")
    @ManyToOne(fetch = FetchType.LAZY)
    @ColumnDefault("0")
    private CampEntity campEntity;

    @Column(length = 100)
    @NotNull
    private String address;

    @Column(length = 100)
    private String addressDetail;

    @Column(columnDefinition = "BIGINT UNSIGNED", length = 11)
    @NotNull
    private Long totalPrice;

    @Column(length = 10, columnDefinition = "INT UNSIGNED")
    @ColumnDefault("3000")
    @NotNull
    private Long shippingPrice;

    @Column(length = 10)
    private String shippingMemo;

    @Column(columnDefinition = "TINYINT", length = 1)
    @ColumnDefault("1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull
    private Integer delYn;

    //KAKAO, CARD
    @Column(columnDefinition = "TINYINT", length = 1)
    @NotNull
    private PayType type;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    @Check(constraints = "shipping IN (0, 1, 2)")
    @ColumnDefault("0")
    @NotNull
    private Integer shipping;
}
