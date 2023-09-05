package com.green.campingsmore.order.payment;

import com.green.campingsmore.entity.*;
import com.green.campingsmore.order.payment.model.*;
import com.green.campingsmore.user.camping.ReserveRepository;
import com.green.campingsmore.user.item.ItemRepository;
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

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final ReserveRepository resRepo;
    private final ShippingAddressRepository shippingRepo;
    private final ItemRepository ItemRepo;

    @Override       //dsl
    @Transactional(rollbackFor = {Exception.class})
    public Long insPayInfo(InsPayInfoDto dto) throws Exception {

        OrderEntity orderEntity = OrderEntity.builder()
                .userEntity(UserEntity.builder().iuser(dto.getIuser()).build())
                .reserveEntity(null)
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .totalPrice(dto.getTotalPrice())
                .shippingPrice(dto.getShippingPrice())
                .shippingMemo(dto.getShippingMemo())
                .type(dto.getType())
                .delYn(1)
                .shipping(0)
                .build();

        if(dto.getReceiveCampingYn() < 0 || dto.getReceiveCampingYn() > 1) {
            throw new Exception("ReceiveCampingYn은 0,1만 가능");
        }

        if (dto.getReceiveCampingYn() == 1) {   //캠핑지로 주소입력함
            SelReserveCheckVO reserveCheck = orderRepo.selReserveCheck(dto.getIuser());

            ReserveEntity reserveEntity = resRepo.findById(reserveCheck.getIreserve()).get();

            orderEntity.setReserveEntity(reserveEntity);
        }

        orderRepo.save(orderEntity);    //결제정보저장

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

            ItemEntity itemResult = ItemRepo.findById(item.getIitem()).get();

            Integer itemStock = itemResult.getStock();
            Integer purchaseStock = item.getQuantity();

            if (itemStock == 0) {
                throw new Exception("재고가 없습니다");
            }

            itemResult.setStock(itemStock - purchaseStock);
            ItemRepo.save(itemResult);

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
    public PaymentDetailDto selPaymentPageItem(Long iitem, Integer quantity, Long iuser) {
        Integer shppingPrice = 3000;

        SelReserveCheckVO reserveCheck = orderRepo.selReserveCheck(iuser);

        if (reserveCheck == null) {
            PaymentDetailDto result = orderRepo.selPaymentPageItem(iitem);
            result.setQuantity(quantity);
            result.setTotalPrice(result.getPrice() * quantity);
            result.setReserveYn(0);
            return result;
        }

        PaymentDetailDto result = orderRepo.selPaymentPageItem(iitem);
        result.setQuantity(quantity);
        result.setShippingPrice(shppingPrice);
        result.setTotalPrice(result.getPrice() * quantity + shppingPrice);
        result.setReserveYn(1);

        SelReserveInfoVo campInfoResult = orderRepo.selCampInfo(reserveCheck.getIreserve());
        result.setCampInfo(campInfoResult);
        return result;
    }

    @Override   //완 //배송비
    @Transactional(rollbackFor = {Exception.class}) //querydsl
    public CartPaymentDetailDto selPaymentPageItemList(CartPKDto dto, Long iuser) {

        SelReserveCheckVO reserveCheck = orderRepo.selReserveCheck(iuser);
        Integer totalPrice = 0;
        Integer shippingPrice = 3000;

        if (reserveCheck == null) {
            List<CartPaymentItemDto> itemList = orderRepo.selPaymentPageItemList(dto);
            try {
                for (CartPaymentItemDto item : itemList) {
                    Integer price = item.getPrice();
                    Integer quantity = item.getQuantity();
                    totalPrice += price * quantity;
                }
                CartPaymentDetailDto result = CartPaymentDetailDto.builder()
                        .itemList(itemList)
                        .shippingPrice(shippingPrice)
                        .totalPrice(totalPrice + shippingPrice)
                        .reserveYn(0)
                        .build();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<CartPaymentItemDto> itemList = orderRepo.selPaymentPageItemList(dto);

        for (CartPaymentItemDto item : itemList) {
            Integer price = item.getPrice();
            Integer quantity = item.getQuantity();
            totalPrice += price * quantity;
        }

        CartPaymentDetailDto result = CartPaymentDetailDto.builder()
                .itemList(itemList)
                .shippingPrice(shippingPrice)
                .totalPrice(totalPrice + shippingPrice)
                .reserveYn(1)
                .build();

        SelReserveInfoVo campInfoResult = orderRepo.selCampInfo(reserveCheck.getIreserve());
        result.setCampInfo(campInfoResult);

        return result;
    }

    @Override   //완
    @Transactional(rollbackFor = {Exception.class}) //jpa
    public Long delPaymentDetail(Long iorderItem) throws Exception {
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
    public SelDetailedItemPaymentInfoVo selDetailedItemPaymentInfo(Long iorderItem) {
        return orderRepo.selDetailedItemPaymentInfo(iorderItem);
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
