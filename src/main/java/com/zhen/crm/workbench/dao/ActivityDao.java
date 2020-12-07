package com.zhen.crm.workbench.dao;

import com.zhen.crm.workbench.domain.Activity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ActivityDao {
    int insertActivity(Activity activity);

    List<Activity> selectActivities(Activity activity);

    int countActivities(Activity activity);

    int deleteByIds(String[] id);

    Activity getById(String id);

    int update(Activity activity);

    Activity getDetailById(String id);

    List<Activity> selectActivitiesByClueId(String clueId);

    List<Activity> selectActivitiesNotRelation(Map<String,String> map);

    List<Activity> selectActivitiesByName(String name);
}
