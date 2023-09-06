package com.green.campingsmore.user.review.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSelRes {
    private Long ireview;
    private String name;
    private String reviewCtnt;
    private String pic;
    private int starRating;
    private int reviewLike;
    private LocalDateTime createdAt;
}
