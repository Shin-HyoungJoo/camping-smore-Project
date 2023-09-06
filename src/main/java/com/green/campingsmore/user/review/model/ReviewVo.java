package com.green.campingsmore.user.review.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewVo {
    private Long ireview;
    private String name;
    private String reviewCtnt;
    private String pic;
    private int starRating;
    private int reviewLike;
    private LocalDateTime createdAt;
}
