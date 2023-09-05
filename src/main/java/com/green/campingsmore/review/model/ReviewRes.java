package com.green.campingsmore.review.model;

import com.green.campingsmore.user.review.model.ReviewSelVo;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ReviewRes {
    private Long iitem;
    private int startIdx;
    private int isMore;
    private int page;
    private int row;
    private List<ReviewSelVo> list;
}
