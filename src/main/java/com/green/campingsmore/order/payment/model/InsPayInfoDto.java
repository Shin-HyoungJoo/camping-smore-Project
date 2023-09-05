package com.green.campingsmore.order.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.green.campingsmore.entity.PayType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class InsPayInfoDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long iuser;
    private String address;
    private String addressDetail;
    private Integer totalPrice;
    private Integer shippingPrice;
    private String shippingMemo;
    private PayType type;
    private Integer ReceiveCampingYn;
    private List<PayDetailInfoVo> purchaseList;
}
