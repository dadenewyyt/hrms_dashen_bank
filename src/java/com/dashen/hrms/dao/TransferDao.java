/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.Transfer;
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
public class TransferDao {
    @Autowired
    SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public boolean save(Transfer T)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
           session.saveOrUpdate(T);
           return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public boolean delete(Transfer T)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
            session.delete(T);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public List<Transfer> fetchAllUnsubmittedOrEditable() {
        List<Transfer> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Transfer T where T.status = 'entry' or T.status = 'reversed entry'");
        list = qr.list();        
        return list;
    }
    public List<Transfer> fetchAllSubmitted() {
        List<Transfer> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Transfer T where T.status = 'submitted' or T.status = 'reversed submission'");
        list = qr.list();        
        return list;
    }
    public List<Transfer> fetchAllApproved() {
        List<Transfer> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Transfer T where T.status = 'approved'");
        list = qr.list();        
        return list;
    }
}
