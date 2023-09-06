package com.green.campingsmore.admin.main;

import com.green.campingsmore.admin.main.model.SelAggregateVO;
import com.green.campingsmore.admin.main.model.SelOrderManageVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Tag(name= "관리자 - 주문")
@RestController
@RequestMapping("/api/admin/order")
@RequiredArgsConstructor
public class MainController {
    private final MainService SERVICE;

//    @GetMapping
//    @Operation(summary = "아직 미완")
//    public List<SelAggregateVO> selAggregate() {
//        return SERVICE.selAggregate();
//    }

    @GetMapping
    @Operation(summary = "주문 관리",
            description =
                    "<h3> startDate : 시작 날짜\n" +
                            "<h3> endDate : 끝 날짜 (생략가능)\n" +
                            "<h3> listBox : " +
                            "<h3>   └주문번호(PK) : 0\n" +
                            "<h3>   └주문자명 : 1\n" +
                            "<h3>   └주문자 아이디 : 2\n" +
                            "<h3>   └주문자 휴대전화 : 3\n" +
                            "<h3>   └주문서 이메일 : 4\n" +
                            "<h3> keyword : 검색어\n" +
                            "<h3> ## 오늘 날만 찍을때는 startDate에만 오늘날짜 기입 \n"+
                            "<h3> ## listBox 기입시 keyword 기입도 필수 \n" +
                            "<h3>-----------------------------------\n" +
                            "<h3> orderDate : 주문 날짜\n" +
                            "<h3> iorder : 주문 PK (주문번호)\n" +
                            "<h3> name : 주문자 이름\n" +
                            "<h3> orderPrice : 주문 가격\n" +
                            "<h3> shippingStatus : 총 가격\n" +
                            "<h3> refundStatus : 취소(환불상태)\n"
    )
    public List<SelOrderManageVo> selOrderManageList(@RequestParam LocalDate startDate,
                                                     @RequestParam(required = false) LocalDate endDate,
                                                     @RequestParam(required = false) Integer listBox,
                                                     @RequestParam(required = false) Object keyword) throws Exception {
        return SERVICE.selOrderManageList(startDate, endDate, listBox, keyword);
    }
}