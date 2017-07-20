/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.InternalVacancApplicants;
import com.dashen.hrms.dao.InternalVacancApplicantsDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dawits
 */
@Service
public class InternalVacancApplicantsService {
    @Autowired
    InternalVacancApplicantsDao inVacAppDao;
    
    public boolean save(InternalVacancApplicants IVA) {  
        return inVacAppDao.save(IVA);
    }
    public boolean delete(InternalVacancApplicants IVA) {
        return inVacAppDao.delete(IVA);
    }            
    public List<InternalVacancApplicants> fetchAllUnsubmittedOrEditable()
    {
        return inVacAppDao.fetchAllUnsubmittedOrEditable();
    }
    public List<InternalVacancApplicants> fetchAllSubmitted()
    {
        return inVacAppDao.fetchAllSubmitted();
    }
    public List<InternalVacancApplicants> fetchAllApproved()
    {
        return inVacAppDao.fetchAllApproved();
    }
    public List<InternalVacancApplicants> getByVacancyId(String vacancyId) {
        return inVacAppDao.getByVacancyId(vacancyId);
    }
}
