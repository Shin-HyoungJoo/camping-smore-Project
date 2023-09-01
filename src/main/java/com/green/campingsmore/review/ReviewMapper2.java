package com.green.campingsmore.review;

import com.green.campingsmore.review.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper2 {
    int insReview(ReviewEntity2 entity);
//    int selReviewOrder(Long iorder, Long iuser, Long iitem);
    int selReviewCheck(ReviewEntity2 entity);
    int selReviewOrder(ReviewEntity2 entity);
    int selLastReview(Long iitem);
    List<ReviewSelVo> selReview(ReviewPageDto dto);
    int updReview(ReviewEntity2 entity);
    int updReviewPic(ReviewEntity2 entity);
    int delReview(ReviewDelDto dto);


}
