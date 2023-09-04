package com.green.campingsmore.order.payment;

import com.green.campingsmore.entity.OrderEntity;
import com.green.campingsmore.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderDetailRepository extends JpaRepository<OrderItemEntity, Long>{
}
