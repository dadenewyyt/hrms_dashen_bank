/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.Vacancy;
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
public class VacancyDao {
    @Autowired
    SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public boolean save(Vacancy V)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
           session.saveOrUpdate(V);
           return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public boolean delete(Vacancy V)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
            session.delete(V);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public List<Vacancy> fetchAllUnsubmittedOrEditable() {
        List<Vacancy> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Vacancy V where V.status = 'entry' or V.status = 'reversed entry'");
        list = qr.list();        
        return list;
    }
    public List<Vacancy> fetchAllSubmitted() {
        List<Vacancy> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Vacancy V where V.status = 'submitted' or V.status = 'reversed submission'");
        list = qr.list();        
        return list;
    }
    public List<Vacancy> fetchAllApproved() {
        List<Vacancy> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Vacancy V where V.status = 'approved'");
        list = qr.list();        
        return list;
    }
    public Vacancy getByVacancyId(String vacancyId) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Vacancy V where V.id = :vacancyId");
        qr.setParameter("vacancyId",vacancyId);
        List<Vacancy> list = qr.list();
        if (list.size() > 0) return list.get(0); else return null;
    }
}
