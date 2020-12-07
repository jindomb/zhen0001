package com.zhen.crm.workbench.web.controller;

import com.zhen.crm.settings.domain.User;
import com.zhen.crm.settings.service.UserService;
import com.zhen.crm.utils.DateTimeUtil;
import com.zhen.crm.utils.UUIDUtil;
import com.zhen.crm.vo.PaginationVO;
import com.zhen.crm.workbench.dao.ClueActivityRelationDao;
import com.zhen.crm.workbench.domain.Activity;
import com.zhen.crm.workbench.domain.Clue;
import com.zhen.crm.workbench.domain.Tran;
import com.zhen.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/workbench/clue")
public class ClueController {
    @Autowired
    private ClueService clueService;

    @RequestMapping(value = "/getUser.do")
    @ResponseBody
    public List<User> queryUsers(){
        return clueService.queryUsers();
    }

    @RequestMapping(value = "/saveClue.do")
    @ResponseBody
    public Map<String,Object> addClue(Clue clue, HttpSession session){
        clue.setCreateTime(DateTimeUtil.getCurrentTime());
        User user = (User) session.getAttribute("user");
        clue.setCreateBy(user.getName());
        clue.setId(UUIDUtil.getUUID());
        boolean flag = clueService.addClue(clue);
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }

    @RequestMapping(value = "/pageList.do")
    @ResponseBody
    public PaginationVO<Clue> queryActivities(Clue clue, Integer pageNo, Integer pageSize){
        return clueService.queryClues(clue,pageNo,pageSize);
    }

    @RequestMapping(value = "/detail.do")
    public ModelAndView getDetail(String id){
        ModelAndView mv = new ModelAndView();
        Clue clue = clueService.getDetailById(id);
        mv.addObject("clue",clue);
        mv.setViewName("detail");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/activityClueRelationList.do")
    public List<Activity> activityClueRelationList(String clueId){
        return clueService.getActivities(clueId);
    }

    @RequestMapping(value = "/rebound.do")
    @ResponseBody
    public Map<String,Object> rebound(String id){
        return clueService.deleteRelation(id);
    }

    @ResponseBody
    @RequestMapping(value = "/notRelationActivities.do")
    public List<Activity> getNotRelationActivities(String name,String clueId){
        return clueService.getNotRelationActivities(name,clueId);
    }

    @RequestMapping(value = "/bundActivity.do")
    @ResponseBody
    public Map<String,Object> bound(String clueId,String[] activityId){
        return clueService.addRelations(clueId,activityId);
    }

    @ResponseBody
    @RequestMapping(value = "/getActivities.do")
    public List<Activity> getActivities(String name){
        return clueService.getActivitiesByName(name);
    }

    @RequestMapping(value = "/convert.do")
    public ModelAndView clueConvert(String clueId, Tran tran,HttpSession session){
        ModelAndView mv = new ModelAndView();
        tran.setCreateTime(DateTimeUtil.getCurrentTime());
        User user = (User) session.getAttribute("user");
        tran.setCreateBy(user.getName());
        clueService.clueConvert(clueId,tran);
        mv.setViewName("index");
        return mv;
    }
}
