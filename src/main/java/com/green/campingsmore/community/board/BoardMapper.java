package com.green.campingsmore.community.board;

import com.green.campingsmore.community.board.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    Long insBoard(BoardEntity entity);
    Long insBoardPic(List<BoardPicEntity> pic);
    List<BoardMyVo> selMyBoard(BoardMyDto dto);
    Long delBoard(BoardDelDto dto);
    List<BoardListVo> selBoardList(BoardPageDto dto);
}
