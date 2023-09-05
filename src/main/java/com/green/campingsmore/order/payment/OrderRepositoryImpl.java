package com.green.campingsmore.order.payment;

import static com.green.campingsmore.entity.QCampEntity.*;
import static com.green.campingsmore.entity.QCartEntity.cartEntity;
import static com.green.campingsmore.entity.QOrderItemEntity.*;

import static com.green.campingsmore.entity.QItemEntity.*;

import static com.green.campingsmore.entity.QReviewEntity.*;

import com.green.campingsmore.entity.*;
import com.green.campingsmore.order.payment.model.*;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
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
public class OrderRepositoryImpl implements OrderRepositoryCustom {

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

    public PaymentDetailDto selPaymentPageItem(Long iitem) {
        PaymentDetailDto result = queryFactory
                .select(Projections.fields(PaymentDetailDto.class,
                        itemEntity.iitem,
                        itemEntity.name,
                        itemEntity.price,
                        itemEntity.pic))
                .from(itemEntity)
                .where(itemEntity.iitem.eq(iitem))
                .fetchOne();
        return result;
    }

    public PaymentDetailDto selCampInfo(Long iitem) {
        CampEntity
        queryFactory
                .select(Projections.fields(SelReserveInfoVo.class,

                        ))
                .from(campEntity)
                .join()
                .where()
    }

    public List<PaymentDetailDto> selPaymentPageItemList(CartPKDto dto) {
        List<Long> list = dto.getIcart();

        return queryFactory.select(Projections.fields(PaymentDetailDto.class,
                        cartEntity.itemEntity.iitem,
                        itemEntity.name,
                        itemEntity.price,
                        cartEntity.quantity,
                        itemEntity.pic))
                .from(cartEntity)
                .innerJoin(itemEntity)
                .on(cartEntity.itemEntity.iitem.eq(itemEntity.iitem))
                .where(cartEntity.icart.in(list))
                .fetch();
    }

    public SelDetailedItemPaymentInfoVo selDetailedItemPaymentInfo(Long iorderItem) {
        return queryFactory.select(Projections.fields(SelDetailedItemPaymentInfoVo.class,
                        orderItemEntity.iorderItem,
                        orderItemEntity.itemEntity.iitem,
                        itemEntity.name,
                        orderItemEntity.price,
                        orderItemEntity.quantity,
                        orderItemEntity.totalPrice,
                        itemEntity.pic,
                        orderEntity.createdAt.as("paymentDate"),
                        orderEntity.address,
                        orderEntity.addressDetail,
                        orderEntity.shippingPrice,
                        orderEntity.shippingMemo
                ))
                .from(orderItemEntity)
                .join(orderEntity, orderEntity)
                .join(itemEntity, itemEntity)
                .where(orderItemEntity.iorderItem.eq(iorderItem))
                .fetchOne();
    }

    @Override
    public List<SelPaymentDetailDto> selPaymentDetailAll(Long iuser) {
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
        return result;
    }

    @Override
    public Integer selPriceFromItem(Long iitem) {
        Integer integer = queryFactory
                .select(itemEntity.price)
                .from(itemEntity)
                .where(itemEntity.iitem.eq(iitem))
                .fetchOne();

        System.out.println("integer = " + integer);

        return integer;
    }
}