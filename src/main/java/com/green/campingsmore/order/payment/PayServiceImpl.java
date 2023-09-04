package com.green.campingsmore.order.payment;

import com.green.campingsmore.entity.*;
import com.green.campingsmore.order.payment.model.*;
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
    private final OrderDetailRepository orderDetailRepo;

    @Override       //dsl
    @Transactional(rollbackFor = {Exception.class})
    public Long insPayInfo(InsPayInfoDto dto) {
        OrderEntity orderEntity = OrderEntity.builder()
                .userEntity(UserEntity.builder().iuser(dto.getIuser()).build())
                .reserveEntity(ReserveEntity.builder().ireserve(dto.getIreserve()).build())
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
                    .build();

            orderDetailRepo.save(result);
        }
        return 1L;
    }

    @Override   //querydsl
    public Optional<PaymentCompleteDto> selPaymentComplete(Long iorder) {
        return orderRepo.selPaymentComplete(iorder);
    }

    @Override
    public Optional<List<SelPaymentDetailDto>> selPaymentDetailAll(Long iuser) {
        return orderRepo.selPaymentDetailAll(iuser);
    }

    @Override
    public PaymentDetailDto selPaymentPageItem(Long iitem, Long quantity) {
        PaymentDetailDto dto = MAPPER.selPaymentPageItem(iitem);
        Long price = dto.getPrice();

        dto.setQuantity(quantity);
        dto.setTotalPrice(price * quantity);
        return dto;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<PaymentDetailDto> selPaymentPageItemList(CartPKDto dto) {
        List<PaymentDetailDto> paymentDetailDtos = MAPPER.selPaymentPageItemList(dto.getIcart());
        try {
            for (PaymentDetailDto paymentDetailDto : paymentDetailDtos) {
                Long price = paymentDetailDto.getPrice();
                Long quantity = paymentDetailDto.getQuantity();
                Long totalPrice = price * quantity;
                paymentDetailDto.setTotalPrice(totalPrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentDetailDtos;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Long delPaymentDetail(Long iorder, Long iitem) {

        try {
            Long result1 = MAPPER.delPaymentDetail(iorder, iitem);
            Long result2 = MAPPER.paymentDetailNullCheck(iorder, iitem);

            if (result1 == 1L && result2 == 0L) {
                MAPPER.delOrder(iorder);
                return 2L;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
        return 1L;
    }

    @Override
    public SelDetailedItemPaymentInfoVo selDetailedItemPaymentInfo(Long iorder, Long iitem) {
        return MAPPER.selDetailedItemPaymentInfo(iorder, iitem);
    }

    @Override
    public Long insAddress(ShippingInsDto1 dto) {
        return MAPPER.insAddress(dto);
    }

    @Override
    public SelUserAddressVo selUserAddress(Long iuser) {
        return MAPPER.selUserAddress(iuser);
    }

    @Override
    public List<ShippingListSelVo> selAddressList(Long iuser) {
        return MAPPER.selAddressList(iuser);
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
