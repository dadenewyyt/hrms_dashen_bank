/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.JobGrade;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.JobGradeService;
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
public class JobGradeBean implements Serializable {

    private JobGrade currentTempJobGrade;

    @Autowired
    private JobGradeService jobGradeService;

    private List<JobGrade> jobGradesList;
    private JobGrade selectedJobGrade;

    private List<JobGrade> tempJobGradesList;
    private JobGrade selectedTempJobGrade;

    public JobGrade getCurrentTempJobGrade() {
        return currentTempJobGrade;
    }


    public void setCurrentTempJobGrade(JobGrade currentTempJobGrade) {
        this.currentTempJobGrade = currentTempJobGrade;
    }

    public void setJobGradeService(JobGradeService jobGradeService) {
        this.jobGradeService = jobGradeService;
    }

    public List<JobGrade> getJobGradesList() {
        return jobGradesList;
    }

    public void setJobGradesList(List<JobGrade> jobGradesList) {
        this.jobGradesList = jobGradesList;
    }

    public JobGrade getSelectedJobGrade() {
        return selectedJobGrade;
    }

    public void setSelectedJobGrade(JobGrade selectedJobGrade) {
        this.selectedJobGrade = selectedJobGrade;
    }

    public List<JobGrade> getTempJobGradesList() {
        return tempJobGradesList;
    }

    public void setTempJobGradesList(List<JobGrade> tempJobGradesList) {
        this.tempJobGradesList = tempJobGradesList;
    }

    public JobGrade getSelectedTempJobGrade() {
        return selectedTempJobGrade;
    }

    public void setSelectedTempJobGrade(JobGrade selectedTempJobGrade) {
        this.selectedTempJobGrade = selectedTempJobGrade;
    }

    @PostConstruct
    public void init() {

        currentTempJobGrade = new JobGrade();

        jobGradesList = jobGradeService.listJobGrades();
        tempJobGradesList = new ArrayList<>();
    }

    public void btnNewJobGrade_Handler() {
        currentTempJobGrade = new JobGrade();
    }

    public boolean isRowSubmitted() {
        if (currentTempJobGrade != null && currentTempJobGrade.getTmpStatus() == TempStatus.EDITABLE) {
            return false;
        }
        return true;
    }
    public void btnRefreshList_Handler() {
        MyUser user = CurrentUser.getCurrentUser();
        tempJobGradesList = jobGradeService.listTmpJobGradesForMaker(user.getUsername());
    }

    public void tmpJobGradesDataTableDataTable_rowSelected(SelectEvent evt) {
        currentTempJobGrade = selectedTempJobGrade;
    }

    public void btnSaveJobGrade_Handler() {
        if (currentTempJobGrade.getTmpStatus() == TempStatus.EDITABLE) {
            if (jobGradeService.isJobGradeExistsWithGrade(currentTempJobGrade.getGrade())) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Failed", "Job grade with your given grade already exists.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                if (currentTempJobGrade.getID() != null && !currentTempJobGrade.getID().isEmpty()) {
                    //is updating existing Temp JobGrade record
                    if (jobGradeService.updateTmpJobGrade(currentTempJobGrade)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Job Grade update", "JobGrade updates saved.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Job Grade update", "Update Failed.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                } else if (currentTempJobGrade.getJobGradeId() != null && !currentTempJobGrade.getJobGradeId().isEmpty()) {//editing existing Job Grade, tmp object is new
                    if (jobGradeService.saveNewTmpJobGrade(currentTempJobGrade, ActionType.UPDATE)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Job Grade update", "Job Grade updates saved.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Job Grade update", "Update Failed.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                } else//new JobGrade, and new tmp object
                {
                    if (jobGradeService.saveNewTmpJobGrade(currentTempJobGrade, ActionType.CREATE)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Job Grade", "New Job Grade added.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Job Grade", "Save Failed.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }
                currentTempJobGrade = new JobGrade();
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Job Grade", "The selected row is already submitted.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnSubmit_Handler() {
        if (selectedTempJobGrade != null) {
            if (selectedTempJobGrade.getTmpStatus() != TempStatus.SUBMITTED) {
                selectedTempJobGrade.setTmpStatus(TempStatus.SUBMITTED);
                if (jobGradeService.updateTmpJobGrade(selectedTempJobGrade)) {
                    //update successful   
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "Row submitted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    //update has failed, undo the change
                    selectedTempJobGrade.setTmpStatus(TempStatus.EDITABLE);
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
        if (selectedTempJobGrade != null) {
            if (selectedTempJobGrade.getTmpStatus() != TempStatus.SUBMITTED) {
                if (jobGradeService.delete(selectedTempJobGrade)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "Row is deleted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    currentTempJobGrade = new JobGrade();
                    tempJobGradesList.remove(selectedTempJobGrade);
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

    public void btnEditSelectedJobGrade_Handler() {
        JobGrade jobGradeTmp = new JobGrade();
        if (selectedJobGrade != null) {
            if (!jobGradeService.checkIfJobGradeHasPendingEdit(selectedJobGrade.getID())) {
                jobGradeTmp.setJobGradeId(selectedJobGrade.getID());
                jobGradeTmp.setGrade(selectedJobGrade.getGrade());
                jobGradeTmp.setDescription(selectedJobGrade.getDescription());
                jobGradeTmp.setBaseSalary(selectedJobGrade.getBaseSalary());
                currentTempJobGrade = jobGradeTmp;
            } else {
                System.out.println("JobGrade has pending changes");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit Job Grade", "The selected Job Grade has pending changes. Please approve or cancel the changes and try again.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }
}
