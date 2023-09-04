package com.green.campingsmore.order.payment;

import com.green.campingsmore.entity.ShippingAddressEntity;
import com.green.campingsmore.order.payment.model.SelUserAddressVo;
import com.green.campingsmore.order.payment.model.ShippingAddressInsRes;
import com.green.campingsmore.order.payment.model.ShippingInsDto;
import com.green.campingsmore.order.payment.model.ShippingListSelVo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingAddressCustom {
    SelUserAddressVo selUserAddress(Long iuser);
    List<ShippingListSelVo> selAddressList(Long iuser);
}
