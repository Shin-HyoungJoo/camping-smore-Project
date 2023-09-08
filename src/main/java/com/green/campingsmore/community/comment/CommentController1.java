package com.green.campingsmore.community.comment;

import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.user.community.comment.model.CommentDelDto;
import com.green.campingsmore.user.community.comment.model.CommentEntity2;
import com.green.campingsmore.user.community.comment.model.CommentInsDto;
import com.green.campingsmore.user.community.comment.model.CommentUpdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "댓글")
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController1 {
    private final CommentService1 service;
    private final AuthenticationFacade FACADE;

    @PostMapping
    @Operation(summary = "댓글 생성")
    public Long insComment(@RequestBody CommentInsDto dto) {
        return service.insComment(dto);
    }

    @PutMapping
    @Operation(summary = "댓글 수정")
    public Long updComment(@RequestBody CommentUpdDto dto){
        CommentEntity2 entity = new CommentEntity2();
        entity.setIcomment(dto.getIcomment());
        entity.setCtnt(dto.getCtnt());
        return service.updComment(entity);
    }

    @PutMapping("/comment")
    @Operation(summary = "댓글 삭제")
    public Long delComment(@RequestBody CommentDelDto dto){
        CommentEntity2 entity = new CommentEntity2();
        entity.setIcomment(dto.getIcomment());
        return service.delComment(entity);

    }
//    @GetMapping("/{iboard}/cmt")
//    @Operation(summary = "게시글에 있는 댓글 보기")
//    public CommentRes selComment(@PathVariable Long iboard,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") @Min(value = 15) int row){
//        CommentPageDto dto = new CommentPageDto();
//        dto.setIboard(iboard);
//        dto.setPage(page);
//        dto.setRow(row);
//        return service.selComment(dto);
    }



