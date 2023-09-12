package com.green.campingsmore.user.camping.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CampingMyList {
    private Long ireserve;
    private Long iuser;
    private String name;
    private Long iday;
    private LocalDate date;

    public CampingMyList(Long ireserve, Long iuser, String name, Long iday, LocalDate date, String mainPic, String campPhone) {
        this.ireserve = ireserve;
        this.iuser = iuser;
        this.name = name;
        this.iday = iday;
        this.date = date;
        this.mainPic = mainPic;
        this.campPhone = campPhone;
    }

    private String mainPic;
    private String campPhone;

}
