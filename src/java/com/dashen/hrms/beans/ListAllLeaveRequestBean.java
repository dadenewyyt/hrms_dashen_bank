/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.service.LeaveService;
import com.dashen.hrms.Employee;
import com.dashen.hrms.service.EmployeeService;
import com.dashen.hrms.LeaveRequest;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.beans.ListAllEmployeesBean;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Daniel Adenew
 */
@Component
@SpringViewScope
public class ListAllLeaveRequestBean {
    
     private List<LeaveRequest> leaveRequestList;
    private List<LeaveRequest> selectedLeaveRequests;

    @Autowired
    private LeaveService leaveService;

    @PostConstruct
    public void init() {
        leaveRequestList = leaveService.getLeaveRequest();
    }
    
    public void viewEmployeeDetails() {
        if (selectedLeaveRequests.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Leave Requests Selected", "Please select Leave Data!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            String id = selectedLeaveRequests.get(0).getLeaveID();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("LeaveRequestDetail.xhtml?id=" + id);
            } catch (IOException ex) {
                Logger.getLogger(ListAllEmployeesBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public List<LeaveRequest> getLeaveRequestList() {
        return leaveRequestList;
    }

    public void setLeaveRequestList(List<LeaveRequest> leaveRequestList) {
        this.leaveRequestList = leaveRequestList;
    }

    public List<LeaveRequest> getSelectedLeaveRequests() {
        return selectedLeaveRequests;
    }

    public void setSelectedLeaveRequests(List<LeaveRequest> selectedLeaveRequests) {
        this.selectedLeaveRequests = selectedLeaveRequests;
    }

    public LeaveService getLeaveService() {
        return leaveService;
    }

    public void setLeaveService(LeaveService leaveService) {
        this.leaveService = leaveService;
    }


}
