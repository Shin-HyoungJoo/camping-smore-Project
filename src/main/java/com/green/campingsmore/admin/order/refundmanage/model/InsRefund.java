package com.green.campingsmore.admin.order.refundmanage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsRefund {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long iuser;
    private Long iorderItem;
    private LocalDateTime refundStartDate;
    private Integer quantity;
    private Integer totalPrice;
}
