package com.green.campingsmore.user.review;

import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.user.review.model.ReviewInsDto;
import com.green.campingsmore.user.review.model.ReviewVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "리뷰")
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "리뷰 추가"
            , description = "" +
            "\"iuser\": [-] 유저 PK,<br>" +
            "\"iorder\": [-]  주문 PK,<br>" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"reviewCtnt\": [-] 리뷰 내용,<br>" +
            "\"starRating\": [-] 별점,<br>" +
            "\"pic\": [-] 사진 이미지<br>")
    public ResponseEntity<ReviewVo> postReview(@AuthenticationPrincipal MyUserDetails userDetails,
                                                @RequestPart ReviewInsDto dto,
                                               @RequestPart(required = false) MultipartFile pic) {

        ReviewVo vo = service.saveReview(dto,pic);
        return ResponseEntity.ok(vo);
    }




}
