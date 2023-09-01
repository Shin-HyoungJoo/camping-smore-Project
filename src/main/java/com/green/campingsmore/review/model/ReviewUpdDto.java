package com.green.campingsmore.review.model;


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

    @Builder
    @Getter
    public static class ReviewVo {
        private Long ireview;
        private String name;
        private String reviewCtnt;
        private String pic;
        private int starRating;
        private int reviewLike;
    }

    @Builder
    @Getter
    public static class ItemSelDetailRes {
        private Long iitemCategory;
        private String text;
        private int sort;
        private int maxPage;
        private int startIdx;
        private int isMore;
        private int page;
        private int row;
        private List<ItemVo> itemList;
    }
}
