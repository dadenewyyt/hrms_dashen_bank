/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.Allowance;
import com.dashen.hrms.AllowanceEmploymentCenter;
import com.dashen.hrms.AllowanceLocation;
import com.dashen.hrms.AllowancePosition;
import com.dashen.hrms.AllowanceType;
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
 * @author biniamt
 */

@Repository
@Transactional
public class AllowanceDao {
    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(Allowance s) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(s);
    }

    public void update(Allowance s) {
        Session session = sessionFactory.getCurrentSession();
        session.update(s);
    }

    public List<Allowance> listTmpAllowances() {
        List<Allowance> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Allowance where TMP_STATUS = '" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "'");
        list = qr.list();
        return list;
    }

    public Allowance getByAllowanceID(String id) {
        Session session = sessionFactory.getCurrentSession();
        Allowance allowance = (Allowance) session.get(Allowance.class, id);
        return allowance;
    }

    public List<Allowance> getByMakerID(String id) {
        List<Allowance> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp allowances of made my a single user
        Query qr = session.createQuery("from Allowance where CREATED_BY = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public void delete(Allowance allowanceTmp) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(allowanceTmp);
    }

    public int allowancePendingEditCount(String id) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from Allowance where id = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        return ((Long) qr.uniqueResult()).intValue();
    }

    public List<Allowance> listTmpAllowancesForMaker(String makerID) {
        List<Allowance> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Allowance where CREATED_BY = '" + makerID + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public List<Allowance> listAllowances() {
        List<Allowance> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Allowance where TMP_STATUS = '" + TempStatus.APPROVED + "'");
        list = qr.list();
        return list;
    }
    
    public Allowance getByID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        Allowance allowance = (Allowance)session.get(Allowance.class, ID);        
        return allowance;
    }

    public void saveNewAllowancePosition(AllowancePosition s) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(s);
    }

    public void saveNewAllowanceLocation(AllowanceLocation s) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(s);
    }
    
    public void saveNewAllowanceEmploymentCenter(AllowanceEmploymentCenter s) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(s);
    }

    public void updateAllowancePosition(AllowancePosition s) {
        Session session = sessionFactory.getCurrentSession();
        session.update(s);
    }

    public void updateAllowanceLocation(AllowanceLocation s) {
        Session session = sessionFactory.getCurrentSession();
        session.update(s);
    }

    public void updateAllowanceEmploymentCenter(AllowanceEmploymentCenter s) {
        Session session = sessionFactory.getCurrentSession();
        session.update(s);
    }

    public AllowancePosition getAllowancePositionByAllowanceID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from AllowancePosition ap where ap.allowance.ID = '" + ID + "'");
        List<AllowancePosition> l = qr.list();
        return l.get(0);
    }

    public AllowanceLocation getAllowanceLocationByAllowanceID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from AllowanceLocation al where al.allowance.ID = '" + ID + "'");
        List<AllowanceLocation> l = qr.list();
        return l.get(0);
    }

    public AllowanceEmploymentCenter getAllowanceEmploymentCenterByAllowanceID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from AllowanceEmploymentCenter aec where aec.allowance.ID = '" + ID + "'");
        List<AllowanceEmploymentCenter> l = qr.list();
        return l.get(0);
    }

    public void deleteAllowancePosition(AllowancePosition ap) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(ap);
    }

    public void deleteAllowanceLocation(AllowanceLocation al) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(al);
    }

    public void deleteAllowanceEmploymentCenter(AllowanceEmploymentCenter aec) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(aec);
    }
    
    public AllowanceType getAllowanceTypeByAllowanceID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select a.allowanceType from Allowance a where a.ID = '" + ID + "'");
        List<AllowanceType> l = qr.list();
        return l.get(0);
    }
}
