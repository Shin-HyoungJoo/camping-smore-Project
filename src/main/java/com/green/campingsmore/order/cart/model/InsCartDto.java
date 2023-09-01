package com.green.campingsmore.order.cart.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InsCartDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long icart;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long iuser;
    private Long iitem;
    private Long quantity;
}