package com.green.campingsmore.order.payment;

import static com.green.campingsmore.entity.QCampEntity.*;
import static com.green.campingsmore.entity.QCartEntity.cartEntity;
import static com.green.campingsmore.entity.QOrderItemEntity.*;

import static com.green.campingsmore.entity.QItemEntity.*;

import static com.green.campingsmore.entity.QReserveEntity.*;
import static com.green.campingsmore.entity.QReviewEntity.*;

import com.green.campingsmore.entity.*;
import com.green.campingsmore.order.payment.model.*;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    @Override
    public SelReserveCheckVO selReserveCheck(Long iuser) {
        LocalDate currentDate = LocalDate.now();

        return queryFactory
                .select(Projections.fields(SelReserveCheckVO.class,
                        reserveEntity.ireserve
                        ))
                .from(reserveEntity)
                .where(reserveEntity.userEntity.iuser.eq(iuser)
                        .and(reserveEntity.payStatus.eq(PayStatus.valueOf("OK")))
                        .and(reserveEntity.reservation.goe(currentDate.plusDays(5))))
                .fetchOne();

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

    public SelReserveInfoVo selCampInfo(Long ireserve) {
        return queryFactory
                .select(Projections.fields(SelReserveInfoVo.class,
                        campEntity.icamp,
                        campEntity.name,
                        campEntity.address,
                        campEntity.mainPic,
                        campEntity.campPhone,
                        reserveEntity.reservation
                ))
                .from(campEntity)
                .innerJoin(reserveEntity)
                .on(campEntity.icamp.eq(reserveEntity.campEntity.icamp))
                .where(reserveEntity.ireserve.eq(ireserve))
                .fetchOne();
    }

    public List<CartPaymentItemDto> selPaymentPageItemList(CartPKDto dto) {
        List<Long> list = dto.getIcart();

        return queryFactory.select(Projections.fields(CartPaymentItemDto.class,
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