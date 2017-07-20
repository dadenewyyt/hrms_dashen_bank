/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;


import com.dashen.hrms.Qualification;
import com.dashen.hrms.Referee;
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
public class RefereeDao {
    
     @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(Referee r) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(r);
    }

    public void update(Referee r) {
        Session session = sessionFactory.getCurrentSession();
        session.update(r);
    }
    
    public List<Referee> listAll() {
        List<Referee> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Referee");
        list = qr.list();
        return list;
    }

    public Referee getByRefereeID(String id) {
        Session session = sessionFactory.getCurrentSession();
        Referee rf = (Referee) session.get(Referee.class, id);
        return rf;
    }

    public List<Referee> getByEmployeeSerialID(String id) {
        List<Referee> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the Referees of an employee
        Query qr = session.createQuery("from Referee where employeeSerialID = '" + id + "'");
        list = qr.list();
        return list;
    }
}
