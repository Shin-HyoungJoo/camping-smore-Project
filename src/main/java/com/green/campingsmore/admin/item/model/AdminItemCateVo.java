package com.green.campingsmore.admin.item.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminItemCateVo {
    private Long iitemCategory;
    private String name;
    private Integer status;
}
