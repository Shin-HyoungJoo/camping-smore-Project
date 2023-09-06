package com.green.campingsmore.user.item;

import com.green.campingsmore.admin.item.model.ItemDetailPicVo;
import com.green.campingsmore.admin.item.model.ItemVo;
import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.entity.*;
import com.green.campingsmore.item.model.ItemSelDetailVo;
import com.green.campingsmore.user.item.model.ItemSelAllParam;
import com.green.campingsmore.user.item.model.ItemSelCateVo;
import com.green.campingsmore.user.review.model.ReviewSelRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.count;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemDao {
    private final JPAQueryFactory jpaQueryFactory;
    private final QItemEntity i = QItemEntity.itemEntity;
    private final QItemCategoryEntity c = QItemCategoryEntity.itemCategoryEntity;
    private final QWishlistEntity w = QWishlistEntity.wishlistEntity;
    private final QItemDetailPicEntity dp = QItemDetailPicEntity.itemDetailPicEntity;
    private final QUserEntity u = QUserEntity.userEntity;
    private final AuthenticationFacade facade;

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

        // 찜하기 나타내기 위해서 요청 들어오자마자 로그인 유무를 확인한다.
//        if(facade.getLoginUser() == null){  // 비로그인일 경우 false  ==> 찜하기 속성이 0? 인걸로 리스트 뱉어준다.
//            System.out.println("비로그인 !!!!");
//
//
//        } else { // 로그인 했을 경우 true ==> 로그인이 확인되었으니까 유저의 PK를 이제부터 조회할 수 있다.
//            facade.getLoginUserPk(); // 유저의 PK를 불러 오는 메서드이다. Long 타입 반환
//            log.info("로그를 찍어줘:{}",page);
//        }

        BooleanBuilder userBuilder = new BooleanBuilder();
        if(facade.isLogin()){ // 로그인 했을 경우 true ==> 로그인이 확인되었으니까 유저의 PK를 이제부터 조회할 수 있다.
            userBuilder.and(u.iuser.eq(facade.getLoginUserPk())).and(w.itemEntity.iitem.eq(i.iitem)); // 유저의 PK를 불러 오는 메서드이다. Long 타입 반환
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
                .where(searchBuilder.and(i.status.eq(1)))
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


