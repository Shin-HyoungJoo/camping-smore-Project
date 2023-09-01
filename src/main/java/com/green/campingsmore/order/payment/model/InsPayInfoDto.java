package com.green.campingsmore.order.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;

import java.util.List;

@Data
public class InsPayInfoDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long iuser;
    private Long iReserve;
    private String address;
    private String addressDetail;
    private Long totalPrice;
    private Long shippingPrice;
    private String shippingMemo;
    private List<PayDetailInfoVo> purchaseList;
}
