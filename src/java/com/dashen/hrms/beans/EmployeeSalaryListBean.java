/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.EmployeeSalary;
import com.dashen.hrms.service.EmployeeSalaryService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 *
 * @author biniamt
 */

@Component
@RequestScope
public class EmployeeSalaryListBean {

    private List<EmployeeSalary> employeeSalaryList;
    List<EmployeeSalary> selectedEmployeeSalaries;

    @Autowired
    private EmployeeSalaryService employeeSalaryService;

    @PostConstruct
    public void init() {
        employeeSalaryList = employeeSalaryService.listAll();
    }

    public List<EmployeeSalary> getEmployeeSalaryList() {
        return employeeSalaryList;
    }

    public void setEmployeeSalaryList(List<EmployeeSalary> employeeSalaryList) {
        this.employeeSalaryList = employeeSalaryList;
    }

    public List<EmployeeSalary> getSelectedEmployeeSalaries() {
        return selectedEmployeeSalaries;
    }

    public void setSelectedEmployeeSalaries(List<EmployeeSalary> selectedEmployeeSalaries) {
        this.selectedEmployeeSalaries = selectedEmployeeSalaries;
    }
}
