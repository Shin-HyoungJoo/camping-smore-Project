package com.green.campingsmore.order.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PaymentDetailDto2 {
    private Long iitem;
    private String name;
    private Integer price;
    private Integer totalPrice;
    private String pic;
    private LocalDateTime paymentDate;
    private Long reviewYn;
}