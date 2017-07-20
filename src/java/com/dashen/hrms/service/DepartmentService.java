/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.Department;
import com.dashen.hrms.dao.DepartmentDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author biniamt
 */


@Service
public class DepartmentService {
    @Autowired
    DepartmentDao departmentDao;

    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Transactional
    public boolean saveNewDepartment(Department newDepartment) {
        departmentDao.save(newDepartment);
        return true;
    }

    @Transactional
    public boolean updateDepartment(Department department) {        
        departmentDao.save(department);
        return true;
    }

    @Transactional
    public List<Department> listAll() {
        return departmentDao.listAll();
    }
    
    @Transactional
    public Department getByID(String ID) {
        return departmentDao.getByID(ID);
    }

    @Transactional
    public boolean delete(Department department) {
        departmentDao.delete(department);
        return true;
    }
}
