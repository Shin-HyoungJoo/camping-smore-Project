package com.green.campingsmore.order.payment;

import com.green.campingsmore.admin.main.model.SelAggregateVO;
import com.green.campingsmore.entity.*;
import com.green.campingsmore.order.payment.model.*;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.green.campingsmore.entity.QCampEntity.campEntity;
import static com.green.campingsmore.entity.QCartEntity.cartEntity;
import static com.green.campingsmore.entity.QItemEntity.itemEntity;
import static com.green.campingsmore.entity.QOrderEntity.orderEntity;
import static com.green.campingsmore.entity.QOrderItemEntity.orderItemEntity;
import static com.green.campingsmore.entity.QReserveEntity.reserveEntity;
import static com.green.campingsmore.entity.QReviewEntity.reviewEntity;

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
    public List<SelAggregateVO> selAggregateInfo() {
        QOrderEntity orderA =new QOrderEntity("orderA");
        QOrderItemEntity orderItemB = new QOrderItemEntity("orderItemB");
        QRefundEntity refundC = new QRefundEntity("refundC");
        QOrderEntity orderAA = new QOrderEntity("orderAA");
        QOrderEntity orderBB = new QOrderEntity("orderBB");
        QRefundEntity refundCC = new QRefundEntity("refundCC");

        StringTemplate orderADate = getDateFormat(orderA.createdAt);
        StringTemplate orderAADate = getDateFormat(orderAA.createdAt);
        StringTemplate orderBBDate = getDateFormat(orderBB.createdAt);
        StringTemplate refundCDate = getDateFormat(refundC.refundStartDate);
        StringTemplate refundCCDate = getDateFormat(refundCC.refundEndDate);

        return queryFactory.select(Projections.fields(SelAggregateVO.class,
                        orderADate.as("date"),
                new CaseBuilder().when(orderA.shipping.in(0,1)).then(orderA.totalPrice).otherwise(0).sum().as("orderTotalPrice"),
                ExpressionUtils.as(
                        JPAExpressions
                                .select(orderAA.iorder.count())
                                .from(orderAA)
                                .where(orderAA.shipping.in(0,1).and(orderADate.eq(orderAADate)))
                        ,"orderTotalCount"),
                new CaseBuilder().when(orderA.shipping.in(2)).then(orderA.totalPrice).otherwise(0).sum().as("shippingCompleteTotalPrice"),
                ExpressionUtils.as(
                        JPAExpressions
                                .select(orderBB.iorder.count())
                                .from(orderBB)
                                .where(orderBB.shipping.in(2).and(orderADate.eq(orderBBDate)))
                        ,"shippingCompleteTotalCount"),
                ExpressionUtils.as(
                        JPAExpressions
                                .select(refundC.totalPrice.sum())
                                .from(refundC)
                                .where(refundCDate.eq(orderADate))
                        ,"refundTotalPrice"),
                ExpressionUtils.as(
                        JPAExpressions
                                .select(refundCC.irefund.count())
                                .from(refundCC)
                                .where(refundCCDate.eq(orderADate))
                        ,"refundTotalCount")
                ))
                .from(orderA)
                .leftJoin(orderItemB).on(orderA.iorder.eq(orderItemB.orderEntity.iorder))
                .groupBy(orderADate)
                .orderBy(orderADate.desc())
                .fetch();
    }

    public StringTemplate getDateFormat(DateTimePath<LocalDateTime> date) {
        return Expressions.stringTemplate("DATE_FORMAT( {0}, {1} )", date, ConstantImpl.create("%m월 %d일"));
    }

    @Override
    public List<SelPaymentDetailDto> selPaymentDetailAll(Long iuser) {
        List<SelPaymentOrderDto> orderList = queryFactory
                .select(Projections.fields(SelPaymentOrderDto.class,
                        orderEntity.iorder,
                        orderEntity.shipping

                ))
                .from(orderEntity)
                .where(orderEntity.userEntity.iuser.eq(iuser)
                        .and(orderEntity.delYn.eq(1)))
                .orderBy(orderEntity.createdAt.desc())
                .fetch();


        List<SelPaymentDetailDto> result = new ArrayList<>();

        for (SelPaymentOrderDto list : orderList) {
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
                    .where(orderEntity.iorder.eq(list.getIorder()).and(orderItemEntity.delYn.eq(1)))
                    .fetch();

            SelPaymentDetailDto item = SelPaymentDetailDto.builder()    //조립
                    .iorder(list.getIorder())
                    .shipping(list.getShipping())
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