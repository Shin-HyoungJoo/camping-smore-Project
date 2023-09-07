package com.green.campingsmore.admin.order.refundmanage;

import com.green.campingsmore.admin.order.refundmanage.model.*;
import com.green.campingsmore.entity.*;
import com.green.campingsmore.order.payment.OrderItemRepository;
import com.green.campingsmore.order.payment.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundService {
    private final RefundRepository refundRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;

    //    public RefundRes insRefund(InsRefund dto) {
//
//        RefundEntity entity = RefundEntity.builder()
//                .userEntity(UserEntity.builder().iuser(dto.getIuser()).build())
//                .orderItemEntity(OrderItemEntity.builder().iorderitem(dto.getIorderItem()).build())
//                .refundStartDate(dto.getRefundStartDate())
//                .quantity(dto.getQuantity())
//                .totalPrice(dto.getTotalPrice())
//                .refundStatus(0)
//                .delYn(1)
//                .build();
//        refundRepo.save(entity);
//
//        OrderItemEntity orderItemEntity = orderItemRepo.findById(dto.getIorderItem()).get();
//        orderItemEntity.setRefund(1);
//        orderItemRepo.save(orderItemEntity);
//
//        return RefundRes.builder()
//                .irefund(entity.getIrefund())
//                .iuser(entity.getUserEntity().getIuser())
//                .iorderitem(entity.getOrderItemEntity().getIorderItem())
//                .refundStartDate(entity.getRefundStartDate())
//                .quantity(entity.getQuantity())
//                .totalPrice(entity.getTotalPrice())
//                .refundStatus(entity.getRefundStatus())
//                .build();
//    }
//
    public RefundRes patchRefund(PatchRefund dto) throws Exception {
        Optional<RefundEntity> entity = refundRepo.findById(dto.getIrefund());

        if (entity.isEmpty()) {
            return new RefundRes();
        }

        RefundEntity result = entity.get();

        if (dto.getRefundStatus() > 3) {
            throw new Exception("1,2,3만 입력가능");
        } else if (dto.getRefundStatus() < 1) {
            throw new Exception("1,2,3만 입력가능");
        }

        if (entity.get().getRefundStatus() == 0 && (dto.getRefundStatus() != 1)) {
            throw new Exception("0일땐 1만 입력가능");
        } else if (entity.get().getRefundStatus() == 1 && (dto.getRefundStatus() < 2 || dto.getRefundStatus() > 3)) {
            throw new Exception("1일땐 2, 3만 입력가능");
        } else if (entity.get().getRefundStatus() == 2 || entity.get().getRefundStatus() == 3) {
            throw new Exception("2, 3일땐 변경 불가능");
        }

        result.setRefundStatus(dto.getRefundStatus());

        if (dto.getRefundStatus() == 2) {   //관리자 환불완료 -> 유저 환불완료 처리
            result.setRefundEndDate(LocalDateTime.now());
            refundRepo.save(result);
            Long iorderitem = result.getOrderItemEntity().getIorderitem();

            OrderItemEntity orderItemEntity = orderItemRepo.findById(iorderitem).get();
            orderItemEntity.setRefund(2);

            orderItemRepo.save(orderItemEntity);    //유저환불상태변경
        } else if (dto.getRefundStatus() == 3) {   //관리자 환불완료 -> 유저 환불완료 처리
            result.setRefundEndDate(LocalDateTime.now());
            refundRepo.save(result);
            Long iorderitem = result.getOrderItemEntity().getIorderitem();

            OrderItemEntity orderItemEntity = orderItemRepo.findById(iorderitem).get();
            orderItemEntity.setRefund(3);

            orderItemRepo.save(orderItemEntity);    //유저환불상태변경
        } else {
            refundRepo.save(result);    //0일경우 - 1로 변경
        }

        return RefundRes.builder()
                .irefund(result.getIrefund())
                .iuser(result.getUserEntity().getIuser())
                .name(refundRepo.selUserName(result.getUserEntity().getIuser()))
                .iorderitem(result.getOrderItemEntity().getOrderEntity().getIorder())
                .refundStartDate(result.getRefundStartDate())
                .refundEndDate(result.getRefundEndDate())
                .quantity(result.getQuantity())
                .totalPrice(result.getTotalPrice())
                .refundStatus(result.getRefundStatus())
                .build();
    }
//
//
//    public Long delRefund(Long irefund) {
//        Optional<RefundEntity> entity = refundRepo.findById(irefund);
//
//        if (entity.isEmpty()) {
//            return 0L;
//        }
//
//        RefundEntity result = entity.get();
//        result.setDelYn(0);
//
//        refundRepo.save(result);
//
//        return 1L;
//    }
//
//    public List<SelRefundVo> selRefund(Long iuser) {
//        return refundRepo.selRefund(iuser);
//    }

    public List<SelRefundManageVo> selRefundManageList(LocalDate startDate, LocalDate endDate, Integer listBox, Object keyword) throws Exception {
        if (listBox == null && keyword != null) {
            throw new Exception("리스트 박스를 입력해주세요.");
        } else if (listBox != null && keyword == null) {
            throw new Exception("키워드를 입력해주세요.");
        }
        return refundRepo.selRefundManageList(startDate, endDate, listBox, keyword);
    }


}
