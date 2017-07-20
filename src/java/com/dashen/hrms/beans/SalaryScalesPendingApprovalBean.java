/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.SalaryScale;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.SalaryScaleService;
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
public class SalaryScalesPendingApprovalBean {
    
    private List<SalaryScale> pendingTmpSalaryScales;
    List<SalaryScale> selectedSalaryScaleTmps;

    @Autowired
    private SalaryScaleService salaryScaleService;

    @PostConstruct
    public void init() {
        pendingTmpSalaryScales = salaryScaleService.listTmpSalaryScales();
    }

    public List<SalaryScale> getPendingTmpSalaryScales() {
        return pendingTmpSalaryScales;
    }

    public void setPendingTmpSalaryScales(List<SalaryScale> pendingTmpSalaryScales) {
        this.pendingTmpSalaryScales = pendingTmpSalaryScales;
    }

    public List<SalaryScale> getSelectedSalaryScaleTmps() {
        return selectedSalaryScaleTmps;
    }

    public void setSelectedSalaryScaleTmps(List<SalaryScale> selectedSalaryScaleTmps) {
        this.selectedSalaryScaleTmps = selectedSalaryScaleTmps;
    }

    public void approveSelectedRows() {
        if (selectedSalaryScaleTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rows Selected", "Please select rows!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            //approve the selected rows
            for (SalaryScale tmpSalaryScale : selectedSalaryScaleTmps) {
                if (tmpSalaryScale.getTmpStatus() == TempStatus.SUBMITTED) {
                    if (tmpSalaryScale.getActionType() == ActionType.CREATE) {
                        salaryScaleService.saveNewSalaryScale(tmpSalaryScale);
                        pendingTmpSalaryScales.remove(tmpSalaryScale);
                    } else if (tmpSalaryScale.getActionType() == ActionType.UPDATE) //existing salary scale was updated
                    {
                        salaryScaleService.updateSalaryScale(tmpSalaryScale);
                        pendingTmpSalaryScales.remove(tmpSalaryScale);
                    }
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "Salary Scale pending items approved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }

    public void makeEditableByMaker() {
        if (selectedSalaryScaleTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            for (SalaryScale tmpSalaryScale : selectedSalaryScaleTmps) {
                TempStatus original = tmpSalaryScale.getTmpStatus();
                tmpSalaryScale.setTmpStatus(TempStatus.EDITABLE);
                if (salaryScaleService.updateTmpSalaryScale(tmpSalaryScale)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Status", "Selected row is made editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    tmpSalaryScale.setTmpStatus(original);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Change Status", "Unable to make the selected row editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }
}
