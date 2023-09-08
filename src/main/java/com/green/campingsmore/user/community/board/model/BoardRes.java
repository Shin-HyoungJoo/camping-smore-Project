package com.green.campingsmore.user.community.board.model;

import com.green.campingsmore.entity.BoardCategoryEntity;
import com.green.campingsmore.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardRes {
    private Long iboard;
    private UserEntity userEntity;
    private BoardCategoryEntity boardCategoryEntity;
    private String title;
    private String ctnt;
}
