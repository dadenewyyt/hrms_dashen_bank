/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.Institution;
import com.dashen.hrms.dao.InstitutionDao;
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
public class InstitutionService {

    @Autowired
    InstitutionDao instDao;

    @Transactional
    public boolean save(Institution inst) {

        instDao.save(inst);
        return true;
    }
    
    @Transactional
    public boolean saveNewInstitution(Institution newInst) {        
        MyUser myUsr = CurrentUser.getCurrentUser();
        newInst.setCreatedBy(myUsr.getUsername());
        newInst.setCreatedDate(new Date());        
        instDao.save(newInst);
        return true;
    }
    
    @Transactional
    public boolean updateInstitution(Institution inst) {        
        MyUser myUsr = CurrentUser.getCurrentUser();
        inst.setLastModifiedBy(myUsr.getUsername());
        inst.setLastModifiedDate(new Date());        
        instDao.save(inst);
        return true;
    }
    
    @Transactional
    public List<Institution> listAll() {
        return instDao.listAll();
    }

    
    @Transactional
    public boolean delete(Institution ins) {
        instDao.delete(ins);
        return true;
    }
}
