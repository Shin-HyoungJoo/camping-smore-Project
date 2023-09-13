package com.green.campingsmore.order.payment;

import com.green.campingsmore.entity.OrderItemEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.green.campingsmore.entity.QOrderEntity.orderEntity;
import static com.green.campingsmore.entity.QOrderItemEntity.orderItemEntity;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public OrderItemEntity selByIorderitem(Long iorderitem) {
         return queryFactory
                 .selectFrom(orderItemEntity)
                 .where(orderItemEntity.iorderitem.eq(iorderitem)
                         .and(orderItemEntity.delYn.eq(1)))
                 .fetchOne();
    }

    @Override
    public List<Long> orderItemList(Long iorder) {
        List<Long> result =
                queryFactory
                .select(orderItemEntity.iorderitem)
                .from(orderItemEntity)
                .where(orderItemEntity.orderEntity.iorder.eq(iorder))
                .fetch();
        return result;
    }
}