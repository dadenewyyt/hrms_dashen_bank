/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.Allowance;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.AllowanceService;
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
public class AllowancesPendingApprovalBean {
    
    private List<Allowance> pendingTmpAllowances;
    List<Allowance> selectedAllowanceTmps;

    @Autowired
    private AllowanceService allowanceService;

    @PostConstruct
    public void init() {
        pendingTmpAllowances = allowanceService.listTmpAllowances();
    }

    public List<Allowance> getPendingTmpAllowances() {
        return pendingTmpAllowances;
    }

    public void setPendingTmpAllowances(List<Allowance> pendingTmpAllowances) {
        this.pendingTmpAllowances = pendingTmpAllowances;
    }

    public List<Allowance> getSelectedAllowanceTmps() {
        return selectedAllowanceTmps;
    }

    public void setSelectedAllowanceTmps(List<Allowance> selectedAllowanceTmps) {
        this.selectedAllowanceTmps = selectedAllowanceTmps;
    }

    public void approveSelectedRows() {
        if (selectedAllowanceTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rows Selected", "Please select rows!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            //approve the selected rows
            for (Allowance tmpAllowance : selectedAllowanceTmps) {
                if (tmpAllowance.getTmpStatus() == TempStatus.SUBMITTED) {
                    if (tmpAllowance.getActionType() == ActionType.CREATE) {
                        allowanceService.saveNewAllowance(tmpAllowance);
                        pendingTmpAllowances.remove(tmpAllowance);
                    } else if (tmpAllowance.getActionType() == ActionType.UPDATE) //existing allowance was updated
                    {
                        allowanceService.updateAllowance(tmpAllowance);
                        pendingTmpAllowances.remove(tmpAllowance);
                    }
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "Allowance pending items approved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }

    public void makeEditableByMaker() {
        if (selectedAllowanceTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            for (Allowance tmpAllowance : selectedAllowanceTmps) {
                TempStatus original = tmpAllowance.getTmpStatus();
                tmpAllowance.setTmpStatus(TempStatus.EDITABLE);
                if (allowanceService.makeTmpAllowanceEditableByMaker(tmpAllowance)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Status", "Selected row is made editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    tmpAllowance.setTmpStatus(original);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Change Status", "Unable to make the selected row editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }
}
