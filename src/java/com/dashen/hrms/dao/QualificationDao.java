/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.Qualification;
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
public class QualificationDao {

    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(Qualification q) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(q);
    }

    public void update(Qualification q) {
        Session session = sessionFactory.getCurrentSession();
        session.update(q);
    }

    public List<Qualification> listAll() {
        List<Qualification> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Qualification");
        list = qr.list();
        return list;
    }

    public Qualification getByQualificationID(String id) {
        Session session = sessionFactory.getCurrentSession();
        Qualification ql = (Qualification) session.get(Qualification.class, id);
        return ql;
    }

    public List<Qualification> getByEmployeeSerialID(String id) {
        List<Qualification> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the qualifications of an employee
        Query qr = session.createQuery("from Qualification where employeeSerialID = '" + id + "'");
        list = qr.list();
        return list;
    }
}
