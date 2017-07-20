/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.Transfer;
import com.dashen.hrms.dao.TransferDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dawits
 */
@Service
public class TransferService {
    @Autowired
    TransferDao traDao;
    
    public boolean save(Transfer T) {  
        return traDao.save(T);
    }
    public boolean delete(Transfer T) {
        return traDao.delete(T);
    }            
    public List<Transfer> fetchAllUnsubmittedOrEditable()
    {
        return traDao.fetchAllUnsubmittedOrEditable();
    }
    public List<Transfer> fetchAllSubmitted()
    {
        return traDao.fetchAllSubmitted();
    }
    public List<Transfer> fetchAllApproved()
    {
        return traDao.fetchAllApproved();
    }
}
