package com.green.campingsmore.user.community.board.model;

import lombok.Data;

@Data
public class BoardEntity2 {
    private Long iboard;
    private Long iuser;
    private Long icategory;
    private String title;
    private String ctnt;
    private String pic;
    private Long iboardpic;
    private Long boardView;
}
