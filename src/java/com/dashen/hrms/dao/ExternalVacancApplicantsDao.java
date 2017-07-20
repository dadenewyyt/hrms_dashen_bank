/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.ExternalVacancApplicants;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dawits
 */
@Repository
@Transactional
public class ExternalVacancApplicantsDao {
    @Autowired
    SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public boolean save(ExternalVacancApplicants EVA)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
           session.saveOrUpdate(EVA);
           return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public boolean delete(ExternalVacancApplicants EVA)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
            session.delete(EVA);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public List<ExternalVacancApplicants> fetchAllUnsubmittedOrEditable() {
        List<ExternalVacancApplicants> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from ExternalVacancApplicants EVA where EVA.status = 'entry' or EVA.status = 'reversed entry'");
        list = qr.list();        
        return list;
    }
    public List<ExternalVacancApplicants> fetchAllSubmitted() {
        List<ExternalVacancApplicants> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from ExternalVacancApplicants EVA where EVA.status = 'submitted' or EVA.status = 'reversed submission'");
        list = qr.list();        
        return list;
    }
    public List<ExternalVacancApplicants> fetchAllApproved() {
        List<ExternalVacancApplicants> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from ExternalVacancApplicants EVA where EVA.status = 'approved'");
        list = qr.list();        
        return list;
    }
}
