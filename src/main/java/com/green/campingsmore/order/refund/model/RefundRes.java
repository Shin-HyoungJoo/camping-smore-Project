package com.green.campingsmore.order.refund.model;

import com.green.campingsmore.entity.OrderItemEntity;
import com.green.campingsmore.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefundRes {
    private Long irefund;
    private Long iuser;
    private Long iorder;
    private Long iitem;
    private LocalDateTime refundStartDate;      //환불접수일
    private LocalDateTime refundEndDate;      //환불종료일
    private Integer quantity;
    private Integer totalPrice;
    private Integer refundStatus;
}
