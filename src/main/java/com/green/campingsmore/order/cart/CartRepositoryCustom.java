package com.green.campingsmore.order.cart;

import com.green.campingsmore.entity.CartEntity;
import com.green.campingsmore.order.cart.model.SelCartVo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepositoryCustom {
    Optional<List<SelCartVo>> selCart(Long iuser);
}
