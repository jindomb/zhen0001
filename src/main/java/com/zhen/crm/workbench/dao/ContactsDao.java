package com.zhen.crm.workbench.dao;

import com.zhen.crm.workbench.domain.Clue;
import com.zhen.crm.workbench.domain.Contacts;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsDao {
    int insert(Contacts contacts);
}
