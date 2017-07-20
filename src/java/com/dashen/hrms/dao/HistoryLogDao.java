/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.HistoryLog;
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
public class HistoryLogDao {
    @Autowired
    SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public boolean save(HistoryLog HL)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
           session.saveOrUpdate(HL);
           return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public boolean delete(HistoryLog HL)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
            session.delete(HL);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public List<HistoryLog> fetchByDataId(String DataId) {
        List<HistoryLog> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from HistoryLog HL where HL.dataId = :DataId");
        qr.setParameter("DataId",DataId);
        list = qr.list();        
        return list;
    }
}