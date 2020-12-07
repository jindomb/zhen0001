package com.zhen.crm.workbench.web.controller;

import com.zhen.crm.settings.domain.User;
import com.zhen.crm.settings.service.UserService;
import com.zhen.crm.utils.DateTimeUtil;
import com.zhen.crm.utils.UUIDUtil;
import com.zhen.crm.vo.PaginationVO;
import com.zhen.crm.workbench.domain.Activity;
import com.zhen.crm.workbench.domain.ActivityRemark;
import com.zhen.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/workbench/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUser.do")
    @ResponseBody
    public List<User> queryUsers(){
        return userService.queryUsers();
    }

    @RequestMapping(value = "/save.do")
    @ResponseBody
    public Map<String,Object> addActivity(Activity activity, HttpSession session){
        activity.setCreateTime(DateTimeUtil.getCurrentTime());
        User user = (User) session.getAttribute("user");
        activity.setCreateBy(user.getName());
        activity.setId(UUIDUtil.getUUID());
        boolean flag = activityService.addActivity(activity);
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }


    @RequestMapping(value = "/pageList.do")
    @ResponseBody
    public PaginationVO<Activity> queryActivities(Activity activity, Integer pageNo, Integer pageSize){
        return activityService.queryActivities(activity,pageNo,pageSize);
    }

    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public Map<String,Object> deleteActivities(String[] id){

        boolean flag = activityService.deleteActivities(id);
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }

    @RequestMapping(value = "/getEditMessage.do")
    @ResponseBody
    public Map<String,Object> getEditMessage(String id){

        return activityService.getEditMessage(id);

    }

    @RequestMapping(value = "/updateActivity.do")
    @ResponseBody
    public Map<String,Object> updateActivity(Activity activity,HttpSession session){
        activity.setEditTime(DateTimeUtil.getCurrentTime());
        User user = (User) session.getAttribute("user");
        activity.setEditBy(user.getName());
        boolean flag = activityService.updateActivity(activity);
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }

    @RequestMapping(value = "/detail.do")
    public ModelAndView getDetail(String id){
        ModelAndView mv = new ModelAndView();
        Activity activity = activityService.getDetailById(id);
        mv.addObject("activity",activity);
        mv.setViewName("detail");
        return mv;
    }

    @RequestMapping(value = "/getRemarks.do")
    @ResponseBody
    public List<ActivityRemark> queryRemarks(String id){

        return activityService.queryRemarks(id);
    }

    @RequestMapping(value = "/deleteRemark.do")
    @ResponseBody
    public Map<String,Object> deleteRemark(String id){
        boolean flag = activityService.deleteRemark(id);
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }

    @RequestMapping(value = "/saveRemark.do")
    @ResponseBody
    public Map<String,Object> addActivityRemark(ActivityRemark activityRemark, HttpSession session){
        activityRemark.setCreateTime(DateTimeUtil.getCurrentTime());
        User user = (User) session.getAttribute("user");
        activityRemark.setCreateBy(user.getName());
        activityRemark.setId(UUIDUtil.getUUID());
        boolean flag = activityService.addActivityRemark(activityRemark);
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("act",activityRemark);
        return map;
    }


    @RequestMapping(value = "/updateRemark.do")
    @ResponseBody
    public Map<String,Object> updateRemark(ActivityRemark activityRemark,HttpSession session){
        activityRemark.setEditTime(DateTimeUtil.getCurrentTime());
        User user = (User) session.getAttribute("user");
        activityRemark.setEditBy(user.getName());
        activityRemark.setEditFlag("1");
        boolean flag = activityService.updateRemark(activityRemark);
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",activityRemark);
        return map;
    }
}
