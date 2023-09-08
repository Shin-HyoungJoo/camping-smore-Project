package com.green.campingsmore.community.comment;

import com.green.campingsmore.user.community.comment.model.CommentEntity2;
import com.green.campingsmore.user.community.comment.model.CommentPageDto;
import com.green.campingsmore.user.community.comment.model.CommentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper1 {
    Long insComment(CommentEntity2 entity);
    Long updComment(CommentEntity2 entity);
    Long delComment(CommentEntity2 entity);
    List<CommentVo> selComment(CommentPageDto dto);
    Long maxComment(CommentPageDto dto);
}
