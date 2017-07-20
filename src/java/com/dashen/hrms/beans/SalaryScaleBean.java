/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.JobGrade;
import com.dashen.hrms.JobLevel;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.SalaryScale;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.JobGradeService;
import com.dashen.hrms.service.JobLevelService;
import com.dashen.hrms.service.SalaryScaleService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author biniamt
 */

@Component
@SpringViewScope
public class SalaryScaleBean implements Serializable {

    private SalaryScale currentTempSalaryScale;

    @Autowired
    private SalaryScaleService salaryScaleService;

    private List<SalaryScale> salaryScalesList;
    private SalaryScale selectedSalaryScale;
    
    @Autowired
    private JobGradeService jobGradeService;
    @Autowired
    private JobLevelService jobLevelService;

    private List<SalaryScale> tempSalaryScalesList;
    private SalaryScale selectedTempSalaryScale;
    
    private List<SelectItem> jobGradeList;
    private List<SelectItem> jobLevelList;

    public SalaryScale getCurrentTempSalaryScale() {
        return currentTempSalaryScale;
    }


    public void setCurrentTempSalaryScale(SalaryScale currentTempSalaryScale) {
        this.currentTempSalaryScale = currentTempSalaryScale;
    }

    public void setSalaryScaleService(SalaryScaleService salaryScaleService) {
        this.salaryScaleService = salaryScaleService;
    }

    public List<SalaryScale> getSalaryScalesList() {
        return salaryScalesList;
    }

    public void setSalaryScalesList(List<SalaryScale> salaryScalesList) {
        this.salaryScalesList = salaryScalesList;
    }

    public SalaryScale getSelectedSalaryScale() {
        return selectedSalaryScale;
    }

    public void setSelectedSalaryScale(SalaryScale selectedSalaryScale) {
        this.selectedSalaryScale = selectedSalaryScale;
    }

    public List<SalaryScale> getTempSalaryScalesList() {
        return tempSalaryScalesList;
    }

    public void setTempSalaryScalesList(List<SalaryScale> tempSalaryScalesList) {
        this.tempSalaryScalesList = tempSalaryScalesList;
    }

    public SalaryScale getSelectedTempSalaryScale() {
        return selectedTempSalaryScale;
    }

    public void setSelectedTempSalaryScale(SalaryScale selectedTempSalaryScale) {
        this.selectedTempSalaryScale = selectedTempSalaryScale;
    }
    
    public List<SelectItem> getJobGradeList() {
        return jobGradeList;
    }

    public void setJobGradeList(List<SelectItem> jobGradeList) {
        this.jobGradeList = jobGradeList;
    }

    public List<SelectItem> getJobLevelList() {
        return jobLevelList;
    }

    public void setJobLevelList(List<SelectItem> jobLevelList) {
        this.jobLevelList = jobLevelList;
    }

    @PostConstruct
    public void init() {

        currentTempSalaryScale = new SalaryScale();

        salaryScalesList = salaryScaleService.listSalaryScales();
        tempSalaryScalesList = new ArrayList<>();
        
        jobGradeList = new ArrayList<>();
        jobLevelList = new ArrayList<>();
        
        List<JobGrade> jList = jobGradeService.listJobGrades();
        for (JobGrade j : jList) {
            jobGradeList.add(new SelectItem(j.getID(), j.getGrade()));
        }
        
        List<JobLevel> lList = jobLevelService.listJobLevels();
        for (JobLevel l : lList) {
            jobLevelList.add(new SelectItem(l.getID(), l.getLevel()));
        }
    }

    public void btnNewSalaryScale_Handler() {
        currentTempSalaryScale = new SalaryScale();
    }

    public boolean isRowSubmitted() {
        if (currentTempSalaryScale != null && currentTempSalaryScale.getTmpStatus() == TempStatus.EDITABLE) {
            return false;
        }
        return true;
    }
    public void btnRefreshList_Handler() {
        MyUser user = CurrentUser.getCurrentUser();
        tempSalaryScalesList = salaryScaleService.listTmpSalaryScalesForMaker(user.getUsername());
    }

    public void tmpSalaryScalesDataTableDataTable_rowSelected(SelectEvent evt) {
        currentTempSalaryScale = selectedTempSalaryScale;
    }

    public void btnSaveSalaryScale_Handler() {
        if (currentTempSalaryScale.getTmpStatus() == TempStatus.EDITABLE) {
            if (salaryScaleService.isSalaryScaleExistsWithGradeAndLevel(jobGradeService.getByID(currentTempSalaryScale.getGrade().getID()).getGrade(), jobLevelService.getByID(currentTempSalaryScale.getLevel().getID()).getLevel())) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Save Failed", "Salary scale with the give grade and level already exist.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                if (currentTempSalaryScale.getID() != null && !currentTempSalaryScale.getID().isEmpty()) {
                    //is updating existing Temp SalaryScale record
                    if (salaryScaleService.updateTmpSalaryScale(currentTempSalaryScale)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Salary Scale update", "Salary Scale updates saved.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Salary Scale update", "Update Failed.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                } else if (currentTempSalaryScale.getSalaryScaleId() != null && !currentTempSalaryScale.getSalaryScaleId().isEmpty()) {
                    //editing existing SalaryScale, tmp object is new
                    if (salaryScaleService.saveNewTmpSalaryScale(currentTempSalaryScale, ActionType.UPDATE)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save SalaryScale update", "SalaryScale updates saved.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save SalaryScale update", "Update Failed.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                } else//new salaryScale, and new tmp object
                {
                    if (salaryScaleService.saveNewTmpSalaryScale(currentTempSalaryScale, ActionType.CREATE)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Salary Scale", "New Salary Scale added.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Salary Scale", "Save Failed.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }
                currentTempSalaryScale = new SalaryScale();
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Salary Scale", "The selected row is already submitted.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnSubmit_Handler() {
        if (selectedTempSalaryScale != null) {
            if (selectedTempSalaryScale.getTmpStatus() != TempStatus.SUBMITTED) {
                selectedTempSalaryScale.setTmpStatus(TempStatus.SUBMITTED);
                if (salaryScaleService.updateTmpSalaryScale(selectedTempSalaryScale)) {
                    //update successful   
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "Row submitted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    //update has failed, undo the change
                    selectedTempSalaryScale.setTmpStatus(TempStatus.EDITABLE);
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
        if (selectedTempSalaryScale != null) {
            if (selectedTempSalaryScale.getTmpStatus() != TempStatus.SUBMITTED) {
                if (salaryScaleService.delete(selectedTempSalaryScale)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "Row is deleted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    currentTempSalaryScale = new SalaryScale();
                    tempSalaryScalesList.remove(selectedTempSalaryScale);
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

    public void btnEditSelectedSalaryScale_Handler() {
        SalaryScale salaryScaleTmp = new SalaryScale();
        if (selectedSalaryScale != null) {
            if (!salaryScaleService.checkIfSalaryScaleHasPendingEdit(selectedSalaryScale.getID())) {
                salaryScaleTmp.setSalaryScaleId(selectedSalaryScale.getID());
                salaryScaleTmp.setGrade(selectedSalaryScale.getGrade());
                salaryScaleTmp.setLevel(selectedSalaryScale.getLevel());
                salaryScaleTmp.setStepIncrement(selectedSalaryScale.getStepIncrement());
                currentTempSalaryScale = salaryScaleTmp;
            } else {
                System.out.println("SalaryScale has pending changes");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit Salary Scale", "The selected salary scale has pending changes. Please approve or cancel the changes and try again.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }
}