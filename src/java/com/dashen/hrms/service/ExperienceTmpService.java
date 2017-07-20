/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.ExperienceTmp;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.QualificationTmp;
import com.dashen.hrms.TempStatus;
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
public class ExperienceTmpService {
    @Autowired
    ExperienceTmpDao expTmpDao;
      
    @Transactional
    public boolean saveNewTmpExperience(ExperienceTmp newTmpE, ActionType acType) {        
        newTmpE.setActionType(acType);
        MyUser myUsr = CurrentUser.getCurrentUser();
        newTmpE.setMakerID(myUsr.getUsername());
        newTmpE.setMakerDate(new Date());
        newTmpE.setTmpStatus(TempStatus.EDITABLE);
        expTmpDao.saveNew(newTmpE);
        return true;
    }
    @Transactional
    public boolean updateTmpExperience(ExperienceTmp tmpE) {       
        expTmpDao.Update(tmpE);
        return true;
    }
    
    @Transactional
    public boolean delete(ExperienceTmp et) {
        expTmpDao.delete(et);
        return true;
    }
    
    public boolean checkIfExperienceHasPendingEdit(String id) {
        return (expTmpDao.experiencePendingEditCount(id) > 0);
    }
    
    @Transactional
    public List<ExperienceTmp> listTmpExperiencesForMaker(String makerID) {
        return expTmpDao.listTmpExperiencesForMaker(makerID);
    }
    
    @Transactional
    public List<ExperienceTmp> listTmpExperiencesForChecker(String checkerID) {
        return expTmpDao.listTmpExperiencesForChecker(checkerID);
    }
    
    @Transactional
    public List<ExperienceTmp> listAll() {
        return expTmpDao.listAll();
    }
}
