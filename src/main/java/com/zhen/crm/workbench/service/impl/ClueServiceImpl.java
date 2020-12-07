package com.zhen.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.zhen.crm.settings.dao.UserDao;
import com.zhen.crm.settings.domain.User;
import com.zhen.crm.utils.UUIDUtil;
import com.zhen.crm.vo.PaginationVO;
import com.zhen.crm.workbench.dao.*;
import com.zhen.crm.workbench.domain.*;
import com.zhen.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService{
    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private ClueRemarkDao clueRemarkDao;
    @Autowired
    private CustomerRemarkDao customerRemarkDao;
    @Autowired
    private ContactsRemarkDao contactsRemarkDao;
    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;

    @Override
    public boolean addClue(Clue clue) {
        return clueDao.insertClue(clue) == 1;
    }

    @Override
    public PaginationVO<Clue> queryClues(Clue clue, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Clue> clues = clueDao.selectClues(clue);
        int cnt = clueDao.countClues(clue);
        PaginationVO<Clue> paginationVO = new PaginationVO<>();
        paginationVO.setTotal(cnt);
        paginationVO.setDataList(clues);
        return paginationVO;
    }

    @Override
    public Clue getDetailById(String id) {
        return clueDao.selectClueById(id);
    }

    @Override
    public List<Activity> getActivities(String clueId) {
        return activityDao.selectActivitiesByClueId(clueId);
    }

    @Override
    public List<User> queryUsers() {
        return userDao.selectUsers();
    }

    @Override
    public Map<String, Object> deleteRelation(String id) {
        Map<String,Object> map = new HashMap<>();
        map.put("success",clueActivityRelationDao.deleteRelation(id) == 1);
        return map;
    }

    @Override
    public List<Activity> getNotRelationActivities(String name, String clueId) {
        Map<String,String> map = new HashMap<>();
        map.put("name",name);
        map.put("clueId",clueId);
        return activityDao.selectActivitiesNotRelation(map);
    }

    @Override
    public Map<String, Object> addRelations(String clueId, String[] activityId) {
        boolean flag = true;
        for(String id : activityId){
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setActivityId(id);
            clueActivityRelation.setClueId(clueId);
            flag = clueActivityRelationDao.insertRelation(clueActivityRelation) == 1;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }

    @Override
    public List<Activity> getActivitiesByName(String name) {
        return activityDao.selectActivitiesByName(name);
    }

    @Transactional
    @Override
    public void clueConvert(String clueId, Tran tran) {
        String createTime = tran.getCreateTime();
        String createBy = tran.getCreateBy();
        Clue clue = clueDao.getById(clueId);
        Customer customer = customerDao.getByName(clue.getCompany());
        if(customer == null){
            customer = new Customer();
            customer.setAddress(clue.getAddress());
            customer.setContactSummary(clue.getContactSummary());
            customer.setCreateBy(createBy);
            customer.setCreateTime(createTime);
            customer.setDescription(clue.getDescription());
            customer.setId(UUIDUtil.getUUID());
            customer.setWebsite(clue.getWebsite());
            customer.setName(clue.getCompany());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setOwner(clue.getOwner());
            customer.setPhone(clue.getPhone());
            if(customerDao.insert(customer) != 1){
                throw new NullPointerException();
            }
        }

        Contacts contacts = new Contacts();
        contacts.setAddress(clue.getAddress());
        contacts.setAppellation(clue.getAppellation());
        contacts.setBirth(null);
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setFullname(clue.getFullname());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(customer.getId());

        if(contactsDao.insert(contacts) != 1){
            throw new NullPointerException();
        }

        String[] noteContents = clueRemarkDao.getByClueId(clueId);
        CustomerRemark customerRemark = new CustomerRemark();
        ContactsRemark contactsRemark = new ContactsRemark();
        customerRemark.setCreateBy(createBy);
        customerRemark.setCreateTime(createTime);
        customerRemark.setCustomerId(customer.getId());
        customerRemark.setEditFlag("0");
        contactsRemark.setContactsId(contacts.getId());
        contactsRemark.setEditFlag("0");
        contactsRemark.setCreateBy(createBy);
        contactsRemark.setCreateTime(createTime);
        for(String noteContent : noteContents){
            customerRemark.setNoteContent(noteContent);
            customerRemark.setId(UUIDUtil.getUUID());
            if(customerRemarkDao.insert(customerRemark) != 1){
                throw new NullPointerException();
            }

            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setId(UUIDUtil.getUUID());
            if(contactsRemarkDao.insert(contactsRemark) != 1){
                throw new NullPointerException();
            }
        }

        String[] activityIds = clueActivityRelationDao.getActivityIds(clueId);
        ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
        contactsActivityRelation.setContactsId(contacts.getId());
        for(String activityId : activityIds){
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            if(contactsActivityRelationDao.insert(contactsActivityRelation) != 1){
                throw new NullPointerException();
            }
        }

        if(tran.getStage() != null){
            tran.setCreateTime(createTime);
            tran.setSource(clue.getSource());
            tran.setOwner(clue.getOwner());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setContactsId(contacts.getId());
            tran.setContactSummary(clue.getContactSummary());
            tran.setCreateBy(createBy);
            tran.setCustomerId(customer.getId());
            tran.setId(UUIDUtil.getUUID());
            tran.setDescription(clue.getDescription());
            tran.setType("新业务");
            if(tranDao.insert(tran) != 1){
                throw new NullPointerException();
            }

            TranHistory tranHistory = new TranHistory();
            tranHistory.setCreateBy(createBy);
            tranHistory.setTranId(tran.getId());
            tranHistory.setStage(tran.getStage());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateTime(createTime);
            if(tranHistoryDao.insert(tranHistory) != 1){
                throw new NullPointerException();
            }
        }

        if(clueActivityRelationDao.deleteByClueId(clueId) != activityIds.length){
            throw new NullPointerException();
        }

        if(clueRemarkDao.deleteByClueId(clueId) != noteContents.length){
            throw new NullPointerException();
        }

        if(clueDao.deleteById(clueId) != 1){
            throw new NullPointerException();
        }
    }
}
