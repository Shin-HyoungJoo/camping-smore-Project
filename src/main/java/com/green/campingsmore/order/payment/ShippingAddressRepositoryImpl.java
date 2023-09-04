package com.green.campingsmore.order.payment;

import com.green.campingsmore.entity.QShippingAddressEntity;
import com.green.campingsmore.entity.QUserEntity;
import com.green.campingsmore.entity.ShippingAddressEntity;
import com.green.campingsmore.order.payment.model.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static com.green.campingsmore.entity.QShippingAddressEntity.*;
import static com.green.campingsmore.entity.QUserEntity.*;

@RequiredArgsConstructor
public class ShippingAddressRepositoryImpl implements ShippingAddressCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public SelUserAddressVo selUserAddress(Long iuser) {
        return queryFactory
                .select(Projections.fields(SelUserAddressVo.class,
                                        userEntity.userAddress,
                                        userEntity.userAddressDetail,
                                        userEntity.name,
                                        userEntity.phone
                                ))
                                .from(userEntity)
                                .where(userEntity.iuser.eq(iuser)
                                        .and(userEntity.delyn.eq(1)))
                .fetchOne();
    }
    @Override
    public List<ShippingListSelVo> selAddressList(Long iuser) {
        return queryFactory
                .select(Projections.fields(ShippingListSelVo.class,
                        shippingAddressEntity.iaddress,
                        shippingAddressEntity.address,
                        shippingAddressEntity.addressDetail,
                        shippingAddressEntity.name,
                        shippingAddressEntity.phone
                ))
                .from(shippingAddressEntity)
                .where(shippingAddressEntity.userEntity.iuser.eq(iuser))
                .fetch();
    }

    @Override
    public ShippingListSelVo selOneAddress(SelUserAddressDto dto) {
        return queryFactory
                .select(Projections.fields(ShippingListSelVo.class,
                        shippingAddressEntity.iaddress,
                        shippingAddressEntity.address,
                        shippingAddressEntity.addressDetail,
                        shippingAddressEntity.name,
                        shippingAddressEntity.phone
                        ))
                .from(shippingAddressEntity)
                .where(shippingAddressEntity.userEntity.iuser.eq(dto.getIuser())
                        .and(shippingAddressEntity.iaddress.eq(dto.getIaddress())))
                .fetchOne();
    }
}
