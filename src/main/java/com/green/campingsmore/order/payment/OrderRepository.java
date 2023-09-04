package com.green.campingsmore.order.payment;

import com.green.campingsmore.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends  JpaRepository<OrderEntity, Long>, OrderRepositoryCustom {
}
