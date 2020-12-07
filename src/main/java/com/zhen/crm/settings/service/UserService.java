package com.zhen.crm.settings.service;

import com.zhen.crm.exception.LoginException;
import com.zhen.crm.settings.domain.User;
import com.zhen.crm.workbench.domain.ActivityRemark;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {
    User loginCheck(User user, HttpServletRequest request) throws LoginException;

    List<User> queryUsers();
}
