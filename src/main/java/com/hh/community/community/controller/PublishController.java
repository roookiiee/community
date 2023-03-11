package com.hh.community.community.controller;

import com.hh.community.community.Model.Question;
import com.hh.community.community.Model.User;
import com.hh.community.community.dto.QuestionDTO;
import com.hh.community.community.mapper.QuestionMapper;
import com.hh.community.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;


    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model){

        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());

        return "publish";
    }

    @PostMapping("/publish/{id}")
    public String doEdit(@PathVariable(name = "id")Integer id,
                         @RequestParam("title") String title,
                         @RequestParam("description") String description,
                         @RequestParam("tag") String tag){
        QuestionDTO questionDTO = questionService.getById(id);
        Question question = new Question();
        BeanUtils.copyProperties(questionDTO,question);
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setGmtModified(System.currentTimeMillis());
        questionService.createOrUpdate(question);
        //可以添加一个是否修改判断？
        return "redirect:/question/"+id;
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ) {
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        model.addAttribute("title", title);
        if (description == null || description == "") {
            model.addAttribute("error", "问题描述不能为空");
            return "publish";
        }
        model.addAttribute("description", description);
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        model.addAttribute("tag", tag);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionService.createOrUpdate(question);
        return "redirect:/";
    }


}
