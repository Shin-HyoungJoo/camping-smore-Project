package com.green.campingsmore.user.review.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ReviewPageDto {
    private Long iitem;
    private int startIdx;
    private int page;
    private int row;

    @Data
    @AllArgsConstructor
    public static class ReviewInsDto {
        private Long iorder;
        private Long iitem;
        private String reviewCtnt;
        private Integer starRating;
    }
}
