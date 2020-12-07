package com.zhen.crm.workbench.service;

import com.zhen.crm.vo.PaginationVO;
import com.zhen.crm.workbench.domain.Activity;
import com.zhen.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean addActivity(Activity activity);

    PaginationVO<Activity> queryActivities(Activity activity, Integer pageNo, Integer pageSize);

    boolean deleteActivities(String[] id);

    Map<String, Object> getEditMessage(String id);

    boolean updateActivity(Activity activity);

    Activity getDetailById(String id);

    List<ActivityRemark> queryRemarks(String id);

    boolean deleteRemark(String id);

    boolean addActivityRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);
}
