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
    @ManyToOne
    private UserEntity userEntity;

    @JoinColumn(name = "ireserve")
    @ManyToOne
    private ReserveEntity reserveEntity;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(name = "address_detail", length = 100)
    private String addressDetail;

    @Column(name = "total_price", nullable = false, columnDefinition = "INT UNSIGNED", length = 10)
    private Integer totalPrice;

    @Column(name = "shipping_price", nullable = false, length = 10, columnDefinition = "INT UNSIGNED")
    @ColumnDefault("3000")
    private Integer shippingPrice;

    @Column(name = "shipping_memo", length = 100)
    private String shippingMemo;

    @Column(name = "del_yn", columnDefinition = "TINYINT not null DEFAULT 1", length = 1)
    private Integer delYn;

    //KAKAO, CARD
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PayType type;

    //0준비중 1배송중 2배송완료 3배송취소
    @Column(columnDefinition = "TINYINT not null DEFAULT 0 CHECK(shipping in (0,1,2,3))", length = 1)
    private Integer shipping;
}
