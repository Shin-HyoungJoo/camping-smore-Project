package com.green.campingsmore.admin.main;

import com.green.campingsmore.admin.main.model.SelAggregateVO;
import com.green.campingsmore.admin.main.model.SevenDaysTotalAverage;
import com.green.campingsmore.admin.main.model.SevenDaysTotalSum;
import com.green.campingsmore.order.payment.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {
    private final OrderRepository orderRepo;

    public List<SelAggregateVO> selAggregate() {
        List<SelAggregateVO> result = orderRepo.selAggregateInfo();

        SevenDaysTotalSum sevenSum = new SevenDaysTotalSum(0,0L,0,0L,0,0L);
        SevenDaysTotalAverage sevenAverage  = new SevenDaysTotalAverage(0,0L,0,0L,0,0L);

        for (SelAggregateVO list : result) {
            if (list.getOrderTotalPrice() == null) {    //널 <-> 0변환
                list.setOrderTotalPrice(0);
            }
            if (list.getRefundTotalPrice() == null) {
                list.setRefundTotalPrice(0);
            }
            if (list.getShippingCompleteTotalPrice() == null) {
                list.setShippingCompleteTotalPrice(0);
            }
            if (list.getOrderTotalCount() == null) {
                list.setOrderTotalCount(0L);
            }
            if (list.getShippingCompleteTotalCount() == null) {
                list.setShippingCompleteTotalCount(0L);
            }
            if (list.getRefundTotalCount() == null) {
                list.setRefundTotalCount(0L);
            }

            sevenSum.setOrderTotalPrice(sevenSum.getOrderTotalPrice() + list.getOrderTotalPrice());
            sevenSum.setOrderTotalCount(sevenSum.getOrderTotalCount() + list.getOrderTotalCount());
            sevenSum.setShippingCompleteTotalPrice(sevenSum.getShippingCompleteTotalPrice() + list.getShippingCompleteTotalPrice());
            sevenSum.setShippingCompleteTotalCount(sevenSum.getShippingCompleteTotalCount() + list.getShippingCompleteTotalCount());
            sevenSum.setRefundTotalPrice(sevenSum.getRefundTotalPrice() + list.getRefundTotalPrice());
            sevenSum.setRefundTotalCount(sevenSum.getRefundTotalCount() + list.getRefundTotalCount());

            sevenAverage.setOrderTotalPrice(sevenSum.getOrderTotalPrice() / result.size());
            sevenAverage.setOrderTotalCount(sevenSum.getOrderTotalCount() / result.size());
            sevenAverage.setShippingCompleteTotalPrice(sevenSum.getShippingCompleteTotalPrice() / result.size());
            sevenAverage.setShippingCompleteTotalCount(sevenSum.getShippingCompleteTotalCount() / result.size());
            sevenAverage.setRefundTotalPrice(sevenSum.getRefundTotalPrice() / result.size());
            sevenAverage.setRefundTotalCount(sevenSum.getRefundTotalCount() / result.size());

        }

        log.info("{}", result);
        return result;
    }
}
