package com.zjl.community.mapper;

import com.zjl.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}