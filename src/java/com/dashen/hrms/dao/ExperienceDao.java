/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.Experience;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mulugetak
 */
@Repository
@Transactional
public class ExperienceDao {
    
    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(Experience e) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(e);
    }

    public void update(Experience e) {
        Session session = sessionFactory.getCurrentSession();
        session.update(e);
    }

    public List<Experience> listAll() {
        List<Experience> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Experience");
        list = qr.list();
        return list;
    }

    public Experience getByExperienceID(String id) {
        Session session = sessionFactory.getCurrentSession();
        Experience ex = (Experience) session.get(Experience.class, id);
        return ex;
    }

    public List<Experience> getByEmployeeSerialID(String id) {
        List<Experience> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the Experiences of an employee
        Query qr = session.createQuery("from Experience where employeeSerialID = '" + id + "'");
        list = qr.list();
        return list;
    }
}
