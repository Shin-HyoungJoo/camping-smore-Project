package com.green.campingsmore.user.community.board;

import com.green.campingsmore.user.community.board.model.BoardInsDto;
import com.green.campingsmore.user.community.board.model.BoardPicRes;
import com.green.campingsmore.user.community.board.model.BoardRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/jpa/board")
@RequiredArgsConstructor
@Tag(name = "보드")
public class BoardController {
    private final BoardService SERVICE;

    @GetMapping("/board")
    @Operation(summary = "pk값 반환")
    public ResponseEntity<BoardRes> postBoard(){
        return ResponseEntity.ok(SERVICE.postBoard());
    }
    @PostMapping(value = "/board-pics",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시판 사진다중업로드")
    public ResponseEntity<List<String>> InsPics(@RequestPart(required = false) List<MultipartFile> pics, @RequestPart Long iboard)throws Exception{
        return ResponseEntity.ok(SERVICE.InsPics(pics,iboard));
    }
    @PostMapping(value = "/board-pic",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시판 사진 한장 업로드")
    public ResponseEntity<BoardPicRes> InsPic(@RequestPart(required = false) MultipartFile pic, @RequestPart Long iboard)throws Exception{
        return ResponseEntity.ok(SERVICE.InsOnePic(iboard, pic));
    }
    @PostMapping("/text")
    @Operation(summary = "게시판 수정 생성")
    public ResponseEntity<BoardRes> InsBoard(@RequestBody BoardInsDto dto){
        return ResponseEntity.ok(SERVICE.updBoard(dto));
    }
    @DeleteMapping("/cancel")
    @Operation(summary = "게시글 작성 취소")
    public void delBoard(Long iboard){
        SERVICE.delWriteBoard(iboard);
    }
}
