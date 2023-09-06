package com.green.campingsmore.item.model;

import com.green.campingsmore.user.review.model.ReviewRes;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDetailReviewVo {
    private ItemSelDetailVo item;
    private ReviewRes review;
}
