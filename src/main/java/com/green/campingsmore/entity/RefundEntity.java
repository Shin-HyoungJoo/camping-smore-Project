package com.green.campingsmore.entity;

import com.green.campingsmore.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
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

    @JoinColumn(name = "iuser")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "iorder", referencedColumnName = "iorder")
    private OrderItemEntity orderItemEntityIorder;

    @OneToOne
    @JoinColumn(name = "iitem", referencedColumnName = "iitem")
    private OrderItemEntity orderItemEntityIitem;

    @Column(name = "refund_date")
    @NotNull
    private LocalDateTime refundDate;      //주문일

    @Column(name = "refund_start_date")
    @NotNull
    private LocalDateTime refundStartDate;      //환불접수일

    @Column(name = "refund_end_date")
    private LocalDateTime refundEndDate;      //환불접수일

    @Column(columnDefinition = "TINYINT", length = 2)
    @NotNull
    private Long quantity;

    @Column(columnDefinition = "BIGINT UNSIGNED", length = 20)
    @NotNull
    private Long totalPrice;

    @Column(columnDefinition = "TINYINT", length = 1)
    @ColumnDefault("1")
    @NotNull
    private Integer refundStatus;
}