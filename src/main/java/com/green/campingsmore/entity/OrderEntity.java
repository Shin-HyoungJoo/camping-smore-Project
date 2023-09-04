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
    @Column(updatable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long iorder;

    @JoinColumn(name = "iuser")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @JoinColumn(name = "ireserve")
    @ManyToOne(fetch = FetchType.LAZY)
    private ReserveEntity reserveEntity;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(length = 100)
    private String addressDetail;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED", length = 10)
    private Integer totalPrice;

    @Column(nullable = false, length = 10, columnDefinition = "INT UNSIGNED")
    @ColumnDefault("3000")
    private Integer shippingPrice;

    @Column(length = 10)
    private String shippingMemo;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    @ColumnDefault("1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer delYn;

    //KAKAO, CARD
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private PayType type;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    @Check(constraints = "shipping IN (0, 1, 2)")
    @ColumnDefault("0")
    private Integer shipping;
}
