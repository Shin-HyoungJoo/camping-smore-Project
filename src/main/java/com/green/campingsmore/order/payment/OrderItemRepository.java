package com.green.campingsmore.order.payment;

import com.green.campingsmore.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long>, OrderItemRepositoryCustom{
}
