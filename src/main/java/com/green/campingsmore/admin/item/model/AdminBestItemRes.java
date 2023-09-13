package com.green.campingsmore.admin.item.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class AdminBestItemRes {
    private int maxPage;
    private int isMore;
    private int page;
    private int row;
    private List<AdminBestItemVo> list;
}
