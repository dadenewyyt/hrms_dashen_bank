/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.Employee;
import com.dashen.hrms.service.EmployeeService;
import com.dashen.hrms.SpringViewScope;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author MulugetaK
 */
@Component
@SpringViewScope
public class EmployeeDetailsBean implements Serializable {

    private String employeeSerialID;

    @Autowired
    EmployeeService empService;

    Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @PostConstruct
    public void init() {
        employeeSerialID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (employeeSerialID != null && !employeeSerialID.isEmpty()) {
            employee = empService.getByEmployeeSerialID(employeeSerialID);
        } else {
            //error
        }
    }

    public void editQualifications_Handler() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit/EditEmployeeQualification.xhtml?id=" + employeeSerialID);
        } catch (IOException ex) {
            Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editExperiences_Handler() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit/EditEmployeeExperience.xhtml?id=" + employeeSerialID);
        } catch (IOException ex) {
            Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editReferees_Handler() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit/EditEmployeeReferee.xhtml?id=" + employeeSerialID);
        } catch (IOException ex) {
            Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
