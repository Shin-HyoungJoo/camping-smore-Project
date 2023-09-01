package com.green.campingsmore.order.cart;

import com.green.campingsmore.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
