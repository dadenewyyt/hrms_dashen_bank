/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.Configuration;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.RefereeTmp;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.Util;
import com.dashen.hrms.service.RefereeService;
import com.dashen.hrms.service.RefereeTmpService;
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
public class RefereePendingItemsBean implements Serializable {

    private List<RefereeTmp> pendingTmpReferees;
    RefereeTmp selectedRefereeTmp;

    @Autowired
    private RefereeTmpService refTmpService;

    @Autowired
    private RefereeService refService;

    @PostConstruct
    public void init() {
        MyUser usr = CurrentUser.getCurrentUser();
        pendingTmpReferees = refTmpService.listTmpRefereesForChecker(usr.getUsername());
    }

    public RefereeTmp getSelectedRefereeTmp() {
        return selectedRefereeTmp;
    }

    public void setSelectedRefereeTmp(RefereeTmp selectedRefereeTmp) {
        this.selectedRefereeTmp = selectedRefereeTmp;
    }

    public List<RefereeTmp> getPendingTmpReferees() {
        return pendingTmpReferees;
    }

    public void setPendingTmpReferees(List<RefereeTmp> pendingTmpReferees) {
        this.pendingTmpReferees = pendingTmpReferees;
    }

    public void approveSelectedRows() {
        if (selectedRefereeTmp == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row Selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else //approve the selected row           
        {
            if (selectedRefereeTmp.getTmpStatus() == TempStatus.SUBMITTED) {
                if (selectedRefereeTmp.getActionType() == ActionType.CREATE) {
                    if (selectedRefereeTmp.isTmpDocumentUploaded()) {
                        selectedRefereeTmp.setFileName("REF" + Util.getDateString() + "." + Util.getFileExtension(selectedRefereeTmp.getTmpDocumentFileName()));
                    }
                    refService.saveNewReferee(selectedRefereeTmp);
                    pendingTmpReferees.remove(selectedRefereeTmp);
                } else if (selectedRefereeTmp.getActionType() == ActionType.UPDATE) //existing referee was updated
                {
                    if (selectedRefereeTmp.isTmpDocumentUploaded()) {
                        if (selectedRefereeTmp.getFileName() != null && !selectedRefereeTmp.getFileName().isEmpty()) {
                            //delete the current document
                        }
                        selectedRefereeTmp.setFileName("REF" + Util.getDateString() + "." + Util.getFileExtension(selectedRefereeTmp.getTmpDocumentFileName()));
                    }
                    refService.updateReferee(selectedRefereeTmp);
                    pendingTmpReferees.remove(selectedRefereeTmp);
                }
                //check if document is uploaded and copy it
                if (selectedRefereeTmp.isTmpDocumentUploaded()) {
                    Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.REFEREE_DOCUMENTS_PATH));

                    Path pathToCopyTo = rootRealPath.resolve(selectedRefereeTmp.getFileName());
                    Path pathToCopyFrom = rootRealPath.resolve(selectedRefereeTmp.getTmpDocumentFileName());

                    try {
                        Files.copy(pathToCopyFrom, pathToCopyTo, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception ex) {

                    }
                }
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "Referee approved.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void makeEditableByMaker() {
        if (selectedRefereeTmp == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else if (TempStatus.SUBMITTED == selectedRefereeTmp.getTmpStatus()) {
            TempStatus original = selectedRefereeTmp.getTmpStatus();
            selectedRefereeTmp.setTmpStatus(TempStatus.EDITABLE);
            if (refTmpService.updateTmpReferee(selectedRefereeTmp)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Status", "Selected row is made editable.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                selectedRefereeTmp.setTmpStatus(original);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Change Status", "Unable to make the selected row editable.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Change Status", "Status cannot be changed for the selected row.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
