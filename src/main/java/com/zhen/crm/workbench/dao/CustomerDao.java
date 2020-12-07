package com.zhen.crm.workbench.dao;

import com.zhen.crm.workbench.domain.Clue;
import com.zhen.crm.workbench.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDao {
    Customer getByName(String name);

    int insert(Customer customer);

    List<String> selectByName(String name);
}
