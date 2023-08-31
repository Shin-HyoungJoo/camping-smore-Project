package com.green.campingsmore.community.comment;

import com.green.campingsmore.community.comment.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    Long insComment(CommentEntity2 entity);
    Long updComment(CommentEntity2 entity);
    Long delComment(CommentEntity2 entity);
    List<CommentVo> selComment(CommentPageDto dto);
    Long maxComment(CommentPageDto dto);
}
