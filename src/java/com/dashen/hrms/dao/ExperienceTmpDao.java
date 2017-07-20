/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.ExperienceTmp;
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
public class ExperienceTmpDao {
    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(ExperienceTmp e) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(e);
    }

    public void Update(ExperienceTmp e) {
        Session session = sessionFactory.getCurrentSession();
        session.update(e);
    }

    public ExperienceTmp getByExperienceTmpID(String id) {
        Session session = sessionFactory.getCurrentSession();
        ExperienceTmp ex = (ExperienceTmp) session.get(ExperienceTmp.class, id);
        return ex;
    }

    public List<ExperienceTmp> getByEmployeeSerialID(String id) {
        List<ExperienceTmp> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp experiences of an employee
        Query qr = session.createQuery("from ExperienceTmp where employeeSerialID = '" + id + "'");
        list = qr.list();
        return list;
    }

    public List<ExperienceTmp> getByMakerID(String id) {
        List<ExperienceTmp> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp experiences of made my a single user
        Query qr = session.createQuery("from ExperienceTmp where MAKER_ID = '" + id + "'");
        list = qr.list();
        return list;
    }

    public void delete(ExperienceTmp qT) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(qT);
    }

    public int experiencePendingEditCount(String id) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from ExperienceTmp where id  = '" + id + "'");
        return ((Long) qr.uniqueResult()).intValue();
    }

    public List<ExperienceTmp> listTmpExperiencesForMaker(String makerID) {
        List<ExperienceTmp> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from ExperienceTmp where MAKER_ID = '" + makerID + "' and (TMP_STATUS = '" + TempStatus.EDITABLE + "' or TMP_STATUS ='" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }
    public List<ExperienceTmp> listTmpExperiencesForChecker(String checkerID) {
        List<ExperienceTmp> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from ExperienceTmp where  TMP_STATUS ='" + TempStatus.SUBMITTED + "'");
        list = qr.list();
        return list;
    }

    public List<ExperienceTmp> listAll() {
        List<ExperienceTmp> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from ExperienceTmp");
        list = qr.list();
        return list;
    }
}
