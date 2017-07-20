/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.VacancyTransfer;
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
public class VacancyTransferDao {
    @Autowired
    SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public boolean save(VacancyTransfer VT)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
           session.saveOrUpdate(VT);
           return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public boolean delete(VacancyTransfer VT)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
            session.delete(VT);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public List<VacancyTransfer> fetchAllUnsubmittedOrEditable() {
        List<VacancyTransfer> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from VacancyTransfer VT where VT.status = 'entry' or VT.status = 'reversed entry'");
        list = qr.list();        
        return list;
    }
    public List<VacancyTransfer> fetchAllSubmitted() {
        List<VacancyTransfer> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from VacancyTransfer VT where VT.status = 'submitted' or VT.status = 'reversed submission'");
        list = qr.list();        
        return list;
    }
    public List<VacancyTransfer> fetchAllApproved() {
        List<VacancyTransfer> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from VacancyTransfer VT where VT.status = 'approved'");
        list = qr.list();        
        return list;
    }
}
