/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.Employee;
import com.dashen.hrms.EmployeePosition;
import com.dashen.hrms.EmployeePositionOrganizationalStructure;
import com.dashen.hrms.EmployeePositionSalaryScale;
import com.dashen.hrms.Position;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.EmployeePositionDao;
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
public class EmployeePositionService {
    
    @Autowired
    EmployeePositionDao empPositionDao;
    
    @Autowired
    EmployeePositionService employeePositionService;

    public void setEmployeePositionDao(EmployeePositionDao empPositionDao) {
        this.empPositionDao = empPositionDao;
    }
    
    @Transactional
    public boolean saveNewEmployeePosition(EmployeePosition empPositionTmp) {
        //new Position
        EmployeePosition newEmployeePosition = new EmployeePosition();
        copyValuesFromTemp(newEmployeePosition, empPositionTmp);
        newEmployeePosition.setCreatedBy(empPositionTmp.getCreatedBy());
        newEmployeePosition.setCreatedDate(empPositionTmp.getCreatedDate());
        newEmployeePosition.setTmpStatus(TempStatus.APPROVED);

        newEmployeePosition.setApprovedBy(CurrentUser.getCurrentUser().getUsername());

        empPositionDao.saveNew(newEmployeePosition);
        empPositionTmp.setTmpStatus(TempStatus.HISTORY);
        empPositionDao.update(empPositionTmp);
        copyEmployeePositionDetails(newEmployeePosition, empPositionTmp);
        return true;
    }

    @Transactional
    public boolean updateEmployeePosition(EmployeePosition empPositionTmp) {
        EmployeePosition employeePosition = empPositionDao.getByID(empPositionTmp.getSourceId());
        copyValuesFromTemp(employeePosition, empPositionTmp);
        empPositionDao.update(employeePosition);
        empPositionTmp.setTmpStatus(TempStatus.HISTORY);
        empPositionDao.update(empPositionTmp);
        copyEmployeePositionDetails(employeePosition, empPositionTmp);
        return true;
    }

    @Transactional
    public List<EmployeePosition> listTmpEmployeePositions() {
        return empPositionDao.listTmpEmployeePositions();
    }
    
    public void copyValuesFromTemp(EmployeePosition destEmployeePosition, EmployeePosition sourceEmployeePosition)
    {
        destEmployeePosition.setID(sourceEmployeePosition.getSourceId());
        destEmployeePosition.setEmployee(sourceEmployeePosition.getEmployee());
        destEmployeePosition.setPosition(sourceEmployeePosition.getPosition());
    }

    public void copyEmployeePositionDetails(EmployeePosition destEmpPosition, EmployeePosition sourceEmpPosition) {

        if (isEmpPositionHasOrgStruct(sourceEmpPosition)) {

            EmployeePositionOrganizationalStructure prevEmpPositionOrgStruct = empPositionDao.getEmpPositionOrgStructByID(sourceEmpPosition.getID());

            EmployeePositionOrganizationalStructure e = new EmployeePositionOrganizationalStructure();
            e.setEmployeePosition(destEmpPosition);
            e.setOrganizationalStructure(prevEmpPositionOrgStruct.getOrganizationalStructure());
            empPositionDao.saveNewEmpPositionOrgStruct(e);
        }

        if (isEmpPosHasSalaryScale(sourceEmpPosition)) {
            EmployeePositionSalaryScale prevEmpPosSalaryScale = getEmpPosSalaryScaleByID(sourceEmpPosition.getID());
            EmployeePositionSalaryScale p = new EmployeePositionSalaryScale();
            p.setEmployeePosition(destEmpPosition);
            p.setSalaryScale(prevEmpPosSalaryScale.getSalaryScale());
            empPositionDao.saveNewEmpPosSalaryScale(p);
        }
    }
      
    @Transactional
    public boolean saveNewTmpEmployeePosition(EmployeePosition newTmpEmployeePosition, ActionType acType,
                        EmployeePositionOrganizationalStructure currentEmpPositionOrgStruct,
                        EmployeePositionSalaryScale currentEmpPosSalaryScale, boolean positionHasOrgStruct) {
        newTmpEmployeePosition.setActionType(acType);
        newTmpEmployeePosition.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
        newTmpEmployeePosition.setCreatedDate(new Date());
        newTmpEmployeePosition.setTmpStatus(TempStatus.EDITABLE);

        empPositionDao.saveNew(newTmpEmployeePosition);

        if (!positionHasOrgStruct && currentEmpPositionOrgStruct.getOrganizationalStructure().getId() != null) {
            currentEmpPositionOrgStruct.setEmployeePosition(newTmpEmployeePosition);
            empPositionDao.saveNewEmpPositionOrgStruct(currentEmpPositionOrgStruct);
        }

        if (currentEmpPosSalaryScale.getSalaryScale().getID() != null) {
            currentEmpPosSalaryScale.setEmployeePosition(newTmpEmployeePosition);
            empPositionDao.saveNewEmpPosSalaryScale(currentEmpPosSalaryScale);
        }
        
        return true;
    }

    @Transactional
    public boolean updateTmpEmployeePosition(EmployeePosition tmpEmployeePosition,
                        EmployeePositionOrganizationalStructure currentEmpPositionOrgStruct,
                        EmployeePositionSalaryScale currentEmpPosSalaryScale, boolean positionHasOrgStruct) {

        String prevEmployeePositionID = tmpEmployeePosition.getID();


        empPositionDao.update(tmpEmployeePosition);

        if (isEmpPositionHasOrgStruct(tmpEmployeePosition)) {
            if (!positionHasOrgStruct && currentEmpPositionOrgStruct.getOrganizationalStructure().getId() != null) {
                empPositionDao.updateEmpPositionOrgStruct(currentEmpPositionOrgStruct);
            } else {
                EmployeePositionOrganizationalStructure e = getEmpPositionOrgStructByID(prevEmployeePositionID);
                empPositionDao.deleteEmpPositionOrgStruct(e);
            }
        } else {
            if (!positionHasOrgStruct && currentEmpPositionOrgStruct.getOrganizationalStructure().getId() != null) {
                currentEmpPositionOrgStruct.setEmployeePosition(tmpEmployeePosition);
                empPositionDao.saveNewEmpPositionOrgStruct(currentEmpPositionOrgStruct);
            }

        }

        if (isEmpPosHasSalaryScale(tmpEmployeePosition)){
            if (currentEmpPosSalaryScale.getSalaryScale().getID() != null && !currentEmpPosSalaryScale.getSalaryScale().getID().isEmpty()) {
                currentEmpPosSalaryScale.setEmployeePosition(tmpEmployeePosition);
                empPositionDao.updateEmpPosSalaryScale(currentEmpPosSalaryScale);
            } else {
                EmployeePositionSalaryScale p = getEmpPosSalaryScaleByID(prevEmployeePositionID);
                empPositionDao.deleteEmpPosSalaryScale(p);
            }
        } else {
            if (currentEmpPosSalaryScale.getSalaryScale().getID() != null && !currentEmpPosSalaryScale.getSalaryScale().getID().isEmpty()) {
                currentEmpPosSalaryScale.setEmployeePosition(tmpEmployeePosition);
                empPositionDao.saveNewEmpPosSalaryScale(currentEmpPosSalaryScale);
            }
        }

        return true;
    }

    @Transactional
    public boolean makeTmpEmployeePositionEditableByMaker(EmployeePosition tmpEmployeePosition) {
        empPositionDao.update(tmpEmployeePosition);
        return true;
    }
    
    @Transactional
    public boolean delete(EmployeePosition empPositionTmp) {
        
        System.out.println("Before deleting employe position id: " + empPositionTmp.getID());

        empPositionDao.delete(empPositionTmp);

        if (isEmpPositionHasOrgStruct(empPositionTmp)) {
            EmployeePositionOrganizationalStructure o = empPositionDao.getEmpPositionOrgStructByID(empPositionTmp.getID());
            System.out.println("Employee position org struct id, name." + o.getOrganizationalStructure().getId() + " , " + o.getOrganizationalStructure().getName());
            empPositionDao.deleteEmpPositionOrgStruct(o);
            System.out.println("Employee position org struct successfully deleted.");
        }

        if (isEmpPosHasSalaryScale(empPositionTmp)) {
            EmployeePositionSalaryScale s = empPositionDao.getEmpPosSalaryScaleByID(empPositionTmp.getID());
            System.out.println("Employee position salary scale id, level." + s.getSalaryScale().getID() + " , " + s.getSalaryScale().getLevel().getLevel());
            empPositionDao.deleteEmpPosSalaryScale(s);
            System.out.println("Employee position salary scale successfully deleted.");
        }
        return true;
    }
    
    public boolean checkIfEmployeePositionHasPendingEdit(String id) {
        return (empPositionDao.employeePositionPendingEditCount(id) > 0);
    }
    
    @Transactional
    public List<EmployeePosition> listTmpEmployeePositionsForMaker(String makerID) {
        return empPositionDao.listTmpEmployeePositionsForMaker(makerID);
    }
    
    @Transactional
    public List<EmployeePosition> listEmployeePositions() {
        return empPositionDao.listEmployeePositions();
    }
    
    @Transactional
    public EmployeePosition getByID(String ID) {
        return empPositionDao.getByID(ID);
    }
    
    @Transactional
    public List<EmployeePositionSalaryScale> listEmployeePositionSalaryScales() {
        return empPositionDao.listEmployeePositionSalaryScales();
    }
    
    
    // public void saveNewEmpPositionOrgStruct(EmployeePositionOrganizationalStructure empOrgStruct) {
    //     empPositionDao.saveNewEmpPositionOrgStruct(empOrgStruct);
    // }
    public void saveNewEmpPosSalaryScale(EmployeePositionSalaryScale empPosSalaryScale) {
        empPositionDao.saveNewEmpPosSalaryScale(empPosSalaryScale);
    }

    // public void updateEmployeeOrgStruct(EmployeePositionOrganizationalStructure empOrgStruct) {
    //     empPositionDao.updateEmployeeOrgStruct(empOrgStruct);
    // }
     public void updateEmpPosSalaryScale(EmployeePositionSalaryScale empPosSalaryScale) {
         empPositionDao.updateEmpPosSalaryScale(empPosSalaryScale);
     }

    public EmployeePositionOrganizationalStructure getEmpPositionOrgStructByID(String ID) {
        return empPositionDao.getEmpPositionOrgStructByID(ID);
    }

    public EmployeePositionSalaryScale getEmpPosSalaryScaleByID(String ID) {
        return empPositionDao.getEmpPosSalaryScaleByID(ID);
    }

    // public void deleteEmpPositionOrgStruct(EmployeePositionOrganizationalStructure p) {
    //     empPositionDao.deleteEmpPositionOrgStruct(p);
    // }

    // public void deleteEmpPosSalaryScale(EmployeePositionSalaryScale p) {
    //     empPositionDao.deleteEmpPosSalaryScale(p);
    // }

    public boolean isEmpPosHasSalaryScale(EmployeePosition empPos) {
        return empPositionDao.isEmpPosHasSalaryScale(empPos);
    }

    public boolean isEmpPositionHasOrgStruct(EmployeePosition empPos) {
        return empPositionDao.isEmpPositionHasOrgStruct(empPos);
    }

    public List<Position> filterPositions(String tierId, String orgStructId, String orgStructTypeId) {
        return empPositionDao.filterPositions(tierId, orgStructId, orgStructTypeId);
    }
    
    @Transactional
    public List<Employee> findEmployeesWithTheSameOrgStructByEmpId(String Id) {
        return empPositionDao.findEmployeesWithTheSameOrgStructByEmpId(Id);
    }

}
