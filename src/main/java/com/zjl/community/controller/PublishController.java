package com.zjl.community.controller;

import com.zjl.community.cache.TagCache;
import com.zjl.community.dto.QuestionDTO;
import com.zjl.community.dto.TagDTO;
import com.zjl.community.model.Question;
import com.zjl.community.model.User;
import com.zjl.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                        Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTags());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());

        return "publish";
    }


    @GetMapping("/publish")
    public String publish(Model model){

        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required=false) String title,
            @RequestParam(value = "description",required=false) String description,
            @RequestParam(value = "tag",required=false) String tag,
            @RequestParam(value = "id",required = false) Long id,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get());


        if (title == null || title== ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description== ""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag== ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if (!StringUtils.isEmpty(invalid)){
            model.addAttribute("error","输入非法标签："+invalid);
            return "publish";
        }

        User user =(User) request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTags(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setId(id);

        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
