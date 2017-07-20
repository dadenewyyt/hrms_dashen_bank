/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.SalaryScale;
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
public class SalaryScaleDao {
    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(SalaryScale s) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(s);
    }

    public void update(SalaryScale s) {
        Session session = sessionFactory.getCurrentSession();
        session.update(s);
    }

    public List<SalaryScale> listTmpSalaryScales() {
        List<SalaryScale> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from SalaryScale where TMP_STATUS = '" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "'");
        list = qr.list();
        return list;
    }

    public SalaryScale getBySalaryScaleID(String id) {
        Session session = sessionFactory.getCurrentSession();
        SalaryScale salaryScale = (SalaryScale) session.get(SalaryScale.class, id);
        return salaryScale;
    }

    public List<SalaryScale> getByMakerID(String id) {
        List<SalaryScale> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp salaryScales of made my a single user
        Query qr = session.createQuery("from SalaryScale where CREATED_BY = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public void delete(SalaryScale salaryScaleTmp) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(salaryScaleTmp);
    }

    public int salaryScalePendingEditCount(String id) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from SalaryScale where id = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        return ((Long) qr.uniqueResult()).intValue();
    }

    public List<SalaryScale> listTmpSalaryScalesForMaker(String makerID) {
        List<SalaryScale> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from SalaryScale where CREATED_BY = '" + makerID + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public List<SalaryScale> listSalaryScales() {
        List<SalaryScale> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from SalaryScale s where s.tmpStatus = '" + TempStatus.APPROVED + "' order by s.grade.grade, s.level.level");
        list = qr.list();
        return list;
    }
    
    public SalaryScale getByID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        SalaryScale salaryScale = (SalaryScale)session.get(SalaryScale.class, ID);        
        return salaryScale;
    }
    
    public List<SalaryScale> listSalaryScalesWithGradeID(String ID) {
        List<SalaryScale> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from SalaryScale s where s.grade.ID='" + ID + "' AND s.tmpStatus = '" + TempStatus.APPROVED + "'");
        list = qr.list();
        return list;
    }

    public SalaryScale getByGradeAndLevel(String grade, String level) {
        List<SalaryScale> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from SalaryScale s where s.grade.grade='" + grade + "' and s.level.level = '" + level + "' and s.tmpStatus = '" + TempStatus.APPROVED + "'");
        list = qr.list();
        if (list.size() > 0)
            return list.get(0);
        return null;   
    }
    public boolean isSalaryScaleExistsWithGradeAndLevel(String grade, String level) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from SalaryScale s where s.grade.grade='" + grade + "' and s.level.level = '" + level + "' and s.tmpStatus = '" + TempStatus.APPROVED + "'");
        return ((Long) qr.uniqueResult()).intValue() > 0;
    }
}
