package com.green.campingsmore.order.cart;

import com.green.campingsmore.entity.CartEntity;
import com.green.campingsmore.entity.ItemEntity;
import com.green.campingsmore.entity.UserEntity;
import com.green.campingsmore.order.cart.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository repo;
    private final CartRepositoryImpl dslRepo;

    @Override   //jpa
    public Optional<CartRes> insCart(InsCartDto dto) {

        CartEntity entity = CartEntity.builder()
                .userEntity(UserEntity.builder().iuser(dto.getIuser()).build())
                .itemEntity(ItemEntity.builder().iitem(dto.getIitem()).build())
                .quantity(dto.getQuantity())
                .build();

        repo.save(entity);

        return Optional.ofNullable(CartRes.builder()
                .icart(entity.getIcart())
                .iuser(entity.getItemEntity().getIitem())
                .iitem(entity.getItemEntity().getIitem())
                .build());
    }

    @Override   //querydsl
    public Optional<List<SelCartVo>> selCart(Long iuser) {
        return dslRepo.selCart(iuser);
    }

    @Override   //jpa
    public Long delCart(Long icart) {
        Optional<CartEntity> existCheck = repo.findById(icart);

        if(existCheck.isEmpty()) {
            return 0L;
        }

        repo.deleteById(icart);
        return 1L;
    }

    @Override   //jpa
    @Transactional(rollbackFor = {Exception.class})
    public Long delCartAll(List<Long> icart) {
        Long count = 0L;

        for (Long search : icart) {
            Optional<CartEntity> existCheck = repo.findById(search);
            if(existCheck.isEmpty()) {
                return 0L;
            }
            count++;
        }
        repo.deleteAllById(icart);
        return count;
    }
}
