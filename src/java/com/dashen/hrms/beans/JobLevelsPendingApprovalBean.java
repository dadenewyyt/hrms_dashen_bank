/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.JobLevel;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempDetail;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.JobLevelService;
import com.dashen.hrms.service.TempDetailService;
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
public class JobLevelsPendingApprovalBean {
    
    private List<JobLevel> pendingTmpJobLevels;
    List<JobLevel> selectedJobLevelTmps;

    @Autowired
    private JobLevelService jobLevelService;
    @Autowired
    private TempDetailService tempDetailService;

    @PostConstruct
    public void init() {
        pendingTmpJobLevels = jobLevelService.listTmpJobLevels();
    }

    public List<JobLevel> getPendingTmpJobLevels() {
        return pendingTmpJobLevels;
    }

    public void setPendingTmpJobLevels(List<JobLevel> pendingTmpJobLevels) {
        this.pendingTmpJobLevels = pendingTmpJobLevels;
    }

    public List<JobLevel> getSelectedJobLevelTmps() {
        return selectedJobLevelTmps;
    }

    public void setSelectedJobLevelTmps(List<JobLevel> selectedJobLevelTmps) {
        this.selectedJobLevelTmps = selectedJobLevelTmps;
    }

    public void approveSelectedRows() {
        if (selectedJobLevelTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rows Selected", "Please select rows!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            //approve the selected rows
            for (JobLevel tmpJobLevel : selectedJobLevelTmps) {
                TempDetail tempDetail = tmpJobLevel.getTempDetail();
                if (tempDetail.getTempStatus() == TempStatus.SUBMITTED) {
                    if(jobLevelService.isJobLevelExistsWithLevel(tmpJobLevel.getLevel().toUpperCase())) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Failed", "Job level with level " + tmpJobLevel.getLevel() + " already exist.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        if (tempDetail.getActionType() == ActionType.CREATE) {
                            jobLevelService.saveNewJobLevel(tmpJobLevel);
                            pendingTmpJobLevels.remove(tmpJobLevel);
                        } else if (tempDetail.getActionType() == ActionType.UPDATE) //existing jobLevel was updated
                        {
                            jobLevelService.updateJobLevel(tmpJobLevel);
                            pendingTmpJobLevels.remove(tmpJobLevel);
                        }
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "JobLevel pending items approved.");
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
        if (selectedJobLevelTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            for (JobLevel tmpJobLevel : selectedJobLevelTmps) {
                TempDetail tempDetail = tmpJobLevel.getTempDetail();
                TempStatus original = tempDetail.getTempStatus();
                tempDetail.setTempStatus(TempStatus.EDITABLE);
                if (tempDetailService.updateTempDetail(tempDetail)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Status", "Selected row is made editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    tempDetail.setTempStatus(original);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Change Status", "Unable to make the selected row editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }
}