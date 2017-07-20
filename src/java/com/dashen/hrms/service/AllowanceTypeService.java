/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.AllowancePaymentOptions;
import com.dashen.hrms.AllowanceType;
import com.dashen.hrms.AllowanceTypeQuantitative;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.AllowanceTypeDao;
import com.dashen.hrms.dao.EmployeeTmpDao;
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
public class AllowanceTypeService {
    
    @Autowired
    AllowanceTypeDao allowanceTypeDao;


    public void setAllowanceTypeDao(AllowanceTypeDao allowanceTypeDao) {
        this.allowanceTypeDao = allowanceTypeDao;
    }
    
    @Transactional
    public boolean saveNewAllowanceType(AllowanceType allowanceTypeTmp) {
        //new AllowanceType
        AllowanceType newAllowanceType = new AllowanceType();
        copyValuesFromTemp(newAllowanceType, allowanceTypeTmp);
        newAllowanceType.setCreatedBy(allowanceTypeTmp.getCreatedBy());
        newAllowanceType.setCreatedDate(allowanceTypeTmp.getCreatedDate());
        newAllowanceType.setTmpStatus(TempStatus.APPROVED);

        newAllowanceType.setApprovedBy(CurrentUser.getCurrentUser().getUsername());

        allowanceTypeDao.saveNew(newAllowanceType);
        allowanceTypeTmp.setTmpStatus(TempStatus.HISTORY);
        allowanceTypeDao.update(allowanceTypeTmp);
        copyAllowanceTypeDetails(newAllowanceType, allowanceTypeTmp);
        return true;
    }

    @Transactional
    public boolean updateAllowanceType(AllowanceType allowanceTypeTmp) {
        AllowanceType allowanceType = allowanceTypeDao.getByAllowanceTypeID(allowanceTypeTmp.getSourceId());
        copyValuesFromTemp(allowanceType, allowanceTypeTmp);
        allowanceTypeDao.update(allowanceType);
        allowanceTypeTmp.setTmpStatus(TempStatus.HISTORY);
        allowanceTypeDao.update(allowanceTypeTmp);
        copyAllowanceTypeDetails(allowanceType, allowanceTypeTmp);
        return true;
    }

    @Transactional
    public List<AllowanceType> listTmpAllowanceTypes() {
        return allowanceTypeDao.listTmpAllowanceTypes();
    }
    
    public void copyValuesFromTemp(AllowanceType destAllowanceType, AllowanceType sourceAllowanceType)
    {
        destAllowanceType.setID(sourceAllowanceType.getSourceId());
        destAllowanceType.setName(sourceAllowanceType.getName());
        destAllowanceType.setDependsOnEmploymentCenter(sourceAllowanceType.isDependsOnEmploymentCenter());
        destAllowanceType.setDependsOnLocation(sourceAllowanceType.isDependsOnLocation());
        destAllowanceType.setDependsOnPosition(sourceAllowanceType.isDependsOnPosition());
        destAllowanceType.setPaymentMethod(sourceAllowanceType.getPaymentMethod());
    }

    public void copyAllowanceTypeDetails(AllowanceType destAllowanceType, AllowanceType sourceAllowanceType) {
        if (sourceAllowanceType.getPaymentMethod().equals(AllowancePaymentOptions.QUANTITATIVE)) {
            AllowanceTypeQuantitative sourceatq = allowanceTypeDao.getAllowanceTypeQuantitativeByTypeID(sourceAllowanceType.getID());
            AllowanceTypeQuantitative atq = new AllowanceTypeQuantitative();
            atq.setAllowanceType(destAllowanceType);
            atq.setUnitPrice(sourceatq.getUnitPrice());
            atq.setMeasurementUnit(sourceatq.getMeasurementUnit());
            allowanceTypeDao.saveNewAllowanceTypeQuantitative(atq);
        }
    }

    @Transactional
    public boolean saveNewTmpAllowanceType(AllowanceType newTmpAllowanceType, AllowanceTypeQuantitative atq, ActionType acType) {
        newTmpAllowanceType.setActionType(acType);
        newTmpAllowanceType.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
        newTmpAllowanceType.setCreatedDate(new Date());
        newTmpAllowanceType.setTmpStatus(TempStatus.EDITABLE);
        allowanceTypeDao.saveNew(newTmpAllowanceType);

        if (newTmpAllowanceType.getPaymentMethod().equals(AllowancePaymentOptions.QUANTITATIVE)) {
            atq.setAllowanceType(newTmpAllowanceType);
            allowanceTypeDao.saveNewAllowanceTypeQuantitative(atq);
        }
        return true;
    }
    @Transactional
    public boolean updateTmpAllowanceType(AllowanceType tmpAllowanceType, AllowanceTypeQuantitative atq) {
        AllowancePaymentOptions prevPaymentMethod = allowanceTypeDao.getAllowancePaymentOptionByTypeID(tmpAllowanceType.getID());
        System.out.println("Previous payment method " + prevPaymentMethod);
        allowanceTypeDao.update(tmpAllowanceType);

        if (prevPaymentMethod.equals(AllowancePaymentOptions.QUANTITATIVE)) {
            if (prevPaymentMethod.equals(AllowancePaymentOptions.QUANTITATIVE)) {
                // update the record
                atq.setAllowanceType(tmpAllowanceType);
                allowanceTypeDao.updateAllowanceTypeQuantitative(atq);
            } else {
                // delete the record
                AllowanceTypeQuantitative a = allowanceTypeDao.getAllowanceTypeQuantitativeByTypeID(tmpAllowanceType.getID());
                allowanceTypeDao.deleteAllowanceTypeQuantitative(a);
            }
        } else {
            // save the new record
            atq.setAllowanceType(tmpAllowanceType);
            allowanceTypeDao.saveNewAllowanceTypeQuantitative(atq);
        }
        return true;
    }

    @Transactional
    public boolean makeTmpAllowaceTypeEditableByMaker(AllowanceType tmpAllowanceType) {
        allowanceTypeDao.update(tmpAllowanceType);
        return true;
    }
    
    @Transactional
    public boolean delete(AllowanceType allowanceTypeTmp) {
        allowanceTypeDao.delete(allowanceTypeTmp);
        return true;
    }
    
    public boolean checkIfAllowanceTypeHasPendingEdit(String id) {
        return (allowanceTypeDao.allowanceTypePendingEditCount(id) > 0);
    }
    
    @Transactional
    public List<AllowanceType> listTmpAllowanceTypesForMaker(String makerID) {
        return allowanceTypeDao.listTmpAllowanceTypesForMaker(makerID);
    }
    
    @Transactional
    public List<AllowanceType> listAllowanceTypes() {
        return allowanceTypeDao.listAllowanceTypes();
    }
    
    @Transactional
    public AllowanceType getByID(String ID) {
        return allowanceTypeDao.getByID(ID);
    }
    
    @Transactional
    public AllowanceTypeQuantitative getAllowanceTypeQuantitativeByTypeID(String typeID) {
        return allowanceTypeDao.getAllowanceTypeQuantitativeByTypeID(typeID);
    }
    
}