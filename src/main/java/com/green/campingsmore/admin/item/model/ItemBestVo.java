package com.green.campingsmore.admin.item.model;

import com.green.campingsmore.entity.ItemEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ItemBestVo {
    private Long id;
    private ItemEntity itemEntity;
    private LocalDate monthLike;
}
