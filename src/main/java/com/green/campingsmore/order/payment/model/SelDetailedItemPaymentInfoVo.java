package com.green.campingsmore.order.payment.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SelDetailedItemPaymentInfoVo {
    private Long iorderitem;
    private Long iitem;
    private String name;
    private Integer price;
    private Integer quantity;
    private Integer totalPrice;
    private String pic;
    private LocalDateTime paymentDate;
    private String address;
    private String addressDetail;
    private Integer shippingPrice;
    private String shippingMemo;
}