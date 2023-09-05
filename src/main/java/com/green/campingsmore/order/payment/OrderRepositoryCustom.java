package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.*;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryCustom {
    List<SelPaymentDetailDto> selPaymentDetailAll(Long iuser);
    Integer selPriceFromItem(Long iitem);
    Optional<PaymentCompleteDto> selPaymentComplete(Long iorder);
    PaymentDetailDto selPaymentPageItem(Long iitem);
    PaymentDetailDto selCampInfo(Long iitem);
    List<PaymentDetailDto> selPaymentPageItemList(CartPKDto dto);
    SelDetailedItemPaymentInfoVo selDetailedItemPaymentInfo(Long iorderItem);
}
