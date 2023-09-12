package com.green.campingsmore.admin.community.board;

import com.green.campingsmore.community.board.BoardMapper1;
import com.green.campingsmore.community.board.utils.FileUtils;
import com.green.campingsmore.user.community.board.model.BoardListVo;
import com.green.campingsmore.user.community.board.model.BoardNoticeList;
import com.green.campingsmore.user.community.board.model.CategoryList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class AdminBoardService {
    private final BoardMapper1 mapper;

    @Value("${file.dir}")
    private String fileDir;
    public List<BoardListVo> admin(LocalDate startDate, LocalDate endDate, String title, Long icategory){
        return mapper.admin(startDate, endDate, title, icategory);
    }
    public List<BoardNoticeList> noticeList(){
        return mapper.noticeList();
    }
    public String noticeCount(Long icategory){
        Long result = mapper.selNoticeCount1(icategory);
        Long result1 = mapper.selNoticeCount(icategory);
        String format = String.format(result +"/"+ result1);
        return format;
    }
    public Long delAdminBoard(Long iboard) {
        try {
            mapper.delBoardPic(iboard);

            String centerPath = String.format("boardPics/%d", iboard);
            FileUtils.delFolder(fileDir + centerPath);
            return mapper.delAdminBoard(iboard);
        } catch (Exception e) {
            // 예외 처리 로직
            e.printStackTrace(); // 예외 정보 출력
            // 예외 처리 후 반환할 값이나 로직을 작성
            return 0L; // 예시로 간단히 null 반환
        }
    }//게시글 삭제
    public List<CategoryList> getCategory(){
        return mapper.getCategory();
    }
    public Long insCategory(String name) {
        return mapper.insCategory(name);
    }
    public List<BoardListVo> adminToday(){
        return mapper.adminToday();
    }
    public List<BoardListVo> adminWeek(){
        return mapper.adminWeek();
    }
    public List<BoardListVo> adminthree(){
        return mapper.adminthree();
    }
    public List<BoardListVo> adminMonth(){
        return mapper.adminMonth();

    }
    public List<BoardListVo> selAdminBoard(){
        return mapper.selAdminBoard();
    }
}
