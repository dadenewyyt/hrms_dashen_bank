/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.Qualification;
import com.dashen.hrms.dao.QualificationDao;
import com.dashen.hrms.QualificationTmp;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.QualificationTmpDao;
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
public class QualificationService {

    @Autowired
    QualificationDao qualDao;

    @Autowired
    QualificationTmpDao qualTmpDao;

    public void setQualDao(QualificationDao qualDao) {
        this.qualDao = qualDao;
    }

    public void setQualTmpDao(QualificationTmpDao qualTmpDao) {
        this.qualTmpDao = qualTmpDao;
    }

    
    @Transactional
    public boolean saveNewQualification(QualificationTmp qT) {
        //new Qualification
        Qualification newQ = new Qualification();
        copyValuesFromTemp(newQ, qT);
        newQ.setCreatedBy(qT.getMakerID());
        newQ.setCreatedDate(qT.getMakerDate());
        qualDao.saveNew(newQ);
        
        //get current user (approver)
        MyUser myUsr = CurrentUser.getCurrentUser();      
        qT.setTmpStatus(TempStatus.HISTORY);        
        qT.setCheckerID(myUsr.getUsername());
        qT.setCheckerDate(new Date());
        
        qualTmpDao.Update(qT);
        return true;
    }

    @Transactional
    public boolean updateQualification(QualificationTmp qT) {
        Qualification ql = qualDao.getByQualificationID(qT.getQualificationId());
        copyValuesFromTemp(ql, qT);
        Date approvedDate = new Date();
        ql.setLastModifiedDate(approvedDate);
        ql.setLastModifiedBy(qT.getMakerID());
        qualDao.update(ql);
        //get current user (approver)
        MyUser myUsr = CurrentUser.getCurrentUser();        
        qT.setTmpStatus(TempStatus.HISTORY);        
        qT.setCheckerID(myUsr.getUsername());
        qT.setCheckerDate(approvedDate);
        qualTmpDao.Update(qT);
        return true;
    }

    @Transactional
    public List<Qualification> listAll() {
        return qualDao.listAll();
    }
    
    public void copyValuesFromTemp(Qualification destQ, QualificationTmp sourceQT)
    {
        destQ.setId(sourceQT.getQualificationId());
        destQ.setEmployeeSerialID(sourceQT.getEmployeeSerialId());
        destQ.setQualification(sourceQT.getQualification());
        destQ.setQualificationLevel(sourceQT.getQualificationLevel());
        destQ.setCGPA(sourceQT.getCGPA());
        destQ.setInstitutionID(sourceQT.getInstitutionID());
        destQ.setStartDate(sourceQT.getStartDate());
        destQ.setDateOfGraduation(sourceQT.getDateOfGraduation());
        destQ.setFileName(sourceQT.getFileName());
        destQ.setStatus(sourceQT.getStatus());        
    }
    
    @Transactional
    public List<Qualification> listQualificationsForEmployee(String empSerialId) {
        return qualDao.getByEmployeeSerialID(empSerialId);
    }
}
