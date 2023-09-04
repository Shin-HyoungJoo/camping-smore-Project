package com.green.campingsmore.entity;

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

import java.time.LocalDateTime;

@Entity
@Table(name = "refund")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class RefundEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, columnDefinition = "BIGINT UNSIGNED", length = 20)
    private Long irefund;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iuser")
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "iorder", referencedColumnName = "iorder")
    private OrderItemEntity orderItem;

    @OneToOne
    @JoinColumn(name = "iitem", referencedColumnName = "iitem")
    private OrderItemEntity item;

    @Column(nullable = false, name = "refund_date")
    private LocalDateTime refundDate;      //주문일

    @Column(nullable = false, name = "refund_start_date")
    private LocalDateTime refundStartDate;      //환불접수일

    @Column(name = "refund_end_date")
    private LocalDateTime refundEndDate;      //환불접수일

    @Column(nullable = false, columnDefinition = "TINYINT", length = 2)
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED", length = 10)
    private Integer totalPrice;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    @ColumnDefault("1")
    @Check(constraints = "shipping IN (0, 1, 2)")
    private Integer refundStatus;
}
