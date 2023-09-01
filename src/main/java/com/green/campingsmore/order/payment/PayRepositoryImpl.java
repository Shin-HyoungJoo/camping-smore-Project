package com.green.campingsmore.order.payment;

import static com.green.campingsmore.entity.CampEntity.*;
import static com.green.campingsmore.entity.QOrderItemEntity.*;

import static com.green.campingsmore.entity.QItemEntity.*;

import static com.green.campingsmore.entity.QReviewEntity.*;

import com.green.campingsmore.entity.OrderEntity;
import com.green.campingsmore.entity.OrderItemEntity;
import com.green.campingsmore.entity.QReviewEntity;
import com.green.campingsmore.entity.UserEntity;
import com.green.campingsmore.order.payment.model.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public Long insPayInfo(InsPayInfoDto dto) {
        Long result1 = queryFactory    //order insert
                .insert(orderEntity)
                .set(orderEntity.userEntity, UserEntity.builder().iuser(dto.getIuser()).build())
                .set(orderEntity.campEntity, builder().icamp(Optional.ofNullable(dto.getIreserve()).orElse(0L)).build())
                .set(orderEntity.address, dto.getAddress())
                .set(orderEntity.addressDetail, dto.getAddressDetail())
                .set(orderEntity.totalPrice, dto.getTotalPrice())
                .set(orderEntity.shippingMemo, dto.getShippingMemo())
                .set(orderEntity.type, dto.getType())
                .execute();

        if (result1 != 1L) {
            return 0L;
        }

        List<PayDetailInfoVo> purchaseList = dto.getPurchaseList();

        for (PayDetailInfoVo purchaseItem : purchaseList) { //order_detail multi insert
            Long result2 = queryFactory
                    .insert(orderItemEntity)
                    .columns(
                            orderItemEntity.orderEntity.iorder,
                            orderItemEntity.itemEntity.iitem,
                            orderItemEntity.price,
                            orderItemEntity.quantity,
                            orderItemEntity.totalPrice
                    )
                    .values(
                            JPAExpressions
                                    .select(orderEntity.iorder)
                                    .from(orderEntity)
                                    .orderBy(orderEntity.iorder.desc())
                                    .offset(0)
                                    .limit(1),
                            purchaseItem.getIitem(),
                            JPAExpressions
                                    .select(itemEntity.price)
                                    .from(itemEntity)
                                    .where(itemEntity.iitem.eq(dto.getPurchaseList().get(0).getIitem())),
                            purchaseItem.getQuantity(),
                            purchaseItem.getTotalPrice()
                    ).execute();
            if (result2 != 1L) {
                return 0L;
            }
        }
        return 1L;
    }

    @Override
    public Optional<List<SelPaymentDetailDto>> selPaymentDetailAll(Long iuser) {
        List<Long> orderList = queryFactory
                .select(orderEntity.iorder)
                .from(orderEntity)
                .where(orderEntity.userEntity.iuser.eq(iuser)
                        .and(orderEntity.delYn.eq(1)))
                .orderBy(orderEntity.createdAt.desc())
                .fetch();


        List<SelPaymentDetailDto> result = new ArrayList<>();

        for (Long iorder : orderList) {
            List<PaymentDetailDto2> dto = queryFactory
                    .select(Projections.fields(PaymentDetailDto2.class,
                            itemEntity.iitem,
                            itemEntity.name,
                            orderItemEntity.price,
                            orderItemEntity.totalPrice,
                            itemEntity.pic,
                            orderEntity.createdAt.as("paymentDate"),
                            ExpressionUtils.as(
                                    JPAExpressions
                                            .select(reviewEntity.ireview.coalesce(0L))
                                            .from(reviewEntity)
                                            .where(reviewEntity.itemEntity.iitem.eq(itemEntity.iitem)
                                                    .and(reviewEntity.delYn.eq(1))), "reviewYn")))
                    .from(orderItemEntity)
                    .innerJoin(itemEntity).on(orderItemEntity.itemEntity.iitem.eq(itemEntity.iitem))
                    .innerJoin(orderEntity).on(orderItemEntity.orderEntity.iorder.eq(orderEntity.iorder))
                    .where(orderEntity.iorder.eq(iorder).and(orderItemEntity.delYn.eq(1)))
                    .fetch();

            SelPaymentDetailDto item = SelPaymentDetailDto.builder()    //조립
                    .iorder(iorder)
                    .itemList(dto)
                    .build();

            result.add(item);
        }
        return Optional.ofNullable(result);
    }
}
//queryFactory
//        .select(Projections.fields(SelPaymentDetailDto.class),
//        itemEntity.iitem,
//        itemEntity.name,
//        orderItemEntity.price,
//        orderItemEntity.totalPrice,
//        itemEntity.pic,
//        orderEntity.createdAt.as("paymentDate"),
//        ExpressionUtils.as(
//        JPAExpressions
//        .select(reviewEntity.ireview.coalesce(0L))
//        .from(reviewEntity)
//        .where(reviewEntity.itemEntity.iitem.eq(itemEntity.iitem)
//        .and(reviewEntity.delYn.eq(1))), "reviewYn"))
//        .from(orderItemEntity)
//        .innerJoin(itemEntity).on(orderItemEntity.itemEntity.iitem.eq(itemEntity.iitem))
//        .innerJoin(orderEntity).on(orderItemEntity.orderEntity.iorder.eq(orderEntity.iorder))
//        .where(orderEntity.iorder.eq(iorder).and(orderItemEntity.delYn.eq(1)))
//        .fetch();