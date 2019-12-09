package com.zjl.community.mapper;

import com.zjl.community.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}