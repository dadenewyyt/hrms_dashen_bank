/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.Department;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.service.DepartmentService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 *
 * @author biniamt
 */


@Component
@SpringViewScope
public class DepartmentBean {
    private Department department;

    @Autowired
    private DepartmentService departmentService;
    private List<Department> departmentsList;
    private Department selectedDepartment;

    public Department getNewDepartment() {
        return department;
    }

    public void setNewDepartment(Department department) {
        this.department = department;
    }

    public List<Department> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<Department> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(Department selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }
    
    

    @PostConstruct
    public void init() {
        department = new Department();
    }

    public void btnNewDepartment_Handler() {
        department = new Department();
    }

    public void btnDeleteDepartment_Handler() {
        if (selectedDepartment != null) {
            if (departmentService.delete(selectedDepartment)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Department", "Department is deleted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                department = new Department();
                departmentsList.remove(selectedDepartment);
            } else {
                //delete failed
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete Department", "Department is not deleted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Department", "No department selected.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnRefreshList_Handler() {
        departmentsList = departmentService.listAll();
    }

    public void departmentsDataTable_rowSelected(SelectEvent evt) {
        department = selectedDepartment;
    }
    
    public void btnSaveNewDepartment_Handler() {
        departmentService.saveNewDepartment(department);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Department", "New department is saved.");
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }
}