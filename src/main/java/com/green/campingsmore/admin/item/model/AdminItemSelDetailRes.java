package com.green.campingsmore.admin.item.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class AdminItemSelDetailRes {
    private Long iitemCategory;
    private String text;
    private Integer sort;
    private Integer maxPage;
    private Integer startIdx;
    private Integer isMore;
    private Integer page;
    private Integer row;
    private List<AdminItemVo> itemList;
}
