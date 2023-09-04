package com.green.campingsmore.order.cart.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelCartVo {
    private Long icart;
    private String pic;
    private String name;
    private Integer price;
    private Integer quantity;
}
