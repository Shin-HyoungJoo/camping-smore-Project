package com.green.campingsmore.order.payment;

import com.green.campingsmore.entity.*;
import com.green.campingsmore.order.payment.model.*;
import com.green.campingsmore.user.camping.ReserveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private final PayMapper MAPPER;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final ReserveRepository resRepo;
    private final ShippingAddressRepository shippingRepo;

    @Override       //dsl
    @Transactional(rollbackFor = {Exception.class})
    public Long insPayInfo(InsPayInfoDto dto) {

        ReserveEntity reserveEntity = dto.getIreserve() == null ? null : resRepo.findById(dto.getIreserve()).get();

        OrderEntity orderEntity = OrderEntity.builder()
                .userEntity(UserEntity.builder().iuser(dto.getIuser()).build())
                .reserveEntity(reserveEntity)
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .totalPrice(dto.getTotalPrice())
                .shippingPrice(dto.getShippingPrice())
                .shippingMemo(dto.getShippingMemo())
                .type(dto.getType())
                .delYn(1)
                .shipping(0)
                .build();

        orderRepo.save(orderEntity);

        List<PayDetailInfoVo> purchaseItem = dto.getPurchaseList();

        for (PayDetailInfoVo item : purchaseItem) {

            OrderItemEntity result = OrderItemEntity.builder()
                    .orderEntity(OrderEntity.builder().iorder(orderEntity.getIorder()).build())
                    .itemEntity(ItemEntity.builder().iitem(item.getIitem()).build())
                    .price(orderRepo.selPriceFromItem(item.getIitem()))
                    .quantity(item.getQuantity())
                    .totalPrice(item.getTotalPrice())
                    .delYn(1)
                    .refund(0)
                    .build();

            orderItemRepo.save(result);
        }
        return 1L;
    }

    @Override   //querydsl
    public Optional<PaymentCompleteDto> selPaymentComplete(Long iorder) {
        return orderRepo.selPaymentComplete(iorder);
    }

    @Override   //querydsl
    public List<SelPaymentDetailDto> selPaymentDetailAll(Long iuser) {
        return orderRepo.selPaymentDetailAll(iuser);
    }

    @Override   //querydsl
    public PaymentDetailDto selPaymentPageItem(Long iitem, Integer quantity) {
        PaymentDetailDto result = orderRepo.selPaymentPageItem(iitem);
        result.setQuantity(quantity);
        result.setTotalPrice(result.getPrice() * quantity);
        return result;
    }

    @Override   //완
    @Transactional(rollbackFor = {Exception.class}) //querydsl
    public List<PaymentDetailDto> selPaymentPageItemList(CartPKDto dto) {

        List<PaymentDetailDto> result = orderRepo.selPaymentPageItemList(dto);
        try {
            for (PaymentDetailDto item : result) {
                Integer price = item.getPrice();
                Integer quantity = item.getQuantity();
                Integer totalPrice = price * quantity;
                item.setTotalPrice(totalPrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override   //완
    @Transactional(rollbackFor = {Exception.class}) //jpa
    public Long delPaymentDetail(Long iorderItem) throws Exception{
        try {
            Optional<OrderItemEntity> delYN = orderItemRepo.findById(iorderItem);
            if (delYN.isEmpty()) {
                return 0L;
            }

            OrderItemEntity result = delYN.get();
            result.setDelYn(0);

            orderItemRepo.save(result);
        } catch (Exception e) {
            throw new Exception("없는 PK입니다");
        }
            return 1L;

    }

    @Override   //dsl
    public SelDetailedItemPaymentInfoVo selDetailedItemPaymentInfo(Long iorder, Long iitem) {
        return orderRepo.selDetailedItemPaymentInfo(iorder, iitem);
    }

    @Override   //jpa
    public Long insAddress(ShippingInsDto dto) {
        ShippingAddressEntity entity = ShippingAddressEntity.builder()
                .userEntity(UserEntity.builder().iuser(dto.getIuser()).build())
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .name(dto.getName())
                .phone(dto.getPhone())
                .build();

        shippingRepo.save(entity);
        return 1L;
    }

    @Override   //jpa
    public SelUserAddressVo selUserAddress(Long iuser) {
        return shippingRepo.selUserAddress(iuser);
    }

    @Override   //dsl
    public List<ShippingListSelVo> selAddressList(Long iuser) {
        return shippingRepo.selAddressList(iuser);
    }

    @Override   //dsl
    public ShippingListSelVo selOneAddress(SelUserAddressDto dto) {
        return shippingRepo.selOneAddress(dto);
    }

    @Override  //jpa
    public Long delAddress(Long iaddress) {
        Optional<ShippingAddressEntity> search = shippingRepo.findById(iaddress);

        if (search.isEmpty()) {
            return null;
        }

        shippingRepo.deleteById(iaddress);
        return 1L;
    }
}
