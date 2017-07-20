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
import com.dashen.hrms.Institution;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.Qualification;
import com.dashen.hrms.QualificationTmp;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.Util;
import com.dashen.hrms.service.EmployeeService;
import com.dashen.hrms.service.InstitutionService;
import com.dashen.hrms.service.QualificationService;
import com.dashen.hrms.service.QualificationTmpService;
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
 * @author MulugetaK
 */
@Component
@SpringViewScope
public class EditEmployeeQualificationBean implements Serializable {

    private String employeeSerialID;

    @Autowired
    EmployeeService empService;

    Employee employee;

    private QualificationTmp currentTempQual;

    @Autowired
    private QualificationTmpService qualTmpService;

    @Autowired
    private QualificationService qualService;

    @Autowired
    private InstitutionService instService;

    private List<Qualification> employeeQualificationsList;
    private Qualification selectedQualification;

    private List<QualificationTmp> tempEmployeeQualificationsList;
    private QualificationTmp selectedTempQualification;

    private List<SelectItem> institutionsList;

    public QualificationTmp getCurrentTempQual() {
        return currentTempQual;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setCurrentTempQual(QualificationTmp currentTempQual) {
        this.currentTempQual = currentTempQual;
    }

    public void setQualTmpService(QualificationTmpService qualTmpService) {
        this.qualTmpService = qualTmpService;
    }

    public void setQualService(QualificationService qualService) {
        this.qualService = qualService;
    }

    public void setInstService(InstitutionService instService) {
        this.instService = instService;
    }

    public List<Qualification> getEmployeeQualificationsList() {
        return employeeQualificationsList;
    }

    public void setEmployeeQualificationsList(List<Qualification> employeeQualificationsList) {
        this.employeeQualificationsList = employeeQualificationsList;
    }

    public Qualification getSelectedQualification() {
        return selectedQualification;
    }

    public void setSelectedQualification(Qualification selectedQualification) {
        this.selectedQualification = selectedQualification;
    }

    public List<QualificationTmp> getTempEmployeeQualificationsList() {
        return tempEmployeeQualificationsList;
    }

    public void setTempEmployeeQualificationsList(List<QualificationTmp> tempEmployeeQualificationsList) {
        this.tempEmployeeQualificationsList = tempEmployeeQualificationsList;
    }

    public QualificationTmp getSelectedTempQualification() {
        return selectedTempQualification;
    }

    public void setSelectedTempQualification(QualificationTmp selectedTempQualification) {
        this.selectedTempQualification = selectedTempQualification;
    }

    public List<SelectItem> getInstitutionsList() {
        return institutionsList;
    }

    public void setInstitutionsList(List<SelectItem> institutionsList) {
        this.institutionsList = institutionsList;
    }

    @PostConstruct
    public void init() {
        employeeSerialID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (employeeSerialID != null && !employeeSerialID.isEmpty()) {
            employee = empService.getByEmployeeSerialID(employeeSerialID);
        } else {
        }
        currentTempQual = new QualificationTmp();
        institutionsList = new ArrayList<>();

        List<Institution> inst = instService.listAll();
        for (Institution ins : inst) {
            institutionsList.add(new SelectItem(ins.getId(), ins.getName()));
        }
        employeeQualificationsList = qualService.listQualificationsForEmployee(employee.getEmployeeSerialID());
        tempEmployeeQualificationsList = new ArrayList<>();
    }

    public void btnNewQualification_Handler() {
        currentTempQual = new QualificationTmp();
    }

    public boolean isRowSubmitted() {
        if (currentTempQual != null && currentTempQual.getTmpStatus() == TempStatus.EDITABLE) {
            return false;
        }
        return true;
    }

    public void btnRefreshList_Handler() {
        MyUser us = CurrentUser.getCurrentUser();
        tempEmployeeQualificationsList = qualTmpService.listTmpQualificationsForMaker(us.getUsername());
    }

    public void tmpQualificationsDataTableDataTable_rowSelected(SelectEvent evt) {
        currentTempQual = selectedTempQualification;
    }

    public void btnSaveQualification_Handler() {
        if (currentTempQual.getTmpStatus() == TempStatus.EDITABLE) {
            if (currentTempQual.getId() != null && !currentTempQual.getId().isEmpty()) {
                //is updating existing Temp qualification record
                if (qualTmpService.updateTmpQualification(currentTempQual)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Qualification update", "Qualification updates saved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Qualification update", "Update Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else if (currentTempQual.getQualificationId() != null && !currentTempQual.getQualificationId().isEmpty()) {//editing existing qualification, tmp object is new
                if (qualTmpService.saveNewTmpQualification(currentTempQual, ActionType.UPDATE)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Qualification update", "Qualification updates saved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Qualification update", "Update Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else//new qualification, and new tmp object
            {
                currentTempQual.setEmployeeSerialId(employee.getEmployeeSerialID());
                if (qualTmpService.saveNewTmpQualification(currentTempQual, ActionType.CREATE)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Qualification", "New Qualification added.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Qualification", "Save Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Qualification", "The selected row is already submitted.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        currentTempQual = new QualificationTmp();
    }

    public void btnSubmit_Handler() {
        if (selectedTempQualification != null) {
            if (selectedTempQualification.getTmpStatus() == TempStatus.EDITABLE) {
                selectedTempQualification.setTmpStatus(TempStatus.SUBMITTED);
                if (qualTmpService.updateTmpQualification(selectedTempQualification)) {
                    //update successful   
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "Row submitted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    //update has failed, undo the change
                    selectedTempQualification.setTmpStatus(TempStatus.EDITABLE);
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
        if (selectedTempQualification != null) {
            if (selectedTempQualification.getTmpStatus() == TempStatus.EDITABLE) {
                if (qualTmpService.delete(selectedTempQualification)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "Row is deleted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    currentTempQual = new QualificationTmp();
                    tempEmployeeQualificationsList.remove(selectedTempQualification);
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

    public void btnEditSelectedQualification_Handler() {
        QualificationTmp qTmp = new QualificationTmp();
        if (selectedQualification != null) {
            if (!qualTmpService.checkIfQualificationHasPendingEdit(selectedQualification.getId())) {
                qTmp.setQualificationId(selectedQualification.getId());
                qTmp.setEmployeeSerialId(selectedQualification.getEmployeeSerialID());
                qTmp.setQualification(selectedQualification.getQualification());
                qTmp.setQualificationLevel(selectedQualification.getQualificationLevel());
                qTmp.setCGPA(selectedQualification.getCGPA());
                qTmp.setInstitutionID(selectedQualification.getInstitutionID());
                qTmp.setStartDate(selectedQualification.getStartDate());
                qTmp.setDateOfGraduation(selectedQualification.getDateOfGraduation());
                qTmp.setFileName(selectedQualification.getFileName());
                qTmp.setStatus(selectedQualification.getStatus());
                currentTempQual = qTmp;
            } else {
                System.out.println("Qualification has pending changes");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit Qualification", "The selected qualification has pending changes. Please approve or cancel the changes and try again.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void upload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        if (uploadedFile != null) {
            String fileName = "QD" + Util.getDateString() + "." + Util.getFileExtension(uploadedFile.getFileName());
            Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.QUALIFICATION_DOCUMENTS_PATH));
            Path pathToSaveTo = rootRealPath.resolve(fileName);
            try (OutputStream strm = Files.newOutputStream(pathToSaveTo, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                strm.write(uploadedFile.getContents());
                currentTempQual.setTmpDocumentUploaded(true);
                currentTempQual.setTmpDocumentFileName(fileName);
                if (qualTmpService.updateTmpQualification(currentTempQual)) {
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
        if (selectedTempQualification != null) {
            if (selectedTempQualification.getTmpStatus() == TempStatus.EDITABLE) {
                if (selectedTempQualification.isTmpDocumentUploaded() == true) {
                    String fileName = selectedTempQualification.getTmpDocumentFileName();
                    Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.QUALIFICATION_DOCUMENTS_PATH));
                    Path pathToDeleteFrom = rootRealPath.resolve(fileName);
                    selectedTempQualification.setTmpDocumentUploaded(false);
                    selectedTempQualification.setTmpDocumentFileName("");
                    if (qualTmpService.updateTmpQualification(selectedTempQualification)) {
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
