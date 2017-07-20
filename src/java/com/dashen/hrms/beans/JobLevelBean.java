/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.JobLevel;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempDetail;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.JobLevelService;
import com.dashen.hrms.service.TempDetailService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author biniamt
 */

@Component
@SpringViewScope
public class JobLevelBean implements Serializable {

    private JobLevel currentTempJobLevel;

    @Autowired
    private JobLevelService jobLevelService;
    
    @Autowired
    private TempDetailService tempDetailService;

    private List<JobLevel> jobLevelsList;
    private JobLevel selectedJobLevel;

    private List<JobLevel> tempJobLevelsList;
    private JobLevel selectedTempJobLevel;

    public JobLevel getCurrentTempJobLevel() {
        return currentTempJobLevel;
    }


    public void setCurrentTempJobLevel(JobLevel currentTempJobLevel) {
        this.currentTempJobLevel = currentTempJobLevel;
    }

    public void setJobLevelService(JobLevelService jobLevelService) {
        this.jobLevelService = jobLevelService;
    }

    public List<JobLevel> getJobLevelsList() {
        return jobLevelsList;
    }

    public void setJobLevelsList(List<JobLevel> jobLevelsList) {
        this.jobLevelsList = jobLevelsList;
    }

    public JobLevel getSelectedJobLevel() {
        return selectedJobLevel;
    }

    public void setSelectedJobLevel(JobLevel selectedJobLevel) {
        this.selectedJobLevel = selectedJobLevel;
    }

    public List<JobLevel> getTempJobLevelsList() {
        return tempJobLevelsList;
    }

    public void setTempJobLevelsList(List<JobLevel> tempJobLevelsList) {
        this.tempJobLevelsList = tempJobLevelsList;
    }

    public JobLevel getSelectedTempJobLevel() {
        return selectedTempJobLevel;
    }

    public void setSelectedTempJobLevel(JobLevel selectedTempJobLevel) {
        this.selectedTempJobLevel = selectedTempJobLevel;
    }

    @PostConstruct
    public void init() {

        currentTempJobLevel = new JobLevel();

        jobLevelsList = jobLevelService.listJobLevels();
        tempJobLevelsList = new ArrayList<>();
    }

    public void btnNewJobLevel_Handler() {
        currentTempJobLevel = new JobLevel();
    }

    public boolean isRowSubmitted() {
        if (currentTempJobLevel != null && currentTempJobLevel.getTempDetail().getTempStatus() == TempStatus.EDITABLE) {
            return false;
        }
        return true;
    }
    public void btnRefreshList_Handler() {
        MyUser user = CurrentUser.getCurrentUser();
        tempJobLevelsList = jobLevelService.listTmpJobLevelsForMaker(user.getUsername());
    }

    public void tmpJobLevelsDataTableDataTable_rowSelected(SelectEvent evt) {
        currentTempJobLevel = selectedTempJobLevel;
    }

    public void btnSaveJobLevel_Handler() {
        if (currentTempJobLevel.getTempDetail().getTempStatus() == TempStatus.EDITABLE) {
            if (jobLevelService.isJobLevelExistsWithLevel(currentTempJobLevel.getLevel().toUpperCase())) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Save Failed", "Job level with your given level already exists.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                if (currentTempJobLevel.getID() != null && !currentTempJobLevel.getID().isEmpty()) {
                    //is updating existing Temp JobLevel record
                    if (jobLevelService.updateTmpJobLevel(currentTempJobLevel)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save JobLevel update", "JobLevel updates saved.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save JobLevel update", "Update Failed.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                } else if (currentTempJobLevel.getTempDetail().getSourceId() != null && !currentTempJobLevel.getTempDetail().getSourceId().isEmpty()) {
                    //editing existing JobLevel, tmp object is new
                    if (jobLevelService.saveNewTmpJobLevel(currentTempJobLevel, ActionType.UPDATE)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save JobLevel update", "JobLevel updates saved.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save JobLevel update", "Update Failed.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                } else//new JobLevel, and new tmp object
                {
                    if (jobLevelService.saveNewTmpJobLevel(currentTempJobLevel, ActionType.CREATE)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New JobLevel", "New JobLevel added.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New JobLevel", "Save Failed.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }
                currentTempJobLevel = new JobLevel();
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update JobLevel", "The selected row is already submitted.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnSubmit_Handler() {
        if (selectedTempJobLevel != null) {
            if (selectedTempJobLevel.getTempDetail().getTempStatus() != TempStatus.SUBMITTED) {
                TempDetail tempDetail = selectedTempJobLevel.getTempDetail();
                tempDetail.setTempStatus(TempStatus.SUBMITTED);
                
                if (tempDetailService.updateTempDetail(tempDetail)) {
                    //update successful   
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "Row submitted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    //update has failed, undo the change
                    tempDetail.setTempStatus(TempStatus.EDITABLE);
                }
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "The selected row is already submitted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "No row selected.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnDeleteRow_Handler() {
        if (selectedTempJobLevel != null) {
            if (selectedTempJobLevel.getTempDetail().getTempStatus() != TempStatus.SUBMITTED) {
                if (jobLevelService.delete(selectedTempJobLevel)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "Row is deleted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    currentTempJobLevel = new JobLevel();
                    tempJobLevelsList.remove(selectedTempJobLevel);
                } else {
                    //delete failed
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete Row", "Row is not deleted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete Row", "Submitted rows cannot be deleted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "No row selected.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnEditSelectedJobLevel_Handler() {
        JobLevel jobLevelTmp = new JobLevel();
        if (selectedJobLevel != null) {
            if (!jobLevelService.checkIfJobLevelHasPendingEdit(selectedJobLevel.getID())) {
                jobLevelTmp.getTempDetail().setSourceId(selectedJobLevel.getID());
                jobLevelTmp.setLevel(selectedJobLevel.getLevel());
                jobLevelTmp.setDescription(selectedJobLevel.getDescription());
                currentTempJobLevel = jobLevelTmp;
            } else {
                System.out.println("JobLevel has pending changes");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit JobLevel", "The selected JobLevel has pending changes. Please approve or cancel the changes and try again.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }
}