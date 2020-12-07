package com.zhen.crm.workbench.dao;

import com.zhen.crm.workbench.domain.ContactsActivityRelation;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsActivityRelationDao {
    int insert(ContactsActivityRelation contactsActivityRelation);
}
