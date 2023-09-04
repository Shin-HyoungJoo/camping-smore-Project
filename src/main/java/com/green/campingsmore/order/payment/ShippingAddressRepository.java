package com.green.campingsmore.order.payment;

import com.green.campingsmore.entity.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddressEntity, Long>,  ShippingAddressCustom{

}
