/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.JobGrade;
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
public class JobGradeDao {
    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(JobGrade j) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(j);
    }

    public void update(JobGrade j) {
        Session session = sessionFactory.getCurrentSession();
        session.update(j);
    }

    public List<JobGrade> listTmpJobGrades() {
        List<JobGrade> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from JobGrade where TMP_STATUS = '" + TempStatus.SUBMITTED + "'");
        list = qr.list();
        return list;
    }

    public JobGrade getByJobGradeID(String id) {
        Session session = sessionFactory.getCurrentSession();
        JobGrade jobGrade = (JobGrade) session.get(JobGrade.class, id);
        return jobGrade;
    }

    public List<JobGrade> getByMakerID(String id) {
        List<JobGrade> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp JobGrades of made my a single user
        Query qr = session.createQuery("from JobGrade where CREATED_BY = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public void delete(JobGrade jobGradeTmp) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(jobGradeTmp);
    }

    public int jobGradePendingEditCount(String id) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from JobGrade where id = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        return ((Long) qr.uniqueResult()).intValue();
    }

    public List<JobGrade> listTmpJobGradesForMaker(String makerID) {
        List<JobGrade> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from JobGrade where CREATED_BY = '" + makerID + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public List<JobGrade> listJobGrades() {
        List<JobGrade> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from JobGrade where TMP_STATUS = '" + TempStatus.APPROVED + "' order by grade");
        list = qr.list();
        return list;
    }
    
    public JobGrade getByID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        JobGrade jobGrade = (JobGrade)session.get(JobGrade.class, ID);        
        return jobGrade;
    }

    public JobGrade getByGrade(String grade) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from JobGrade j where j.grade = '"+grade+"' and j.tmpStatus='" + TempStatus.APPROVED + "'");
        List<JobGrade> l = qr.list();
        return l.get(0);
    }


    public boolean isJobGradeExistsWithGrade(String grade) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from JobGrade j where j.grade = '" + grade + "' AND j.tmpStatus='" + TempStatus.APPROVED + "'");
        return ((Long) qr.uniqueResult()).intValue() > 0;
    }
}
