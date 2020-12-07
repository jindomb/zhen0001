package com.zhen.crm.workbench.dao;

import com.zhen.crm.workbench.domain.Tran;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TranDao {
    int insert(Tran tran);

    Tran selectById(String id);

    int update(Tran tran);

    int count();

    List<Map<String, String>> getCountByStage();
}
