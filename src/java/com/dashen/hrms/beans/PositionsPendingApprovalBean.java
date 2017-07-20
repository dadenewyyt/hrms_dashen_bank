/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.Position;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.PositionService;
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
public class PositionsPendingApprovalBean {
    
    private List<Position> pendingTmpPositions;
    List<Position> selectedPositionTmps;

    @Autowired
    private PositionService positionService;

    @PostConstruct
    public void init() {
        pendingTmpPositions = positionService.listTmpPositions();
    }

    public List<Position> getPendingTmpPositions() {
        return pendingTmpPositions;
    }

    public void setPendingTmpPositions(List<Position> pendingTmpPositions) {
        this.pendingTmpPositions = pendingTmpPositions;
    }

    public List<Position> getSelectedPositionTmps() {
        return selectedPositionTmps;
    }

    public void setSelectedPositionTmps(List<Position> selectedPositionTmps) {
        this.selectedPositionTmps = selectedPositionTmps;
    }

    public void approveSelectedRows() {
        if (selectedPositionTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rows Selected", "Please select rows!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            //approve the selected rows
            for (Position tmpPosition : selectedPositionTmps) {
                if (tmpPosition.getTmpStatus() == TempStatus.SUBMITTED) {
                    if (tmpPosition.getActionType() == ActionType.CREATE) {
                        positionService.saveNewPosition(tmpPosition);
                        pendingTmpPositions.remove(tmpPosition);
                    } else if (tmpPosition.getActionType() == ActionType.UPDATE) //existing position was updated
                    {
                        positionService.updatePosition(tmpPosition);
                        pendingTmpPositions.remove(tmpPosition);
                    }
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "Position pending items approved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }

    public void makeEditableByMaker() {
        if (selectedPositionTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            for (Position tmpPosition : selectedPositionTmps) {
                TempStatus original = tmpPosition.getTmpStatus();
                tmpPosition.setTmpStatus(TempStatus.EDITABLE);
                if (positionService.makeTmpPositionEditableByMaker(tmpPosition)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Status", "Selected row is made editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    tmpPosition.setTmpStatus(original);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Change Status", "Unable to make the selected row editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }
}
