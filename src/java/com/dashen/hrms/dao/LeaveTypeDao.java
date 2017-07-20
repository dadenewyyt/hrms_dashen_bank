/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.LeaveRequest;
import com.dashen.hrms.LeaveType;
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
 * @author Daniel Adenew
 */
@Repository
@Transactional
public class LeaveTypeDao {

    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(LeaveType q) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(q);
    }

    public void update(LeaveType q) {
        Session session = sessionFactory.getCurrentSession();
        session.update(q);
    }

    public List<LeaveType> listAll() {
        List<LeaveType> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from LeaveType");
        list = qr.list();
        return list;
    }

    public LeaveType getByLeaveTypeID(String id) {
        Session session = sessionFactory.getCurrentSession();
        LeaveType ql = (LeaveType) session.get(LeaveType.class, id);
        return ql;
    }

    public List<LeaveType> getByEmployeeSerialID(String id) {
        List<LeaveType> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the qualifications of an employee
        Query qr = session.createQuery("from LeaveType where employeeSerialID = '" + id + "'");
        list = qr.list();
        return list;
    }
    
     public void delete(LeaveType leaveType) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(leaveType);
    }
}
