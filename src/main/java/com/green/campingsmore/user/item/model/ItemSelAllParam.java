package com.green.campingsmore.user.item.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ItemSelAllParam {
    private Long iitemCategory;
    private String text;
}
