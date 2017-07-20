/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.Institution;
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
public class InstitutionDao {

    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Institution ins) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(ins);

    }
    
    public List<Institution> listAll()
    {
        List<Institution> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Institution");
        list = qr.list();        
        return list;
    }
    
    public Institution getByID(String id)
    {
        Session session = sessionFactory.getCurrentSession();
        Institution inst = (Institution)session.get(Institution.class, id);        
        return inst;
    }
    
    public void delete(Institution ins) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(ins);
    }
}
