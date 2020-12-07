package com.zhen.crm.settings.service.impl;

import com.zhen.crm.settings.dao.DicTypeDao;
import com.zhen.crm.settings.dao.DicValueDao;
import com.zhen.crm.settings.domain.DicValue;
import com.zhen.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {
    @Autowired
    private DicTypeDao dicTypeDao;
    @Autowired
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getAllDicValues() {
        List<DicValue> dicValues = dicValueDao.selectAll();
        Map<String, List<DicValue>> map = new HashMap<>();
        for(DicValue dicValue : dicValues){
            List<DicValue> list = map.getOrDefault(dicValue.getTypeCode(),new ArrayList<>());
            list.add(dicValue);
            map.put(dicValue.getTypeCode(),list);
        }
        return map;
    }
}
