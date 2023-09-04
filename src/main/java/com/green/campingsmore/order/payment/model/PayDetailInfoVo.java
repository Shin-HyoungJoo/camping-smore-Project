package com.green.campingsmore.order.payment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PayDetailInfoVo {
    private Long iitem;
    private Integer quantity;
    private Integer totalPrice;
}
