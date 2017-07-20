/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.JobLevel;
import com.dashen.hrms.TempStatus;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
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
public class JobLevelDao {
    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(JobLevel j) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(j);
    }

    public void update(JobLevel j) {
        Session session = sessionFactory.getCurrentSession();
        session.update(j);
    }

    public List<JobLevel> listTmpJobLevels() {
        List<JobLevel> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from JobLevel j where j.tempDetail.tempStatus = '" + TempStatus.SUBMITTED + "'");
        list = qr.list();
        return list;
    }

    public JobLevel getByJobLevelID(String id) {
        Session session = sessionFactory.getCurrentSession();
        JobLevel jobLevel = (JobLevel) session.get(JobLevel.class, id);
        return jobLevel;
    }

    public List<JobLevel> getByMakerID(String id) {
        List<JobLevel> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp JobLevels of made my a single user
        Query qr = session.createQuery("from JobLevel j where j.tempDetail.createdBy = '" + id + "' AND (j.tempDetail.tempStatus='" + TempStatus.EDITABLE + "' or j.tempDetail.tempStatus = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public void delete(JobLevel jobLevelTmp) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(jobLevelTmp);
    }

    public int jobLevelPendingEditCount(String id) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from JobLevel j where j.id = '" + id + "' AND (j.tempDetail.tempStatus='" + TempStatus.EDITABLE + "' or j.tempDetail.tempStatus = '" + TempStatus.SUBMITTED + "')");
        return ((Long) qr.uniqueResult()).intValue();
    }

    public List<JobLevel> listTmpJobLevelsForMaker(String makerID) {
        List<JobLevel> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from JobLevel j where j.tempDetail.createdBy = '" + makerID + "' AND (j.tempDetail.tempStatus='" + TempStatus.EDITABLE + "' or j.tempDetail.tempStatus = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public List<JobLevel> listJobLevels() {
        List<JobLevel> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from JobLevel j where j.tempDetail.tempStatus = '" + TempStatus.APPROVED + "' order by j.level");
        list = qr.list();
        return list;
    }
    
    public JobLevel getByID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        JobLevel jobLevel = (JobLevel)session.get(JobLevel.class, ID);
        return jobLevel;
    }

    public JobLevel getByLevel(String level) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from JobLevel j where j.level = '"+level+"' and j.tempDetail.tempStatus='" + TempStatus.APPROVED + "'");
        List<JobLevel> l = qr.list();
        return l.get(0);
    }

    public boolean isJobLevelExistsWithLevel(String level) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from JobLevel j where j.level = '" + level + "' AND j.tempDetail.tempStatus='" + TempStatus.APPROVED + "'");
        return ((Long) qr.uniqueResult()).intValue() > 0;
    }
    
    public String getMaxLevel() {
        Session session = sessionFactory.getCurrentSession();

        Query qr = session.createQuery("select max(j.level) from JobLevel j where j.tempDetail.tempStatus='" + TempStatus.APPROVED + "'");
        List l = qr.list();
        return l.get(0).toString();
    }
    
}
