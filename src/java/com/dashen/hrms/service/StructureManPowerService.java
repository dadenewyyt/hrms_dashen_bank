/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.StructureManPower;
import com.dashen.hrms.dao.StructureManPowerDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dawits
 */
@Service
public class StructureManPowerService {
    @Autowired
    StructureManPowerDao strManPowDao;
    
    public boolean save(StructureManPower SMP) {  
        return strManPowDao.save(SMP);
    }
    public boolean delete(StructureManPower SMP) {
        return strManPowDao.delete(SMP);
    }            
    public List<StructureManPower> fetchAllUnsubmittedOrEditable()
    {
        return strManPowDao.fetchAllUnsubmittedOrEditable();
    }
    public List<StructureManPower> fetchAllSubmitted()
    {
        return strManPowDao.fetchAllSubmitted();
    }
    public List<StructureManPower> fetchAllApproved()
    {
        return strManPowDao.fetchAllApproved();
    }
}