/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.EmployeeSalary;
import com.dashen.hrms.dao.EmployeeSalaryDao;
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
public class EmployeeSalaryService {
    
    @Autowired
    EmployeeSalaryDao employeeSalaryDao;

    public void setEmployeeSalaryDao(EmployeeSalaryDao employeeSalaryDao) {
        this.employeeSalaryDao = employeeSalaryDao;
    }

    @Transactional
    public boolean saveNewEmployeeSalary(EmployeeSalary newEmployeeSalary) {
        newEmployeeSalary.setStatus("Active");
        for (EmployeeSalary es : listAll()) {
            if (es.getStatus().equals("Active")) {
                es.setStatus("Inactive");
                es.setEndDate(new Date());
                employeeSalaryDao.save(es);
            }
            
        }
        employeeSalaryDao.save(newEmployeeSalary);
        return true;
    }

    @Transactional
    public List<EmployeeSalary> listAll() {
        return employeeSalaryDao.listAll();
    }
    

    @Transactional
    public EmployeeSalary getByID(String ID) {
        return employeeSalaryDao.getByID(ID);
    }
    
}