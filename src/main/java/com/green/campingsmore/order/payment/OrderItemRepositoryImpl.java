package com.green.campingsmore.order.payment;

import com.green.campingsmore.entity.OrderItemEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.green.campingsmore.entity.QOrderItemEntity.orderItemEntity;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public OrderItemEntity selByIorderitem(Long iorderitem) {
         return queryFactory
                 .selectFrom(orderItemEntity)
                 .where(orderItemEntity.iorderitem.eq(iorderitem))
                 .fetchOne();
    }
}