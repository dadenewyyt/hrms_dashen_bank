/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.QualificationTmp;
import com.dashen.hrms.RefereeTmp;
import com.dashen.hrms.TempStatus;
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
public class RefereeTmpDao {
    
    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(RefereeTmp r) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(r);
    }

    public void Update(RefereeTmp r) {
        Session session = sessionFactory.getCurrentSession();
        session.update(r);
    }
    
    public RefereeTmp getByRefereeTmpID(String id) {
        Session session = sessionFactory.getCurrentSession();
        RefereeTmp rf = (RefereeTmp) session.get(RefereeTmp.class, id);
        return rf;
    }

    public List<RefereeTmp> getByEmployeeSerialID(String id) {
        List<RefereeTmp> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp refereetmps of an employee
        Query qr = session.createQuery("from RefereeTmp where employeeSerialID = '" + id + "'");
        list = qr.list();
        return list;
    }
    
    public List<RefereeTmp> getByMakerID(String id) {
        List<RefereeTmp> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp referees made my a single user
        Query qr = session.createQuery("from RefereeTmp where MAKER_ID = '" + id + "'");
        list = qr.list();
        return list;
    }

    public void delete(RefereeTmp rf) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(rf);
    }
    
    public int refereePendingEditCount(String id) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from RefereeTmp where id  = '" + id + "'");
        return ((Long) qr.uniqueResult()).intValue();
    }

    public List<RefereeTmp> listTmpRefereesForMaker(String makerID) {
        List<RefereeTmp> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from RefereeTmp where MAKER_ID = '" + makerID + "' and (TMP_STATUS = '" + TempStatus.EDITABLE + "' or TMP_STATUS ='" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }
    
    public List<RefereeTmp> listTmpRefereesForChecker(String checkerID) {
        List<RefereeTmp> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from RefereeTmp where  TMP_STATUS ='" + TempStatus.SUBMITTED + "'");
        list = qr.list();
        return list;
    }

    public List<RefereeTmp> listAll() {
        List<RefereeTmp> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from RefereeTmp");
        list = qr.list();
        return list;
    }
}
