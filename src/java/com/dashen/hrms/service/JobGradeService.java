/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.JobGrade;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.JobGradeDao;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author biniamt
 */

@Service
public class JobGradeService {
    
    @Autowired
    JobGradeDao jobGradeDao;


    public void setJobGradeDao(JobGradeDao jobGradeDao) {
        this.jobGradeDao = jobGradeDao;
    }
    
    @Transactional
    public boolean saveNewJobGrade(JobGrade jobGradeTmp) {
        //new JobGrade
        JobGrade newJobGrade = new JobGrade();
        jobGradeTmp.setGrade(jobGradeTmp.getGrade().toUpperCase());
        copyValuesFromTemp(newJobGrade, jobGradeTmp);
        newJobGrade.setCreatedBy(jobGradeTmp.getCreatedBy());
        newJobGrade.setCreatedDate(jobGradeTmp.getCreatedDate());
        newJobGrade.setTmpStatus(TempStatus.APPROVED);

        newJobGrade.setApprovedBy(CurrentUser.getCurrentUser().getUsername());

        jobGradeDao.saveNew(newJobGrade);
        jobGradeTmp.setTmpStatus(TempStatus.HISTORY);
        jobGradeDao.update(jobGradeTmp);
        return true;
    }

    @Transactional
    public boolean updateJobGrade(JobGrade jobGradeTmp) {
        JobGrade jobGrade = jobGradeDao.getByJobGradeID(jobGradeTmp.getJobGradeId());
        jobGradeTmp.setGrade(jobGradeTmp.getGrade().toUpperCase());
        copyValuesFromTemp(jobGrade, jobGradeTmp);
        jobGrade.setLastModifiedDate(new Date());
        jobGrade.setLastModifiedBy(jobGradeTmp.getCreatedBy());
        jobGradeDao.update(jobGrade);
        jobGradeTmp.setTmpStatus(TempStatus.HISTORY);
        jobGradeDao.update(jobGradeTmp);
        return true;
    }

    @Transactional
    public List<JobGrade> listTmpJobGrades() {
        return jobGradeDao.listTmpJobGrades();
    }
    
    public void copyValuesFromTemp(JobGrade destJobGrade, JobGrade sourceJobGrade)
    {
        destJobGrade.setID(sourceJobGrade.getJobGradeId());
        destJobGrade.setGrade(sourceJobGrade.getGrade());
        destJobGrade.setDescription(sourceJobGrade.getDescription());
        destJobGrade.setBaseSalary(sourceJobGrade.getBaseSalary());
    }
      
    @Transactional
    public boolean saveNewTmpJobGrade(JobGrade newTmpJobGrade, ActionType acType) {
        newTmpJobGrade.setGrade(newTmpJobGrade.getGrade().toUpperCase());
        newTmpJobGrade.setActionType(acType);
        newTmpJobGrade.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
        newTmpJobGrade.setCreatedDate(new Date());
        newTmpJobGrade.setTmpStatus(TempStatus.EDITABLE);
        jobGradeDao.saveNew(newTmpJobGrade);
        return true;
    }
    @Transactional
    public boolean updateTmpJobGrade(JobGrade tmpJobGrade) {
        tmpJobGrade.setGrade(tmpJobGrade.getGrade().toUpperCase());
        jobGradeDao.update(tmpJobGrade);
        return true;
    }
    
    @Transactional
    public boolean delete(JobGrade jobGradeTmp) {
        jobGradeDao.delete(jobGradeTmp);
        return true;
    }
    
    public boolean checkIfJobGradeHasPendingEdit(String id) {
        return (jobGradeDao.jobGradePendingEditCount(id) > 0);
    }
    
    @Transactional
    public List<JobGrade> listTmpJobGradesForMaker(String makerID) {
        return jobGradeDao.listTmpJobGradesForMaker(makerID);
    }
    
    @Transactional
    public List<JobGrade> listJobGrades() {
        return jobGradeDao.listJobGrades();
    }
    
    @Transactional
    public JobGrade getByID(String ID) {
        return jobGradeDao.getByID(ID);
    }

    @Transactional
    public boolean isJobGradeExistsWithGrade(String grade) {
        return jobGradeDao.isJobGradeExistsWithGrade(grade);
    }
    
}