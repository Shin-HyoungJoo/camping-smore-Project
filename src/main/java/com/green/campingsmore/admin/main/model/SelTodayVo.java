package com.green.campingsmore.admin.main.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SelTodayVo {
    private Long userCount;
    private Long shippingBefore;
    private Long shipping;
    private Long refundBefore;
    private Long soldOut;
    private Long newBoard;
    private Long newReserve;
}
