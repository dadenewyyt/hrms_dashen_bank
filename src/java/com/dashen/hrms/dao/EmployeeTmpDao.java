/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.EmployeeTmp;
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
 * @author MulugetaK
 */
@Repository
@Transactional
public class EmployeeTmpDao {

    @Autowired
    SessionFactory sessionFactory;

    public EmployeeTmpDao() {
        //sessonFactory = new Configuration().configure().buildSessionFactory();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(EmployeeTmp empTmp) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(empTmp);
    }

    public void Update(EmployeeTmp empTmp) {
        Session session = sessionFactory.getCurrentSession();
        session.update(empTmp);
    }

    public List<EmployeeTmp> listTmpEmployeesForMaker(String makerID) {
        List<EmployeeTmp> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from EmployeeTmp where MAKER_ID = '" + makerID + "'");
        list = qr.list();
        return list;
    }
    
    public List<EmployeeTmp> listPendingTmpEmployeesForMaker(String makerID)
    {
         List<EmployeeTmp> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from EmployeeTmp where MAKER_ID = '" + makerID + "' and ( TMP_STATUS = '" + TempStatus.EDITABLE + "' or TMP_STATUS ='" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }
    
    public List<EmployeeTmp> listPendingTmpEmployeesForChecker(String checkerID)
    {
         List<EmployeeTmp> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from EmployeeTmp where TMP_STATUS ='" + TempStatus.SUBMITTED + "'");
        list = qr.list();
        return list;
    }
    

    public List<EmployeeTmp> listAll() {
        List<EmployeeTmp> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from EmployeeTmp");
        list = qr.list();
        return list;
    }

    public void delete(EmployeeTmp empT) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(empT);
    }

    public int employeePendingEditCount(String employeeSerialID) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from EmployeeTmp where employeeSerialID = '" + employeeSerialID + "'");
        return ((Long) qr.uniqueResult()).intValue();
    }
}
