package com.green.campingsmore.user.camping.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CampingMyList {
    private Long ireserve;
    private Long iuser;
    private String name;
    private Long iday;

    public CampingMyList(Long ireserve, Long iuser, String name, Long iday, String mainPic, String campPhone) {
        this.ireserve = ireserve;
        this.iuser = iuser;
        this.name = name;
        this.iday = iday;
        this.mainPic = mainPic;
        this.campPhone = campPhone;
    }

    private String mainPic;
    private String campPhone;

}
