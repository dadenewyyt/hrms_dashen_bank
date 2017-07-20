/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.QualificationTmp;
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
public class QualificationTmpDao {

    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(QualificationTmp q) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(q);
    }

    public void Update(QualificationTmp q) {
        Session session = sessionFactory.getCurrentSession();
        session.update(q);
    }

    public QualificationTmp getByQualificationTmpID(String id) {
        Session session = sessionFactory.getCurrentSession();
        QualificationTmp ql = (QualificationTmp) session.get(QualificationTmp.class, id);
        return ql;
    }

    public List<QualificationTmp> getByEmployeeSerialID(String id) {
        List<QualificationTmp> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp qualifications of an employee
        Query qr = session.createQuery("from QualificationTmp where employeeSerialID = '" + id + "'");
        list = qr.list();
        return list;
    }

    public List<QualificationTmp> getByMakerID(String id) {
        List<QualificationTmp> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp qualifications of made my a single user
        Query qr = session.createQuery("from QualificationTmp where MAKER_ID = '" + id + "'");
        list = qr.list();
        return list;
    }

    public void delete(QualificationTmp qT) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(qT);
    }

    public int qualificationPendingEditCount(String id) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from QualificationTmp where id  = '" + id + "'");
        return ((Long) qr.uniqueResult()).intValue();
    }

    public List<QualificationTmp> listTmpQualificationsForMaker(String makerID) {
        List<QualificationTmp> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from QualificationTmp where MAKER_ID = '" + makerID + "' and (TMP_STATUS = '" + TempStatus.EDITABLE + "' or TMP_STATUS ='" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }
    public List<QualificationTmp> listTmpQualificationsForChecker(String checkerID) {
        List<QualificationTmp> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from QualificationTmp where  TMP_STATUS ='" + TempStatus.SUBMITTED + "'");
        list = qr.list();
        return list;
    }

    public List<QualificationTmp> listAll() {
        List<QualificationTmp> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from QualificationTmp");
        list = qr.list();
        return list;
    }
}
