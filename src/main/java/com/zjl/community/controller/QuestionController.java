package com.zjl.community.controller;

import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id){
        return "question";
    }
}
