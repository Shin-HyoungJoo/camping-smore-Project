package com.green.campingsmore.order.cart;

import com.green.campingsmore.order.cart.model.SelCartVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.green.campingsmore.entity.QCartEntity.cartEntity;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {
    private JPAQueryFactory queryFactory;

    @Override
    public Optional<List<SelCartVo>> selCart(Long iuser) {
        return Optional.ofNullable(queryFactory
                .select(Projections.fields(SelCartVo.class,
                        cartEntity.icart,
                        cartEntity.itemEntity.pic,
                        cartEntity.itemEntity.name,
                        cartEntity.itemEntity.price,
                        cartEntity.quantity))
                .from(cartEntity)
                .where(cartEntity.userEntity.iuser.eq(iuser))
                .fetch());
    }
}
