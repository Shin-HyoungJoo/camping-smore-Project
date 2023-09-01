package com.green.campingsmore.user.review.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewVo {
    private Long ireview;
    private String name;
    private String reviewCtnt;
    private String pic;
    private int starRating;
    private int reviewLike;
}
