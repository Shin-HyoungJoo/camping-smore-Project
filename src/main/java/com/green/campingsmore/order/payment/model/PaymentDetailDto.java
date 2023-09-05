package com.green.campingsmore.order.payment.model;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDetailDto {
    private Long iitem;
    private String name;
    private Integer price;
    private Integer quantity;
    private Integer totalPrice;
    private String pic;
    private SelReserveInfoVo campInfo;
}