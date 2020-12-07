package com.zhen.crm.workbench.service.impl;

import com.zhen.crm.settings.dao.UserDao;
import com.zhen.crm.settings.domain.User;
import com.zhen.crm.utils.UUIDUtil;
import com.zhen.crm.workbench.dao.CustomerDao;
import com.zhen.crm.workbench.dao.TranDao;
import com.zhen.crm.workbench.dao.TranHistoryDao;
import com.zhen.crm.workbench.domain.Customer;
import com.zhen.crm.workbench.domain.Tran;
import com.zhen.crm.workbench.domain.TranHistory;
import com.zhen.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<User> getUsers() {
        return userDao.selectUsers();
    }

    @Transactional
    @Override
    public void insert(Tran tran, String customerName) {
        Customer customer = customerDao.getByName(customerName);
        if(customer == null){
            customer = new Customer();
            customer.setContactSummary(tran.getContactSummary());
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(tran.getCreateTime());
            customer.setDescription(tran.getDescription());
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setOwner(tran.getOwner());
            if(customerDao.insert(customer) != 1){
                throw new NullPointerException();
            }
        }
        tran.setCustomerId(customer.getId());
        tran.setId(UUIDUtil.getUUID());

        if(tranDao.insert(tran) != 1){
            throw new NullPointerException();
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setTranId(tran.getId());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(tran.getCreateTime());
        if(tranHistoryDao.insert(tranHistory) != 1){
            throw new NullPointerException();
        }
    }

    @Override
    public Tran getById(String id) {
        return tranDao.selectById(id);
    }

    @Override
    public List<TranHistory> getTranHistories(String tranId) {
        return tranHistoryDao.selectByTranId(tranId);
    }

    @Transactional
    @Override
    public Map<String, Object> changeStage(Tran tran) {
        if(tranDao.update(tran) != 1){
            throw new NullPointerException();
        }
        TranHistory tranHistory = new TranHistory();
        tranHistory.setCreateBy(tran.getEditBy());
        tranHistory.setTranId(tran.getId());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(tran.getEditTime());
        if(tranHistoryDao.insert(tranHistory) != 1){
            throw new NullPointerException();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("tran",tran);
        return map;
    }

    @Override
    public Map<String, Object> getCharts() {
        int total = tranDao.count();
        List<Map<String,String>> dataList = tranDao.getCountByStage();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("dataList",dataList);
        return map;
    }
}
