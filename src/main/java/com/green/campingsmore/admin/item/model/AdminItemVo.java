package com.green.campingsmore.admin.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminItemVo {
    private String categoryName;
    private Long iitem;
    private String name;
    private String pic;
    private Integer price;
    private LocalDateTime createdAt;
    private Integer status;

}
