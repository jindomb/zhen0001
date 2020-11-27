package com.zhen.controller;

import com.zhen.domain.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class MyController {

    @ResponseBody
    @RequestMapping(value = "/return")
    public List<Student> doReturn(){
        return Arrays.asList(new Student(1,"貂蝉",19),new Student(2,"甄姬",18));
    }
}
