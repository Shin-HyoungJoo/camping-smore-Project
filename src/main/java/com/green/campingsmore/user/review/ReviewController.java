package com.green.campingsmore.user.review;


import com.green.campingsmore.user.review.model.ReviewUpdDto;
import com.green.campingsmore.user.review.model.ReviewInsDto;
import com.green.campingsmore.user.review.model.ReviewRes;
import com.green.campingsmore.user.review.model.ReviewVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@Tag(name = "유저 - 리뷰")
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "리뷰 추가"
            , description = "" +
            "\"iorder\": [-]  주문 PK,<br>" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"reviewCtnt\": [-] 리뷰 내용,<br>" +
            "\"starRating\": [-] 별점,<br>" +
            "\"pic\": [-] 사진 이미지<br>")
    public ResponseEntity<ReviewVo> postReview(@RequestPart ReviewInsDto dto,
                                                  @RequestPart(required = false) MultipartFile pic) {
        log.info("dto :{}",dto);
        ReviewVo vo = service.saveReview(dto,pic);
        return ResponseEntity.ok(vo);
    }

/*    @GetMapping("/{iitem}/detail")
    @Operation(summary = "리뷰 리스트"
            , description = "" +
            "\"iitem\": [-] 아이템 PK<br>" )
    public ResponseEntity<List<ReviewSelRes>> getReview(@PathVariable Long iitem,
                                                        @ParameterObject @PageableDefault(sort = "ireview", direction = Sort.Direction.DESC, size = 5)Pageable page) {

        return ResponseEntity.ok(service.selectItemReview(page, iitem));
    }*/

    @GetMapping("/{iitem}/detail")
    @Operation(summary = "리뷰 리스트"
            , description = "" +
            "\"iitem\": [-] 아이템 PK<br>" )
    public ResponseEntity<ReviewRes> getReview(@PathVariable Long iitem,
                                               @ParameterObject @PageableDefault(sort = "ireview", direction = Sort.Direction.DESC, size = 5)Pageable page) {

        return ResponseEntity.ok(service.selectItemReview(page, iitem));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "리뷰 수정"
            , description = "" +
            "\"ireview\": [-] 리뷰 PK,<br>" +
            "\"reviewCtnt\": [-] 리뷰 내용,<br>" +
            "\"starRating\": [-] 별점,<br>" +
            "\"pic\": [-] 사진 이미지<br>")
    public ResponseEntity<String> updReview(@RequestPart ReviewUpdDto dto,
                            @RequestPart(required = false) MultipartFile pic) {
        return ResponseEntity.ok(service.updReview(dto, pic));
    }

    @DeleteMapping
    @Operation(summary = "리뷰 삭제"
            , description = "" +
            "\"ireview\": [-] 리뷰 PK<br>" )
    public ResponseEntity<Integer> delReview(@RequestParam Long ireview) {
        service.delReview(ireview);
        return ResponseEntity.ok(1);
    }



}
