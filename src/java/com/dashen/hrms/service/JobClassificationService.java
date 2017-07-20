/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.JobClassification;
import com.dashen.hrms.dao.JobClassificationDao;
import com.dashen.hrms.MyUser;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MulugetaK
 */
@Service
public class JobClassificationService {
    @Autowired
    JobClassificationDao jobClDao;
    
    @Transactional
    public boolean saveNewJobClassification(JobClassification jcl) {
        jcl.setDescription(jcl.getDescription().trim().toUpperCase());
        //get current user (approver)
        MyUser myUsr = CurrentUser.getCurrentUser();
        jcl.setCreatedBy(myUsr.getUsername());
        jcl.setCreatedDate(new Date());
        jobClDao.save(jcl);
        return true;        
    }
    
    @Transactional
    public List<JobClassification> listAll() {
        return jobClDao.listAll();
    }
    
    @Transactional
    public JobClassification getByID(String id) {
        return jobClDao.getByID(id);
    }
    
    @Transactional
    public boolean delete(JobClassification cl) {
        jobClDao.delete(cl);
        return true;
    }
}
