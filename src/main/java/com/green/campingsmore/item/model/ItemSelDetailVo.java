package com.green.campingsmore.item.model;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemSelDetailVo {
    private Long iitem;
    private String name;
    private String pic;
    private Integer price;
    private LocalDateTime createdAt;
}
