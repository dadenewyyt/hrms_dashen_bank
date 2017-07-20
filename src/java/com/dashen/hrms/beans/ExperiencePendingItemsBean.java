/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.Configuration;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.ExperienceTmp;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.Util;
import com.dashen.hrms.service.ExperienceService;
import com.dashen.hrms.service.ExperienceTmpService;
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
public class ExperiencePendingItemsBean implements Serializable {

    private List<ExperienceTmp> pendingTmpExperiences;
    ExperienceTmp selectedExperienceTmp;

    @Autowired
    private ExperienceTmpService expTmpService;

    @Autowired
    private ExperienceService expService;

    @PostConstruct
    public void init() {
        MyUser usr = CurrentUser.getCurrentUser();
        pendingTmpExperiences = expTmpService.listTmpExperiencesForChecker(usr.getUsername());
    }

    public List<ExperienceTmp> getPendingTmpExperiences() {
        return pendingTmpExperiences;
    }

    public void setPendingTmpExperiences(List<ExperienceTmp> pendingTmpExperiences) {
        this.pendingTmpExperiences = pendingTmpExperiences;
    }

    public ExperienceTmp getSelectedExperienceTmp() {
        return selectedExperienceTmp;
    }

    public void setSelectedExperienceTmp(ExperienceTmp selectedExperienceTmp) {
        this.selectedExperienceTmp = selectedExperienceTmp;
    }

    public void approveSelectedRows() {
        if (selectedExperienceTmp == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row Selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else //approve the selected row           
        {
            if (selectedExperienceTmp.getTmpStatus() == TempStatus.SUBMITTED) {
                if (selectedExperienceTmp.getActionType() == ActionType.CREATE) {
                    if (selectedExperienceTmp.isTmpDocumentUploaded()) {
                        selectedExperienceTmp.setFileName("EXP" + Util.getDateString() + "." + Util.getFileExtension(selectedExperienceTmp.getTmpDocumentFileName()));
                    }
                    expService.saveNewExperience(selectedExperienceTmp);
                    pendingTmpExperiences.remove(selectedExperienceTmp);
                } else if (selectedExperienceTmp.getActionType() == ActionType.UPDATE) //existing Experience was updated
                {
                    if (selectedExperienceTmp.isTmpDocumentUploaded()) {
                        if (selectedExperienceTmp.getFileName() != null && !selectedExperienceTmp.getFileName().isEmpty()) {
                            //delete the current document
                        }
                        selectedExperienceTmp.setFileName("EXP" + Util.getDateString() + "." + Util.getFileExtension(selectedExperienceTmp.getTmpDocumentFileName()));
                    }
                    expService.updateExperience(selectedExperienceTmp);
                    pendingTmpExperiences.remove(selectedExperienceTmp);
                }
                //check if document is uploaded and copy it
                if (selectedExperienceTmp.isTmpDocumentUploaded()) {
                    Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.EXPERIENCE_DOCUMENTS_PATH));

                    Path pathToCopyTo = rootRealPath.resolve(selectedExperienceTmp.getFileName());
                    Path pathToCopyFrom = rootRealPath.resolve(selectedExperienceTmp.getTmpDocumentFileName());

                    try {
                        Files.copy(pathToCopyFrom, pathToCopyTo, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception ex) {

                    }
                }
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "Experience approved.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void makeEditableByMaker() {
        if (selectedExperienceTmp == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else if (TempStatus.SUBMITTED == selectedExperienceTmp.getTmpStatus()) {
            TempStatus original = selectedExperienceTmp.getTmpStatus();
            selectedExperienceTmp.setTmpStatus(TempStatus.EDITABLE);
            if (expTmpService.updateTmpExperience(selectedExperienceTmp)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Status", "Selected row is made editable.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                selectedExperienceTmp.setTmpStatus(original);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Change Status", "Unable to make the selected row editable.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Change Status", "Status cannot be changed for the selected row.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
