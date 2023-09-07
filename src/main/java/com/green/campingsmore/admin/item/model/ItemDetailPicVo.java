package com.green.campingsmore.admin.item.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class ItemDetailPicVo {
    private Long iitem;
    private List<String> picList;
}
