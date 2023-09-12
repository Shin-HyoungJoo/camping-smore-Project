package com.green.campingsmore.order.payment;

import com.green.campingsmore.admin.main.model.SelAggregateVO;
import com.green.campingsmore.admin.main.model.SelTodayVo;
import com.green.campingsmore.admin.order.ordermanage.model.SelOrderManageVo;
import com.green.campingsmore.entity.*;
import com.green.campingsmore.order.payment.model.*;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.green.campingsmore.entity.QBoardEntity.*;
import static com.green.campingsmore.entity.QCampEntity.campEntity;
import static com.green.campingsmore.entity.QCartEntity.cartEntity;
import static com.green.campingsmore.entity.QItemEntity.itemEntity;
import static com.green.campingsmore.entity.QOrderEntity.orderEntity;
import static com.green.campingsmore.entity.QOrderItemEntity.orderItemEntity;
import static com.green.campingsmore.entity.QRefundEntity.*;
import static com.green.campingsmore.entity.QReserveEntity.reserveEntity;
import static com.green.campingsmore.entity.QReviewEntity.reviewEntity;
import static com.green.campingsmore.entity.QUserEntity.userEntity;

@Slf4j
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

    public SelDetailedItemPaymentInfoVo selDetailedItemPaymentInfo(Long iorderitem) {
        return queryFactory.select(Projections.fields(SelDetailedItemPaymentInfoVo.class,
                        orderItemEntity.iorderitem,
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
                .where(orderItemEntity.iorderitem.eq(iorderitem))
                .fetchOne();
    }

    @Override
    public List<SelAggregateVO> selAggregateInfo() {
        QOrderEntity orderA = new QOrderEntity("orderA");
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
                        new CaseBuilder().when(orderA.shipping.in(0, 1)).then(orderA.totalPrice).otherwise(0).sum().as("orderTotalPrice"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(orderAA.iorder.count())
                                        .from(orderAA)
                                        .where(orderAA.shipping.in(0, 1).and(orderADate.eq(orderAADate)))
                                , "orderTotalCount"),
                        new CaseBuilder().when(orderA.shipping.in(2)).then(orderA.totalPrice).otherwise(0).sum().as("shippingCompleteTotalPrice"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(orderBB.iorder.count())
                                        .from(orderBB)
                                        .where(orderBB.shipping.in(2).and(orderADate.eq(orderBBDate)))
                                , "shippingCompleteTotalCount"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(refundC.totalPrice.sum())
                                        .from(refundC)
                                        .where(refundCDate.eq(orderADate)
                                                .and(refundC.refundStatus.eq(2)))
                                , "refundTotalPrice"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(refundCC.irefund.count())
                                        .from(refundCC)
                                        .where(refundCCDate.eq(orderADate)
                                                .and(refundCC.refundStatus.eq(2)))
                                , "refundTotalCount")
                ))
                .from(orderA)
                .leftJoin(orderItemB).on(orderA.iorder.eq(orderItemB.orderEntity.iorder))
                .groupBy(orderADate)
                .orderBy(orderADate.desc())
                .limit(7)
                .fetch();
    }

    @Override
    public SelTodayVo selTodayInfo() {
        Long userCount = queryFactory
                .select(userEntity.iuser.count())
                .from(userEntity)
                .fetchOne();

        Long shippingBefore = queryFactory
                .select(orderEntity.iorder.count())
                .from(orderEntity)
                .where(orderEntity.shipping.eq(0)
                        .and(orderEntity.delYn.eq(1)))
                .fetchOne();

        Long shipping = queryFactory
                .select(orderEntity.iorder.count())
                .from(orderEntity)
                .where(orderEntity.shipping.eq(1)
                        .and(orderEntity.delYn.eq(1)))
                .fetchOne();

        Long refundBefore = queryFactory
                .select(refundEntity.irefund.count())
                .from(refundEntity)
                .where(refundEntity.refundStatus.eq(0)
                        .and(refundEntity.delYn.eq(1)))
                .fetchOne();

        Long soldOut = queryFactory
                .select(itemEntity.iitem.count())
                .from(itemEntity)
                .where(itemEntity.stock.eq(0)
                        .and(itemEntity.status.eq(1)))
                .fetchOne();

        Long newBoard = queryFactory
                .select(boardEntity.iboard.count())
                .from(boardEntity)
                .where(getDateFormatPlusTime(boardEntity.createdAt).eq(getDateFormatPlusTime(LocalDateTime.now()))
                        .and(boardEntity.delYn.eq(1)))
                .fetchOne();

        Long newReserve = queryFactory
                .select(reserveEntity.ireserve.count())
                .from(reserveEntity)
                .where(getDateFormatPlusTime(reserveEntity.createdAt).eq(getDateFormatPlusTime(LocalDateTime.now())))
                .fetchOne();

        return SelTodayVo.builder()
                .userCount(userCount)
                .shippingBefore(shippingBefore)
                .shipping(shipping)
                .refundBefore(refundBefore)
                .soldOut(soldOut)
                .newBoard(newBoard)
                .newReserve(newReserve)
                .build();
    }

    @Override
    public List<SelOrderManageVo> selOrderManageInfo(LocalDate startDate, LocalDate endDate, Integer listBox, Object keyword) {
        QOrderEntity A = new QOrderEntity("A");
        QUserEntity B = new QUserEntity("B");
        QOrderItemEntity C = new QOrderItemEntity("C");
        StringTemplate date = getDateFormatPlusTime(A.createdAt);
//서비스에서 리스트박스입력시 키워드도 입력하라고 막기

        return queryFactory
                .select(Projections.fields(SelOrderManageVo.class,
                        date.as("orderDate"),
                        A.iorder.as("iorder"),
                        B.name.as("name"),
                        C.totalPrice.sum().as("orderPrice"),
                        A.totalPrice.as("totalPrice"),
                        A.shipping.as("shippingStatus"),
                        new CaseBuilder()
                                .when(C.refund.eq(1)).then("환불 진행중")
                                .when(C.refund.eq(2)).then("환불 완료")
                                .when(C.refund.eq(2)).then("환불 불가")
                                .otherwise("-")
                                .as("refundStatus")
                ))
                .from(A)
                .innerJoin(B)
                .on(A.userEntity.iuser.eq(B.iuser))
                .innerJoin(C)
                .on(A.iorder.eq(C.orderEntity.iorder))
                .where(
                        createdAtRange(startDate, endDate),
                        pickListBox(listBox, keyword)
                )
                .groupBy(C.orderEntity.iorder)
                .fetch();
    }

    public BooleanExpression createdAtRange(LocalDate startDate, LocalDate endDate) {
        QOrderEntity A = new QOrderEntity("A");
        return endDate != null ? A.createdAt.between(startDate.atStartOfDay(), endDate.atStartOfDay().plusDays(1)) : A.createdAt.between(startDate.atStartOfDay(), startDate.plusDays(1).atStartOfDay());
    }

    public BooleanExpression pickListBox(Integer listBox, Object keyword) {
        Long getIorder = 0L;
        String getName = "";
        String getUserId = "";
        String getNumber = "";
        String getEmail = "";

        QOrderEntity A = new QOrderEntity("A");
        QUserEntity B = new QUserEntity("B");

        if (listBox != null) {
            if (listBox == 0) {
                getIorder = Long.parseLong(String.valueOf(keyword));
                return A.iorder.eq(getIorder);
            } else if (listBox == 1) {
                getName = String.valueOf(keyword);
                return B.name.eq(getName);
            } else if (listBox == 2) {
                getUserId = String.valueOf(keyword);
                return B.uid.eq(getUserId);
            } else if (listBox == 3) {
                getNumber = String.valueOf(keyword);
                return B.phone.eq(getNumber);
            } else if (listBox == 4) {
                getEmail = String.valueOf(keyword);
                return B.email.eq(getEmail);
            }
        }
        return null;
    }

    public StringTemplate getDateFormatPlusTime(DateTimePath<LocalDateTime> date) {
        return Expressions.stringTemplate("DATE_FORMAT( {0}, {1} )", date, ConstantImpl.create("%y-%m-%d %h:%m"));
    }

    public StringTemplate getDateFormatPlusTime(LocalDateTime date) {
        return Expressions.stringTemplate("DATE_FORMAT( {0}, {1} )", date, ConstantImpl.create("%y-%m-%d %h:%m"));
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
                            orderItemEntity.iorderitem,
                            orderEntity.createdAt.as("paymentDate"),
                            ExpressionUtils.as(
                                    JPAExpressions
                                            .select(reviewEntity.ireview.coalesce(0L))
                                            .from(reviewEntity)
                                            .where(reviewEntity.itemEntity.iitem.eq(itemEntity.iitem)
                                                    .and(reviewEntity.delYn.eq(1))
                                                    .and(reviewEntity.userEntity.iuser.eq(iuser))), "reviewYn")))
                    .from(orderItemEntity)
                    .innerJoin(itemEntity).on(orderItemEntity.itemEntity.iitem.eq(itemEntity.iitem))
                    .innerJoin(orderEntity).on(orderItemEntity.orderEntity.iorder.eq(orderEntity.iorder))
                    .where(orderEntity.iorder.eq(list.getIorder()).and(orderItemEntity.delYn.eq(1)))
                    .fetch();

            SelPaymentDetailDto item = SelPaymentDetailDto.builder()//조립
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