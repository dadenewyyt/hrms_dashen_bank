/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.Institution;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.service.InstitutionService;
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
 * @author MulugetaK
 */
@Component
@SpringViewScope
public class InstitutionBean implements Serializable {

    private Institution inst;

    @Autowired
    private InstitutionService instService;

    private List<Institution> institutionsList;
    private Institution selectedInstitution;

    public Institution getInst() {
        return inst;
    }

    public void setInst(Institution inst) {
        this.inst = inst;
    }

    public Institution getSelectedInstitution() {
        return selectedInstitution;
    }

    public void setSelectedInstitution(Institution selectedInstitution) {
        this.selectedInstitution = selectedInstitution;
    }

    public List<Institution> getInstitutionsList() {
        return institutionsList;
    }

    @PostConstruct
    public void init() {
        inst = new Institution();
        institutionsList = new ArrayList<>();
    }

    public void btnNewInstitution_Handler() {
        inst = new Institution();
    }

    public void btnSaveInstitution_Handler() {
        if (inst.getId() != null && !inst.getId().isEmpty()) {
            //is updating existing institution
            instService.updateInstitution(inst);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Institution", "Institution details updated.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {//new Institution
            instService.saveNewInstitution(inst);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Institution", "New institution added.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        inst = new Institution();
        System.out.println("Save Instituion called ");

    }

    public void btnDeleteInstitution_Handler() {
        if (selectedInstitution != null) {
            if (instService.delete(selectedInstitution)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Institution", "Institution is deleted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                inst = new Institution();
                institutionsList.remove(selectedInstitution);
            } else {
                //delete failed
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete Institution", "Institution is not deleted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Institution", "No institution selected.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnRefreshList_Handler() {
        institutionsList = instService.listAll();
    }

    public void institutionsDataTable_rowSelected(SelectEvent evt) {
        inst = selectedInstitution;
    }
}
