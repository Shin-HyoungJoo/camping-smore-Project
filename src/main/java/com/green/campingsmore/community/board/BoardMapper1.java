package com.green.campingsmore.community.board;

import com.green.campingsmore.user.community.board.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BoardMapper1 {
    Long insBoard(BoardEntity2 entity);//pk값 반환

    Long updBoardMain(BoardEntity2 entity);//게시글 작성

    Long insBoardPic(List<BoardPicEntity> pic);//사진 다중 업로드

    List<BoardMyVo> selMyBoard(BoardMyDto dto);//내가 작성한글 보기- 마이페이지에서 사용

    Long delBoard(BoardDelDto dto);//게시글 삭제 하기

    List<BoardListVo> selBoardList(BoardPageDto dto);//게시글 리스트 보기

    Long maxBoard();//맥스 페이지

    List<BoardListVo> categoryBoardList(BoardPageDto dto);//카테고리별 게시글 리스트 보기

    List<BoardSelVo> selBoard(BoardSelPageDto dto);//제목으로 검색

    Long maxSelBoard(BoardSelPageDto dto);// 검색된거 맥스개수

    BoardDeVo deBoard(BoardDeDto dto);//게시글 디테일

    List<BoardPicVo> picBoard(BoardDeDto dto); // 게시글에 있는 사진 여러장

    Long updBoard(BoardEntity2 entity);//

    Long delPic(BoardPicEntity pic);

    Long viewBoard(BoardDeDto dto); // 조회수 올리기

    Long delWriteBoard(Long iboard);//작성중인 글 내용 삭제

    Long delPicBoard(Long iboard); // 작성중인 사진 삭제

    Long insBoardOnePic(BoardPicEntity pic); // 사진 한장 업로드

    Long delOnePic(BoardEntity2 entity); // 사진 한장 삭제

    Long delBoardPic(Long iboard);

    String selPicName(Long iboardPic);

    Long insCategory(String name);

    List<BoardNoticeList> noticeList();
    Long selNoticeCount1(Long icategory);
    Long selNoticeCount(Long icategory);
    Long delAdminBoard(Long iboard);
    List<CategoryList> getCategory();
    List<BoardListVo> admin(LocalDate startDate, LocalDate endDate, String title, Long icategory);
    List<BoardListVo> adminToday();
    List<BoardListVo> adminWeek();
    List<BoardListVo> adminthree();
    List<BoardListVo> adminMonth();
    void updSchedule();
    List<BoardListVo> selAdminBoard();

}