package com.green.campingsmore.user.community.board.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BoardListVo {
    private Long iboard;
    private Long icategory;
    private String name;
    private String title;
    private String ctnt;
    private LocalDate createdat;
    private Long boardview;
}
