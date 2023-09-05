package com.green.campingsmore.admin.order.refund;

import com.green.campingsmore.admin.order.refund.model.SelRefundVo;

import java.util.List;

public interface RefundRepositoryCustom {
    List<SelRefundVo> selRefund(Long iuser);
}
