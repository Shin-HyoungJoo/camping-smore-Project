package com.green.campingsmore.admin.order.refund.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SelRefundVo {
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
