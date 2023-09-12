package com.green.campingsmore.admin.item.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminBestItemVo {
    private Long ibestItem;
    private Long iitem;
    private String itemNm;
    private LocalDate monthLike;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
