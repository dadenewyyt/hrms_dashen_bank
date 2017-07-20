/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.Employee;
import com.dashen.hrms.service.EmployeeService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 *
 * @author mulugetak
 */
@Component
@RequestScope
public class ListAllEmployeesBean {

    private List<Employee> employeesList;
    private Employee selectedEmployee;

    @Autowired
    private LazyDataModel<Employee> lazyEmployeeModel;

    @Autowired
    private EmployeeService empService;

    @PostConstruct
    public void init() {
        employeesList = empService.listAll();

    }

    public List<Employee> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employee> employeesList) {
        this.employeesList = employeesList;
    }

    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employee selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public LazyDataModel<Employee> getLazyEmployeeModel() {
        return lazyEmployeeModel;
    }

    public void newEmployee() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit/EditEmployees.xhtml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void employeesDataTable_rowSelected(SelectEvent event) {
        Employee e = (Employee) event.getObject();

    }

    public void changeProfilePhoto() {
        if (selectedEmployee == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Employee Selected", "Please select employee!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            String id = selectedEmployee.getEmployeeSerialID();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("ChangeProfilePhoto.xhtml?id=" + id);
            } catch (IOException ex) {
                Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void viewEmployeeDetails() {
        if (selectedEmployee == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Employee Selected", "Please select employee!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            String id = selectedEmployee.getEmployeeSerialID();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("EmployeeDetails.xhtml?id=" + id);
            } catch (IOException ex) {
                Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void addEmployeeQualification() {
        if (selectedEmployee == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Employee Selected", "Please select employee!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
//            String id = selectedEmployees.getEmployeeSerialID();
//            try {
//                
//            } catch (IOException ex) {
//                Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
    }

    public void viewEmployeeQualification() {
        if (selectedEmployee == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Employee Selected", "Please select employee!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
//            String id = selectedEmployees.getEmployeeSerialID();
//            try {
//                
//            } catch (IOException ex) {
//                Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
    }

    public void addEmployeeExperience() {
        if (selectedEmployee == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Employee Selected", "Please select employee!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
//            String id = selectedEmployees.getEmployeeSerialID();
//            try {
//                
//            } catch (IOException ex) {
//                Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
    }

    public void viewEmployeeExperience() {
        if (selectedEmployee == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Employee Selected", "Please select employee!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
//            String id = selectedEmployees.getEmployeeSerialID();
//            try {
//                
//            } catch (IOException ex) {
//                Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
    }

    public void addEmployeeTraining() {
        if (selectedEmployee == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Employee Selected", "Please select employee!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
//            String id = selectedEmployees.getEmployeeSerialID();
//            try {
//                
//            } catch (IOException ex) {
//                Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
    }

    public void viewEmployeeTraining() {
        if (selectedEmployee == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Employee Selected", "Please select employee!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
//            String id = selectedEmployees.getEmployeeSerialID();
//            try {
//                
//            } catch (IOException ex) {
//                Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
    }

    public void addEmployeeReferee() {
        if (selectedEmployee == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Employee Selected", "Please select employee!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
//            String id = selectedEmployees.getEmployeeSerialID();
//            try {
//                
//            } catch (IOException ex) {
//                Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
    }

    public void viewEmployeeReferee() {
        if (selectedEmployee == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Employee Selected", "Please select employee!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
//            String id = selectedEmployees.getEmployeeSerialID();
//            try {
//                
//            } catch (IOException ex) {
//                Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
    }

    public void addEmployeeReference() {
        if (selectedEmployee == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Employee Selected", "Please select employee!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
//            String id = selectedEmployees.getEmployeeSerialID();
//            try {
//                
//            } catch (IOException ex) {
//                Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
    }

    public void viewEmployeeReference() {
        if (selectedEmployee == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Employee Selected", "Please select employee!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
//            String id = selectedEmployees.getEmployeeSerialID();
//            try {
//                
//            } catch (IOException ex) {
//                Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
    }

}
