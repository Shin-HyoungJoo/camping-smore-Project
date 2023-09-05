package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.*;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryCustom {
    List<SelPaymentDetailDto> selPaymentDetailAll(Long iuser);
    Integer selPriceFromItem(Long iitem);
    Optional<PaymentCompleteDto> selPaymentComplete(Long iorder);
    SelReserveCheckVO selReserveCheck(Long iuser);
    PaymentDetailDto selPaymentPageItem(Long iitem);
    SelReserveInfoVo selCampInfo(Long ireserve);
    List<CartPaymentItemDto> selPaymentPageItemList(CartPKDto dto);
    SelDetailedItemPaymentInfoVo selDetailedItemPaymentInfo(Long iorderItem);
}
