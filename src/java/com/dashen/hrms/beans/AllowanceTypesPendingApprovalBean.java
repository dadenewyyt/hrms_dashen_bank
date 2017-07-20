/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.AllowanceType;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.AllowanceTypeService;
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
public class AllowanceTypesPendingApprovalBean {
    
    private List<AllowanceType> pendingTmpAllowanceTypes;
    List<AllowanceType> selectedAllowanceTypeTmps;

    @Autowired
    private AllowanceTypeService allowanceTypeService;

    @PostConstruct
    public void init() {
        pendingTmpAllowanceTypes = allowanceTypeService.listTmpAllowanceTypes();
    }

    public List<AllowanceType> getPendingTmpAllowanceTypes() {
        return pendingTmpAllowanceTypes;
    }

    public void setPendingTmpAllowanceTypes(List<AllowanceType> pendingTmpAllowanceTypes) {
        this.pendingTmpAllowanceTypes = pendingTmpAllowanceTypes;
    }

    public List<AllowanceType> getSelectedAllowanceTypeTmps() {
        return selectedAllowanceTypeTmps;
    }

    public void setSelectedAllowanceTypeTmps(List<AllowanceType> selectedAllowanceTypeTmps) {
        this.selectedAllowanceTypeTmps = selectedAllowanceTypeTmps;
    }

    public void approveSelectedRows() {
        if (selectedAllowanceTypeTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rows Selected", "Please select rows!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            //approve the selected rows
            for (AllowanceType tmpAllowanceType : selectedAllowanceTypeTmps) {
                if (tmpAllowanceType.getTmpStatus() == TempStatus.SUBMITTED) {
                    if (tmpAllowanceType.getActionType() == ActionType.CREATE) {
                        allowanceTypeService.saveNewAllowanceType(tmpAllowanceType);
                        pendingTmpAllowanceTypes.remove(tmpAllowanceType);
                    } else if (tmpAllowanceType.getActionType() == ActionType.UPDATE) //existing allowanceType was updated
                    {
                        allowanceTypeService.updateAllowanceType(tmpAllowanceType);
                        pendingTmpAllowanceTypes.remove(tmpAllowanceType);
                    }
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "AllowanceType pending items approved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }

    public void makeEditableByMaker() {
        if (selectedAllowanceTypeTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            for (AllowanceType tmpAllowanceType : selectedAllowanceTypeTmps) {
                TempStatus original = tmpAllowanceType.getTmpStatus();
                tmpAllowanceType.setTmpStatus(TempStatus.EDITABLE);
                if (allowanceTypeService.makeTmpAllowaceTypeEditableByMaker(tmpAllowanceType)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Status", "Selected row is made editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    tmpAllowanceType.setTmpStatus(original);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Change Status", "Unable to make the selected row editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }
}
