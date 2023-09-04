package com.green.campingsmore.order.refund;

import com.green.campingsmore.entity.QRefundEntity;
import com.green.campingsmore.entity.RefundEntity;
import com.green.campingsmore.order.refund.model.SelRefundVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static com.green.campingsmore.entity.QRefundEntity.*;

@RequiredArgsConstructor
public class RefundRepositoryImpl implements RefundRepositoryCustom {
    private final JPAQueryFactory queryFactory;

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
}