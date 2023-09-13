package com.green.campingsmore.order.payment;

import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.order.payment.model.kakao.KakaoApproveResponseDto;
import com.green.campingsmore.order.payment.model.kakao.KakaoReadyResponseDto;
import com.green.campingsmore.order.payment.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Tag(name = "결제")
public class PayController {

    private final PayService SERVICE;
    private final KakaoPayService kakaoPayService;

    @PostMapping    //Check, if문 분기(카카오, 예약시) 필요
    @Operation(summary = "결제 정보 저장하기",
            description =
                    "<h3> address : 배송지\n" +
                            "<h3> addressDetail : 상세 배송지\n" +
                            "<h3> totalPrice : 총 결제 금액\n" +
                            "<h3> shippingPrice : 배송비\n" +
                            "<h3> shippingMemo : 배송 메모\n" +
                            "<h3> type : 결제 타입 (KAKAO, CARD) 택 1\n" +
                            "<h3> ReceiveCampingYn : 캠핑지로 받을건지 여부 (0 : 일반 배송지, 1 : 캠핑지) \n" +
                            "<h3> purchaseList : 구입 목록\n" +
                            "<h3>   └iitem : 결제한 아이템 PK\n" +
                            "<h3>   └quantity : 아이템 수량\n" +
                            "<h3>   └totalPrice : 아이템별 총 가격\n" +
                            "<h3>-----------------------------------\n" +
                            "<h3>   CODE 1 : DB 정보 저장 성공\n" +
                            "<h3>   CODE 0 : DB 정보 저장 실패\n"
    )
    public Long postPayInfo(@AuthenticationPrincipal MyUserDetails user,
                            @RequestBody InsPayInfoDto dto) throws Exception {
        dto.setIuser(user.getIuser());
        return SERVICE.insPayInfo(dto);
    }

    @GetMapping("/{iorder}")    //check
    @Operation(summary = "결제 내역 보기(결제 완료 페이지)",
            description = "<h3> iorder : 주문 PK\n" +
                    "<h3>-----------------------------------\n" +
                    "<h3> address : 주소\n" +
                    "<h3> addressDetail : 상세주소\n" +
                    "<h3> shippingMemo : 배송 메모\n" +
                    "<h3> totalPrice : 총 가격\n"


    )    //유저 결제시 띄움(결제창에서 바로 띄움)
    public ResponseEntity<Optional<PaymentCompleteDto>> getPaymentComplete(@PathVariable Long iorder) {
        return ResponseEntity.ok(SERVICE.selPaymentComplete(iorder));
    }

    @GetMapping("/payment-list")
    @Operation(summary = "전체 결제 내역 보기(마이 페이지)",
            description = "<h3> iorder : 주문 PK\n" +
                    "<h3>-----------------------------------\n" +
                    "<h3> iorder : 주문 PK\n" +
                    "<h3> shipping : 배송 상태 (0 : 배송 준비중, 1 : 배송중, 2 : 배송완료, 3 : 배송취소)\n" +
                    "<h3> itemList : 결제 내역 리스트 PK\n" +
                    "<h3>   └iitem : 아이템 PK\n" +
                    "<h3>   └name : 아이템 이름 PK\n" +
                    "<h3>   └totalPrice : 총 가격 PK\n" +
                    "<h3>   └Pic : 사진 PK\n" +
                    "<h3>   └paymentDate : 결제일\n" +
                    "<h3>   └reviewYn : 리뷰 존재 여부 (있을 시 ireview 반환, 없을 시 0 반환)\n"
    )
    //유저마이페이지에서 조회
    public ResponseEntity<List<SelPaymentDetailDto>> getPaymentList(@AuthenticationPrincipal MyUserDetails user) {
        Long iuser = user.getIuser();
        return ResponseEntity.ok(SERVICE.selPaymentDetailAll(iuser));
    }

    @GetMapping("/payment-list/detail/{iorderitem}")
    @Operation(summary = "상세 결제 내역 보기(마이 페이지)",
            description = "<h3> iorderitem : 상세 결제내역 PK\n" +
                    "<h3>-----------------------------------\n" +
                    "<h3> iorderitem : 상세 결제내역 PK\n" +
                    "<h3> iitem : 아이템 PK\n" +
                    "<h3> name : 아이템 이름\n" +
                    "<h3> price : 아이템 가격\n" +
                    "<h3> quantity : 아이템 수량\n" +
                    "<h3> totalPrice : 총 가격\n" +
                    "<h3> pic : 아이템 사진\n" +
                    "<h3> paymentDate : 결제일\n" +
                    "<h3> address : 주소 \n" +
                    "<h3> addressDetail : 상세 주소\n" +
                    "<h3> shippingPrice : 배송비 PK\n" +
                    "<h3> shippingMemo : 배송 메모 PK\n"

    ) //유저마이페이지에서 조회
    public SelDetailedItemPaymentInfoVo getDetailedItemPaymentInfo(@PathVariable Long iorderitem) {
        return SERVICE.selDetailedItemPaymentInfo(iorderitem);
    }

    @PutMapping("/payment-list/{iorderitem}")
    @Operation(summary = "전체 결제 내역에서 하나의 결제 내역 삭제(아이템별, 마이 페이지)",
            description = "<h3> iorderitem : 상세 결제내역 PK\n" +
                    "<h3>-----------------------------------\n" +
                    "<h3>CODE 1 : 선택한 결제내역 삭제\n" +
                    "<h3>CODE 0 : 삭제되지 않음\n"
    ) //유저마이페이지에서 조회
    public Long delPaymentDetail(@PathVariable Long iorderitem) throws Exception {
        return SERVICE.delPaymentDetail(iorderitem);
    }

    @PostMapping("/order/cart")
    @Operation(summary = "장바구니 결제 버튼 -> 체크된 장바구니 아이템 정보들을 결제 페이지에서 보여주기",
            description = "<h3> icart : 장바구니 PK\n" +
                    "<h3>-----------------------------------\n" +
                    "<h4># Post 요청이지만 아이템 정보를 보여줌 #\n" +
                    "<h3>-----------------------------------\n" +
                    "<h3> itemList : 결제 내역 리스트 PK\n" +
                    "<h3>   └iitem : 아이템 PK\n" +
                    "<h3>   └name : 아이템 이름\n" +
                    "<h3>   └price : 아이템 가격\n" +
                    "<h3>   └quantity : 아이템 수량\n" +
                    "<h3>   └pic : 사진\n" +
                    "<h3> shippingPrice : 배송비 PK\n" +
                    "<h3> totalPrice : 총 가격\n" +
                    "<h3> reserveYn : 캠핑예약 여부 (\n" +
                    "<h3> campInfo : 예약 정보\n" +
                    "<h3>   └icamp : 캠핑지 PK\n" +
                    "<h3>   └name : 캠핑지 이름 \n" +
                    "<h3>   └address : 캠핑지 주소 \n" +
                    "<h3>   └campPhone : 캠핑지 전화 \n" +
                    "<h3>   └reservation : 예약날짜 \n"
    )
    public CartPaymentDetailDto getPaymentItemList(@AuthenticationPrincipal MyUserDetails user,
                                                   @RequestBody CartPKDto dto) {
        return SERVICE.selPaymentPageItemList(dto, user.getIuser());
    }

    @GetMapping("/order/{iitem}")
    @Operation(summary = "아이템 구매 버튼 -> 단일 아이템 정보를 결제 페이지에서 보여주기",
            description =
                    "<h3> iitem : 결제 클릭한 아이템의 PK\n" +
                            "<h3> quantity : 올려놓은 아이템의 수량\n" +
                            "<h3> ireserve : 캠핑 예약 PK\n" +
                            "<h3>-----------------------------------\n" +
                            "<h3> iitem : 아이템 PK\n" +
                            "<h3> name : 아이템 이름\n" +
                            "<h3> price : 아이템 가격\n" +
                            "<h3> quantity : 아이템 수량\n" +
                            "<h3> shppingPrice : 배송비\n" +
                            "<h3> totalPrice : 결제 총 가격(배송비 포함)\n" +
                            "<h3> pic : 이미지\n" +
                            "<h3> reserveYn : 캠핑 예약 여부 (0 : 예약X, 1: 예약O)\n" +
                            "<h3> campInfo : 예약 정보\n" +
                            "<h3>   └icamp : 캠핑지 PK\n" +
                            "<h3>   └name : 캠핑지 이름 \n" +
                            "<h3>   └address : 캠핑지 주소 \n" +
                            "<h3>   └campPhone : 캠핑지 전화 \n" +
                            "<h3>   └reservation : 예약날짜 \n"

    )
    public PaymentDetailDto getPaymentItem(@AuthenticationPrincipal MyUserDetails user,
                                           @PathVariable Long iitem, @RequestParam Integer quantity) {
        return SERVICE.selPaymentPageItem(iitem, quantity, user.getIuser());
    }

    @PostMapping("/address")
    @Operation(summary = "배송지 추가 등록",
            description =
                    "<h3> address : 주소\n" +
                            "<h3> addressDetail : 상세 주소\n" +
                            "<h3> name : 수령인\n" +
                            "<h3> phone : 전화번호 (하이픈 '-'없이)\n" +
                            "<h3>-----------------------------------\n" +
                            "CODE 1 : 등록 성공\n"
    )
    public Long insAddress(@AuthenticationPrincipal MyUserDetails user,
                           @RequestBody ShippingInsDto dto) {
        dto.setIuser(user.getIuser());
        return SERVICE.insAddress(dto);
    }

    @GetMapping("/address")
    @Operation(summary = "기본 배송지(유저 주소) 출력",
            description =
                    "<h3>-----------------------------------\n" +
                            "<h3> userAddress : 해당 유저의 주소\n" +
                            "<h3> userAddressDetail : 해당 유저의 상세 주소\n" +
                            "<h3> name : 수령인\n" +
                            "<h3> phone : 전화번호 (하이픈 '-'없이)\n"
    )
    public SelUserAddressVo selUserAddress(@AuthenticationPrincipal MyUserDetails user) {
        Long iuser = user.getIuser();
        return SERVICE.selUserAddress(iuser);
    }

    @GetMapping("/address-list")
    @Operation(summary = "등록된 배송지 리스트 출력",
            description =
                    "<h3>-----------------------------------\n" +
                            "<h3> address : 주소\n" +
                            "<h3> addressDetail : 상세 주소\n" +
                            "<h3> name : 수령인\n" +
                            "<h3> phone : 전화번호 (하이픈 '-'없이)\n"
    )
    public List<ShippingListSelVo> selAddressList(@AuthenticationPrincipal MyUserDetails user) {
        Long iuser = user.getIuser();
        return SERVICE.selAddressList(iuser);
    }

    @GetMapping("/address-list/{iaddress}")
    @Operation(summary = "등록된 배송지 중 선택한 배송지 정보 출력",
            description =
                    "<h3> iaddress : 등록한 배송지PK\n" +
                            "<h3>-----------------------------------\n" +
                            "<h3> address : 주소\n" +
                            "<h3> addressDetail : 상세 주소\n" +
                            "<h3> name : 수령인\n" +
                            "<h3> phone : 전화번호 (하이픈 '-'없이)\n"
    )
    public ShippingListSelVo selOneAddress(@AuthenticationPrincipal MyUserDetails user,
                                           @PathVariable Long iaddress) {
        SelUserAddressDto dto1 = new SelUserAddressDto();
        Long iuser = user.getIuser();
        dto1.setIuser(iuser);
        dto1.setIaddress(iaddress);
        return SERVICE.selOneAddress(dto1);
    }

    @DeleteMapping("/address/{iaddress}")
    @Operation(summary = "등록된 배송지 제거",
            description = "<h3> iaddress : 등록한 배송지PK\n" +
                    "<h3>-----------------------------------\n" +
                    "Code 1 : 삭제 성공\n"
    )
    public Long delAddress(@PathVariable Long iaddress) {
        return SERVICE.delAddress(iaddress);
    }

    @PatchMapping("/payment-list/detail/{iorderitem}")
    @Operation(summary = "해당 상세주문 환불 요청",
            description = "<h3> iorderitem : 상세 주문 PK\n" +
                    "<h3>-----------------------------------\n" +
                    "<h3>iorderitem : 주문 상세 PK\n" +
                    "<h3>iitem : 아이템PK\n" +
                    "<h3>refund : 환불 상태 \n" +
                    "<h3>  └0 : 환불 전(기본값) \n" +
                    "<h3>  └1 : 환불 진행중 \n" +
                    "<h3>  └2 : 환불 완료 \n" +
                    "<h3>  └3 : 환불 불가 \n" +
                    "<h3>  ## refund가 1이 되면 관리자 측에 환불 정보가 넘어감 "
    )
    public refundRequestRes refundRequest(@AuthenticationPrincipal MyUserDetails user,
                                          @PathVariable Long iorderitem) throws Exception {
        return SERVICE.refundRequest(iorderitem, user.getIuser());
    }

    @PatchMapping("/payment-list/{iorder}")
    @Operation(summary = "배송 준비중일때 취소요청",
            description = "<h3> iorder : 주문 PK\n" +
                    "<h3>-----------------------------------\n" +
                    "<h3>Code 1 : 취소 성공, (환불 테이블 데이터생성) PK\n"
    )
    public Long cancelRequest (@AuthenticationPrincipal MyUserDetails user,
                               @PathVariable Long iorder) throws Exception {
        return SERVICE.cancelRequest(iorder, user.getIuser());
    }










    @PostMapping("/kakao/ready")
    @Operation(summary = "카카오 페이 요청",
    description = "<h3> ireserve : 캠핑 예약번호 (없을 경우 그대로 둘 것)\n" +
            "<h3> address : 배송지\n" +
            "<h3> addressDetail : 상세 배송지\n" +
            "<h3> totalPrice : 총 결제 금액\n" +
            "<h3> shippingPrice : 배송비\n" +
            "<h3> shippingMemo : 배송 메모\n" +
            "<h3> type : 결제 타입 (KAKAO, CARD) 택 1\n" +
            "<h3> ReceiveCampingYn : 캠핑지로 받을건지 여부 (0 : 일반 배송지, 1 : 캠핑지) \n" +
            "<h3> purchaseList : 구입 목록\n" +
            "<h3>   └iitem : 결제한 아이템 PK\n" +
            "<h3>   └quantity : 아이템 수량\n" +
            "<h3>   └totalPrice : 아이템별 총 가격\n" +
            "<h3>-----------------------------------\n" +
            "<h3>   CODE 1 : DB 정보 저장 성공\n" +
            "<h3>   CODE 0 : DB 정보 저장 실패\n"
    )
    public KakaoReadyResponseDto readyToKakaoPay(@AuthenticationPrincipal MyUserDetails user,
                                                 @RequestBody InsPayInfoDto dto) {
        dto.setIuser(user.getIuser());
        return kakaoPayService.kakaoPayReady(dto);
    }

    /**
     * 결제 성공
     */
    @GetMapping("/kakao/success")
    public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pgToken) throws Exception {

        KakaoApproveResponseDto kakaoApprove = kakaoPayService.approveResponse(pgToken);

        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
//        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }
}