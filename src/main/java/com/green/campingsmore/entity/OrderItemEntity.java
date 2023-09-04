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

import java.io.Serializable;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@IdClass(OrderItemId.class)
public class OrderItemEntity extends BaseEntity implements Serializable {
    @Id
    @JoinColumn(name = "iorder")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity orderEntity;

    @Id
    @JoinColumn(name = "iitem")
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemEntity itemEntity;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED", length = 10)
    private Integer price;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 2)
    @ColumnDefault("1")
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED", length = 10)
    private Integer totalPrice;

    //0 - default, 1 - 환불 진행중, 2 - 환불 완료, 3 - 환불 불가
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    @ColumnDefault("0")
    @Check(constraints = "refund IN (0, 1, 2, 3)")
    private Integer refund;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    @ColumnDefault("1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer delYn;
}