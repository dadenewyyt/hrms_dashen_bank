/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.Configuration;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.Employee;
import com.dashen.hrms.Experience;
import com.dashen.hrms.ExperienceTmp;
import com.dashen.hrms.Institution;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.QualificationTmp;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.Util;
import com.dashen.hrms.service.EmployeeService;
import com.dashen.hrms.service.ExperienceService;
import com.dashen.hrms.service.ExperienceTmpService;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mulugetak
 */
@Component
@SpringViewScope
public class EditEmployeeExperienceBean implements Serializable {

    private String employeeSerialID;

    @Autowired
    EmployeeService empService;

    Employee employee;

    private ExperienceTmp currentTempExp;

    @Autowired
    private ExperienceTmpService expTmpService;

    @Autowired
    private ExperienceService expService;

    private List<Experience> employeeExperiencesList;
    private Experience selectedExperience;

    private List<ExperienceTmp> tempEmployeeExperiencesList;
    private ExperienceTmp selectedTempExperience;

    public Employee getEmployee() {
        return employee;
    }

    public ExperienceTmp getCurrentTempExp() {
        return currentTempExp;
    }

    public void setCurrentTempExp(ExperienceTmp currentTempExp) {
        this.currentTempExp = currentTempExp;
    }

    public List<Experience> getEmployeeExperiencesList() {
        return employeeExperiencesList;
    }

    public void setEmployeeExperiencesList(List<Experience> employeeExperiencesList) {
        this.employeeExperiencesList = employeeExperiencesList;
    }

    public Experience getSelectedExperience() {
        return selectedExperience;
    }

    public void setSelectedExperience(Experience selectedExperience) {
        this.selectedExperience = selectedExperience;
    }

    public List<ExperienceTmp> getTempEmployeeExperiencesList() {
        return tempEmployeeExperiencesList;
    }

    public void setTempEmployeeExperiencesList(List<ExperienceTmp> tempEmployeeExperiencesList) {
        this.tempEmployeeExperiencesList = tempEmployeeExperiencesList;
    }

    public ExperienceTmp getSelectedTempExperience() {
        return selectedTempExperience;
    }

    public void setSelectedTempExperience(ExperienceTmp selectedTempExperience) {
        this.selectedTempExperience = selectedTempExperience;
    }

    @PostConstruct
    public void init() {
        employeeSerialID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (employeeSerialID != null && !employeeSerialID.isEmpty()) {
            employee = empService.getByEmployeeSerialID(employeeSerialID);
        } else {
        }
        currentTempExp = new ExperienceTmp();

        employeeExperiencesList = expService.listExperiencesForEmployee(employee.getEmployeeSerialID());
        tempEmployeeExperiencesList = new ArrayList<>();
    }

    public void btnNewExperience_Handler() {
        currentTempExp = new ExperienceTmp();
    }

    public boolean isRowSubmitted() {
        if (currentTempExp != null && currentTempExp.getTmpStatus() == TempStatus.EDITABLE) {
            return false;
        }
        return true;
    }

    public void btnRefreshList_Handler() {
        MyUser us = CurrentUser.getCurrentUser();
        tempEmployeeExperiencesList = expTmpService.listTmpExperiencesForMaker(us.getUsername());
    }

    public void tmpExperiencesDataTableDataTable_rowSelected(SelectEvent evt) {
        currentTempExp = selectedTempExperience;
    }

    public void btnSaveExperience_Handler() {
        if (currentTempExp.getTmpStatus() == TempStatus.EDITABLE) {
            if (currentTempExp.getId() != null && !currentTempExp.getId().isEmpty()) {
                //is updating existing Temp experience record
                if (expTmpService.updateTmpExperience(currentTempExp)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Experience update", "Experience updates saved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Experience update", "Update Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else if (currentTempExp.getExperienceID() != null && !currentTempExp.getExperienceID().isEmpty()) {//editing existing experience, tmp object is new
                if (expTmpService.saveNewTmpExperience(currentTempExp, ActionType.UPDATE)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Experience update", "Experience updates saved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Experience update", "Update Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else//new qualification, and new tmp object
            {
                currentTempExp.setEmployeeSerialID(employee.getEmployeeSerialID());
                if (expTmpService.saveNewTmpExperience(currentTempExp, ActionType.CREATE)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Experience", "New Experience added.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Experience", "Save Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Experience", "The selected row is already submitted.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        currentTempExp = new ExperienceTmp();
    }

    public void btnSubmit_Handler() {
        if (selectedTempExperience != null) {
            if (selectedTempExperience.getTmpStatus() == TempStatus.EDITABLE) {
                selectedTempExperience.setTmpStatus(TempStatus.SUBMITTED);
                if (expTmpService.updateTmpExperience(selectedTempExperience)) {
                    //update successful   
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "Row submitted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    //update has failed, undo the change
                    selectedTempExperience.setTmpStatus(TempStatus.EDITABLE);
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
        if (selectedTempExperience != null) {
            if (selectedTempExperience.getTmpStatus() == TempStatus.EDITABLE) {
                if (expTmpService.delete(selectedTempExperience)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "Row is deleted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    currentTempExp = new ExperienceTmp();
                    tempEmployeeExperiencesList.remove(selectedTempExperience);
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

    public void btnEditSelectedExperience_Handler() {
        ExperienceTmp eTmp = new ExperienceTmp();
        if (selectedExperience != null) {
            if (!expTmpService.checkIfExperienceHasPendingEdit(selectedExperience.getId())) {
                eTmp.setExperienceID(selectedExperience.getId());
                eTmp.setEmployeeSerialID(selectedExperience.getEmployeeSerialID());
                eTmp.setNameOfEmployer(selectedExperience.getNameOfEmployer());
                eTmp.setEmployerAddress(selectedExperience.getEmployerAddress());
                eTmp.setPosition(selectedExperience.getPosition());
                eTmp.setStartDate(selectedExperience.getStartDate());
                eTmp.setEndDate(selectedExperience.getEndDate());
                eTmp.setTotalYearsOfExperience(selectedExperience.getTotalYearsOfExperience());
                eTmp.setReasonForResignation(selectedExperience.getReasonForResignation());
                eTmp.setFileName(selectedExperience.getFileName());

                currentTempExp = eTmp;
            } else {
                System.out.println("Experience has pending changes");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit Experience", "The selected experience has pending changes. Please approve or cancel the changes and try again.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void upload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        if (uploadedFile != null) {
            String fileName = "EX" + Util.getDateString() + "." + Util.getFileExtension(uploadedFile.getFileName());
            Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.EXPERIENCE_DOCUMENTS_PATH));
            Path pathToSaveTo = rootRealPath.resolve(fileName);
            try (OutputStream strm = Files.newOutputStream(pathToSaveTo, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                strm.write(uploadedFile.getContents());
                currentTempExp.setTmpDocumentUploaded(true);
                currentTempExp.setTmpDocumentFileName(fileName);
                if (expTmpService.updateTmpExperience(currentTempExp)) {
                    FacesMessage message = new FacesMessage("Upload Successful", uploadedFile.getFileName() + " is uploaded.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else {
                    //database is not updated remove the uploaded file
                    try {
                        Files.deleteIfExists(pathToSaveTo);
                    } catch (Exception ex) {

                    }
                    FacesMessage message = new FacesMessage("Upload Failed", "Failed to update database. Try again. ");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                FacesMessage message = new FacesMessage("Upload Failed", "Upload failed. Please try again. ");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage("Upload Failed", "Upload failed. Please try again. ");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void btnRemoveUploadedPhoto_Handler() {
        if (selectedTempExperience != null) {
            if (selectedTempExperience.getTmpStatus() == TempStatus.EDITABLE) {
                if (selectedTempExperience.isTmpDocumentUploaded() == true) {
                    String fileName = selectedTempExperience.getTmpDocumentFileName();
                    Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.EXPERIENCE_DOCUMENTS_PATH));
                    Path pathToDeleteFrom = rootRealPath.resolve(fileName);
                    selectedTempExperience.setTmpDocumentUploaded(false);
                    selectedTempExperience.setTmpDocumentFileName("");
                    if (expTmpService.updateTmpExperience(selectedTempExperience)) {
                        try {
                            Files.deleteIfExists(pathToDeleteFrom);
                        } catch (Exception ex) {

                        }
                        FacesMessage message = new FacesMessage("Remove Successful", "Uploaded document is removed.");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    } else {
                        //database is not updated report failur                    
                        FacesMessage message = new FacesMessage("Remove Failed", "Failed to update database. Try again. ");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Uploaded document", "Selected row has no uploaded document.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Remove Uploaded document", "Document cannot be removed for submitted rows.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Remove Uploaded document", "No row selected.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
