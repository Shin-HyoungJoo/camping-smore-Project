package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.PaymentCompleteDto;

import java.util.Optional;

public interface PayRepositoryCustom {
    Optional<PaymentCompleteDto> selPaymentComplete(Long iorder);
}
