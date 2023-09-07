package com.green.campingsmore.admin.order.ordermanage;

import com.green.campingsmore.admin.order.ordermanage.model.SelAggregateVO;
import com.green.campingsmore.admin.order.ordermanage.model.SevenDaysTotalAverage;
import com.green.campingsmore.admin.order.ordermanage.model.SelOrderManageVo;
import com.green.campingsmore.admin.order.ordermanage.model.SevenDaysTotalSum;
import com.green.campingsmore.admin.order.refundmanage.model.PatchShipping;
import com.green.campingsmore.admin.order.refundmanage.model.ShippingRes;
import com.green.campingsmore.entity.OrderEntity;
import com.green.campingsmore.order.payment.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderManageService {
    private final OrderRepository orderRepo;

//    public List<SelAggregateVO> selAggregate() {
//        List<SelAggregateVO> result = orderRepo.selAggregateInfo();
//
//        SevenDaysTotalSum sevenSum = new SevenDaysTotalSum(0,0L,0,0L,0,0L);
//        SevenDaysTotalAverage sevenAverage  = new SevenDaysTotalAverage(0,0L,0,0L,0,0L);
//
//        for (SelAggregateVO list : result) {
//            if (list.getOrderTotalPrice() == null) {    //널 <-> 0변환
//                list.setOrderTotalPrice(0);
//            }
//            if (list.getRefundTotalPrice() == null) {
//                list.setRefundTotalPrice(0);
//            }
//            if (list.getShippingCompleteTotalPrice() == null) {
//                list.setShippingCompleteTotalPrice(0);
//            }
//            if (list.getOrderTotalCount() == null) {
//                list.setOrderTotalCount(0L);
//            }
//            if (list.getShippingCompleteTotalCount() == null) {
//                list.setShippingCompleteTotalCount(0L);
//            }
//            if (list.getRefundTotalCount() == null) {
//                list.setRefundTotalCount(0L);
//            }
//
//            sevenSum.setOrderTotalPrice(sevenSum.getOrderTotalPrice() + list.getOrderTotalPrice());
//            sevenSum.setOrderTotalCount(sevenSum.getOrderTotalCount() + list.getOrderTotalCount());
//            sevenSum.setShippingCompleteTotalPrice(sevenSum.getShippingCompleteTotalPrice() + list.getShippingCompleteTotalPrice());
//            sevenSum.setShippingCompleteTotalCount(sevenSum.getShippingCompleteTotalCount() + list.getShippingCompleteTotalCount());
//            sevenSum.setRefundTotalPrice(sevenSum.getRefundTotalPrice() + list.getRefundTotalPrice());
//            sevenSum.setRefundTotalCount(sevenSum.getRefundTotalCount() + list.getRefundTotalCount());
//
//            sevenAverage.setOrderTotalPrice(sevenSum.getOrderTotalPrice() / result.size());
//            sevenAverage.setOrderTotalCount(sevenSum.getOrderTotalCount() / result.size());
//            sevenAverage.setShippingCompleteTotalPrice(sevenSum.getShippingCompleteTotalPrice() / result.size());
//            sevenAverage.setShippingCompleteTotalCount(sevenSum.getShippingCompleteTotalCount() / result.size());
//            sevenAverage.setRefundTotalPrice(sevenSum.getRefundTotalPrice() / result.size());
//            sevenAverage.setRefundTotalCount(sevenSum.getRefundTotalCount() / result.size());
//
//        }
//
//        log.info("{}", result);
//        return result;
//    }

    @Transactional(rollbackFor = {Exception.class})
    public List<SelOrderManageVo> selOrderManageList(LocalDate startDate, LocalDate endDate, Integer listBox, Object keyword) throws Exception {
        if (listBox == null && keyword != null) {
            throw new Exception("리스트 박스를 입력해주세요.");
        } else if (listBox != null && keyword == null) {
            throw new Exception("키워드를 입력해주세요.");
        }

        return orderRepo.SelOrderManageInfo(startDate, endDate, listBox, keyword);
    }

    public ShippingRes patchShipping(PatchShipping dto) throws Exception {
        Optional<OrderEntity> entity = orderRepo.findById(dto.getIorder());

        if (entity.isEmpty()) {
            return new ShippingRes();
        }

        OrderEntity result = entity.get();

        if (dto.getShipping() > 3) {
            throw new Exception("1,2,3만 입력가능");
        } else if (dto.getShipping() < 1) {
            throw new Exception("1,2,3만 입력가능");
        }

        if (entity.get().getShipping() == 0 && dto.getShipping() != 1 && dto.getShipping() != 3) {
            throw new Exception("배송준비(0)일땐 배송중(1)과 배송취소(3)만 입력가능");
        } else if (entity.get().getShipping() == 1 && dto.getShipping() != 2) {
            throw new Exception("배송중(1)일땐 배송완료(2)만 입력가능");
        } else if (entity.get().getShipping() == 2) {
            throw new Exception("배송완료(2)일땐 변경 불가능");
        } else if (entity.get().getShipping() == 3) {
            throw new Exception("배송취소(3)일땐 변경 불가능");
        }

        result.setShipping(dto.getShipping());
        orderRepo.save(result);

        return ShippingRes.builder()
                .iorder(result.getIorder())
                .shipping(result.getShipping())
                .build();
    }
}
