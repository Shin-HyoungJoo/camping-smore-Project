package com.green.campingsmore.user.mypage.model;

import lombok.Data;

@Data
public class WishDto {
    private int iwish;
    private int iuser;
    private int iitem;
    private String created_at;
    private int del_yn;
}
