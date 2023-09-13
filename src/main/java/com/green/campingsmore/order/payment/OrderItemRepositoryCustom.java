package com.green.campingsmore.order.payment;

import com.green.campingsmore.entity.OrderItemEntity;
import com.green.campingsmore.order.payment.model.*;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepositoryCustom {
    OrderItemEntity selByIorderitem(Long iorderitem);

    List<Long> orderItemList(Long iorder);
}
