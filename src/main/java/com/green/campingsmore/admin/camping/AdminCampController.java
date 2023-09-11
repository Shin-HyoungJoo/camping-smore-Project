package com.green.campingsmore.admin.camping;

import com.green.campingsmore.user.camping.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/camping")
@RequiredArgsConstructor
@Tag(name = "캠핑관리자")
public class AdminCampController {
    private final AdminCampService SERVICE;
    @PostMapping(value = "camp",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "캠핑장 만들기")
    public ResponseEntity<CampingRes> InsCamp(@RequestPart(required = false) MultipartFile pic,
                                              @RequestPart CampingDto dto) throws Exception {
        return ResponseEntity.ok(SERVICE.InsCamp(pic, dto));
    }

    @PutMapping(value = "camp",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "캠핑장 수정")
    public ResponseEntity<CampingRes> updCamp(@RequestPart(required = false) MultipartFile pic,
                                              @RequestPart CampingUpdDto dto) throws Exception {
        return ResponseEntity.ok(SERVICE.updCamping(pic, dto));
    }

    @PutMapping("/camping")
    @Operation(summary = "캠핑장 삭제")
    public ResponseEntity<CampingRes> delCamp(@RequestBody CampingDelDto dto) {
        return ResponseEntity.ok(SERVICE.delCamping(dto));
    }

    @PostMapping(value = "/photo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "사진 다중 업로드")
    public ResponseEntity<List<String>> InsPic(@RequestPart(required = false) List<MultipartFile> pics, @RequestPart CampingPicDto dto) throws Exception {
        return ResponseEntity.ok(SERVICE.InsPic(pics, dto));
    }

    @DeleteMapping("/photo")
    @Operation(summary = "상세이미지 삭제")
    public ResponseEntity<Long> delPic(@RequestBody CampingPicDelDto dto) {
        return ResponseEntity.ok(SERVICE.delPic(dto));
    }
    @PostMapping("/reserve-camp")
    @Operation(summary = "예약 가능 캠핑장")
    public ResponseEntity<DailyRes> InsReserveCamp(@RequestBody DailyDto dto){
        return ResponseEntity.ok(SERVICE.InsReserveCamp(dto));
    }
    @PostMapping("/camping")
    @Operation(summary = "캠핑장 31일치 만들기 터치x")
    public ResponseEntity<List<DailyRes>> InsCampMain(){
        return ResponseEntity.ok(SERVICE.InsMainCamp());
    }
    @GetMapping("/{icamp}")
    @Operation(summary = "관리자 캠핑 상세보기")
    public ResponseEntity<CampingDetailList1> adminCamp(@PathVariable Long icamp) {
        return ResponseEntity.ok(SERVICE.selCampingPic(icamp));
    }
}
