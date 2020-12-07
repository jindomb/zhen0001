package com.zhen.crm.settings.web.controller;

import com.zhen.crm.exception.LoginException;
import com.zhen.crm.settings.domain.User;
import com.zhen.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/settings/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login.do")
    @ResponseBody
    public Map<String,String> login(User user,
                                    HttpServletRequest request, HttpSession session) throws LoginException {
        User loginUser = userService.loginCheck(user,request);
        session.setAttribute("user",loginUser);
        Map<String,String> map = new HashMap<>();
        map.put("success","true");
        return map;
    }
}

