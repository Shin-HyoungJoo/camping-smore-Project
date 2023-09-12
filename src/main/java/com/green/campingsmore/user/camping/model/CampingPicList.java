package com.green.campingsmore.user.camping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
public class CampingPicList {
    private Long icampPic;
    private String pic; // pic을 List<String>으로 변경

    public CampingPicList(Long icampPic, String pic) {
        this.icampPic = icampPic;
        this.pic = pic;
    }
}
