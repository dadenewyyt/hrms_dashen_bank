/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.LeaveRequest;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.LeaveService;
import java.io.Serializable;
import java.util.List;
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
public class PendingLeaveRequestItemsBean implements Serializable {

    private List<LeaveRequest> pendingLeaveRequestItems;
    List<LeaveRequest> selectedLeaveRequest;
    @Autowired
    private LeaveService leaveService;

    @PostConstruct
    public void init() {
        MyUser usr = CurrentUser.getCurrentUser();
        pendingLeaveRequestItems = leaveService.listLeaveRequestForChecker(usr.getUsername());
        System.out.println(pendingLeaveRequestItems.size());
    }

    public List<LeaveRequest> getPendingLeaveRequestItems() {
        return pendingLeaveRequestItems;
    }

    public void setPendingLeaveRequestItems(List<LeaveRequest> pendingLeaveRequestItems) {
        this.pendingLeaveRequestItems = pendingLeaveRequestItems;
    }

    public List<LeaveRequest> getSelectedLeaveRequest() {
        return selectedLeaveRequest;
    }

    public void setSelectedLeaveRequest(List<LeaveRequest> selectedLeaveRequest) {
        this.selectedLeaveRequest = selectedLeaveRequest;
    }

    public LeaveService getLeaveService() {
        return leaveService;
    }

    public void setLeaveService(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

   
    public void approveSelectedRows() {
         System.out.println("Approve");
        if (selectedLeaveRequest.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rows Selected", "Please select rows!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            //approve the selected rows
            System.out.println("Approve2");
            for (LeaveRequest leave : selectedLeaveRequest) {
                
                if (leave.getMaker_checker_status()== TempStatus.SUBMITTED) {
                   System.out.println(leave);
                   leave.setMaker_checker_status(TempStatus.APPROVED);
                   if(leaveService.updateLeaveRequest(leave)){
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "LeaveRequests pending items approved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                   }
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
      this.init();

    }

    public void makeEditableByMaker() {
        if (selectedLeaveRequest.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            for (LeaveRequest leave : selectedLeaveRequest) {
                TempStatus original = leave.getMaker_checker_status();
                if (TempStatus.SUBMITTED == original) {
                    leave.setMaker_checker_status(TempStatus.EDITABLE);
                    leaveService.updateLeaveRequest(leave);
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Status", "Selected row is now editable for makers.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                   
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Status Change Not Allowed", "The status change is not allowed for the selected row.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
        this.init();
    }
     
}
