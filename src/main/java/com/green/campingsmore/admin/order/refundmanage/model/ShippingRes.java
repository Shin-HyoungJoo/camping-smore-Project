package com.green.campingsmore.admin.order.refundmanage.model;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingRes {
    private Long iorder;
    private Integer shipping;
}
