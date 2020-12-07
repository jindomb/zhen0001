package com.zhen.crm.handler;

import com.zhen.crm.exception.LoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public Map<String,Object> doLoginException(Exception e){
        Map<String,Object> map = new HashMap<>();
        map.put("success",false);
        map.put("msg",e.getMessage());
        return map;
    }

    @ResponseBody
    @ExceptionHandler
    public Map<String,Object> doDefaultException(Exception e){
        System.out.println(e.getMessage());
        Map<String,Object> map = new HashMap<>();
        map.put("success",false);
        return map;
    }
}
