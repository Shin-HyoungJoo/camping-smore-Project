package com.green.campingsmore.user.item;

import com.green.campingsmore.admin.item.model.ItemDetailPicVo;
import com.green.campingsmore.admin.item.model.ItemVo;
import com.green.campingsmore.entity.*;
import com.green.campingsmore.item.model.ItemSelDetailVo;
import com.green.campingsmore.user.item.model.ItemSelAllParam;
import com.green.campingsmore.user.item.model.ItemSelCateVo;
import com.green.campingsmore.user.review.model.ReviewSelRes;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemDao {
    private final JPAQueryFactory jpaQueryFactory;
    private final QItemEntity i = QItemEntity.itemEntity;
    private final QItemCategoryEntity c = QItemCategoryEntity.itemCategoryEntity;
    private final QWishlistEntity w = QWishlistEntity.wishlistEntity;
    private final QItemDetailPicEntity dp = QItemDetailPicEntity.itemDetailPicEntity;

    public List<ItemSelCateVo> selCategory() {
        JPQLQuery<ItemSelCateVo> query = jpaQueryFactory.select(Projections.bean(ItemSelCateVo.class,
                c.iitemCategory, c.name
                ))
                .from(c)
                .orderBy(c.name.desc())
                .where(c.status.eq(1));

        return query.fetch();
    }

    public List<ItemVo> selItem(Pageable page) {

        JPQLQuery<ItemVo> query = jpaQueryFactory.select(Projections.bean(ItemVo.class,
                       i.iitem, i.name, i.pic, i.price,i.createdAt
                ))
                .from(i)
                .join(i.itemCategoryEntity, c)
                .where( i.status.eq(1))
                .orderBy()
                .offset(page.getOffset())
                .limit(page.getPageSize());

        return query.fetch();
    }

    public ItemSelDetailVo selItemDetail(Long iitem) {
        JPQLQuery<ItemSelDetailVo> query = jpaQueryFactory.select(Projections.bean(ItemSelDetailVo.class,
                    i.iitem, i.name, i.pic, i.price, i.createdAt))
                .from(i)
                .join(dp.itemEntity, i)
                .where(i.itemEntity.iitem.eq(iitem).and(i.status.eq(1)));

        return query.fetchOne();
    }

    public Integer itemCount() {
        return Math.toIntExact(jpaQueryFactory
                .select(i.iitem.count())
                .from(i)
                .where(i.status.eq(1))
                .fetchOne());

    }
}


