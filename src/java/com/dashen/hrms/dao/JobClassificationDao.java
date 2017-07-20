/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.JobClassification;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MulugetaK
 */
@Repository
@Transactional
public class JobClassificationDao {
    @Autowired
    SessionFactory sessionFactory;   
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void save(JobClassification c)
    {
        Session session = sessionFactory.getCurrentSession();        
        session.saveOrUpdate(c);  
       
    }

    public List<JobClassification> listAll()
    {
        List<JobClassification> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from JobClassification");
        list = qr.list();        
        return list;
    }
    public JobClassification getByID(String id)
    {
        Session session = sessionFactory.getCurrentSession();
        JobClassification cl = (JobClassification)session.get(JobClassification.class, id);        
        return cl;
    }
    
    public void delete(JobClassification cl) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(cl);
    }
}
