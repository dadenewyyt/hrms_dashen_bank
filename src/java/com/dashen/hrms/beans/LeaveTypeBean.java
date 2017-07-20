/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.LeaveType;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.service.LeaveService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Daniel Adenew
 */


@Component
@SpringViewScope
public class LeaveTypeBean {
    
    private LeaveType leaveType;

    @Autowired
    private LeaveService leaveService;
    
    private List<LeaveType> leaveTypeList;
    
    private LeaveType selectedLeaveType;

  

    @PostConstruct
    public void init() {
        leaveType= new LeaveType();
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public LeaveService getLeaveService() {
        return leaveService;
    }

    public void setLeaveService(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    public List<LeaveType> getLeaveTypeList() {
        return leaveTypeList;
    }

    public void setLeaveTypeList(List<LeaveType> leaveTypeList) {
        this.leaveTypeList = leaveTypeList;
    }

    public LeaveType getSelectedLeaveType() {
        return selectedLeaveType;
    }

    public void setSelectedLeaveType(LeaveType selectedLeaveType) {
        this.selectedLeaveType = selectedLeaveType;
    }

    public void btnNewLeaveType_Handler() {
        leaveType = new LeaveType();
    }
    

    public void btnDeleteLeaveType_Handler() {
        if (selectedLeaveType != null) {
            if (leaveService.delete(selectedLeaveType)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete LeaveType", "Leave Type is deleted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                leaveType = new LeaveType();
                leaveTypeList.remove(selectedLeaveType);
            } else {
                //delete failed
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete Leave Type", "Leave Type is not deleted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Department", "No department selected.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnRefreshList_Handler() {
        leaveTypeList = leaveService.getLeaveTypes();
    }

    public void leaveTypeDataTable_rowSelected(SelectEvent evt) {
        leaveType = selectedLeaveType;
    }
    
    public void btnSaveNewLeaveType_Handler() {
        
         if (leaveType.getID()!= null && !leaveType.getID().isEmpty()) {
            //is updating existing leaveType
            leaveService.updateLeaveType(leaveType);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Leave Type", "Leave Type details updated.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {//new leaveType
            leaveService.saveNewLeaveType(leaveType);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Leave Type", "New Leave Type added.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        

    }
}