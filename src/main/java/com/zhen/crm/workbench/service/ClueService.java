package com.zhen.crm.workbench.service;

import com.zhen.crm.settings.domain.User;
import com.zhen.crm.vo.PaginationVO;
import com.zhen.crm.workbench.domain.Activity;
import com.zhen.crm.workbench.domain.Clue;
import com.zhen.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface ClueService {
    boolean addClue(Clue clue);

    PaginationVO<Clue> queryClues(Clue clue, Integer pageNo, Integer pageSize);

    Clue getDetailById(String id);

    List<Activity> getActivities(String clueId);

    List<User> queryUsers();

    Map<String, Object> deleteRelation(String id);

    List<Activity> getNotRelationActivities(String name, String clueId);

    Map<String, Object> addRelations(String clueId, String[] activityId);

    List<Activity> getActivitiesByName(String name);

    void clueConvert(String clueId, Tran tran);
}
