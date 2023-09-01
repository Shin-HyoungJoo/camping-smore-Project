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
    private final PayRepository repo;
    private final PayRepositoryImpl dslRepo;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Long insPayInfo(InsPayInfoDto dto) {
//
//        OrderEntity entity = OrderEntity.builder()
//                .userEntity(UserEntity.builder().iuser(dto.getIuser()).build())
//                .campEntity(CampEntity.builder().icamp(dto.getIReserve()).build())
//                .address(dto.getAddress())
//                .addressDetail(dto.getAddressDetail())
//                .totalPrice(dto.getTotalPrice())
//                .shippingMemo(dto.getShippingMemo())
//                .build();
//
//
//        repo.save(entity);
//
//        List<PayDetailInfoVo> itemList = dto.getPurchaseList();
//        for (PayDetailInfoVo item : itemList) {
//            OrderItemEntity.builder()
//                    .orderEntity(OrderEntity.builder().iorder(repo.findTopByOrderByIOrderDesc()))
//                    .itemEntity(ItemEntity.builder().iitem(item.getIitem()).build())
//                    .price(repo.findById())
//                            .build());
//        }
//
//        InsPayInfoDto1 orderDto = new InsPayInfoDto1();
//
//        try {
//            orderDto.setIuser(dto.getIuser());
//            orderDto.setAddress(dto.getAddress());
//            orderDto.setAddressDetail(dto.getAddressDetail());
//            orderDto.setShippingPrice(dto.getShippingPrice());
//            orderDto.setShippingMemo(dto.getShippingMemo());
//            orderDto.setTotalPrice(dto.getTotalPrice());
//            MAPPER.insPayInfo(orderDto);
//
//            List<PayDetailInfoVo> purchaseList = dto.getPurchaseList();
//            log.info("purchaseList = {}", purchaseList);
//            MAPPER.insPayDetailInfo(purchaseList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0L;
//        }
        return 1L;
    }

    @Override   //querydsl
    public Optional<PaymentCompleteDto> selPaymentComplete(Long iorder) {
        return dslRepo.selPaymentComplete(iorder);
    }

    @Override
    public List<SelPaymentDetailDto> selPaymentDetailAll(Long iuser) {
        return MAPPER.selPaymentDetailAll1(iuser);
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
