package com.green.campingsmore.order.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.green.campingsmore.entity.PayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

public class InsPayInfoDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long iuser;
    private Long ireserve;
    private String address;
    private String addressDetail;
    private Long totalPrice;
    private Long shippingPrice;
    private String shippingMemo;
    private PayType type;
    private List<PayDetailInfoVo> purchaseList;
}
