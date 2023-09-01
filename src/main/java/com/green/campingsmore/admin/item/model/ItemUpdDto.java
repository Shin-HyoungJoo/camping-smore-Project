package com.green.campingsmore.admin.item.model;

import lombok.Data;

import java.util.List;
@Data
public class ItemUpdDto {
    private Long iitem;
    private Long iitemCategory;
    private String name;
    private String pic;
    private Integer price;
    private String info;
    private Integer stock;
    private Integer status;
    private List<String> picUrl;
}
