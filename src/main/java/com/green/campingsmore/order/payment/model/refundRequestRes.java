package com.green.campingsmore.order.payment.model;

import com.green.campingsmore.entity.RefundEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class refundRequestRes {
    private Long iorderItem;
    private Long iitem;
    private Integer refund;
}
