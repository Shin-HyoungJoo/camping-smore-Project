package com.green.campingsmore.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.campingsmore.jpa.BaseEntity;
import jakarta.persistence.*;
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
@EqualsAndHashCode
@ToString(callSuper = true)
@SuperBuilder
public class RefundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, columnDefinition = "BIGINT UNSIGNED", length = 20)
    private Long irefund;

    @ManyToOne
    @JoinColumn(name = "iuser")
    @ToString.Exclude
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "iorder_item")
    @ToString.Exclude
    private OrderItemEntity orderItemEntity;

    @Column(nullable = false, name = "refund_start_date")
    private LocalDateTime refundStartDate;      //환불접수일

    @Column(name = "refund_end_date")
    private LocalDateTime refundEndDate;      //환불종료일

    @Column(nullable = false, columnDefinition = "TINYINT", length = 2)
    private Integer quantity;

    @Column(name = "total_price", nullable = false, columnDefinition = "INT UNSIGNED", length = 10)
    private Integer totalPrice;

    @Column(name = "refund_status", columnDefinition = "TINYINT not null DEFAULT 0 CHECK(refund_status in (0,1,2,3))", length = 1)
    private Integer refundStatus;

    @Column(name = "del_yn",  columnDefinition = "TINYINT NOT NULL DEFAULT 1", length = 1)
    private Integer delYn;
}
