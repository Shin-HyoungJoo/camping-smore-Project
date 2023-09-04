package com.green.campingsmore.order.cart.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartRes {
    private Long icart;
    private Long iuser;
    private Long iitem;
    private Integer quantity;
}