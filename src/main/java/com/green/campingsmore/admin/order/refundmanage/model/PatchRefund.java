package com.green.campingsmore.admin.order.refundmanage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchRefund {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long iuser;
    private Long irefund;
    private Integer refundStatus;
}
