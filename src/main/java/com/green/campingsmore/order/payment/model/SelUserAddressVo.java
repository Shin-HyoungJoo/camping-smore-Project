package com.green.campingsmore.order.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SelUserAddressVo {
    private String userAddress;
    private String userAddressDetail;
    private String name;
    private String phone;
}
