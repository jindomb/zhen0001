package com.zhen.crm.workbench.service.impl;

import com.zhen.crm.workbench.dao.CustomerDao;
import com.zhen.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<String> getCustomerName(String name) {
        return customerDao.selectByName(name);
    }
}
