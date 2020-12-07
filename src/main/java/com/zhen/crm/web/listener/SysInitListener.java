package com.zhen.crm.web.listener;

import com.zhen.crm.settings.domain.DicValue;
import com.zhen.crm.settings.service.DicService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:conf/applicationContext.xml");
        DicService dicService = (DicService) act.getBean("dicServiceImpl");
        Map<String, List<DicValue>> map = dicService.getAllDicValues();
        ServletContext application = servletContextEvent.getServletContext();
        for(String typeCode : map.keySet()){
            application.setAttribute(typeCode,map.get(typeCode));
        }

        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");
        Map<String,String> pMap = new HashMap<>();
        Enumeration<String> keys = rb.getKeys();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            String value = rb.getString(key);
            pMap.put(key,value);
        }
        application.setAttribute("pmap",pMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
