package com.hh.community.community.service;

import com.hh.community.community.Model.Question;
import com.hh.community.community.Model.User;
import com.hh.community.community.dto.QuestionDTO;
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

    public List<QuestionDTO> findAllQuestion() {
        // 先获取 所有问题
        List<Question> questions = questionMapper.findAllQuestion();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            //遍历所有问题 通过问题中的创造者 然后找到 对应的用户
            User user = userMapper.findById(question.getCreator());
            // 新建一个QuestionDTO 对象，多了User 的question类
            QuestionDTO questionDTO = new QuestionDTO();
            // BeanUtils.copyProperties 直接帮助我们setAttribute
            BeanUtils.copyProperties(question,questionDTO);
            // 最后加上user
            questionDTO.setUser(user);
            // 添加到要返回的列表中
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
