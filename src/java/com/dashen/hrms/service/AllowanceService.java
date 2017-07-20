/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.Allowance;
import com.dashen.hrms.AllowancePosition;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.Allowance;
import com.dashen.hrms.AllowanceEmploymentCenter;
import com.dashen.hrms.AllowanceLocation;
import com.dashen.hrms.AllowanceType;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.AllowanceDao;
import com.dashen.hrms.dao.AllowanceDao;
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
public class AllowanceService {
    
    @Autowired
    AllowanceDao allowanceDao;
    
    @Autowired
    AllowanceTypeService allowanceTypeService;


    public void setAllowanceDao(AllowanceDao allowanceDao) {
        this.allowanceDao = allowanceDao;
    }
    
    @Transactional
    public boolean saveNewAllowance(Allowance allowanceTmp) {
        //new Allowance
        Allowance newAllowance = new Allowance();
        copyValuesFromTemp(newAllowance, allowanceTmp);
        newAllowance.setCreatedBy(allowanceTmp.getCreatedBy());
        newAllowance.setCreatedDate(allowanceTmp.getCreatedDate());
        newAllowance.setTmpStatus(TempStatus.APPROVED);

        newAllowance.setApprovedBy(CurrentUser.getCurrentUser().getUsername());

        allowanceDao.saveNew(newAllowance);
        allowanceTmp.setTmpStatus(TempStatus.HISTORY);
        allowanceDao.update(allowanceTmp);
        copyAllowanceDetails(newAllowance, allowanceTmp);
        return true;
    }

    @Transactional
    public boolean updateAllowance(Allowance allowanceTmp) {
        Allowance allowance = allowanceDao.getByAllowanceID(allowanceTmp.getSourceId());
        copyValuesFromTemp(allowance, allowanceTmp);
        allowanceDao.update(allowance);
        allowanceTmp.setTmpStatus(TempStatus.HISTORY);
        allowanceDao.update(allowanceTmp);
        copyAllowanceDetails(allowance, allowanceTmp);
        return true;
    }

    @Transactional
    public List<Allowance> listTmpAllowances() {
        return allowanceDao.listTmpAllowances();
    }
    
    public void copyValuesFromTemp(Allowance destAllowance, Allowance sourceAllowance)
    {
        destAllowance.setID(sourceAllowance.getSourceId());
        destAllowance.setAllowanceType(sourceAllowance.getAllowanceType());
        destAllowance.setValue(sourceAllowance.getValue());
        destAllowance.setValue(sourceAllowance.getValue());
    }

    public void copyAllowanceDetails(Allowance destAllowance, Allowance sourceAllowance) {
        if (sourceAllowance.getAllowanceType().isDependsOnPosition()) {
            AllowancePosition sourceAllowancePosition = allowanceDao.getAllowancePositionByAllowanceID(sourceAllowance.getID());
            AllowancePosition ap = new AllowancePosition();
            ap.setAllowance(destAllowance);
            ap.setPosition(sourceAllowancePosition.getPosition());
            allowanceDao.saveNewAllowancePosition(ap);
        }

        if (sourceAllowance.getAllowanceType().isDependsOnLocation()) {
            AllowanceLocation sourceAllowanceLocation = allowanceDao.getAllowanceLocationByAllowanceID(sourceAllowance.getID());
            AllowanceLocation al = new AllowanceLocation();
            al.setAllowance(destAllowance);
            al.setCity(sourceAllowanceLocation.getCity());
            allowanceDao.saveNewAllowanceLocation(al);
        }

        if (sourceAllowance.getAllowanceType().isDependsOnEmploymentCenter()) {
            AllowanceEmploymentCenter sourceAllowanceEmploymentCenter = allowanceDao.getAllowanceEmploymentCenterByAllowanceID(sourceAllowance.getID());
            AllowanceEmploymentCenter aec = new AllowanceEmploymentCenter();
            aec.setAllowance(destAllowance);
            aec.setCity(sourceAllowanceEmploymentCenter.getCity());
            allowanceDao.saveNewAllowanceEmploymentCenter(aec);
        }
    }
      
    @Transactional
    public boolean saveNewTmpAllowance(Allowance newTmpAllowance, AllowancePosition currentAllowancePosition, AllowanceLocation currentAllowanceLocation, AllowanceEmploymentCenter currentAllowanceEmploymentCenter, ActionType acType) {
        newTmpAllowance.setActionType(acType);
        newTmpAllowance.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
        newTmpAllowance.setCreatedDate(new Date());
        newTmpAllowance.setTmpStatus(TempStatus.EDITABLE);
        allowanceDao.saveNew(newTmpAllowance);
        
        AllowanceType newAllowanceType  = allowanceTypeService.getByID(newTmpAllowance.getAllowanceType().getID());
        
        
        if (newAllowanceType.isDependsOnPosition()) {
            currentAllowancePosition.setAllowance(newTmpAllowance);
            allowanceDao.saveNewAllowancePosition(currentAllowancePosition);
        }

        if (newAllowanceType.isDependsOnLocation()) {
            currentAllowanceLocation.setAllowance(newTmpAllowance);
            allowanceDao.saveNewAllowanceLocation(currentAllowanceLocation);
        }

        if (newAllowanceType.isDependsOnEmploymentCenter()) {
            currentAllowanceEmploymentCenter.setAllowance(newTmpAllowance);
            allowanceDao.saveNewAllowanceEmploymentCenter(currentAllowanceEmploymentCenter);
        }
        return true;
    }

    @Transactional
    public boolean updateTmpAllowance(Allowance tmpAllowance, AllowancePosition currentAllowancePosition, AllowanceLocation currentAllowanceLocation, AllowanceEmploymentCenter currentAllowanceEmploymentCenter) {
        AllowanceType prevAllowanceType = getAllowanceTypeByAllowanceID(tmpAllowance.getID());
        AllowanceType tmpAllowanceType = allowanceTypeService.getByID(tmpAllowance.getAllowanceType().getID());
        allowanceDao.update(tmpAllowance);

        if (prevAllowanceType.isDependsOnPosition()) {
            if (tmpAllowanceType.isDependsOnPosition()) {
                // update the record
                currentAllowancePosition.setAllowance(tmpAllowance);
                allowanceDao.updateAllowancePosition(currentAllowancePosition);
            } else {
                // delete the existing record
                AllowancePosition ap = allowanceDao.getAllowancePositionByAllowanceID(tmpAllowance.getID());
                allowanceDao.deleteAllowancePosition(ap);
            }
        } else if (tmpAllowanceType.isDependsOnPosition()) {
            currentAllowancePosition.setAllowance(tmpAllowance);
            allowanceDao.saveNewAllowancePosition(currentAllowancePosition);
        }


        if (prevAllowanceType.isDependsOnLocation()) {
            if (tmpAllowanceType.isDependsOnLocation()) {
                // update the record
                currentAllowanceLocation.setAllowance(tmpAllowance);
                allowanceDao.updateAllowanceLocation(currentAllowanceLocation);
            } else {
                // delete the existing record
                AllowanceLocation al = allowanceDao.getAllowanceLocationByAllowanceID(tmpAllowance.getID());
                allowanceDao.deleteAllowanceLocation(al);
            }
        } else if (tmpAllowanceType.isDependsOnLocation()) {
            currentAllowanceLocation.setAllowance(tmpAllowance);
            allowanceDao.saveNewAllowanceLocation(currentAllowanceLocation);
        }

        if (prevAllowanceType.isDependsOnEmploymentCenter()) {
            if (tmpAllowanceType.isDependsOnEmploymentCenter()) {
                // update the record
                currentAllowanceEmploymentCenter.setAllowance(tmpAllowance);
                allowanceDao.updateAllowanceEmploymentCenter(currentAllowanceEmploymentCenter);

            } else {
                // delete the record
                AllowanceEmploymentCenter aec = allowanceDao.getAllowanceEmploymentCenterByAllowanceID(tmpAllowance.getID());
                allowanceDao.deleteAllowanceEmploymentCenter(aec);
            }
        } else if (tmpAllowanceType.isDependsOnEmploymentCenter()) {
            currentAllowanceEmploymentCenter.setAllowance(tmpAllowance);
            allowanceDao.saveNewAllowanceEmploymentCenter(currentAllowanceEmploymentCenter);
        }

        return true;
    }

    @Transactional
    public boolean makeTmpAllowanceEditableByMaker(Allowance tmpAllowance) {
        allowanceDao.update(tmpAllowance);
        return true;
    }
    
    @Transactional
    public boolean delete(Allowance allowanceTmp) {
        allowanceDao.delete(allowanceTmp);
        return true;
    }
    
    public boolean checkIfAllowanceHasPendingEdit(String id) {
        return (allowanceDao.allowancePendingEditCount(id) > 0);
    }
    
    @Transactional
    public List<Allowance> listTmpAllowancesForMaker(String makerID) {
        return allowanceDao.listTmpAllowancesForMaker(makerID);
    }
    
    @Transactional
    public List<Allowance> listAllowances() {
        return allowanceDao.listAllowances();
    }
    
    @Transactional
    public Allowance getByID(String ID) {
        return allowanceDao.getByID(ID);
    }
    
    public AllowancePosition getAllowancePositionByAllowanceID(String ID) {
        return allowanceDao.getAllowancePositionByAllowanceID(ID);
    }
    
    public AllowanceLocation getAllowanceLocationByAllowanceID(String ID) {
        return allowanceDao.getAllowanceLocationByAllowanceID(ID);
    }
    
    public AllowanceEmploymentCenter getAllowanceEmploymentCenterByAllowanceID(String ID) {
        return allowanceDao.getAllowanceEmploymentCenterByAllowanceID(ID);
    }
    
    public AllowanceType getAllowanceTypeByAllowanceID(String ID) {
        return allowanceDao.getAllowanceTypeByAllowanceID(ID);
    }
    
}