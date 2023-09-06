package com.green.campingsmore.admin.order.ordermanage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelOrderManageVo {
    private String orderDate;
    private Long iorder;
    private String name;
    private Integer orderPrice;
    private Integer totalPrice;
    private Integer shippingStatus;
    private String refundStatus;
}
