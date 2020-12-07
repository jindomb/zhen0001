package com.zhen.crm.workbench.dao;

import com.zhen.crm.workbench.domain.TranHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranHistoryDao {
    int insert(TranHistory tranHistory);

    List<TranHistory> selectByTranId(String tranId);
}
