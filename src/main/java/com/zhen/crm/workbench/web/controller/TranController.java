package com.zhen.crm.workbench.web.controller;

import com.zhen.crm.settings.domain.User;
import com.zhen.crm.utils.DateTimeUtil;
import com.zhen.crm.workbench.domain.Tran;
import com.zhen.crm.workbench.domain.TranHistory;
import com.zhen.crm.workbench.service.CustomerService;
import com.zhen.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/workbench/transaction")
public class TranController {
    @Autowired
    private TranService tranService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/getUsers.do")
    public ModelAndView getUsers(){
        ModelAndView mv = new ModelAndView();
        List<User> users = tranService.getUsers();
        mv.addObject("users",users);
        mv.setViewName("save");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/getCustomerName.do")
    public List<String> getCustomerNames(String name){
        return customerService.getCustomerName(name);
    }

    @RequestMapping(value = "/save.do")
    public ModelAndView save(Tran tran, String customerName, HttpSession session){
        ModelAndView mv = new ModelAndView();
        tran.setCreateTime(DateTimeUtil.getCurrentTime());
        User user = (User) session.getAttribute("user");
        tran.setCreateBy(user.getName());
        tranService.insert(tran,customerName);
        mv.setViewName("redirect:/workbench/transaction/index.jsp");
        return mv;
    }

    @RequestMapping(value = "/detail.do")
    public ModelAndView detail(String id, HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        Tran tran = tranService.getById(id);
        Map<String,String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pmap");
        tran.setPossibility(pMap.get(tran.getStage()));
        mv.addObject("t",tran);
        mv.setViewName("detail");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/getTranHistories.do")
    public List<TranHistory> getTranHistories(String tranId,HttpServletRequest request){
        List<TranHistory> tranHistories = tranService.getTranHistories(tranId);
        Map<String,String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pmap");
        for(TranHistory tranHistory : tranHistories){
            tranHistory.setPossibility(pMap.get(tranHistory.getStage()));
        }
        return tranHistories;
    }

    @ResponseBody
    @RequestMapping(value = "/changeStage.do")
    public Map<String,Object> changeStage(Tran tran,HttpServletRequest request,HttpSession session){
        tran.setEditTime(DateTimeUtil.getCurrentTime());
        User user = (User) session.getAttribute("user");
        tran.setEditBy(user.getName());
        Map<String,String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pmap");
        tran.setPossibility(pMap.get(tran.getStage()));
        return tranService.changeStage(tran);
    }

    @ResponseBody
    @RequestMapping(value = "/getCharts.do")
    public Map<String,Object> getCharts(){
        return tranService.getCharts();
    }
}
