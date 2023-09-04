package com.green.campingsmore.order.payment.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShippingAddressInsRes {
    private Long iuser;
    private String address;
    private String addressDetail;
    private String name;
    private String phone;

}
