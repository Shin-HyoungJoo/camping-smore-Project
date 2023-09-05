package com.green.campingsmore.order.payment;

import com.green.campingsmore.entity.ShippingAddressEntity;
import com.green.campingsmore.order.payment.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingAddressCustom {
    SelUserAddressVo selUserAddress(Long iuser);
    List<ShippingListSelVo> selAddressList(Long iuser);
    ShippingListSelVo selOneAddress(SelUserAddressDto dto);
}
