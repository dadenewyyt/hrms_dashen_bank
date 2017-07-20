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
import com.dashen.hrms.QualificationTmp;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.Util;
import com.dashen.hrms.service.QualificationService;
import com.dashen.hrms.service.QualificationTmpService;
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
 * @author MulugetaK
 */
@Component
@SpringViewScope
public class QualificationPendingItemsBean {

    private List<QualificationTmp> pendingTmpQualifications;
    QualificationTmp selectedQualificationTmp;

    @Autowired
    private QualificationTmpService qualTmpService;

    @Autowired
    private QualificationService qualService;

    @PostConstruct
    public void init() {
        MyUser usr = CurrentUser.getCurrentUser();
        pendingTmpQualifications = qualTmpService.listTmpQualificationsForChecker(usr.getUsername());
    }

    public List<QualificationTmp> getPendingTmpQualifications() {
        return pendingTmpQualifications;
    }

    public void setPendingTmpQualifications(List<QualificationTmp> pendingTmpQualifications) {
        this.pendingTmpQualifications = pendingTmpQualifications;
    }

    public QualificationTmp getSelectedQualificationTmp() {
        return selectedQualificationTmp;
    }

    public void setSelectedQualificationTmp(QualificationTmp selectedQualificationTmp) {
        this.selectedQualificationTmp = selectedQualificationTmp;
    }

    public void approveSelectedRows() {
        if (selectedQualificationTmp == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row Selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else //approve the selected row           
         if (selectedQualificationTmp.getTmpStatus() == TempStatus.SUBMITTED) {
                if (selectedQualificationTmp.getActionType() == ActionType.CREATE) {
                    if (selectedQualificationTmp.isTmpDocumentUploaded()) {
                        selectedQualificationTmp.setFileName("QD" + Util.getDateString() + "." + Util.getFileExtension(selectedQualificationTmp.getTmpDocumentFileName()));
                    }
                    qualService.saveNewQualification(selectedQualificationTmp);
                    pendingTmpQualifications.remove(selectedQualificationTmp);
                } else if (selectedQualificationTmp.getActionType() == ActionType.UPDATE) //existing qualification was updated
                {
                    if (selectedQualificationTmp.isTmpDocumentUploaded()) {
                        if (selectedQualificationTmp.getFileName() != null && !selectedQualificationTmp.getFileName().isEmpty()) {
                            //delete the current document
                        }
                        selectedQualificationTmp.setFileName("QD" + Util.getDateString() + "." + Util.getFileExtension(selectedQualificationTmp.getTmpDocumentFileName()));
                    }
                    qualService.updateQualification(selectedQualificationTmp);
                    pendingTmpQualifications.remove(selectedQualificationTmp);
                }
                //check if document is uploaded and copy it
                if (selectedQualificationTmp.isTmpDocumentUploaded()) {
                    Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.QUALIFICATION_DOCUMENTS_PATH));

                    Path pathToCopyTo = rootRealPath.resolve(selectedQualificationTmp.getFileName());
                    Path pathToCopyFrom = rootRealPath.resolve(selectedQualificationTmp.getTmpDocumentFileName());

                    try {
                        Files.copy(pathToCopyFrom, pathToCopyTo, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception ex) {

                    }
                }
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "Qualification approved.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
    }

    public void makeEditableByMaker() {
        if (selectedQualificationTmp == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else if (TempStatus.SUBMITTED == selectedQualificationTmp.getTmpStatus()) {
            TempStatus original = selectedQualificationTmp.getTmpStatus();
            selectedQualificationTmp.setTmpStatus(TempStatus.EDITABLE);
            if (qualTmpService.updateTmpQualification(selectedQualificationTmp)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Status", "Selected row is made editable.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                selectedQualificationTmp.setTmpStatus(original);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Change Status", "Unable to make the selected row editable.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Change Status", "Status cannot be changed for the selected row.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
