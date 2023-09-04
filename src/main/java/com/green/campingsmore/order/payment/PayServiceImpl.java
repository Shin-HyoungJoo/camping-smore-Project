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
    public PaymentDetailDto selPaymentPageItem(Long iitem, Long quantity) {
        PaymentDetailDto result = orderRepo.selPaymentPageItem(iitem);
        result.setQuantity(quantity);
        result.setTotalPrice(result.getPrice() * quantity);
        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}) //querydsl
    public List<PaymentDetailDto> selPaymentPageItemList(CartPKDto dto) {

        List<PaymentDetailDto> result = orderRepo.selPaymentPageItemList(dto);
        try {
            for (PaymentDetailDto item : result) {
                Long price = item.getPrice();
                Long quantity = item.getQuantity();
                Long totalPrice = price * quantity;
                item.setTotalPrice(totalPrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}) //jpa
    public Long delPaymentDetail(Long iorder, Long iitem) {
        Optional<OrderItemEntity> delYN = orderItemRepo.findById(iorder);
        if (delYN.isEmpty()) {
            return 0L;
        }
        orderItemRepo.deleteById(iorder);
        return 1L;
    }

    @Override   //dsl
    public SelDetailedItemPaymentInfoVo selDetailedItemPaymentInfo(Long iorder, Long iitem) {
        return orderRepo.selDetailedItemPaymentInfo(iorder, iitem);
    }

    @Override   //jpa
    public Long insAddress(ShippingInsDto dto) {
        ShippingAddressEntity entity = ShippingAddressEntity.builder()
                .iuser(UserEntity.builder().iuser(dto.getIuser()).build())
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .name(dto.getName())
                .phone(dto.getPhone())
                .build();

        shippingRepo.save(entity);
        return 1L;
    }

    @Override
    public SelUserAddressVo selUserAddress(Long iuser) {
        return shippingRepo.selUserAddress(iuser);
    }

    @Override
    public List<ShippingListSelVo> selAddressList(Long iuser) {
//        shippingRepo.selAddressList(Long iuser);
        return null;
    }

    @Override
    public ShippingListSelVo selOneAddress(SelUserAddressDto dto) {
        return MAPPER.selOneAddress(dto);
    }

    @Override
    public Long delAddress(Long iaddress) {
        return MAPPER.delAddress(iaddress);
    }
}
