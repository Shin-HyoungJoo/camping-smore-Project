package com.green.campingsmore.order.payment;

import com.green.campingsmore.admin.order.refundmanage.RefundRepository;
import com.green.campingsmore.entity.*;
import com.green.campingsmore.order.payment.model.*;
import com.green.campingsmore.user.camping.ReserveRepository;
import com.green.campingsmore.user.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final RefundRepository refundRepo;

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

        if (dto.getReceiveCampingYn() != null) {


            if (dto.getReceiveCampingYn() < 0 || dto.getReceiveCampingYn() > 1) {
                throw new Exception("ReceiveCampingYn은 0,1만 가능");
            }

            if (dto.getReceiveCampingYn() == 1) {   //캠핑지로 주소입력함
                SelReserveCheckVO reserveCheck = orderRepo.selReserveCheck(dto.getIuser());

                ReserveEntity reserveEntity = resRepo.findById(reserveCheck.getIreserve()).get();

                orderEntity.setReserveEntity(reserveEntity);
            }
            orderRepo.save(orderEntity);    //결제정보저장
        } else {
            orderRepo.save(orderEntity);
        }

        List<PayDetailInfoVo> purchaseItem = dto.getPurchaseList();
        List<OrderItemEntity> entities = new ArrayList<>();
        Long iorder = orderEntity.getIorder();

        for (PayDetailInfoVo item : purchaseItem) {
            ItemEntity itemResult = ItemRepo.findById(item.getIitem()).get();

            Integer itemStock = itemResult.getStock();
            Integer purchaseStock = item.getQuantity();

            if (itemStock == 0) {
                throw new Exception("재고가 없습니다");
            }

            itemResult.setStock(itemStock - purchaseStock);
            ItemRepo.save(itemResult);


            OrderItemEntity result = OrderItemEntity.builder()
                    .orderEntity(OrderEntity.builder().iorder(iorder).build())
                    .itemEntity(ItemEntity.builder().iitem(item.getIitem()).build())
                    .price(ItemRepo.selPriceFromItem(item.getIitem()))
                    .quantity(item.getQuantity())
                    .totalPrice(item.getTotalPrice())
                    .delYn(1)
                    .refund(0)
                    .build();

            entities.add(result);
        }

        orderItemRepo.saveAll(entities);
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
    public Long delPaymentDetail(Long iorderitem) throws Exception {
        try {
            Optional<OrderItemEntity> delYN = orderItemRepo.findById(iorderitem);
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
    public SelDetailedItemPaymentInfoVo selDetailedItemPaymentInfo(Long iorderitem) {
        return orderRepo.selDetailedItemPaymentInfo(iorderitem);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public refundRequestRes refundRequest(Long iorderitem, Long iuser) throws Exception {
//        OrderItemEntity entity = orderItemRepo.selByIorderitem(iorderitem);
        Optional<OrderItemEntity> optEntity = orderItemRepo.findById(iorderitem);
//
        if (optEntity.isEmpty()) {
            throw new Exception("PK에 해당하는 상세주문이 없습니다");
        }

        OrderItemEntity entity = optEntity.get();

        entity.setRefund(1);
        orderItemRepo.save(entity);

        RefundEntity refundEntity = RefundEntity.builder()
                .userEntity(UserEntity.builder().iuser(iuser).build())
                .orderItemEntity(OrderItemEntity.builder().iorderitem(entity.getIorderitem()).build())
                .refundStartDate(LocalDateTime.now())
                .quantity(entity.getQuantity())
                .totalPrice(entity.getTotalPrice())
                .build();

        refundRepo.save(refundEntity);

        return refundRequestRes.builder()
                .iorderitem(entity.getIorderitem())
                .iitem(entity.getItemEntity().getIitem())
                .refund(entity.getRefund())
                .build();
    }
}
