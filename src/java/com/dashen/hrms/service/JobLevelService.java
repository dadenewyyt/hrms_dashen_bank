/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.JobLevel;
import com.dashen.hrms.TempDetail;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.JobLevelDao;
import com.dashen.hrms.dao.TempDetailDao;
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
public class JobLevelService {
    
    @Autowired
    JobLevelDao jobLevelDao;
    
    @Autowired
    TempDetailDao tempDetailDao;


    public void setJobLevelDao(JobLevelDao jobLevelDao) {
        this.jobLevelDao = jobLevelDao;
    }
    
    @Transactional
    public boolean saveNewJobLevel(JobLevel jobLevelTmp) {
        //new JobLevel
        JobLevel newJobLevel = new JobLevel();
        jobLevelTmp.setLevel(jobLevelTmp.getLevel().toUpperCase());
        copyValuesFromTemp(newJobLevel, jobLevelTmp);
        
        TempDetail tempDetail = new TempDetail();
        tempDetail.setCreatedBy(jobLevelTmp.getTempDetail().getCreatedBy());
        tempDetail.setCreatedDate(jobLevelTmp.getTempDetail().getCreatedDate());
        tempDetail.setTempStatus(TempStatus.APPROVED);
        
        tempDetailDao.saveNew(tempDetail);
        newJobLevel.setTempDetail(tempDetail);

        newJobLevel.getTempDetail().setApprovedBy(CurrentUser.getCurrentUser().getUsername());

        jobLevelDao.saveNew(newJobLevel);
        TempDetail t = jobLevelTmp.getTempDetail();
        t.setTempStatus(TempStatus.HISTORY);
        tempDetailDao.update(t);
        jobLevelDao.update(jobLevelTmp);
        return true;
    }

    @Transactional
    public boolean updateJobLevel(JobLevel jobLevelTmp) {
        JobLevel jobLevel = jobLevelDao.getByJobLevelID(jobLevelTmp.getTempDetail().getSourceId());
        jobLevelTmp.setLevel(jobLevelTmp.getLevel().toUpperCase());
        copyValuesFromTemp(jobLevel, jobLevelTmp);
        
        TempDetail tempDetail = jobLevel.getTempDetail();
        tempDetail.setLastModifiedDate(new Date());
        tempDetail.setLastModifiedBy(jobLevelTmp.getTempDetail().getCreatedBy());
        tempDetailDao.update(tempDetail);
        
        jobLevelDao.update(jobLevel);
        
        TempDetail t = jobLevelTmp.getTempDetail();
        t.setTempStatus(TempStatus.HISTORY);
        tempDetailDao.update(t);
        return true;
    }

    @Transactional
    public List<JobLevel> listTmpJobLevels() {
        return jobLevelDao.listTmpJobLevels();
    }
    
    public void copyValuesFromTemp(JobLevel destJobLevel, JobLevel sourceJobLevel)
    {
        destJobLevel.setID(sourceJobLevel.getTempDetail().getSourceId());
        destJobLevel.setLevel(sourceJobLevel.getLevel());
        destJobLevel.setDescription(sourceJobLevel.getDescription());
    }
      
    @Transactional
    public boolean saveNewTmpJobLevel(JobLevel newTmpJobLevel, ActionType acType) {        
        TempDetail tempDetail = newTmpJobLevel.getTempDetail();
        tempDetail.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
        tempDetail.setCreatedDate(new Date());
        tempDetail.setActionType(acType);
        tempDetail.setTempStatus(TempStatus.EDITABLE);
        tempDetailDao.saveNew(tempDetail);
        
        newTmpJobLevel.setTempDetail(tempDetail);
        newTmpJobLevel.setLevel(newTmpJobLevel.getLevel().toUpperCase());
        jobLevelDao.saveNew(newTmpJobLevel);
        return true;
    }
    @Transactional
    public boolean updateTmpJobLevel(JobLevel tmpJobLevel) {
        tmpJobLevel.setLevel(tmpJobLevel.getLevel().toUpperCase());
        jobLevelDao.update(tmpJobLevel);
        return true;
    }
    
    @Transactional
    public boolean delete(JobLevel jobLevelTmp) {
        jobLevelDao.delete(jobLevelTmp);
        return true;
    }
    
    public boolean checkIfJobLevelHasPendingEdit(String id) {
        return (jobLevelDao.jobLevelPendingEditCount(id) > 0);
    }
    
    @Transactional
    public List<JobLevel> listTmpJobLevelsForMaker(String makerID) {
        return jobLevelDao.listTmpJobLevelsForMaker(makerID);
    }
    
    @Transactional
    public List<JobLevel> listJobLevels() {
        return jobLevelDao.listJobLevels();
    }
    
    @Transactional
    public JobLevel getByID(String ID) {
        return jobLevelDao.getByID(ID);
    }

    @Transactional
    public JobLevel getByLevel(String level) {
        return jobLevelDao.getByLevel(level);
    }

    @Transactional
    public boolean isJobLevelExistsWithLevel(String level) {
        return jobLevelDao.isJobLevelExistsWithLevel(level);
    }
    
    @Transactional
    public String getMaxLevel() {
        return jobLevelDao.getMaxLevel();
    }
    
}