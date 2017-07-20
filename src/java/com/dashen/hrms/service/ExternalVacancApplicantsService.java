/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.ExternalVacancApplicants;
import com.dashen.hrms.dao.ExternalVacancApplicantsDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dawits
 */
@Service
public class ExternalVacancApplicantsService {
    @Autowired
    ExternalVacancApplicantsDao extVacAppDao;
    
    public boolean save(ExternalVacancApplicants EVA) {  
        return extVacAppDao.save(EVA);
    }
    public boolean delete(ExternalVacancApplicants EVA) {
        return extVacAppDao.delete(EVA);
    }            
    public List<ExternalVacancApplicants> fetchAllUnsubmittedOrEditable()
    {
        return extVacAppDao.fetchAllUnsubmittedOrEditable();
    }
    public List<ExternalVacancApplicants> fetchAllSubmitted()
    {
        return extVacAppDao.fetchAllSubmitted();
    }
    public List<ExternalVacancApplicants> fetchAllApproved()
    {
        return extVacAppDao.fetchAllApproved();
    }
}
