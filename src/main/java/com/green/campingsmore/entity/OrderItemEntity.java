package com.green.campingsmore.entity;


import com.green.campingsmore.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class OrderItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iorder_item", nullable = false, updatable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long iorderitem;

    @JoinColumn(name = "iorder")
    @ManyToOne
    @ToString.Exclude
    private OrderEntity orderEntity;

    @JoinColumn(name = "iitem")
    @ManyToOne
    @ToString.Exclude
    private ItemEntity itemEntity;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED", length = 10)
    private Integer price;

    @Column(columnDefinition = "TINYINT not null DEFAULT 0", length = 2)
    private Integer quantity;

    @Column(name = "total_price", nullable = false, columnDefinition = "INT UNSIGNED", length = 10)
    private Integer totalPrice;

    //0 - default, 1 - 환불 진행중, 2 - 환불 완료, 3 - 환불 불가
    @Column(columnDefinition = "TINYINT not null DEFAULT 0 CHECK(refund in (0,1,2,3))", length = 1)
    private Integer refund;

    @Column(name = "del_yn", columnDefinition = "TINYINT not null DEFAULT 1", length = 1)
    private Integer delYn;
}