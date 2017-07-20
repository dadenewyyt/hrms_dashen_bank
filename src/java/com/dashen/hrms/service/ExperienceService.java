/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.Experience;
import com.dashen.hrms.ExperienceTmp;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.ExperienceDao;
import com.dashen.hrms.dao.ExperienceTmpDao;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mulugetak
 */
@Service
public class ExperienceService {
    @Autowired
    ExperienceDao expDao;
    
    @Autowired
    ExperienceTmpDao expTmpDao;
    
    @Transactional
    public boolean saveNewExperience(ExperienceTmp eT) {
        //new Experience
        Experience newE = new Experience();
        copyValuesFromTemp(newE, eT);        
        expDao.saveNew(newE);
        
        //get current user (approver)
        MyUser myUsr = CurrentUser.getCurrentUser();      
        eT.setTmpStatus(TempStatus.HISTORY);        
        eT.setCheckerID(myUsr.getUsername());
        eT.setCheckerDate(new Date());
        
        expTmpDao.Update(eT);
        return true;
    }
    
    @Transactional
    public boolean updateExperience(ExperienceTmp eT) {
        Experience ex = expDao.getByExperienceID(eT.getExperienceID());
        copyValuesFromTemp(ex, eT);
        Date approvedDate = new Date();        
        expDao.update(ex);
        //get current user (approver)
        MyUser myUsr = CurrentUser.getCurrentUser();        
        eT.setTmpStatus(TempStatus.HISTORY);        
        eT.setCheckerID(myUsr.getUsername());
        eT.setCheckerDate(approvedDate);
        expTmpDao.Update(eT);
        return true;
    }

    @Transactional
    public List<Experience> listAll() {
        return expDao.listAll();
    }
    
    public void copyValuesFromTemp(Experience destE, ExperienceTmp sourceET)
    {
        destE.setId(sourceET.getExperienceID());
        destE.setEmployeeSerialID(sourceET.getEmployeeSerialID());
        destE.setNameOfEmployer(sourceET.getNameOfEmployer());
        destE.setEmployerAddress(sourceET.getEmployerAddress());
        destE.setPosition(sourceET.getPosition());
        destE.setStartDate(sourceET.getStartDate());
        destE.setEndDate(sourceET.getEndDate());
        destE.setTotalYearsOfExperience(sourceET.getTotalYearsOfExperience());
        destE.setReasonForResignation(sourceET.getReasonForResignation());
        destE.setFileName(sourceET.getFileName());               
    }
    
    @Transactional
    public List<Experience> listExperiencesForEmployee(String empSerialId) {
        return expDao.getByEmployeeSerialID(empSerialId);
    }
    
}
