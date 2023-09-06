package com.green.campingsmore.order.payment.model;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartPaymentDetailDto {
    private List<CartPaymentItemDto> itemList;
    private Integer shippingPrice;
    private Integer totalPrice;
    private Integer reserveYn;
    private SelReserveInfoVo campInfo;
}