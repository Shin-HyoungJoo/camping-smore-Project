package com.green.campingsmore.admin.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVo {
    private Long iitem;
    private String name;
    private String pic;
    private Integer price;
    private LocalDate createdAt;
    private Integer wish;
}
