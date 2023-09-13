package com.green.campingsmore.item.model;

import com.green.campingsmore.admin.item.model.ItemVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ItemSelDetailRes {
    private Long iitemCategory;
    private String text;
    private Integer sort;
    private Integer maxPage;
    private Integer startIdx;
    private Integer isMore;
    private Integer page;
    private Integer row;
    private List<ItemVo> itemList;
}