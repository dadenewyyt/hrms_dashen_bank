/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.EmployeePosition;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.EmployeePositionService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author biniamt
 */

@Component
@SpringViewScope
public class EmployeePositionsPendingApprovalBean {
    
    private List<EmployeePosition> pendingTmpEmployeePositions;
    List<EmployeePosition> selectedEmployeePositionTmps;

    @Autowired
    private EmployeePositionService employeePositionService;

    @PostConstruct
    public void init() {
        pendingTmpEmployeePositions = employeePositionService.listTmpEmployeePositions();
    }

    public List<EmployeePosition> getPendingTmpEmployeePositions() {
        return pendingTmpEmployeePositions;
    }

    public void setPendingTmpEmployeePositions(List<EmployeePosition> pendingTmpEmployeePositions) {
        this.pendingTmpEmployeePositions = pendingTmpEmployeePositions;
    }

    public List<EmployeePosition> getSelectedEmployeePositionTmps() {
        return selectedEmployeePositionTmps;
    }

    public void setSelectedEmployeePositionTmps(List<EmployeePosition> selectedEmployeePositionTmps) {
        this.selectedEmployeePositionTmps = selectedEmployeePositionTmps;
    }

    public void approveSelectedRows() {
        if (selectedEmployeePositionTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rows Selected", "Please select rows!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            //approve the selected rows
            for (EmployeePosition tmpEmployeePosition : selectedEmployeePositionTmps) {
                if (tmpEmployeePosition.getTmpStatus() == TempStatus.SUBMITTED) {
                    if (tmpEmployeePosition.getActionType() == ActionType.CREATE) {
                        employeePositionService.saveNewEmployeePosition(tmpEmployeePosition);
                        pendingTmpEmployeePositions.remove(tmpEmployeePosition);
                    } else if (tmpEmployeePosition.getActionType() == ActionType.UPDATE) //existing employeePosition was updated
                    {
                        employeePositionService.updateEmployeePosition(tmpEmployeePosition);
                        pendingTmpEmployeePositions.remove(tmpEmployeePosition);
                    }
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "EmployeePosition pending items approved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }

    public void makeEditableByMaker() {
        if (selectedEmployeePositionTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            for (EmployeePosition tmpEmployeePosition : selectedEmployeePositionTmps) {
                TempStatus original = tmpEmployeePosition.getTmpStatus();
                tmpEmployeePosition.setTmpStatus(TempStatus.EDITABLE);
                if (employeePositionService.makeTmpEmployeePositionEditableByMaker(tmpEmployeePosition)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Status", "Selected row is made editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    tmpEmployeePosition.setTmpStatus(original);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Change Status", "Unable to make the selected row editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }
}

