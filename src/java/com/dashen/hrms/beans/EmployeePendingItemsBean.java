/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.Configuration;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.service.EmployeeService;
import com.dashen.hrms.EmployeeTmp;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.Util;
import com.dashen.hrms.service.EmployeeTmpService;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mulugetak
 */
@Component
@SpringViewScope
public class EmployeePendingItemsBean implements Serializable {

    private List<EmployeeTmp> pendingEmployeeItems;
    List<EmployeeTmp> selectedEmployeeTmps;
    @Autowired
    private EmployeeTmpService empTmpService;

    @Autowired
    private EmployeeService empService;

    @PostConstruct
    public void init() {
        MyUser usr = CurrentUser.getCurrentUser();
        pendingEmployeeItems = empTmpService.listPendingTmpEmployeesForChecker(usr.getUsername());
    }

    public List<EmployeeTmp> getPendingEmployeeItems() {
        return pendingEmployeeItems;
    }

    public void setPendingEmployeeItems(List<EmployeeTmp> pendingEmployeeItems) {
        this.pendingEmployeeItems = pendingEmployeeItems;
    }

    public List<EmployeeTmp> getSelectedEmployeeTmps() {
        return selectedEmployeeTmps;
    }

    public void setSelectedEmployeeTmps(List<EmployeeTmp> selectedEmployeeTmps) {
        this.selectedEmployeeTmps = selectedEmployeeTmps;
    }

    public void approveSelectedRows() {
        if (selectedEmployeeTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rows Selected", "Please select rows!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            //approve the selected rows
            for (EmployeeTmp empT : selectedEmployeeTmps) {
                if (empT.getTmpStatus() == TempStatus.SUBMITTED) {
                    if (empT.getActionType() == ActionType.CREATE) {

                        if (empT.isTmpPhotoUploaded()) {
                            empT.setPhotoFileName("P" + Util.getDateString() + "." + Util.getFileExtension(empT.getTmpPhotoFileName()));
                        }
                        if (empT.isTmpMaritalStatusUploaded()) {
                            empT.setMaritalStatusFileName("M" + Util.getDateString() + "." + Util.getFileExtension(empT.getTmpMaritalStatusFileName()));
                        }
                        if (empService.saveNewEmployee(empT)) {
                            copyUploadedFiles(empT);
                            pendingEmployeeItems.remove(empT);
                            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Employee Approved", "New Employee creation approved.");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        } else {
                            empT.setPhotoFileName("");
                            empT.setMaritalStatusFileName("");
                        }
                    } else if (empT.getActionType() == ActionType.UPDATE) //existing employee was updated
                    {
                        String currentPhotoFileName = empT.getPhotoFileName();
                        String currentMaritalStatusFileName = empT.getMaritalStatusFileName();
                        if (empT.isTmpPhotoUploaded()) {
                            if (empT.getPhotoFileName() != null && !empT.getPhotoFileName().isEmpty()) {
                                //delete the current profile photo
                            }
                            empT.setPhotoFileName("P" + Util.getDateString() + "." + Util.getFileExtension(empT.getTmpPhotoFileName()));
                        }
                        if (empT.isTmpMaritalStatusUploaded()) {
                            if (empT.getMaritalStatusFileName() != null && !empT.getMaritalStatusFileName().isEmpty()) {
                                //delete the current marital status document
                            }
                            empT.setMaritalStatusFileName("M" + Util.getDateString() + "." + Util.getFileExtension(empT.getTmpMaritalStatusFileName()));
                        }
                        if (empService.updateEmployee(empT)) {
                            copyUploadedFiles(empT);
                            pendingEmployeeItems.remove(empT);
                            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Employee Update Approved", "Employee information update is approved.");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        } else {
                            //update failed, so set the changes to the original one.
                            empT.setPhotoFileName(currentPhotoFileName);
                            empT.setMaritalStatusFileName(currentMaritalStatusFileName);
                        }
                    } 
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }

    private void copyUploadedFiles(EmployeeTmp empT) {
        //check if profile photo is uploaded and copy it
        if (empT.isTmpPhotoUploaded()) {
            Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.PROFILE_PHOTO_PATH));

            Path pathToCopyTo = rootRealPath.resolve(empT.getPhotoFileName());
            Path pathToCopyFrom = rootRealPath.resolve(empT.getTmpPhotoFileName());

            try {
                Files.copy(pathToCopyFrom, pathToCopyTo, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception ex) {

            }
        }
        //check if marital status document is uploaded and copy it
        if (empT.isTmpMaritalStatusUploaded()) {
            Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.MARITAL_STATUS_DOCUMENTS_PATH));

            Path pathToCopyTo = rootRealPath.resolve(empT.getMaritalStatusFileName());
            Path pathToCopyFrom = rootRealPath.resolve(empT.getTmpMaritalStatusFileName());

            try {
                Files.copy(pathToCopyFrom, pathToCopyTo, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception ex) {

            }
        }
    }

    public void makeEditableByMaker() {
        if (selectedEmployeeTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            for (EmployeeTmp empT : selectedEmployeeTmps) {
                TempStatus original = empT.getTmpStatus();
                if (TempStatus.SUBMITTED == original) {
                    empT.setTmpStatus(TempStatus.EDITABLE);
                    if (empTmpService.updateTmpEmployee(empT)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Status", "Selected row is made editable.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        empT.setTmpStatus(original);
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Change Status", "Unable to make the selected row editable.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Status Change Not Allowed", "The status change is not allowed for the selected row.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }
}
