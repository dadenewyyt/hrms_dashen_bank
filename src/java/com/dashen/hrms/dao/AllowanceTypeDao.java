/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.AllowancePaymentOptions;
import com.dashen.hrms.AllowanceType;
import com.dashen.hrms.AllowanceTypeQuantitative;
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
public class AllowanceTypeDao {
    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(AllowanceType s) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(s);
    }

    public void update(AllowanceType s) {
        Session session = sessionFactory.getCurrentSession();
        session.update(s);
    }

    public List<AllowanceType> listTmpAllowanceTypes() {
        List<AllowanceType> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from AllowanceType where TMP_STATUS = '" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "'");
        list = qr.list();
        return list;
    }

    public AllowanceType getByAllowanceTypeID(String id) {
        Session session = sessionFactory.getCurrentSession();
        AllowanceType allowanceType = (AllowanceType) session.get(AllowanceType.class, id);
        return allowanceType;
    }

    public List<AllowanceType> getByMakerID(String id) {
        List<AllowanceType> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp allowanceTypes of made my a single user
        Query qr = session.createQuery("from AllowanceType where CREATED_BY = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public void delete(AllowanceType allowanceTypeTmp) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(allowanceTypeTmp);
    }

    public int allowanceTypePendingEditCount(String id) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from AllowanceType where id = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        return ((Long) qr.uniqueResult()).intValue();
    }

    public List<AllowanceType> listTmpAllowanceTypesForMaker(String makerID) {
        List<AllowanceType> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from AllowanceType where CREATED_BY = '" + makerID + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public List<AllowanceType> listAllowanceTypes() {
        List<AllowanceType> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from AllowanceType where TMP_STATUS = '" + TempStatus.APPROVED + "'");
        list = qr.list();
        return list;
    }
    
    public AllowanceType getByID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        AllowanceType allowanceType = (AllowanceType)session.get(AllowanceType.class, ID);        
        return allowanceType;
    }
    
    public void saveNewAllowanceTypeQuantitative(AllowanceTypeQuantitative a) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(a);
    }
    
    public AllowanceTypeQuantitative getAllowanceTypeQuantitativeByTypeID(String typeID) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from AllowanceTypeQuantitative a where a.allowanceType.ID = '" + typeID + "'");
        List<AllowanceTypeQuantitative> l = qr.list();
        return l.get(0);
    }
    
    public void updateAllowanceTypeQuantitative(AllowanceTypeQuantitative a) {
        Session session = sessionFactory.getCurrentSession();
        session.update(a);
    }
    
    public void deleteAllowanceTypeQuantitative(AllowanceTypeQuantitative atq) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(atq);
    }

    public AllowancePaymentOptions getAllowancePaymentOptionByTypeID(String typeID) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select a.paymentMethod from AllowanceType a where a.ID = '" + typeID + "'");
        List<AllowancePaymentOptions> l = qr.list();
        return l.get(0);
    }
}
