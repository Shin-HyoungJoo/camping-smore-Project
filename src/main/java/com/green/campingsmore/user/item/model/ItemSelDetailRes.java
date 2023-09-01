package com.green.campingsmore.user.item.model;

import com.green.campingsmore.admin.item.model.ItemVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ItemSelDetailRes {
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
