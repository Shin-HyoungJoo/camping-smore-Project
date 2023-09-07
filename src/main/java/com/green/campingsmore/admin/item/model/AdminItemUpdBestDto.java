package com.green.campingsmore.admin.item.model;

import com.green.campingsmore.entity.ItemEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminItemUpdBestDto {
    private Long ibestItem;
    private Long iitem;
    private LocalDate monthLike;
}
