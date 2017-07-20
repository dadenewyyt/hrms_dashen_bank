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
import com.dashen.hrms.QualificationTmp;
import com.dashen.hrms.Referee;
import com.dashen.hrms.RefereeTmp;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.Util;
import com.dashen.hrms.service.EmployeeService;
import com.dashen.hrms.service.RefereeService;
import com.dashen.hrms.service.RefereeTmpService;
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
public class EditEmployeeRefereeBean implements Serializable {

    private String employeeSerialID;

    @Autowired
    EmployeeService empService;

    Employee employee;

    private RefereeTmp currentTempRef;

    @Autowired
    private RefereeTmpService refTmpService;

    @Autowired
    private RefereeService refService;

    private List<Referee> employeeRefereesList;
    private Referee selectedReferee;

    private List<RefereeTmp> tempEmployeeRefereesList;
    private RefereeTmp selectedTempReferee;

    public Employee getEmployee() {
        return employee;
    }

    public RefereeTmp getCurrentTempRef() {
        return currentTempRef;
    }

    public void setCurrentTempRef(RefereeTmp currentTempRef) {
        this.currentTempRef = currentTempRef;
    }

    public List<Referee> getEmployeeRefereesList() {
        return employeeRefereesList;
    }

    public void setEmployeeRefereesList(List<Referee> employeeRefereesList) {
        this.employeeRefereesList = employeeRefereesList;
    }

    public Referee getSelectedReferee() {
        return selectedReferee;
    }

    public void setSelectedReferee(Referee selectedReferee) {
        this.selectedReferee = selectedReferee;
    }

    public List<RefereeTmp> getTempEmployeeRefereesList() {
        return tempEmployeeRefereesList;
    }

    public void setTempEmployeeRefereesList(List<RefereeTmp> tempEmployeeRefereesList) {
        this.tempEmployeeRefereesList = tempEmployeeRefereesList;
    }

    public RefereeTmp getSelectedTempReferee() {
        return selectedTempReferee;
    }

    public void setSelectedTempReferee(RefereeTmp selectedTempReferee) {
        this.selectedTempReferee = selectedTempReferee;
    }

    @PostConstruct
    public void init() {
        employeeSerialID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (employeeSerialID != null && !employeeSerialID.isEmpty()) {
            employee = empService.getByEmployeeSerialID(employeeSerialID);
        } else {
        }
        currentTempRef = new RefereeTmp();

        employeeRefereesList = refService.listRefereesForEmployee(employee.getEmployeeSerialID());
        tempEmployeeRefereesList = new ArrayList<>();
    }

    public void btnNewReferee_Handler() {
        currentTempRef = new RefereeTmp();
    }

    public boolean isRowSubmitted() {
        if (currentTempRef != null && currentTempRef.getTmpStatus() == TempStatus.EDITABLE) {
            return false;
        }
        return true;
    }

    public void btnRefreshList_Handler() {
        MyUser us = CurrentUser.getCurrentUser();
        tempEmployeeRefereesList = refTmpService.listTmpRefereesForMaker(us.getUsername());
    }

    public void tmpRefereesDataTableDataTable_rowSelected(SelectEvent evt) {
        currentTempRef = selectedTempReferee;
    }

    public void btnSaveReferee_Handler() {
        if (currentTempRef.getTmpStatus() == TempStatus.EDITABLE) {
            if (currentTempRef.getId() != null && !currentTempRef.getId().isEmpty()) {
                //is updating existing Temp referee record
                if (refTmpService.updateTmpReferee(currentTempRef)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Referee update", "Referee updates saved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Referee update", "Update Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else if (currentTempRef.getRefereeID() != null && !currentTempRef.getRefereeID().isEmpty()) {//editing existing referee, tmp object is new
                if (refTmpService.saveNewTmpReferee(currentTempRef, ActionType.UPDATE)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Referee update", "Referee updates saved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Referee update", "Update Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else//new referee, and new tmp object
            {
                currentTempRef.setEmployeeSerialID(employee.getEmployeeSerialID());
                if (refTmpService.saveNewTmpReferee(currentTempRef, ActionType.CREATE)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Referee", "New Referee added.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Referee", "Save Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Referee", "The selected row is already submitted.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        currentTempRef = new RefereeTmp();
    }

    public void btnSubmit_Handler() {
        if (selectedTempReferee != null) {
            if (selectedTempReferee.getTmpStatus() == TempStatus.EDITABLE) {
                selectedTempReferee.setTmpStatus(TempStatus.SUBMITTED);
                if (refTmpService.updateTmpReferee(selectedTempReferee)) {
                    //update successful   
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "Row submitted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    //update has failed, undo the change
                    selectedTempReferee.setTmpStatus(TempStatus.EDITABLE);
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
        if (selectedTempReferee != null) {
            if (selectedTempReferee.getTmpStatus() == TempStatus.EDITABLE) {
                if (refTmpService.delete(selectedTempReferee)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "Row is deleted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    currentTempRef = new RefereeTmp();
                    tempEmployeeRefereesList.remove(selectedTempReferee);
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

    public void btnEditSelectedReferee_Handler() {
        RefereeTmp rTmp = new RefereeTmp();
        if (selectedReferee != null) {
            if (!refTmpService.checkIfRefereeHasPendingEdit(selectedReferee.getId())) {
                rTmp.setRefereeID(selectedReferee.getId());
                rTmp.setEmployeeSerialID(selectedReferee.getEmployeeSerialID());
                rTmp.setFirstName(selectedReferee.getFirstName());
                rTmp.setMiddleName(selectedReferee.getMiddleName());
                rTmp.setLastName(selectedReferee.getLastName());
                rTmp.setSalary(selectedReferee.getSalary());
                rTmp.setKebeleId(selectedReferee.getKebeleId());
                rTmp.setResidentialAddress(selectedReferee.getResidentialAddress());
                rTmp.setEmployer(selectedReferee.getEmployer());
                rTmp.setEmployerAddress(selectedReferee.getEmployerAddress());
                rTmp.setFileName(selectedReferee.getFileName());
                currentTempRef = rTmp;
            } else {
                System.out.println("Referee has pending changes");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit Referee", "The selected referee has pending changes. Please approve or cancel the changes and try again.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void upload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        if (uploadedFile != null) {
            String fileName = "RF" + Util.getDateString() + "." + Util.getFileExtension(uploadedFile.getFileName());
            Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.REFEREE_DOCUMENTS_PATH));
            Path pathToSaveTo = rootRealPath.resolve(fileName);
            try (OutputStream strm = Files.newOutputStream(pathToSaveTo, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                strm.write(uploadedFile.getContents());
                currentTempRef.setTmpDocumentUploaded(true);
                currentTempRef.setTmpDocumentFileName(fileName);
                if (refTmpService.updateTmpReferee(currentTempRef)) {
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
        if (selectedTempReferee != null) {
            if (selectedTempReferee.getTmpStatus() == TempStatus.EDITABLE) {
                if (selectedTempReferee.isTmpDocumentUploaded() == true) {
                    String fileName = selectedTempReferee.getTmpDocumentFileName();
                    Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.REFEREE_DOCUMENTS_PATH));
                    Path pathToDeleteFrom = rootRealPath.resolve(fileName);
                    selectedTempReferee.setTmpDocumentUploaded(false);
                    selectedTempReferee.setTmpDocumentFileName("");
                    if (refTmpService.updateTmpReferee(selectedTempReferee)) {
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
