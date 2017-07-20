/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.TmpStorage;
import com.dashen.hrms.dao.TmpStorageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author biniamt
 */

@Service
public class TmpStorageService {
    
    @Autowired
    TmpStorageDao tmpStorageDao;

    public void setTmpStorageDao(TmpStorageDao tmpStorageDao) {
        this.tmpStorageDao = tmpStorageDao;
    }

    @Transactional
    public boolean saveNewTmpStorage(TmpStorage newTmpStorage) {
        tmpStorageDao.save(newTmpStorage);
        return true;
    }

    @Transactional
    public TmpStorage getByID(String ID) {
        return tmpStorageDao.getByID(ID);
    }
    
}