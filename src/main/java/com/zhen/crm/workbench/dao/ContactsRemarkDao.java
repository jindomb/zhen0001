package com.zhen.crm.workbench.dao;

import com.zhen.crm.workbench.domain.ContactsRemark;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRemarkDao {
    int insert(ContactsRemark contactsRemark);
}
