package com.green.campingsmore.admin.order.refundmanage;

import com.green.campingsmore.admin.order.refundmanage.model.SelRefundManageVo;
import com.green.campingsmore.admin.order.refundmanage.model.SelRefundVo;

import java.time.LocalDate;
import java.util.List;

public interface RefundRepositoryCustom {
    List<SelRefundVo> selRefund(Long iuser);
    List<SelRefundManageVo> SelRefundManageList(LocalDate startDate, LocalDate endDate, Integer listBox, Object keyword);
}
