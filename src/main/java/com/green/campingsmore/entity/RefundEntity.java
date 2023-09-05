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
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "iorder_item")
    private OrderItemEntity orderItemEntity;

    @Column(nullable = false, name = "refund_start_date")
    private LocalDateTime refundStartDate;      //환불접수일

    @Column(name = "refund_end_date")
    private LocalDateTime refundEndDate;      //환불종료일

    @Column(nullable = false, columnDefinition = "TINYINT", length = 2)
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED", length = 10)
    private Integer totalPrice;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    @ColumnDefault("0")
    @Check(constraints = "refund_status IN (0, 1, 2)")
    private Integer refundStatus;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    @ColumnDefault("1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer delYn;
}
