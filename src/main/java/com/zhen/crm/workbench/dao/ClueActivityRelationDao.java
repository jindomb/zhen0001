package com.zhen.crm.workbench.dao;

import com.zhen.crm.workbench.domain.ClueActivityRelation;
import org.springframework.stereotype.Repository;

@Repository
public interface ClueActivityRelationDao {
    int deleteRelation(String id);

    int insertRelation(ClueActivityRelation clueActivityRelation);

    String[] getActivityIds(String clueId);

    int deleteByClueId(String clueId);
}
