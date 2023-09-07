package com.green.campingsmore.order.payment;

import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.entity.ItemEntity;
import com.green.campingsmore.order.payment.PayService;
import com.green.campingsmore.order.payment.model.kakao.KakaoApproveResponseDto;
import com.green.campingsmore.order.payment.model.kakao.KakaoReadyResponseDto;
import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import com.green.campingsmore.user.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoPayService {
    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    static final String admin_Key = "087a71f9ac5adb8e18338a341208ac5d"; // 공개 조심! 본인 애플리케이션의 어드민 키를 넣어주세요
    private KakaoReadyResponseDto kakaoReady;
    private final ItemRepository itemRepo;
    private InsPayInfoDto insPayInfoDto;
    private final PayService SERVICE;

    public KakaoReadyResponseDto kakaoPayReady(InsPayInfoDto dto) {
        Long iitem = dto.getPurchaseList().get(0).getIitem();
        ItemEntity itemEntity = itemRepo.findById(iitem).get();

        // 카카오페이 요청 양식
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", "가맹점 주문 번호");
        parameters.add("partner_user_id", "가맹점 회원 ID");

        if (dto.getPurchaseList().size() == 1) {
            parameters.add("item_name", itemEntity.getName());  //@@@@@@@@@@@@@@
        } else if (dto.getPurchaseList().size() >= 2) {
            String stock = " 외 " + String.valueOf(dto.getPurchaseList().size() - 1) + "건";
            String names = itemEntity.getName().substring(0,9) + "…" + stock;
            parameters.add("item_name", names);  //@@@@@@@@@@@@@@
        }

        parameters.add("quantity", 1); //수량    //@@@@@@@@@@@@@@
        parameters.add("total_amount", dto.getTotalPrice());  //총금액       ////@@@@@@@@@@@@@@
        parameters.add("vat_amount", 0); //부가세       ////@@@@@@@@@@@@@@
        parameters.add("tax_free_amount", 0); //상품 비과세 금액       ////@@@@@@@@@@@@@@
        parameters.add("approval_url", "http://localhost:8080/api/payment/kakao/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8080/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8080/payment/fail"); // 실패 시 redirect url
        this.insPayInfoDto = dto;
        log.info("11");
        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        log.info("2");
        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponseDto.class);
        log.info("3");
        return kakaoReady;
    }

    /**
     * 결제 완료 승인
     */
    public KakaoApproveResponseDto approveResponse(String pgToken) throws Exception {

        log.info("진행전");
        log.info("{}",this.insPayInfoDto);
        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoReady.getTid());
        parameters.add("partner_order_id", "가맹점 주문 번호");
        parameters.add("partner_user_id", "가맹점 회원 ID");
        parameters.add("pg_token", pgToken);

        log.info("인서트 전");
        SERVICE.insPayInfo(this.insPayInfoDto);
        log.info("인서트후");

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveResponseDto approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveResponseDto.class);
        log.info("완료");

        return approveResponse;
    }

    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + admin_Key;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }
}
