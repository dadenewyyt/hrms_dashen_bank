/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import com.dashen.hrms.service.EmployeeTmpService;
import com.dashen.hrms.service.JobClassificationService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 *
 * @author mulugetak
 */
@Component
@RequestScope
public class NewEmployeeBean {

    private EmployeeTmp newTmpEmp;

    @Autowired
    private EmployeeTmpService empTmpService;
    @Autowired
    private JobClassificationService jbClService;

    private List<SelectItem> jobClassificationList;

    public EmployeeTmp getNewTmpEmp() {
        return newTmpEmp;
    }

    public void setNewTmpEmp(EmployeeTmp newTmpEmp) {
        this.newTmpEmp = newTmpEmp;
    }

    public List<SelectItem> getJobClassificationList() {
        return jobClassificationList;
    }

    public void setJobClassificationList(List<SelectItem> jobClassificationList) {
        this.jobClassificationList = jobClassificationList;
    }

    @PostConstruct
    public void init() {
        newTmpEmp = new EmployeeTmp();
        jobClassificationList = new ArrayList<>();
        List<JobClassification> jbClList = jbClService.listAll();
        for (JobClassification jbCl : jbClList) {            
            jobClassificationList.add(new SelectItem(jbCl.getId(), jbCl.getDescription()));
        }

    }

    public void btnSaveNewEmployee_Handler() {

        empTmpService.saveNewTmpEmployee(newTmpEmp, ActionType.CREATE);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Employee", "New Employee is added to database.");
        FacesContext.getCurrentInstance().addMessage(null, msg);

//        if (Employee.saveNewTmpEmployee(newEmp)) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Employee", "New Employee is added to database.");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        } else {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "New Employee", "Error while saving new Employee. The new employee is not saved to database.");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//            System.out.println("Filed to save New Employee.");
//        }
    }

}
