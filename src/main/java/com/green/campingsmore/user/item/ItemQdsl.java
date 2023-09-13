package com.green.campingsmore.user.item;

import com.green.campingsmore.admin.item.model.AdminBestItemVo;
import com.green.campingsmore.admin.item.model.AdminItemCateVo;
import com.green.campingsmore.admin.item.model.AdminItemVo;
import com.green.campingsmore.admin.item.model.ItemVo;
import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.entity.*;
import com.green.campingsmore.item.model.ItemSelDetailVo;
import com.green.campingsmore.user.item.model.ItemSelCateVo;
import com.green.campingsmore.user.mypage.model.ReviewMypageVo;
import com.green.campingsmore.user.mypage.model.WishMypageVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

import static java.time.LocalDate.now;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemQdsl {
    private final JPAQueryFactory jpaQueryFactory;
    private final QItemEntity i = QItemEntity.itemEntity;
    private final QItemCategoryEntity c = QItemCategoryEntity.itemCategoryEntity;
    private final QWishlistEntity w = QWishlistEntity.wishlistEntity;
    private final QItemDetailPicEntity dp = QItemDetailPicEntity.itemDetailPicEntity;
    private final QUserEntity u = QUserEntity.userEntity;

    private final QBestItemEntity bi = QBestItemEntity.bestItemEntity;

    private final AuthenticationFacade facade;


    // 관리자 아이템 ------------------------------------------------------------------------------------------------------

    public List<AdminItemCateVo> selAdminCategory() {
        JPQLQuery<AdminItemCateVo> query = jpaQueryFactory.select(Projections.bean(AdminItemCateVo.class,
                        c.iitemCategory, c.name, c.status
                ))
                .from(c)
                .orderBy(c.name.desc())
                .where(c.status.between(1,2));

        return query.fetch();
    }

    public AdminItemCateVo selAdminCategoryDetail(Long iitemCategory) {
        JPQLQuery<AdminItemCateVo> query = jpaQueryFactory.select(Projections.bean(AdminItemCateVo.class,
                        c.iitemCategory, c.name, c.status
                ))
                .from(c)
                .orderBy(c.name.desc())
                .where(c.iitemCategory.eq(iitemCategory).and(c.status.between(1,2)));

        return query.fetchOne();
    }

    public List<AdminItemVo> searchAdminItem(Pageable page, Long cate, String text, Integer date,
                                             LocalDate searchStartDate, LocalDate searchEndDate) {

        JPQLQuery<AdminItemVo> query = jpaQueryFactory.select(Projections.bean(AdminItemVo.class,
                        c.name.as("categoryName"), i.iitem, i.name, i.pic, i.price, i.createdAt, i.stock, i.status
                         ))
                .from(i)
                .join(i.itemCategoryEntity, c)
                .where(i.status.between(1,2),
                        findCate(cate),
                        findDate(date),
                        findName(text),
                        findSearchDate(searchStartDate,searchEndDate)
                )
                .orderBy(getAllOrderSpecifiers(page))
                .offset(page.getOffset())
                .limit(page.getPageSize());

        return query.fetch();
    }

    private BooleanExpression findName(String text) {
        if(StringUtils.isEmpty(text)) {
            return null;
        }
        return i.name.contains(text);
    }
    private BooleanExpression findCate(Long cate) {
        if (cate == null) {
            return null;
        }
        return c.iitemCategory.eq(cate);
    }

    private BooleanExpression findDate(Integer date) {
        if (date == null) {
            return null;
        }
        StringExpression createDate = Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", i.createdAt);
        StringExpression nowDate = Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", now());
        LocalDateTime addDate = LocalDateTime.now().minus(date, ChronoUnit.DAYS);
        if(date == 0) {
            return createDate.eq(nowDate);
        } else if(date == 3 || date ==7 || date ==30 || date ==90 || date ==360){
           return i.createdAt.between(addDate,LocalDateTime.now());
        }
        return null;
    }

    private BooleanExpression findSearchDate(LocalDate searchStartDate, LocalDate searchEndDate) {
        if(searchStartDate == null || searchEndDate == null ||(searchStartDate == null && searchEndDate == null)) {
            return null;
        }

        BooleanExpression startDateTimeBoolean = i.createdAt.goe(LocalDateTime.of(searchStartDate, LocalTime.MIN));
        BooleanExpression endDateTimeBoolean = i.createdAt.loe(LocalDateTime.of(searchEndDate, LocalTime.MAX).withNano(0));

        return Expressions.allOf(startDateTimeBoolean,endDateTimeBoolean);
    }


    public Integer adminItemCount(Long cate, String text, Integer date, LocalDate searchStartDate, LocalDate searchEndDate) {

        return Math.toIntExact(jpaQueryFactory
                .select(i.iitem.count())
                .from(i)
                .where(i.status.between(1,2),
                        findCate(cate),
                        findDate(date),
                        findName(text),
                        findSearchDate(searchStartDate,searchEndDate))
                .fetchOne());

    }

    // 어드민 베스트 아이템  ------------------------------------------------------------------

    public List<AdminBestItemVo> adminSelBestItem(Pageable page) {

        JPQLQuery<AdminBestItemVo> query = jpaQueryFactory.select(Projections.fields(AdminBestItemVo.class,
                        bi.ibestItem,i.iitem, i.name.as("itemNm"),
                        i.pic,
                        i.price,
                        bi.monthLike,
                        bi.createdAt, bi.updatedAt
                ))
                .from(bi)
                .join(bi.itemEntity, i);

        return query.fetch();
    }

    public Integer bestItemCount() {
        return Math.toIntExact(jpaQueryFactory
                .select(bi.ibestItem.count())
                .from(bi)
                .fetchOne());

    }


///// 유저 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<ItemSelCateVo> selCategory() {
        JPQLQuery<ItemSelCateVo> query = jpaQueryFactory.select(Projections.bean(ItemSelCateVo.class,
                c.iitemCategory, c.name
                ))
                .from(c)
                .orderBy(c.name.desc())
                .where(c.status.eq(1));

        return query.fetch();
    }

    public List<ItemVo> searchItem(Pageable page, Long cate, String text) {

        BooleanBuilder userBuilder = new BooleanBuilder();
        if(facade.getLoginUser() != null){
            userBuilder.and(u.iuser.eq(facade.getLoginUserPk())).and(w.itemEntity.iitem.eq(i.iitem));
        } else {
            userBuilder.and(u.iuser.eq(0L));
        }
        
        BooleanBuilder searchBuilder = new BooleanBuilder();
        if(cate != null && text == null) {
            searchBuilder.and(c.iitemCategory.eq(cate));
        } else if (text != null && cate == null) {
            searchBuilder.and(i.name.contains(text));
        } else if (text != null && cate != null){
            searchBuilder.and(c.iitemCategory.eq(cate)).and(i.name.contains(text));
        }

        JPQLQuery<ItemVo> query = jpaQueryFactory.select(Projections.bean(ItemVo.class,
                       i.iitem, i.name, i.pic, i.price,i.createdAt,
                        ExpressionUtils.as(JPAExpressions.select(w.delYn).from(w).where(userBuilder), "wish")
                ))
                .from(i)
                .join(i.itemCategoryEntity, c)
                .where(i.status.eq(1),searchBuilder)
                .orderBy(getAllOrderSpecifiers(page))
                .offset(page.getOffset())
                .limit(page.getPageSize());

        return query.fetch();
    }


    private OrderSpecifier[] getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier> orders = new LinkedList();
        if(!pageable.getSort().isEmpty()) {
            for(Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty().toLowerCase()) {
                    case "iitem" :orders.add(new OrderSpecifier(direction, i.iitem)); break;
                    case "name": orders.add(new OrderSpecifier(direction, i.name)); break;
                    case "price" : orders.add(new OrderSpecifier(direction, i.price)); break;
                }
            }
        }

        return orders.stream().toArray(OrderSpecifier[]::new);
    }

    public ItemSelDetailVo selItemDetail(Long iitem) {
        JPQLQuery<ItemSelDetailVo> query = jpaQueryFactory.select(Projections.bean(ItemSelDetailVo.class,
                    i.iitem, i.name, i.pic, i.price, i.createdAt))
                .from(i)
                .where(i.iitem.eq(iitem).and(i.status.eq(1)));

        return query.fetchOne();
    }

    public List<String> selItemDetailPicList(Long iitem) {
        JPQLQuery<String> query = jpaQueryFactory.select(
                        dp.pic)
                .from(dp)
                .where(dp.itemEntity.iitem.eq(iitem));
        return query.fetch();
    }

    public Integer itemCount(Long cate, String text) {
        BooleanBuilder searchBuilder = new BooleanBuilder();
        if(cate != null && text == null) {
            searchBuilder.and(c.iitemCategory.eq(cate));
        } else if (text != null && cate == null) {
            searchBuilder.and(i.name.contains(text));
        } else if (text != null && cate != null){
            searchBuilder.and(c.iitemCategory.eq(cate)).and(i.name.contains(text));
        }
        return Math.toIntExact(jpaQueryFactory
                .select(i.iitem.count())
                .from(i)
                .where(i.status.eq(1),searchBuilder)
                .fetchOne());

    }

    // 유저 추천 아이템 ------------------------------------------------------------------------------------------------------
    public List<ItemVo> selBestItem() {

        BooleanBuilder userBuilder = new BooleanBuilder();
        if(facade.getLoginUser() != null){
            userBuilder.and(u.iuser.eq(facade.getLoginUserPk())).and(w.itemEntity.iitem.eq(i.iitem));
        } else {
            userBuilder.and(u.iuser.eq(0L));
        }

        StringExpression likeDate = Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m')", bi.monthLike);
        StringExpression nowDate = Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m')", now());

        JPQLQuery<ItemVo> query = jpaQueryFactory.select(Projections.bean(ItemVo.class,
                        i.iitem, i.name, i.pic, i.price,i.createdAt,
                        ExpressionUtils.as(JPAExpressions.select(w.delYn).from(w).where(userBuilder), "wish")
                ))
                .from(bi)
                .join(bi.itemEntity, i)
                .where(likeDate.eq(nowDate))
                .orderBy();

        return query.fetch();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // 유저 마이페이지 위시 리스트----------------------------------------------------------------------------
    public List<WishMypageVo> selReviewlist(Long iuser) {
        JPQLQuery<WishMypageVo> query = jpaQueryFactory.select(Projections.bean(WishMypageVo.class,
                        w.iwish ,i.iitem, i.name.as("itemNm")
                ))
                .from(w)
                .join(w.itemEntity, i)
                .where(w.userEntity.iuser.eq(iuser))
                .orderBy(w.iwish.desc());

        return query.fetch();
    }
}


