package com.zjl.community.service;

import com.zjl.community.dto.PaginationDTO;
import com.zjl.community.dto.QuestionDTO;
import com.zjl.community.mapper.QuestionMapper;
import com.zjl.community.mapper.UserMapper;
import com.zjl.community.model.Question;
import com.zjl.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


    public PaginationDTO list(Integer page, Integer size) {

        Integer offset = size * (page - 1);

        List<Question> questionList = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionList(questionDTOList);
        Integer totalCount = questionMapper.count();

        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer offset = size * (page - 1);

        List<Question> questionList = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionList(questionDTOList);
        Integer totalCount = questionMapper.countByUserId(userId);

        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;
    }
}
