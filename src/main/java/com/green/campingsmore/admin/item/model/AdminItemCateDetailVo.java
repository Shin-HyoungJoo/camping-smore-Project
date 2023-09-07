package com.green.campingsmore.admin.item.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminItemCateDetailVo {
    private Long iitemCategory;
    private String name;
    private String url;
    private Integer status;
}
