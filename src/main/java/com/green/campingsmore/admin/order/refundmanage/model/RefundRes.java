package com.green.campingsmore.admin.order.refundmanage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefundRes {
    private Long irefund;
    private Long iuser;
    private Long iorderitem;
    private LocalDateTime refundStartDate;      //환불접수일
    private LocalDateTime refundEndDate;      //환불종료일
    private Integer quantity;
    private Integer totalPrice;
    private Integer refundStatus;
}
