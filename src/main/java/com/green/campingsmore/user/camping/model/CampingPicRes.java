package com.green.campingsmore.user.camping.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CampingPicRes {
    private Long icampPic;
    private String pic;
    private Long icamp;
}
