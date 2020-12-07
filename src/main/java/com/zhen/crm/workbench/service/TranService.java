package com.zhen.crm.workbench.service;

import com.zhen.crm.settings.domain.User;
import com.zhen.crm.workbench.domain.Tran;
import com.zhen.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    List<User> getUsers();

    void insert(Tran tran, String customerName);

    Tran getById(String id);

    List<TranHistory> getTranHistories(String tranId);

    Map<String, Object> changeStage(Tran tran);

    Map<String, Object> getCharts();
}
