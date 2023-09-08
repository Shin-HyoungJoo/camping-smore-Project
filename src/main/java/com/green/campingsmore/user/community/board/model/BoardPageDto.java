package com.green.campingsmore.user.community.board.model;

import lombok.Data;

@Data
public class BoardPageDto {
    private String title;
    private Long icategory;
    private int row;
    private int page;
    private int startIdx;
    private int maxpage;
}
