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
import com.dashen.hrms.EmployeeTmp;
import com.dashen.hrms.JobClassification;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.Util;
import com.dashen.hrms.service.EmployeeTmpService;
import com.dashen.hrms.service.JobClassificationService;
import java.io.IOException;
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
public class EditEmployeesBean implements Serializable {

    private EmployeeTmp currentTempEmp;

    @Autowired
    private EmployeeTmpService empTmpService;
    @Autowired
    private JobClassificationService jbClService;

    private List<SelectItem> jobClassificationList;

    private List<EmployeeTmp> tmpEmployeesList;
    private EmployeeTmp selectedTmpEmployee;

    private Employee selectedEmployeeFromSearchResults;

    public EmployeeTmp getCurrentTempEmp() {
        return currentTempEmp;
    }

    public void setCurrentTempEmp(EmployeeTmp currentTempEmp) {
        this.currentTempEmp = currentTempEmp;
    }

    public void setEmpTmpService(EmployeeTmpService empTmpService) {
        this.empTmpService = empTmpService;
    }

    public void setJbClService(JobClassificationService jbClService) {
        this.jbClService = jbClService;
    }

    public List<SelectItem> getJobClassificationList() {
        return jobClassificationList;
    }

    public void setJobClassificationList(List<SelectItem> jobClassificationList) {
        this.jobClassificationList = jobClassificationList;
    }

    public List<EmployeeTmp> getTmpEmployeesList() {
        return tmpEmployeesList;
    }

    public void setTmpEmployeesList(List<EmployeeTmp> tmpEmployeesList) {
        this.tmpEmployeesList = tmpEmployeesList;
    }

    public EmployeeTmp getSelectedTmpEmployee() {
        return selectedTmpEmployee;
    }

    public void setSelectedTmpEmployee(EmployeeTmp selectedTmpEmployee) {
        this.selectedTmpEmployee = selectedTmpEmployee;
    }

    public Employee getSelectedEmployeeFromSearchResults() {
        return selectedEmployeeFromSearchResults;
    }

    public void setSelectedEmployeeFromSearchResults(Employee selectedEmployeeFromSearchResults) {
        this.selectedEmployeeFromSearchResults = selectedEmployeeFromSearchResults;
    }

    @PostConstruct
    public void init() {
        currentTempEmp = new EmployeeTmp();
        jobClassificationList = new ArrayList<>();
        List<JobClassification> jbClList = jbClService.listAll();
        for (JobClassification jbCl : jbClList) {
            jobClassificationList.add(new SelectItem(jbCl.getId(), jbCl.getDescription()));
        }
        tmpEmployeesList = new ArrayList<>();
    }

    public void btnNewEmployee_Handler() {
        currentTempEmp = new EmployeeTmp();
    }

    public boolean isRowSubmitted() {
        if (currentTempEmp != null && currentTempEmp.getTmpStatus() == TempStatus.EDITABLE) {
            return false;
        }
        return true;
    }

    public void btnSaveEmployee_Handler() {
        if (currentTempEmp.getTmpStatus() == TempStatus.EDITABLE) {
            if (currentTempEmp.getSerialID() != null && !currentTempEmp.getSerialID().isEmpty()) {
                //is updating existing Temp employee record
                if (empTmpService.updateTmpEmployee(currentTempEmp)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Employee update", "Employee updates saved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Employee update", "Update Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else if (currentTempEmp.getEmployeeSerialID() != null && !currentTempEmp.getEmployeeSerialID().isEmpty()) {//editing existing employee, tmp object is new
                if (empTmpService.saveNewTmpEmployee(currentTempEmp, ActionType.UPDATE)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Employee update", "Employee updates saved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Employee update", "Update Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else//new Employee, and new tmp object
             if (empTmpService.saveNewTmpEmployee(currentTempEmp, ActionType.CREATE)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Employee", "New employee added.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Employee", "Save Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Employee", "The selected row is already submitted.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        currentTempEmp = new EmployeeTmp();
    }

    public void btnRefreshList_Handler() {
        MyUser us = CurrentUser.getCurrentUser();
        tmpEmployeesList = empTmpService.listPendingTmpEmployeesForMaker(us.getUsername());
    }

    public void btnSubmit_Handler() {
        if (selectedTmpEmployee != null) {
            if (selectedTmpEmployee.getTmpStatus() != TempStatus.SUBMITTED) {
                selectedTmpEmployee.setTmpStatus(TempStatus.SUBMITTED);
                if (empTmpService.updateTmpEmployee(selectedTmpEmployee)) {
                    //update successful   
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "Row submitted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    //update has failed, undo the change
                    selectedTmpEmployee.setTmpStatus(TempStatus.EDITABLE);
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
        if (selectedTmpEmployee != null) {
            if (selectedTmpEmployee.getTmpStatus() == TempStatus.EDITABLE) {
                if (empTmpService.delete(selectedTmpEmployee)) {
                    if (selectedTmpEmployee.isTmpPhotoUploaded() == true) {
                        //remove uploaded photo if any
                        String fileName = selectedTmpEmployee.getTmpPhotoFileName();
                        Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.PROFILE_PHOTO_PATH));
                        Path pathToDeleteFrom = rootRealPath.resolve(fileName);
                        try {
                            Files.deleteIfExists(pathToDeleteFrom);
                        } catch (Exception ex) {

                        }
                    }
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "Row is deleted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    currentTempEmp = new EmployeeTmp();
                    tmpEmployeesList.remove(selectedTmpEmployee);
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

    public void employeesDataTable_rowSelected(SelectEvent evt) {
        currentTempEmp = selectedTmpEmployee;
    }

    public void btnEditSelectedEmployeeFromSerchResutls_Handler() {
        EmployeeTmp empt = new EmployeeTmp();
        System.out.println("Edit after search called.");
        if (selectedEmployeeFromSearchResults != null) {
            if (!empTmpService.checkIfEmployeeHasPendingEdit(selectedEmployeeFromSearchResults.getEmployeeSerialID())) {
                empt.setEmployeeSerialID(selectedEmployeeFromSearchResults.getEmployeeSerialID());
                empt.setFirstName(selectedEmployeeFromSearchResults.getFirstName());
                empt.setMiddleName(selectedEmployeeFromSearchResults.getMiddleName());
                empt.setLastName(selectedEmployeeFromSearchResults.getLastName());
                empt.setFileIndexNumber(selectedEmployeeFromSearchResults.getFileIndexNumber());
                empt.setEmployeeID(selectedEmployeeFromSearchResults.getEmployeeID());
                empt.setGender(selectedEmployeeFromSearchResults.getGender());
                empt.setDateOfBirth(selectedEmployeeFromSearchResults.getDateOfBirth());
                empt.setDateOfEmployement(selectedEmployeeFromSearchResults.getDateOfEmployement());
                empt.setJobClasificationID(selectedEmployeeFromSearchResults.getJobClasificationID());
                empt.setEmergecyContact(selectedEmployeeFromSearchResults.getEmergecyContact());
                empt.setFirstName(selectedEmployeeFromSearchResults.getFirstName());
                empt.setPhotoFileName(selectedEmployeeFromSearchResults.getPhotoFileName());
                empt.setStatus(selectedEmployeeFromSearchResults.getStatus());
                empt.setDatePermanent(selectedEmployeeFromSearchResults.getDatePermanent());
                empt.setMaritalStatus(selectedEmployeeFromSearchResults.getMaritalStatus());
                empt.setMaritalStatusFileName(selectedEmployeeFromSearchResults.getMaritalStatusFileName());
                currentTempEmp = empt;
                System.out.println("Changed editing object");
            } else {
                System.out.println("Empoloyee has pending changes");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit Employee", "The selected employee has pending changes. Please approve or cancel the changes and try again.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void uploadProfilePhoto_Handler(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        if (uploadedFile != null) {
            String fileName = "P" + Util.getDateString() + "." + Util.getFileExtension(uploadedFile.getFileName());
            Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.PROFILE_PHOTO_PATH));
            Path pathToSaveTo = rootRealPath.resolve(fileName);
            try (OutputStream strm = Files.newOutputStream(pathToSaveTo, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                strm.write(uploadedFile.getContents());
                boolean currentTmpPhotoUploaded = selectedTmpEmployee.isTmpPhotoUploaded();
                String currentTmpPhotoFileName = selectedTmpEmployee.getTmpPhotoFileName();
                selectedTmpEmployee.setTmpPhotoUploaded(true);
                selectedTmpEmployee.setTmpPhotoFileName(fileName);
                if (empTmpService.updateTmpEmployee(selectedTmpEmployee)) {
                    FacesMessage message = new FacesMessage("Upload Successful", uploadedFile.getFileName() + " is uploaded.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else {
                    //database is not updated, un do the changes on the object and remove the uploaded file
                    selectedTmpEmployee.setTmpPhotoUploaded(currentTmpPhotoUploaded);
                    selectedTmpEmployee.setTmpPhotoFileName(currentTmpPhotoFileName);
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
        if (selectedTmpEmployee != null) {
            if (selectedTmpEmployee.getTmpStatus() == TempStatus.EDITABLE) {
                if (selectedTmpEmployee.isTmpPhotoUploaded() == true) {
                    String fileName = selectedTmpEmployee.getTmpPhotoFileName();
                    Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.PROFILE_PHOTO_PATH));
                    Path pathToDeleteFrom = rootRealPath.resolve(fileName);
                    boolean currentTmpPhotoUploaded = selectedTmpEmployee.isTmpPhotoUploaded();
                    String currentTmpPhotoFileName = selectedTmpEmployee.getTmpPhotoFileName();
                    selectedTmpEmployee.setTmpPhotoUploaded(false);
                    selectedTmpEmployee.setTmpPhotoFileName("");
                    if (empTmpService.updateTmpEmployee(selectedTmpEmployee)) {
                        try {
                            Files.deleteIfExists(pathToDeleteFrom);
                        } catch (Exception ex) {

                        }
                        FacesMessage message = new FacesMessage("Remove Successful", "Uploaded profile Photo is removed.");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    } else {
                        //database is not updated report failur   
                        selectedTmpEmployee.setTmpPhotoUploaded(currentTmpPhotoUploaded);
                        selectedTmpEmployee.setTmpPhotoFileName(currentTmpPhotoFileName);
                        FacesMessage message = new FacesMessage("Remove Failed", "Failed to update database. Try again. ");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Uploaded Photo", "Selected row has no uploaded photo.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Remove Uploaded Photo", "Profile Photo cannot be removed for submitted rows.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Remove Uploaded Photo", "No row selected.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void uploadMaritalStatusDocument_Handler(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        if (uploadedFile != null) {
            String fileName = "M" + Util.getDateString() + "." + Util.getFileExtension(uploadedFile.getFileName());
            Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.MARITAL_STATUS_DOCUMENTS_PATH));
            Path pathToSaveTo = rootRealPath.resolve(fileName);
            try (OutputStream strm = Files.newOutputStream(pathToSaveTo, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                strm.write(uploadedFile.getContents());
                boolean currentTmpMaritalStatusUploaded = selectedTmpEmployee.isTmpMaritalStatusUploaded();
                String currentTmpMaritalStatusFileName = selectedTmpEmployee.getTmpMaritalStatusFileName();
                selectedTmpEmployee.setTmpMaritalStatusUploaded(true);
                selectedTmpEmployee.setTmpMaritalStatusFileName(fileName);
                if (empTmpService.updateTmpEmployee(selectedTmpEmployee)) {
                    FacesMessage message = new FacesMessage("Upload Successful", uploadedFile.getFileName() + " is uploaded.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else {
                    //database is not updated remove the uploaded file
                    selectedTmpEmployee.setTmpMaritalStatusUploaded(currentTmpMaritalStatusUploaded);
                    selectedTmpEmployee.setTmpMaritalStatusFileName(currentTmpMaritalStatusFileName);
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

    public void btnRemoveUploadedMaritalStatusDocument_Handler() {
        if (selectedTmpEmployee != null) {
            if (selectedTmpEmployee.getTmpStatus() == TempStatus.EDITABLE) {
                if (selectedTmpEmployee.isTmpMaritalStatusUploaded() == true) {
                    String fileName = selectedTmpEmployee.getTmpMaritalStatusFileName();
                    Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.MARITAL_STATUS_DOCUMENTS_PATH));
                    Path pathToDeleteFrom = rootRealPath.resolve(fileName);
                    boolean currentTmpMaritalStatusUploaded = selectedTmpEmployee.isTmpMaritalStatusUploaded();
                    String currentTmpMaritalStatusFileName = selectedTmpEmployee.getTmpMaritalStatusFileName();
                    selectedTmpEmployee.setTmpMaritalStatusUploaded(false);
                    selectedTmpEmployee.setTmpMaritalStatusFileName("");
                    if (empTmpService.updateTmpEmployee(selectedTmpEmployee)) {
                        try {
                            Files.deleteIfExists(pathToDeleteFrom);
                        } catch (Exception ex) {

                        }
                        FacesMessage message = new FacesMessage("Remove Successful", "Uploaded document is removed.");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    } else {
                        //database is not updated report failur  
                        selectedTmpEmployee.setTmpMaritalStatusUploaded(currentTmpMaritalStatusUploaded);
                        selectedTmpEmployee.setTmpMaritalStatusFileName(currentTmpMaritalStatusFileName);
                        FacesMessage message = new FacesMessage("Remove Failed", "Failed to update database. Try again. ");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Uploaded Document", "Selected row has no uploaded document.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Remove Uploaded Document", "Uploaded document cannot be removed for submitted rows.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Remove Uploaded Document", "No row selected.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
