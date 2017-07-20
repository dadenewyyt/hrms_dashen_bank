/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.EmployeeTmp;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.EmployeeTmpDao;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MulugetaK
 */
@Service
public class EmployeeTmpService {

    @Autowired
    EmployeeTmpDao empTmpDao;

    @Transactional
    public boolean saveNewTmpEmployee(EmployeeTmp newTmpEmp, ActionType acType) {
        newTmpEmp.setFirstName(newTmpEmp.getFirstName().toUpperCase().trim());
        newTmpEmp.setMiddleName(newTmpEmp.getMiddleName().toUpperCase().trim());
        newTmpEmp.setLastName(newTmpEmp.getLastName().toUpperCase().trim());
        newTmpEmp.setEmployeeID(newTmpEmp.getEmployeeID().toUpperCase());
        newTmpEmp.setActionType(acType);
        MyUser myUsr = CurrentUser.getCurrentUser();
        newTmpEmp.setMakerID(myUsr.getUsername());
        newTmpEmp.setMakerDate(new Date());
        newTmpEmp.setTmpStatus(TempStatus.EDITABLE);
        empTmpDao.saveNew(newTmpEmp);
        return true;
    }
    
    @Transactional
    public boolean updateTmpEmployee(EmployeeTmp tmpEmp) {        
        empTmpDao.Update(tmpEmp);
        return true;
    }

    @Transactional
    public List<EmployeeTmp> listPendingTmpEmployeesForMaker(String makerID) {
        return empTmpDao.listPendingTmpEmployeesForMaker(makerID);
    }

    @Transactional
    public List<EmployeeTmp> listPendingTmpEmployeesForChecker(String checkerID) {
        return empTmpDao.listPendingTmpEmployeesForChecker(checkerID);
    }
    
    @Transactional
    public List<EmployeeTmp> listAll() {
        return empTmpDao.listAll();
    }

    @Transactional
    public boolean delete(EmployeeTmp empT) {
        empTmpDao.delete(empT);
        return true;
    }

    public boolean checkIfEmployeeHasPendingEdit(String employeeSerialID) {
        return (empTmpDao.employeePendingEditCount(employeeSerialID) > 0);
    }
}
