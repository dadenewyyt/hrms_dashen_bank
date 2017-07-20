/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.SalaryScale;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.SalaryScaleDao;
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
public class SalaryScaleService {
    
    @Autowired
    SalaryScaleDao salaryScaleDao;


    public void setSalaryScaleDao(SalaryScaleDao salaryScaleDao) {
        this.salaryScaleDao = salaryScaleDao;
    }
    
    @Transactional
    public boolean saveNewSalaryScale(SalaryScale salaryScaleTmp) {
        //new SalaryScale
        SalaryScale newSalaryScale = new SalaryScale();
        copyValuesFromTemp(newSalaryScale, salaryScaleTmp);
        newSalaryScale.setCreatedBy(salaryScaleTmp.getCreatedBy());
        newSalaryScale.setCreatedDate(salaryScaleTmp.getCreatedDate());
        newSalaryScale.setTmpStatus(TempStatus.APPROVED);

        newSalaryScale.setApprovedBy(CurrentUser.getCurrentUser().getUsername());

        salaryScaleDao.saveNew(newSalaryScale);
        salaryScaleTmp.setTmpStatus(TempStatus.HISTORY);
        salaryScaleDao.update(salaryScaleTmp);
        return true;
    }

    @Transactional
    public boolean updateSalaryScale(SalaryScale salaryScaleTmp) {
        SalaryScale salaryScale = salaryScaleDao.getBySalaryScaleID(salaryScaleTmp.getSalaryScaleId());
        copyValuesFromTemp(salaryScale, salaryScaleTmp);
        salaryScale.setLastModifiedDate(new Date());
        salaryScale.setLastModifiedBy(salaryScaleTmp.getCreatedBy());
        salaryScaleDao.update(salaryScale);
        salaryScaleTmp.setTmpStatus(TempStatus.HISTORY);
        salaryScaleDao.update(salaryScaleTmp);
        return true;
    }

    @Transactional
    public List<SalaryScale> listTmpSalaryScales() {
        return salaryScaleDao.listTmpSalaryScales();
    }
    
    public void copyValuesFromTemp(SalaryScale destSalaryScale, SalaryScale sourceSalaryScale)
    {
        destSalaryScale.setID(sourceSalaryScale.getSalaryScaleId());
        destSalaryScale.setGrade(sourceSalaryScale.getGrade());
        destSalaryScale.setLevel(sourceSalaryScale.getLevel());
        destSalaryScale.setStepIncrement(sourceSalaryScale.getStepIncrement());
    }
      
    @Transactional
    public boolean saveNewTmpSalaryScale(SalaryScale newTmpSalaryScale, ActionType acType) {
        newTmpSalaryScale.setActionType(acType);
        newTmpSalaryScale.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
        newTmpSalaryScale.setCreatedDate(new Date());
        newTmpSalaryScale.setTmpStatus(TempStatus.EDITABLE);
        salaryScaleDao.saveNew(newTmpSalaryScale);
        return true;
    }
    @Transactional
    public boolean updateTmpSalaryScale(SalaryScale tmpSalaryScale) {       
        salaryScaleDao.update(tmpSalaryScale);
        return true;
    }
    
    @Transactional
    public boolean delete(SalaryScale salaryScaleTmp) {
        salaryScaleDao.delete(salaryScaleTmp);
        return true;
    }
    
    public boolean checkIfSalaryScaleHasPendingEdit(String id) {
        return (salaryScaleDao.salaryScalePendingEditCount(id) > 0);
    }
    
    @Transactional
    public List<SalaryScale> listTmpSalaryScalesForMaker(String makerID) {
        return salaryScaleDao.listTmpSalaryScalesForMaker(makerID);
    }
    
    @Transactional
    public List<SalaryScale> listSalaryScales() {
        return salaryScaleDao.listSalaryScales();
    }
    
    @Transactional
    public SalaryScale getByID(String ID) {
        return salaryScaleDao.getByID(ID);
    }
    
    @Transactional
    public List<SalaryScale> listSalaryScalesWithGradeID(String ID) {
        return salaryScaleDao.listSalaryScalesWithGradeID(ID);
    }

    @Transactional
    public SalaryScale getByGradeAndLevel(String grade, String level) {
        return salaryScaleDao.getByGradeAndLevel(grade, level);
    }

    @Transactional
    public boolean isSalaryScaleExistsWithGradeAndLevel(String grade, String level) {
        return salaryScaleDao.isSalaryScaleExistsWithGradeAndLevel(grade, level);
    }
    
}