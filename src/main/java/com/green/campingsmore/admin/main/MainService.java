package com.green.campingsmore.admin.main;

import com.green.campingsmore.admin.main.model.*;
import com.green.campingsmore.order.payment.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {

    private final OrderRepository orderRepo;

    public SelAggregateFinalVO selAggregate() {
        List<SelAggregateVO> statistics = orderRepo.selAggregateInfo();
        List<SelAggregateVO> result = new ArrayList<>();

        //일주일 합계
        SevenDaysTotalSum sevenSum = new SevenDaysTotalSum(0, 0L, 0, 0L, 0, 0L);

        //일주일 평균
        SevenDaysTotalAverage sevenAverage = new SevenDaysTotalAverage(0, 0L, 0, 0L, 0, 0L);

        // null을  0으로 세팅
        for (SelAggregateVO list : statistics) {
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
        }

        // 일주일치 통계 세팅
        SimpleDateFormat format = new SimpleDateFormat("MM월 dd일");

        Calendar cal = Calendar.getInstance();
        List<String> sysDate = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            sysDate.add(format.format(cal.getTime()));
            System.out.println(format.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        loop:
        for (String date : sysDate) {

            for (SelAggregateVO list : statistics) {
                if (date.equals(list.getDate())) {    //둘이 날짜 일치하면
                    result.add(list);
                    continue loop;
                }
            }
            result.add(new SelAggregateVO(date, 0, 0L, 0, 0L, 0, 0L));
        }

        for (SelAggregateVO list : result) {
            sevenSum.setOrderTotalPrice(sevenSum.getOrderTotalPrice() + list.getOrderTotalPrice());
            sevenSum.setOrderTotalCount(sevenSum.getOrderTotalCount() + list.getOrderTotalCount());
            sevenSum.setShippingCompleteTotalPrice(sevenSum.getShippingCompleteTotalPrice() + list.getShippingCompleteTotalPrice());
            sevenSum.setShippingCompleteTotalCount(sevenSum.getShippingCompleteTotalCount() + list.getShippingCompleteTotalCount());
            sevenSum.setRefundTotalPrice(sevenSum.getRefundTotalPrice() + list.getRefundTotalPrice());
            sevenSum.setRefundTotalCount(sevenSum.getRefundTotalCount() + list.getRefundTotalCount());

            sevenAverage.setOrderTotalPrice(sevenSum.getOrderTotalPrice() / statistics.size());
            sevenAverage.setOrderTotalCount(sevenSum.getOrderTotalCount() / statistics.size());
            sevenAverage.setShippingCompleteTotalPrice(sevenSum.getShippingCompleteTotalPrice() / statistics.size());
            sevenAverage.setShippingCompleteTotalCount(sevenSum.getShippingCompleteTotalCount() / statistics.size());
            sevenAverage.setRefundTotalPrice(sevenSum.getRefundTotalPrice() / statistics.size());
            sevenAverage.setRefundTotalCount(sevenSum.getRefundTotalCount() / statistics.size());
        }

        SelAggregateFinalVO finalStatistics = new SelAggregateFinalVO();
        finalStatistics.setStatistics(result);
        finalStatistics.setSevenSum(sevenSum);
        finalStatistics.setSevenAverage(sevenAverage);

        log.info("{}", finalStatistics);
        return finalStatistics;
    }

    public SelTodayVo selTodayInfo() {
        return orderRepo.selTodayInfo();
    }
}
