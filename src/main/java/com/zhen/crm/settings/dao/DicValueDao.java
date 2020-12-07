package com.zhen.crm.settings.dao;

import com.zhen.crm.settings.domain.DicValue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DicValueDao {
    List<DicValue> selectAll();
}
