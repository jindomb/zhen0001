package com.zhen.crm.workbench.dao;

import com.zhen.crm.workbench.domain.CustomerRemark;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRemarkDao {
    int insert(CustomerRemark customerRemark);
}
