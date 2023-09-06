package com.green.campingsmore.user.review.model;

import com.green.campingsmore.user.review.model.ReviewSelRes;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ReviewRes {
    private Long iitem;
    private Integer startIdx;
    private Integer isMore;
    private Integer page;
    private Integer row;
    private List<ReviewSelRes> list;
}
