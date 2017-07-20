/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.Institution;
import com.dashen.hrms.OrganizationalStructureType;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.service.StructureService;
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
 * @author mulugetak
 */
@Component
@SpringViewScope
public class OrganizationalStructureTypeBean implements Serializable {

    private OrganizationalStructureType orgSTRType;

    @Autowired
    private StructureService strService;

    private List<OrganizationalStructureType> organizationalStructureTypesList;
    private OrganizationalStructureType selectedOrganizationalStructureType;

    public OrganizationalStructureType getOrgSTRType() {
        return orgSTRType;
    }

    public void setOrgSTRType(OrganizationalStructureType orgSTRType) {
        this.orgSTRType = orgSTRType;
    }

    public List<OrganizationalStructureType> getOrganizationalStructureTypesList() {
        return organizationalStructureTypesList;
    }

    public void setOrganizationalStructureTypesList(List<OrganizationalStructureType> OrganizationalStructureTypesList) {
        this.organizationalStructureTypesList = OrganizationalStructureTypesList;
    }

    public OrganizationalStructureType getSelectedOrganizationalStructureType() {
        return selectedOrganizationalStructureType;
    }

    public void setSelectedOrganizationalStructureType(OrganizationalStructureType selectedOrganizationalStructureType) {
        this.selectedOrganizationalStructureType = selectedOrganizationalStructureType;
    }

    @PostConstruct
    public void init() {
        orgSTRType = new OrganizationalStructureType();
        organizationalStructureTypesList = new ArrayList<>();
    }

    public void btnNewOrganizationalStructureType_Handler() {
        orgSTRType = new OrganizationalStructureType();
    }

    public void btnSaveOrganizationalStructureType_Handler() {
        if (orgSTRType.getId() != null && !orgSTRType.getId().isEmpty()) {
            //is updating existing type
            if (strService.updateOrganizationalStructureType(orgSTRType)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Organizational Structure Type", "Organizational Structure Type details updated.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed Update Organizational Structure Type", "Failed to update Organizational Structure Type details.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else//new type
         if (strService.saveNewOrganizationalStructureType(orgSTRType)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Organizational Structure Type", "New Organizational Structure Type added.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed Save Organizational Structure Type", "Failed to save Organizational Structure Type details.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        orgSTRType = new OrganizationalStructureType();
    }

    public void btnRefreshList_Handler() {
        organizationalStructureTypesList = strService.listAllOrganizationalStructureTypes();
    }

    public void organizationalStructureTypesDataTable_rowSelected(SelectEvent evt) {
        orgSTRType = selectedOrganizationalStructureType;
    }
}
