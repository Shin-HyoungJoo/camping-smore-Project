package com.green.campingsmore.user.camping;

import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.user.camping.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/camp")
@RequiredArgsConstructor
@Tag(name = "캠핑장")
public class CampingController {
    private final CampingService SERVICE;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "캠핑장 만들기")
    public ResponseEntity<CampingRes> InsCamp(@RequestPart(required = false) MultipartFile pic,
                                              @RequestPart CampingDto dto) throws Exception {
        return ResponseEntity.ok(SERVICE.InsCamp(pic, dto));
    }

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "캠핑장 수정")
    public ResponseEntity<CampingRes> updCamp(@RequestPart(required = false) MultipartFile pic,
                                              @RequestPart CampingUpdDto dto) throws Exception {
        return ResponseEntity.ok(SERVICE.updCamping(pic, dto));
    }

    @PutMapping("/delcamp")
    @Operation(summary = "캠핑장 삭제")
    public ResponseEntity<CampingRes> delCamp(@RequestBody CampingDelDto dto) {
        return ResponseEntity.ok(SERVICE.delCamping(dto));
    }

    @PostMapping(value = "/detail", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "사진 다중 업로드")
    public ResponseEntity<List<String>> InsPic(@RequestPart(required = false) List<MultipartFile> pics, @RequestPart CampingPicDto dto) throws Exception {
        return ResponseEntity.ok(SERVICE.InsPic(pics, dto));
    }

    @DeleteMapping
    @Operation(summary = "상세이미지 삭제")
    public ResponseEntity<Long> delPic(@RequestBody CampingPicDelDto dto) {
        return ResponseEntity.ok(SERVICE.delPic(dto));
    }
    @PostMapping("/reserve-camp")
    @Operation(summary = "예약 가능 캠핑장")
    public ResponseEntity<DailyRes> InsReserveCamp(@RequestBody DailyDto dto){
        return ResponseEntity.ok(SERVICE.InsReserveCamp(dto));
    }

    @PostMapping("/reserve")
    @Operation(summary = "예약")
    public ResponseEntity<ReserveRes> InsReserve(@RequestBody ReserveDto dto) throws Exception {
        return ResponseEntity.ok(SERVICE.InsReserve(dto));
    }

    @PutMapping("/cancel")
    @Operation(summary = "예약 취소")
    public ResponseEntity<ReserveRes> cancelReserve(@RequestBody ReserveCancelDto dto) throws Exception {
        return ResponseEntity.ok(SERVICE.cancelReserve(dto));
    }

    @PutMapping("/change")
    @Operation(summary = "예약 변경")
    public ResponseEntity<ReserveRes> updReserve(@RequestBody ReserveUpdDto dto) throws Exception {
        return ResponseEntity.ok(SERVICE.updReserve(dto));
    }

    @GetMapping("/city-list")
    @Operation(summary = "도시별 캠핑장 리스트")
    public ResponseEntity<List<CampingList>> getCamping(@RequestParam Long inationwide) {
        return ResponseEntity.ok(SERVICE.getCamping(inationwide));
    }

    @GetMapping("/camping-list")
    @Operation(summary = "캠핑장 리스트")
    public ResponseEntity<List<CampingList>> getCampingAll() {
        return ResponseEntity.ok(SERVICE.getCampingAll());
    }

    @GetMapping("/detail-camping")
    @Operation(summary = "캠핑장 상세보기")
    public ResponseEntity<List<CampingDetailList>> getDeCamping(@RequestParam Long icamp){
        return ResponseEntity.ok(SERVICE.getDeCamping(icamp));
    }
    @GetMapping("/my-reserve")
    @Operation(summary = "내 예약 리스트")
    public ResponseEntity<List<CampingMyList>> getMyList(){
        return ResponseEntity.ok(SERVICE.getMyList());
    }

    @GetMapping("/camping-title")
    @Operation(summary = "캠핑장 검색")
    public ResponseEntity<List<CampingList>> getCampingTitle(String name){
        return ResponseEntity.ok(SERVICE.getCampingTitle(name));
    }
    @PostMapping("/camp")
    @Operation(summary = "캠핑장 31일치 만들기 터치x")
    public ResponseEntity<List<DailyRes>> InsCampMain(){
        return ResponseEntity.ok(SERVICE.InsMainCamp());

    }
}
