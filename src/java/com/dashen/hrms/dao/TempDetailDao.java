/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.TempDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author biniamt
 */

@Repository
@Transactional
public class TempDetailDao {
    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(TempDetail t) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(t);
    }

    public void update(TempDetail t) {
        Session session = sessionFactory.getCurrentSession();
        session.update(t);
    }

    public void delete(TempDetail tempDetail) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(tempDetail);
    }
    
    public TempDetail getByID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        TempDetail tempDetail = (TempDetail)session.get(TempDetail.class, ID);        
        return tempDetail;
    }
}
