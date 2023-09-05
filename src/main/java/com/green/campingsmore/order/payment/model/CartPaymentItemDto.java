package com.green.campingsmore.order.payment.model;
import lombok.Data;

@Data
public class CartPaymentItemDto {
    private Long iitem;
    private String name;
    private Integer price;
    private Integer quantity;
    private String pic;
}