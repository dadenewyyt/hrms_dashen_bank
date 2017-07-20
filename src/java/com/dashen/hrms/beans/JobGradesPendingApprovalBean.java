/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.JobGrade;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.JobGradeService;
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
public class JobGradesPendingApprovalBean {
    
    private List<JobGrade> pendingTmpJobGrades;
    List<JobGrade> selectedJobGradeTmps;

    @Autowired
    private JobGradeService jobGradeService;

    @PostConstruct
    public void init() {
        pendingTmpJobGrades = jobGradeService.listTmpJobGrades();
    }

    public List<JobGrade> getPendingTmpJobGrades() {
        return pendingTmpJobGrades;
    }

    public void setPendingTmpJobGrades(List<JobGrade> pendingTmpJobGrades) {
        this.pendingTmpJobGrades = pendingTmpJobGrades;
    }

    public List<JobGrade> getSelectedJobGradeTmps() {
        return selectedJobGradeTmps;
    }

    public void setSelectedJobGradeTmps(List<JobGrade> selectedJobGradeTmps) {
        this.selectedJobGradeTmps = selectedJobGradeTmps;
    }

    public void approveSelectedRows() {
        if (selectedJobGradeTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rows Selected", "Please select rows!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            //approve the selected rows
            for (JobGrade tmpJobGrade : selectedJobGradeTmps) {
                if (tmpJobGrade.getTmpStatus() == TempStatus.SUBMITTED) {
                    if(jobGradeService.isJobGradeExistsWithGrade(tmpJobGrade.getGrade().toUpperCase())) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Failed", "Job grade with grade " + tmpJobGrade.getGrade() + " already exist.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        if (tmpJobGrade.getActionType() == ActionType.CREATE) {
                            jobGradeService.saveNewJobGrade(tmpJobGrade);
                            pendingTmpJobGrades.remove(tmpJobGrade);
                        } else if (tmpJobGrade.getActionType() == ActionType.UPDATE) //existing jobGrade was updated
                        {
                            jobGradeService.updateJobGrade(tmpJobGrade);
                            pendingTmpJobGrades.remove(tmpJobGrade);
                        }
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "JobGrade pending items approved.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }

    public void makeEditableByMaker() {
        if (selectedJobGradeTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            for (JobGrade tmpJobGrade : selectedJobGradeTmps) {
                TempStatus original = tmpJobGrade.getTmpStatus();
                tmpJobGrade.setTmpStatus(TempStatus.EDITABLE);
                if (jobGradeService.updateTmpJobGrade(tmpJobGrade)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Status", "Selected row is made editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    tmpJobGrade.setTmpStatus(original);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Change Status", "Unable to make the selected row editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }
}