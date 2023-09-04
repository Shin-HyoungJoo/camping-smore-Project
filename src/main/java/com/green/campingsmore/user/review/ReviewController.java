/*
package com.green.campingsmore.user.review;


import com.green.campingsmore.admin.item.model.ItemCategoryInsDto;
import com.green.campingsmore.user.review.model.ReviewInsDto;
import com.green.campingsmore.user.review.model.ReviewSelVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ReviewSelVo> postReview(@RequestBody ReviewInsDto dto,
                                                  @RequestPart(required = false) MultipartFile pic) {
        ReviewSelVo vo = service.saveReview(dto,pic);
        return ResponseEntity.ok(vo);
    }

}
*/
