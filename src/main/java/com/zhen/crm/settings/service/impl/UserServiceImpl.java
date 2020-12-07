package com.zhen.crm.settings.service.impl;

import com.zhen.crm.exception.LoginException;
import com.zhen.crm.settings.dao.UserDao;
import com.zhen.crm.settings.domain.User;
import com.zhen.crm.settings.service.UserService;
import com.zhen.crm.utils.DateTimeUtil;
import com.zhen.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User loginCheck(User user, HttpServletRequest request) throws LoginException {

        user.setLoginPwd(MD5Util.getMD5(user.getLoginPwd()));
        User loginUser = userDao.login(user);
        //loginUser.getExpireTime().compareTo(DateTimeUtil.getCurrentTime()) < 0
        if(loginUser == null || "0".equals(loginUser.getLockState())){
            throw new LoginException("您输入的用户名或密码有误，请重新输入");
        }
        return loginUser;
    }

    @Override
    public List<User> queryUsers() {

        return userDao.selectUsers();
    }
}
