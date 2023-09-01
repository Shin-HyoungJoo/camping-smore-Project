package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.PaymentCompleteDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.green.campingsmore.entity.QOrderEntity.orderEntity;

@Repository
@RequiredArgsConstructor
public class PayRepositoryImpl implements PayRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PaymentCompleteDto> selPaymentComplete(Long iorder) {
        return Optional.ofNullable(queryFactory
                .select(Projections.fields(PaymentCompleteDto.class,
                        orderEntity.address,
                        orderEntity.addressDetail,
                        orderEntity.shippingMemo,
                        orderEntity.totalPrice
                ))
                .from(orderEntity)
                .where(orderEntity.iorder.eq(iorder).and(orderEntity.delYn.eq(1)))
                .fetchOne());
    }
}
