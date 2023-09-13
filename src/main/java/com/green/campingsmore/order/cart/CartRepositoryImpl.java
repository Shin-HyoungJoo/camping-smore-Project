package com.green.campingsmore.order.cart;

import com.green.campingsmore.entity.QItemEntity;
import com.green.campingsmore.order.cart.model.SelCartVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.green.campingsmore.entity.QCartEntity.cartEntity;
import static com.green.campingsmore.entity.QItemEntity.*;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<SelCartVo>> selCart(Long iuser) {
        return Optional.ofNullable(queryFactory
                .select(Projections.fields(SelCartVo.class,
                        cartEntity.icart,
                        itemEntity.iitem,
                        cartEntity.itemEntity.pic,
                        cartEntity.itemEntity.name,
                        cartEntity.itemEntity.price,
                        cartEntity.quantity))
                .from(cartEntity)
                .innerJoin(itemEntity)
                .on(cartEntity.itemEntity.iitem.eq(itemEntity.iitem))
                .where(cartEntity.userEntity.iuser.eq(iuser))
                .fetch());
    }

    @Override
    public Long selIcart(Long iuser, Long iitem) {
        return queryFactory.select(cartEntity.icart)
                .from(cartEntity)
                .where(cartEntity.userEntity.iuser.eq(iuser)
                        .and(cartEntity.itemEntity.iitem.eq(iitem)))
                .fetchOne();
    }


}
