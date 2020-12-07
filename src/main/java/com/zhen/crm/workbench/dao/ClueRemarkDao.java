package com.zhen.crm.workbench.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface ClueRemarkDao {
    String[] getByClueId(String clueId);

    int deleteByClueId(String clueId);
}
