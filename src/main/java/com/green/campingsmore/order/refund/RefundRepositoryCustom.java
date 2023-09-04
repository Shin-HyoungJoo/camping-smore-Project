package com.green.campingsmore.order.refund;

import com.green.campingsmore.order.refund.model.SelRefundVo;

import java.util.List;

public interface RefundRepositoryCustom {
    List<SelRefundVo> selRefund(Long iuser);
}
