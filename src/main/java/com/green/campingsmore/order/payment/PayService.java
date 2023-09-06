package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.*;

import java.util.List;
import java.util.Optional;

public interface PayService {
    //결제 관련
    Long insPayInfo(InsPayInfoDto dto) throws Exception;
    Optional<PaymentCompleteDto> selPaymentComplete(Long iorder);
    List<SelPaymentDetailDto> selPaymentDetailAll(Long iuser);
    PaymentDetailDto selPaymentPageItem(Long iitem, Integer quantity, Long iuser);
    CartPaymentDetailDto selPaymentPageItemList(CartPKDto dto, Long iuser);
    Long delPaymentDetail(Long iorderItem) throws Exception;
    SelDetailedItemPaymentInfoVo selDetailedItemPaymentInfo(Long iorderItem);

    //주소 관련
    Long insAddress(ShippingInsDto dto);
    SelUserAddressVo selUserAddress(Long iuser);
    List<ShippingListSelVo> selAddressList(Long iuser);
    ShippingListSelVo selOneAddress(SelUserAddressDto dto);
    Long delAddress(Long iaddress);
    refundRequestRes refundRequest(Long iorderItem, Long iuser) throws Exception;
}