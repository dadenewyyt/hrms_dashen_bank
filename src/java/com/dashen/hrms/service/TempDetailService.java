/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.TempDetail;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.TempDetail;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.TempDetailDao;
import com.dashen.hrms.dao.TempDetailDao;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author biniamt
 */

@Service
public class TempDetailService {
    
    @Autowired
    TempDetailDao tempDetailDao;

    public void setTempDetailDao(TempDetailDao tempDetailDao) {
        this.tempDetailDao = tempDetailDao;
    }

    @Transactional
    public boolean updateTempDetail(TempDetail tmpTempDetail) {       
        tempDetailDao.update(tmpTempDetail);
        return true;
    }
    
    @Transactional
    public boolean delete(TempDetail tempDetailTmp) {
        tempDetailDao.delete(tempDetailTmp);
        return true;
    }
    
    @Transactional
    public TempDetail getByID(String ID) {
        return tempDetailDao.getByID(ID);
    }
    
}