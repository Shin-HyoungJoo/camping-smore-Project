package com.green.campingsmore.order.cart;

import com.green.campingsmore.entity.CartEntity;
import com.green.campingsmore.order.cart.model.SelCartVo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepositoryCustom {
    List<SelCartVo> selCart(Long iuser);
}
