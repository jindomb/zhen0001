package com.zhen.crm.workbench.service.impl;

import com.zhen.crm.settings.dao.UserDao;
import com.zhen.crm.settings.domain.User;
import com.zhen.crm.vo.PaginationVO;
import com.zhen.crm.workbench.dao.ActivityDao;
import com.zhen.crm.workbench.dao.ActivityRemarkDao;
import com.zhen.crm.workbench.domain.Activity;
import com.zhen.crm.workbench.domain.ActivityRemark;
import com.zhen.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActivityRemarkDao activityRemarkDao;

    @Autowired
    private UserDao userDao;

    @Override
    public boolean addActivity(Activity activity) {
        return activityDao.insertActivity(activity) == 1;
    }

    @Override
    public PaginationVO<Activity> queryActivities(Activity activity, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Activity> activities = activityDao.selectActivities(activity);
        int cnt = activityDao.countActivities(activity);
        PaginationVO<Activity> paginationVO = new PaginationVO<>();
        paginationVO.setTotal(cnt);
        paginationVO.setDataList(activities);
        return paginationVO;
    }

    @Transactional
    @Override
    public boolean deleteActivities(String[] id) {
        int total = activityRemarkDao.countByIds(id);
        int cnt = activityRemarkDao.deleteByIds(id);
        if(total != cnt){
            throw new NullPointerException();
        }
        int cnt2 = activityDao.deleteByIds(id);
        if(cnt2 != id.length){
            throw new NullPointerException();
        }
        return true;
    }

    @Override
    public Map<String, Object> getEditMessage(String id) {
        List<User> users = userDao.selectUsers();
        Activity activity = activityDao.getById(id);
        Map<String,Object> map = new HashMap<>();
        map.put("users",users);
        map.put("activity",activity);
        return map;
    }

    @Override
    public boolean updateActivity(Activity activity) {
        int cnt = activityDao.update(activity);
        return cnt == 1;
    }

    @Override
    public Activity getDetailById(String id) {
        return activityDao.getDetailById(id);
    }

    @Override
    public List<ActivityRemark> queryRemarks(String id) {
        return activityRemarkDao.selectRemarks(id);
    }

    @Override
    public boolean deleteRemark(String id) {
        return activityRemarkDao.deleteRemarkById(id) == 1;
    }

    @Override
    public boolean addActivityRemark(ActivityRemark activityRemark) {
        return activityRemarkDao.insertRemark(activityRemark) == 1;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        return activityRemarkDao.updateRemark(activityRemark) == 1;
    }
}
