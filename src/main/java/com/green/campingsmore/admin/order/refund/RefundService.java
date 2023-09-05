package com.green.campingsmore.admin.order.refund;

import com.green.campingsmore.admin.order.refund.model.InsRefund;
import com.green.campingsmore.entity.*;
import com.green.campingsmore.admin.order.refund.model.PatchRefund;
import com.green.campingsmore.admin.order.refund.model.RefundRes;
import com.green.campingsmore.admin.order.refund.model.SelRefundVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundService {
    private final RefundRepository refundRepo;

    public RefundRes insRefund(InsRefund dto) {

        RefundEntity entity = RefundEntity.builder()
                .userEntity(UserEntity.builder().iuser(dto.getIuser()).build())
                .orderItemEntity(OrderItemEntity.builder().orderEntity(OrderEntity.builder().iorder(dto.getIorder()).build()).build())
//                .orderItemEntity2(OrderItemEntity.builder().itemEntity(ItemEntity.builder().iitem(dto.getIitem()).build()).build())
                .refundStartDate(dto.getRefundStartDate())
                .refundStartDate(dto.getRefundStartDate())
                .quantity(dto.getQuantity())
                .totalPrice(dto.getTotalPrice())
                .refundStatus(0)
                .delYn(1)
                .build();

        refundRepo.save(entity);

        return RefundRes.builder()
                .irefund(entity.getIrefund())
                .iuser(entity.getUserEntity().getIuser())
                .iorder(entity.getOrderItemEntity().getOrderEntity().getIorder())
//                .iitem(entity.getOrderItemEntity2().getItemEntity().getIitem())
                .refundStartDate(entity.getRefundStartDate())
                .quantity(entity.getQuantity())
                .totalPrice(entity.getTotalPrice())
                .refundStatus(entity.getRefundStatus())
                .build();
    }

    public RefundRes patchRefund(PatchRefund dto) throws Exception {
        Optional<RefundEntity> entity = refundRepo.findById(dto.getIrefund());

        if (entity.isEmpty()) {
            return new RefundRes();
        }

        RefundEntity result = entity.get();
        result.setRefundStatus(dto.getRefundStatus());

        if (dto.getRefundStatus() > 3) {
            throw new Exception("0,1,2,3만 입력가능");
        } else if (dto.getRefundStatus() < 0) {
            throw new Exception("0,1,2,3만 입력가능");
        }

        if (entity.get().getRefundStatus() == 0 && (dto.getRefundStatus() != 1)) {
            throw new Exception("0일땐 1만 입력가능");
        } else if (entity.get().getRefundStatus() == 1 && (dto.getRefundStatus() != 2)) {
            throw new Exception("1일땐 2만 입력가능");
        } else if (entity.get().getRefundStatus() == 2 && (dto.getRefundStatus() != 3)) {
            throw new Exception("2일땐 3만 입력가능");
        } else if (entity.get().getRefundStatus() == 3 && (dto.getRefundStatus() <= 0)) {
            throw new Exception("3일땐 변경 불가능");
        }

            refundRepo.save(result);

            return RefundRes.builder()
                    .irefund(result.getIrefund())
                    .iuser(result.getUserEntity().getIuser())
                    .iorder(result.getOrderItemEntity().getOrderEntity().getIorder())
//                    .iitem(result.getOrderItemEntity2().getItemEntity().getIitem())
                    .refundStartDate(result.getRefundStartDate())
                    .quantity(result.getQuantity())
                    .totalPrice(result.getTotalPrice())
                    .refundStatus(result.getRefundStatus())
                    .build();
        }


        public Long delRefund (Long irefund){
            Optional<RefundEntity> entity = refundRepo.findById(irefund);

            if (entity.isEmpty()) {
                return 0L;
            }

            RefundEntity result = entity.get();
            result.setDelYn(0);

            refundRepo.save(result);

            return 1L;
        }

        public List<SelRefundVo> selRefund (Long iuser){
            return refundRepo.selRefund(iuser);
        }
    }
