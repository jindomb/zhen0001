package com.zhen.crm.workbench.dao;

import com.zhen.crm.workbench.domain.ActivityRemark;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRemarkDao {
    int countByIds(String[] id);

    int deleteByIds(String[] id);

    List<ActivityRemark> selectRemarks(String id);

    int deleteRemarkById(String id);

    int insertRemark(ActivityRemark activityRemark);

    int updateRemark(ActivityRemark activityRemark);
}
