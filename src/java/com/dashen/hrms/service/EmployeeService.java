/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.Employee;
import com.dashen.hrms.dao.EmployeeDao;
import com.dashen.hrms.EmployeeTmp;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.SortOrder;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.EmployeeTmpDao;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MulugetaK
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeDao empDao;

    @Autowired
    private EmployeeTmpDao empTmpDao;

    public void setEmpDao(EmployeeDao empDao) {
        this.empDao = empDao;
    }

    public void setEmpTmpDao(EmployeeTmpDao empTmpDao) {
        this.empTmpDao = empTmpDao;
    }

    @Transactional
    public boolean saveNewEmployee(EmployeeTmp newTmpEmp) {
        //new employee
        Employee emp = new Employee();
        copyValuesFromTemp(emp, newTmpEmp);
        emp.setCreatedBy(newTmpEmp.getMakerID());
        emp.setCreatedDate(newTmpEmp.getMakerDate());
        //get current user (approver)
        MyUser myUsr = CurrentUser.getCurrentUser();
        emp.setApprovedBy(myUsr.getUsername());
        Date approvedDate = new Date();
        emp.setApprovedDate(approvedDate);
        empDao.saveNew(emp);

        newTmpEmp.setTmpStatus(TempStatus.HISTORY);
        newTmpEmp.setCheckerID(myUsr.getUsername());
        newTmpEmp.setCheckerDate(approvedDate);

        empTmpDao.Update(newTmpEmp);
        return true;
    }

    @Transactional
    public boolean updateEmployee(EmployeeTmp empT) {
        Employee emp = getByEmployeeSerialID(empT.getEmployeeSerialID());
        copyValuesFromTemp(emp, empT);
        Date approvedDate = new Date();
        emp.setLastModifiedDate(approvedDate);
        emp.setLastModifiedBy(empT.getMakerID());

        empDao.Update(emp);
        //get current user (approver)
        MyUser myUsr = CurrentUser.getCurrentUser();
        empT.setTmpStatus(TempStatus.HISTORY);
        empT.setCheckerID(myUsr.getUsername());
        empT.setCheckerDate(approvedDate);

        empTmpDao.Update(empT);
        return true;
    }

    @Transactional
    public List<Employee> listAll() {
        return empDao.listAll();
    }
    
    @Transactional
    public List<Employee> listByUserOragnizationStructure() {
        return empDao.listAll();
    }

    public void copyValuesFromTemp(Employee destEmp, EmployeeTmp sourceEmpTemp) {
        destEmp.setEmployeeSerialID(sourceEmpTemp.getEmployeeSerialID());
        destEmp.setFirstName(sourceEmpTemp.getFirstName());
        destEmp.setMiddleName(sourceEmpTemp.getMiddleName());
        destEmp.setLastName(sourceEmpTemp.getLastName());
        destEmp.setFileIndexNumber(sourceEmpTemp.getFileIndexNumber());
        destEmp.setEmployeeID(sourceEmpTemp.getEmployeeID());
        destEmp.setGender(sourceEmpTemp.getGender());
        destEmp.setDateOfBirth(sourceEmpTemp.getDateOfBirth());
        destEmp.setDateOfEmployement(sourceEmpTemp.getDateOfEmployement());
        destEmp.setJobClasificationID(sourceEmpTemp.getJobClasificationID());
        destEmp.setEmergecyContact(sourceEmpTemp.getEmergecyContact());
        destEmp.setPhotoFileName(sourceEmpTemp.getPhotoFileName());
        destEmp.setStatus(sourceEmpTemp.getStatus());
        destEmp.setDatePermanent(sourceEmpTemp.getDatePermanent());
        destEmp.setMaritalStatus(sourceEmpTemp.getMaritalStatus());
        destEmp.setMaritalStatusFileName(sourceEmpTemp.getMaritalStatusFileName());

    }

    @Transactional
    public Employee getByEmployeeSerialID(String employeeSerialID) {
        return empDao.getByEmployeeSerialID(employeeSerialID);
    }

    @Transactional
    public List<Employee> searchEmployee(String employeeId, String firstName, String middleName, String lastName, String fileIndexNo) {
        return empDao.searchEmployee(employeeId, firstName, middleName, lastName, fileIndexNo);
    }

    @Transactional
    public List<Employee> findEmployees(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return empDao.findEmployees(first, pageSize, sortField, sortOrder, filters);
    }

    @Transactional
    public Long countEmployees(Map<String, Object> filters) {
        return empDao.countEmployees(filters);
    }

}
