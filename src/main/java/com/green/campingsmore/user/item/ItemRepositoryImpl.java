package com.green.campingsmore.user.item;

import com.green.campingsmore.entity.ItemEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import static com.green.campingsmore.entity.QItemEntity.itemEntity;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

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
