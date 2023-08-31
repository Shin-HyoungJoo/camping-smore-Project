package com.green.campingsmore.order.cart;

import com.green.campingsmore.order.cart.model.CartRes;
import com.green.campingsmore.order.cart.model.InsCartDto;
import com.green.campingsmore.order.cart.model.InsCartDto1;
import com.green.campingsmore.order.cart.model.SelCartVo;

import java.util.List;
import java.util.Optional;


public interface CartService {
    Optional<CartRes> insCart(InsCartDto dto);
    Optional<List<SelCartVo>> selCart(Long iuser);    //dsl
    Long delCart(Long icart);
    Long delCartAll(List<Long> icart);
}
