/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.MyUser;
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
 * @author mulugetak
 */
@Service
public class QualificationTmpService {
    @Autowired
    QualificationTmpDao qualTmpDao;
      
    @Transactional
    public boolean saveNewTmpQualification(QualificationTmp newTmpQ, ActionType acType) {        
        newTmpQ.setActionType(acType);
        MyUser myUsr = CurrentUser.getCurrentUser();
        newTmpQ.setMakerID(myUsr.getUsername());
        newTmpQ.setMakerDate(new Date());
        newTmpQ.setTmpStatus(TempStatus.EDITABLE);
        qualTmpDao.saveNew(newTmpQ);
        return true;
    }
    @Transactional
    public boolean updateTmpQualification(QualificationTmp tmpQ) {       
        qualTmpDao.Update(tmpQ);
        return true;
    }
    
    @Transactional
    public boolean delete(QualificationTmp qt) {
        qualTmpDao.delete(qt);
        return true;
    }
    
    public boolean checkIfQualificationHasPendingEdit(String id) {
        return (qualTmpDao.qualificationPendingEditCount(id) > 0);
    }
    
    @Transactional
    public List<QualificationTmp> listTmpQualificationsForMaker(String makerID) {
        return qualTmpDao.listTmpQualificationsForMaker(makerID);
    }
    
    @Transactional
    public List<QualificationTmp> listTmpQualificationsForChecker(String checkerID) {
        return qualTmpDao.listTmpQualificationsForChecker(checkerID);
    }
    
    @Transactional
    public List<QualificationTmp> listAll() {
        return qualTmpDao.listAll();
    }
}
