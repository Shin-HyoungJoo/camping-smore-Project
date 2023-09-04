package com.green.campingsmore.order.payment.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SelPaymentDetailDto {
    private Long iorder;
    private List<PaymentDetailDto2> itemList;
}