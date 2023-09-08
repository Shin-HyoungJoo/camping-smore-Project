package com.green.campingsmore.admin.main;

import com.green.campingsmore.admin.main.model.SelAggregateFinalVO;
import com.green.campingsmore.admin.main.model.SelAggregateVO;
import com.green.campingsmore.admin.main.model.SelTodayVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name= "관리자 - 메인")
@RestController
@RequestMapping("/api/admin/main")
@RequiredArgsConstructor
public class MainController {
    private final MainService SERVICE;

    @GetMapping("/today")
    @Operation(summary = "상단 통계표",
            description =
                    "<h3>-----------------------------------\n" +
                            "<h3>userCount : 회원 수 \n" +
                            "<h3>shippingBefore : 배송 준비중 \n" +
                            "<h3>shipping : 배송중 \n" +
                            "<h3>refundBefore : 환불전 \n" +
                            "<h3>soldOut : 품절 상품 \n" +
                            "<h3>newBoard : 새 게시물 \n" +
                            "<h3>newReserve : 예약 \n"
    )
    public SelTodayVo selTodayInfo() {
        return SERVICE.selTodayInfo();
    }

    @GetMapping
    @Operation(summary = "하단 통계표",
            description =
                    "<h3>-----------------------------------\n" +
                    "<h3>statistics : 오늘기준 과거 일주일까지 통계 \n" +
                    "<h3>  └date : 날짜 (x월 x일) PK\n" +
                    "<h3>  └orderTotalPrice : 주문 가격 \n" +
                    "<h3>  └orderTotalCount : 주문 개수 \n" +
                    "<h3>  └shippingCompleteTotalPrice : 배송 완료(된) 가격 \n" +
                    "<h3>  └shippingCompleteTotalCount : 배송 완료(된) 개수 \n" +
                    "<h3>  └refundTotalPrice : 환불(환불 완료된) 가격 \n" +
                    "<h3>  └refundTotalCount : 환불(환불 완료된) 개수 \n" +
                    "<h3>sevenSum : 최근 7일 합계 \n" +
                    "<h3>sevenAverage : 최근 7일 평균 \n"
    )
    public SelAggregateFinalVO selAggregate() {
        return SERVICE.selAggregate();
    }
}
