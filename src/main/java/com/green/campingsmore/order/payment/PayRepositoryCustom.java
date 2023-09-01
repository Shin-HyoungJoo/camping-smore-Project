package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import com.green.campingsmore.order.payment.model.PaymentCompleteDto;
import com.green.campingsmore.order.payment.model.SelPaymentDetailDto;

import java.util.List;
import java.util.Optional;

public interface PayRepositoryCustom {
    Optional<PaymentCompleteDto> selPaymentComplete(Long iorder);
    Long insPayInfo(InsPayInfoDto dto);
    Optional<List<SelPaymentDetailDto>> selPaymentDetailAll(Long iuser);

}
