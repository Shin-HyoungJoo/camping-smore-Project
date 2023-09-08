package com.green.campingsmore.user.community.board.model;

import com.green.campingsmore.entity.BoardEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardPicRes {
    private Long iboardPic;
    private BoardEntity boardEntity;
    private String pic;
}
