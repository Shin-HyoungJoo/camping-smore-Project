package com.green.campingsmore.user.mypage.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewMypageVo {
    private Long ireview;
    private Long iuser;
    private Long iorder;
    private String reviewCtnt;
    private String pic;
    private Integer starRating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer reviewLike;
}
