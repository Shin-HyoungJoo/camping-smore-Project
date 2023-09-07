package com.green.campingsmore.admin.order.refundmanage;

import com.green.campingsmore.admin.order.refundmanage.model.SelRefundManageVo;
import com.green.campingsmore.admin.order.refundmanage.model.SelRefundVo;
import com.green.campingsmore.entity.QOrderEntity;
import com.green.campingsmore.entity.QOrderItemEntity;
import com.green.campingsmore.entity.QRefundEntity;
import com.green.campingsmore.entity.QUserEntity;
import com.green.campingsmore.order.payment.OrderRepositoryImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.green.campingsmore.entity.QRefundEntity.*;
import static com.green.campingsmore.entity.QUserEntity.*;

@RequiredArgsConstructor
public class RefundRepositoryImpl implements RefundRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final OrderRepositoryImpl orderRepoImpl;

    @Override
    public List<SelRefundVo> selRefund(Long iuser) {
        return queryFactory.select(Projections.fields(SelRefundVo.class,
                        refundEntity.irefund,
                        refundEntity.userEntity.iuser,
                        refundEntity.orderItemEntity.orderEntity.iorder,
//                        refundEntity.orderItemEntity2.itemEntity.iitem,
                        refundEntity.refundStartDate,
                        refundEntity.refundEndDate,
                        refundEntity.quantity,
                        refundEntity.totalPrice,
                        refundEntity.refundStatus))
                .from(refundEntity)
                .where(refundEntity.userEntity.iuser.eq(iuser))
                .fetch();
    }

    @Override
    public List<SelRefundManageVo> selRefundManageList(LocalDate startDate, LocalDate endDate, Integer listBox, Object keyword) {
        QRefundEntity A = new QRefundEntity("A");
        QUserEntity B = new QUserEntity("B");
        QOrderItemEntity C = new QOrderItemEntity("C");

        StringTemplate dateC = orderRepoImpl.getDateFormatPlusTime(C.createdAt);
        StringTemplate startDateA = orderRepoImpl.getDateFormatPlusTime(A.refundStartDate);
        StringTemplate endDateA = null;

        if (A.refundEndDate != null) {
            endDateA = orderRepoImpl.getDateFormatPlusTime(A.refundEndDate);
        }

        return queryFactory
                .select(Projections.fields(SelRefundManageVo.class,
                        A.irefund.as("irefund"),
                        dateC.as("orderDate"),
                        startDateA.as("refundStartDate"),
                        endDateA.as("refundEndDate"),
                        C.iorderitem.as("iorderitem"),
                        B.name.as("name"),
                        C.quantity.as("quantity"),
                        A.totalPrice.as("totalRefund"),
                        A.refundStatus.as("refundStatus")
                ))
                .from(A)
                .innerJoin(B)
                .on(A.userEntity.iuser.eq(B.iuser))
                .innerJoin(C)
                .on(A.orderItemEntity.iorderitem.eq(C.iorderitem))
                .where(
                        refundCreatedAtRange(startDate, endDate),
                        orderRepoImpl.pickListBox(listBox, keyword)
                )
                .fetch();
    }

    @Override
    public String selUserName(Long iuser) {
        return queryFactory
                .select(userEntity.name)
                .from(userEntity)
                .where(userEntity.iuser.eq(iuser))
                .fetchOne();
    }

    public BooleanExpression refundCreatedAtRange(LocalDate startDate, LocalDate endDate) {
        QRefundEntity A = new QRefundEntity("A");
        return endDate != null ? A.refundStartDate.between(startDate.atStartOfDay(), endDate.atStartOfDay()) : A.refundStartDate.between(startDate.atStartOfDay(), startDate.plusDays(1).atStartOfDay());
    }
}