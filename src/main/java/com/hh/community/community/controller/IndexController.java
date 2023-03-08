package com.hh.community.community.controller;

import com.hh.community.community.Model.Question;
import com.hh.community.community.Model.User;
import com.hh.community.community.dto.QuestionDTO;
import com.hh.community.community.mapper.UserMapper;
import com.hh.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        // 获取所有的问题（包括user）
        List<QuestionDTO> questionList = questionService.findAllQuestion();

        model.addAttribute("questions",questionList);
        return "index";
    }
}
