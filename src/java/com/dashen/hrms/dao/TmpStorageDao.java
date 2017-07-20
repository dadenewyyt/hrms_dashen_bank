/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.TmpStorage;
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
public class TmpStorageDao {
    @Autowired
    SessionFactory sessionFactory;    

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void save(TmpStorage tmpStorage)
    {
        Session session = sessionFactory.getCurrentSession();        
        session.saveOrUpdate(tmpStorage);  
       
    }
  
    public TmpStorage getByID(String ID)
    {
        Session session = sessionFactory.getCurrentSession();
        TmpStorage tmpStorage = (TmpStorage)session.get(TmpStorage.class, ID);        
        return tmpStorage;
    }
}
