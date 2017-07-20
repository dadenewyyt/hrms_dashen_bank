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
import com.dashen.hrms.RefereeTmp;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.RefereeTmpDao;
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
public class RefereeTmpService {
    @Autowired
    RefereeTmpDao refTmpDao;
      
    @Transactional
    public boolean saveNewTmpReferee(RefereeTmp newTmpR, ActionType acType) {        
        newTmpR.setActionType(acType);
        MyUser myUsr = CurrentUser.getCurrentUser();
        newTmpR.setMakerID(myUsr.getUsername());
        newTmpR.setMakerDate(new Date());
        newTmpR.setTmpStatus(TempStatus.EDITABLE);
        refTmpDao.saveNew(newTmpR);
        return true;
    }
    @Transactional
    public boolean updateTmpReferee(RefereeTmp tmpR) {       
        refTmpDao.Update(tmpR);
        return true;
    }
    
    @Transactional
    public boolean delete(RefereeTmp rf) {
        refTmpDao.delete(rf);
        return true;
    }
    
    public boolean checkIfRefereeHasPendingEdit(String id) {
        return (refTmpDao.refereePendingEditCount(id) > 0);
    }
    
    @Transactional
    public List<RefereeTmp> listTmpRefereesForMaker(String makerID) {
        return refTmpDao.listTmpRefereesForMaker(makerID);
    }
    
    @Transactional
    public List<RefereeTmp> listTmpRefereesForChecker(String checkerID) {
        return refTmpDao.listTmpRefereesForChecker(checkerID);
    }
    
    @Transactional
    public List<RefereeTmp> listAll() {
        return refTmpDao.listAll();
    }
}
