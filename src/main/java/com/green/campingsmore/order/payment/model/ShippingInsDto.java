package com.green.campingsmore.order.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShippingInsDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long iuser;
    private String address;
    private String addressDetail;
    private String name;
    private String phone;
}
