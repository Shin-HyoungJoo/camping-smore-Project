package com.green.campingsmore.admin.community.board;

import com.green.campingsmore.user.community.board.model.BoardListVo;
import com.green.campingsmore.user.community.board.model.BoardNoticeList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/admin/board")
@RequiredArgsConstructor
@Tag(name = "관리자 게시판")
public class AdminBoardController {
    private final AdminBoardService service;
    @GetMapping("/admin-board")
    @Operation(summary = "보드관리자")
    public List<BoardListVo> admin(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                   @RequestParam(required = false) Long icategory,
                                   @RequestParam(required = false) String title
    ) {
        return service.admin(startDate, endDate, title, icategory);
    }
    @GetMapping("/category-count")
    @Operation(summary = "카테고리 갯수")
    public String noticeCount(Long icategory) {
        return service.noticeCount(icategory);
    }

    @GetMapping("/notice-list")
    @Operation(summary = "공지 리스트")
    public List<BoardNoticeList> noticeList() {
        return service.noticeList();
    }
    @PostMapping("/category")
    @Operation(summary = "카테고리 생성")
    public Long insCategory(String name) {
        return service.insCategory(name);
    }

    @PutMapping("/{iboard}")
    @Operation(summary = "게시글 삭제 하기")
    public Long delAdminBoard(@PathVariable Long iboard) {
        return service.delAdminBoard(iboard);

    }
    @GetMapping("/today")
    @Operation(summary = "오늘 게시글 조회")
    public List<BoardListVo> adminToday(){
        return service.adminToday();
    }
    @GetMapping("/week")
    @Operation(summary = "일주일 게시글 조회")
    public List<BoardListVo> adminWeek(){
        return service.adminWeek();
    }
    @GetMapping("/three")
    @Operation(summary = "3일치 게시글 조회")
    public List<BoardListVo> adminthree(){
        return service.adminthree();
    }
    @GetMapping("/month")
    @Operation(summary = "한달치 게시글 조회")
    public List<BoardListVo> adminMonth(){
        return service.adminMonth();
    }

}
