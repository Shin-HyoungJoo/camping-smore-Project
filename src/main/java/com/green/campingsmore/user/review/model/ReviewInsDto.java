package com.green.campingsmore.user.review.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewInsDto {
    private Long iorder;
    private Long iitem;
    private String reviewCtnt;
    private int starRating;
}
