package com.green.campingsmore.user.review;

import com.green.campingsmore.entity.QReviewEntity;
import com.green.campingsmore.entity.QUserEntity;
import com.green.campingsmore.user.review.model.ReviewRes;
import com.green.campingsmore.user.review.model.ReviewSelRes;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewDao {
    private final JPAQueryFactory jpaQueryFactory;
    private final QReviewEntity r = QReviewEntity.reviewEntity;
    private final QUserEntity u = QUserEntity.userEntity;

    public List<ReviewSelRes> selReview(Pageable page, Long iitem) {

        JPQLQuery<ReviewSelRes> query = jpaQueryFactory.select(Projections.bean(ReviewSelRes.class,
                r.ireview, u.name, r.reviewCtnt, r.pic, r.starRating, r.reviewLike, r.createdAt
                ))
                .from(r)
                .join(r.userEntity, u)
                .orderBy(r.ireview.desc())
                .offset(page.getOffset())
                .where(r.itemEntity.iitem.eq(iitem).and(r.delYn.eq(1)))
                .limit(page.getPageSize());

        return query.fetch();
    }

    public Integer reviewCount(Long iitem) {
        return Math.toIntExact(jpaQueryFactory
                .select(r.ireview.count())
                .from(r)
                .where(r.itemEntity.iitem.eq(iitem).and(r.delYn.eq(0)))
                .fetchOne());

    }

    public Boolean reviewUser(Long iitem) {
        if((jpaQueryFactory
                .select(r.userEntity.iuser)
                .from(r)
                .where(r.userEntity.iuser.eq(iitem))
                .fetchOne()) != null) {
            return true;
        }
        return false;
    }
}
