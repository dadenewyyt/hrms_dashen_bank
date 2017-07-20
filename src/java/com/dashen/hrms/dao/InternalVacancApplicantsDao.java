/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.InternalVacancApplicants;
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
public class InternalVacancApplicantsDao {
    @Autowired
    SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public boolean save(InternalVacancApplicants IVA)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
           session.saveOrUpdate(IVA);
           return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public boolean delete(InternalVacancApplicants IVA)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
            session.delete(IVA);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public List<InternalVacancApplicants> fetchAllUnsubmittedOrEditable() {
        List<InternalVacancApplicants> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from InternalVacancApplicants IVA where IVA.status = 'entry' or IVA.status = 'reversed entry'");
        list = qr.list();        
        return list;
    }
    public List<InternalVacancApplicants> fetchAllSubmitted() {
        List<InternalVacancApplicants> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from InternalVacancApplicants IVA where IVA.status = 'submitted' or IVA.status = 'reversed submission'");
        list = qr.list();        
        return list;
    }
    public List<InternalVacancApplicants> fetchAllApproved() {
        List<InternalVacancApplicants> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from InternalVacancApplicants IVA where IVA.status = 'approved'");
        list = qr.list();        
        return list;
    }
    public List<InternalVacancApplicants> getByVacancyId(String vacancyId) {
        List<InternalVacancApplicants> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from InternalVacancApplicants IVA where IVA.vacancy.id = :vacancyId");
        qr.setParameter("vacancyId",vacancyId);
        list = qr.list();        
        return list;
    }
}
