package com.green.campingsmore.order.payment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ShippingListSelVo {
    private Long iaddress;
    private String address;
    private String addressDetail;
    private String name;
    private String phone;
}
