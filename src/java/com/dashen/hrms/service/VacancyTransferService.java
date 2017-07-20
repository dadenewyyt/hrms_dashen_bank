/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.VacancyTransfer;
import com.dashen.hrms.dao.VacancyTransferDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dawits
 */
@Service
public class VacancyTransferService {
    @Autowired
    VacancyTransferDao vacTraDao;
    
    public boolean save(VacancyTransfer VT) {  
        return vacTraDao.save(VT);
    }
    public boolean delete(VacancyTransfer VT) {
        return vacTraDao.delete(VT);
    }            
    public List<VacancyTransfer> fetchAllUnsubmittedOrEditable()
    {
        return vacTraDao.fetchAllUnsubmittedOrEditable();
    }
    public List<VacancyTransfer> fetchAllSubmitted()
    {
        return vacTraDao.fetchAllSubmitted();
    }
    public List<VacancyTransfer> fetchAllApproved()
    {
        return vacTraDao.fetchAllApproved();
    }
}
