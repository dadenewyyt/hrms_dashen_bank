/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.Vacancy;
import com.dashen.hrms.dao.VacancyDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dawits
 */
@Service
public class VacancyService {
    @Autowired
    VacancyDao vacDao;
    
    @Transactional
    public boolean save(Vacancy V) {  
        return vacDao.save(V);
    }
    
    @Transactional
    public boolean delete(Vacancy V) {
        return vacDao.delete(V);
    } 
    
    @Transactional
    public List<Vacancy> fetchAllUnsubmittedOrEditable()
    {
        return vacDao.fetchAllUnsubmittedOrEditable();
    }
    
    @Transactional
    public List<Vacancy> fetchAllSubmitted()
    {
        return vacDao.fetchAllSubmitted();
    }
    
    @Transactional
    public List<Vacancy> fetchAllApproved()
    {
        return vacDao.fetchAllApproved();
    }
    
    @Transactional
    public Vacancy getByVacancyId(String vacancyId) {
        return vacDao.getByVacancyId(vacancyId);
    }
}
