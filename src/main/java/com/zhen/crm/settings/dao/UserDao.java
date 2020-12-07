package com.zhen.crm.settings.dao;

import com.zhen.crm.settings.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    User login(User user);

    List<User> selectUsers();
}
