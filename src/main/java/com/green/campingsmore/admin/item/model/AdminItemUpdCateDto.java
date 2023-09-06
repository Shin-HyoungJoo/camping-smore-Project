package com.green.campingsmore.admin.item.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminItemUpdCateDto {
    private Long iitemCategory;
    private String name;
    private Integer status;
}
