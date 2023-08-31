package com.green.campingsmore.user.camping;


import com.green.campingsmore.user.camping.model.CampingDto;
import com.green.campingsmore.user.camping.model.CampingRes;
import com.querydsl.jpa.impl.JPAInsertClause;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.stereotype.Repository;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@RequiredArgsConstructor
public class CampingRepositoryImpl implements CampingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

}
