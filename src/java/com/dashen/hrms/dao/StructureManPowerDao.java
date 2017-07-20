/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.StructureManPower;
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
public class StructureManPowerDao {
    @Autowired
    SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public boolean save(StructureManPower SMP)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
           session.saveOrUpdate(SMP);
           return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public boolean delete(StructureManPower SMP)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
            session.delete(SMP);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public List<StructureManPower> fetchAllUnsubmittedOrEditable() {
        List<StructureManPower> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from StructureManPower SMP where SMP.status = 'entry' or SMP.status = 'reversed entry'");
        list = qr.list();        
        return list;
    }
    public List<StructureManPower> fetchAllSubmitted() {
        List<StructureManPower> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from StructureManPower SMP where SMP.status = 'submitted' or SMP.status = 'reversed submission'");
        list = qr.list();        
        return list;
    }
    public List<StructureManPower> fetchAllApproved() {
        List<StructureManPower> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from StructureManPower SMP where SMP.status = 'approved'");
        list = qr.list();        
        return list;
    }
}
