package com.green.campingsmore.admin.order.refundmanage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelRefundManageVo {
    private String orderDate;
    private String refundStartDate;      //환불접수일
    private String refundEndDate;
    private Long iorderItem;
    private String name;
    public Integer quantity;
    public Integer totalRefund;
    public Integer refundStatus;
}
