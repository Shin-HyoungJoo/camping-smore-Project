package com.green.campingsmore.user.review.model;


import com.green.campingsmore.admin.item.model.ItemVo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class ReviewUpdDto {
    private Long ireview;
    private String reviewCtnt;
    private int starRating;
}
