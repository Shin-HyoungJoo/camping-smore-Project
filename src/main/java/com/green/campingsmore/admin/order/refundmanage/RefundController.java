package com.green.campingsmore.admin.order.refundmanage;

import com.green.campingsmore.admin.order.refundmanage.model.*;
import com.green.campingsmore.config.security.model.MyUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Tag(name = "환불 아직 건들지 마시오")
@RestController
@RequestMapping("/api/admin/refund")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService SERVICE;

    @GetMapping
    @Operation(summary = "환불 관리 보여주기",
            description =
                    "<h3> startDate : 시작 날짜(환불 시작 날짜 기준)\n" +
                            "<h3> endDate : 끝 날짜 (환불 시작 날짜 기준)\n" +
                            "<h3> listBox : " +
                            "<h3>   └주문번호(PK) : 0\n" +
                            "<h3>   └주문자명 : 1\n" +
                            "<h3>   └주문자 아이디 : 2\n" +
                            "<h3>   └주문자 휴대전화 : 3\n" +
                            "<h3>   └주문서 이메일 : 4\n" +
                            "<h3> keyword : 검색어\n" +
                            "<h3> ## 오늘 날만 찍을때는 startDate에만 오늘날짜 기입 \n" +
                            "<h3> ## listBox 기입시 keyword 기입도 필수 \n" +
                            "<h3>-----------------------------------\n" +
                            "<h3> orderDate : 주문 날짜\n" +
                            "<h3> refundStartDate : 환불 시작 날짜\n" +
                            "<h3> refundEndDate : 환불 종료 날짜\n" +
                            "<h3> iorderItem : 상세 주문 PK (주문 상세 번호)\n" +
                            "<h3> name : 주문자 이름\n" +
                            "<h3> quantity : 환불 개수\n" +
                            "<h3> totalRefund : 총 가격\n" +
                            "<h3> refundStatus : 환불 상태\n" +
                            "<h3>    └0 : 환불 대기(신청 들어온 상태)\n" +
                            "<h3>    └1 : 환불 처리중(관리자가 확인하고 처리중인 상태)\n" +
                            "<h3>    └2 : 환불 완료(환불이 완료된 상태)\n" +
                            "<h3>    └2 : 환불 불가(환불이 불가된 상태)\n"
    )
    public List<SelRefundManageVo> selRefundManageList(@RequestParam LocalDate startDate,
                                                       @RequestParam(required = false) LocalDate endDate,
                                                       @RequestParam(required = false) Integer listBox,
                                                       @RequestParam(required = false) Object keyword) throws Exception {
        return SERVICE.selRefundManageList(startDate, endDate, listBox, keyword);
    }

//    @PostMapping
//    public RefundRes postRefund(@AuthenticationPrincipal MyUserDetails user,
//                                @RequestBody InsRefund dto) {
//        dto.setIuser(user.getIuser());
//        return SERVICE.insRefund(dto);
//    }
//
//    @PatchMapping
//    public RefundRes patchRefund(@AuthenticationPrincipal MyUserDetails user,
//                                 @RequestBody PatchRefund dto) throws Exception{
//        dto.setIuser(user.getIuser());
//        return SERVICE.patchRefund(dto);
//    }
//
//    @DeleteMapping("/{irefund}")
//    public Long delRefund(@PathVariable Long irefund) {
//        return SERVICE.delRefund(irefund);
//    }
//
//    @GetMapping
//    public Long selRefund(@AuthenticationPrincipal MyUserDetails user, @RequestParam Object aaa) {
//        Long aa = Long.parseLong(String.valueOf(aaa));
//        return aa;
////        return SERVICE.selRefund(user.getIuser());
//    }

}
