package com.green.campingsmore.admin.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVo {
    private Long iitem;
    private String name;
    private String pic;
    private Integer price;
    private LocalDateTime createdAt;
    private Integer wish;
}
