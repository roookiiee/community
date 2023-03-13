package com.hh.community.community.service;

import com.hh.community.community.Model.Question;
import com.hh.community.community.Model.User;
import com.hh.community.community.dto.PaginationDTO;
import com.hh.community.community.dto.QuestionDTO;
import com.hh.community.community.exception.CustomizeErrorCode;
import com.hh.community.community.exception.CustomizeException;
import com.hh.community.community.mapper.QuestionMapper;
import com.hh.community.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    /**
     * 通过service 来 讲 user 放进 question 中
     */
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO findAllQuestion(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);

        if (page < 1){
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        // 计算页码
        Integer offset = size * (page - 1);
        // 先获取 所有问题
        List<Question> questions = questionMapper.findAllQuestion(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //遍历所有问题 通过问题中的创造者 然后找到 对应的用户
            User user = userMapper.findById(question.getCreator());
            // 新建一个QuestionDTO 对象，多了User 的question类
            QuestionDTO questionDTO = new QuestionDTO();
            // BeanUtils.copyProperties 直接帮助我们setAttribute
            BeanUtils.copyProperties(question, questionDTO);
            // 最后加上user
            questionDTO.setUser(user);
            // 添加到要返回的列表中
            questionDTOList.add(questionDTO);
        }
        // 放进分页数据中
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO findQuestionByUser(Long id, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(id);
        paginationDTO.setPagination(totalCount,page,size);

        if (page < 1){
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        // 计算页码
        Integer offset = size * (page - 1);
        // 先获取 所有问题
        List<Question> questions = questionMapper.findAllQuestionByUserId(id,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //遍历所有问题 通过问题中的创造者 然后找到 对应的用户
            User user = userMapper.findById(question.getCreator());
            // 新建一个QuestionDTO 对象，多了User 的question类
            QuestionDTO questionDTO = new QuestionDTO();
            // BeanUtils.copyProperties 直接帮助我们setAttribute
            BeanUtils.copyProperties(question, questionDTO);
            // 最后加上user
            questionDTO.setUser(user);
            // 添加到要返回的列表中
            questionDTOList.add(questionDTO);
        }
        // 放进分页数据中
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;

    }

    public QuestionDTO getById(Long id) {
        Question question= questionMapper.getById(id);

        QuestionDTO questionDTO = new QuestionDTO();
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        //每查询一次就增加一次阅读次数
        questionMapper.viewCountAdd(id);
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(userMapper.findById(questionDTO.getCreator()));
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() != null){
            questionMapper.edit(question);
        }else {
            questionMapper.create(question);
        }
    }

}
