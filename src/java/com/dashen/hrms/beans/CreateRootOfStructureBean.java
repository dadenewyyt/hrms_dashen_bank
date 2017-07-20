/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.OrganizationalStructure;
import com.dashen.hrms.OrganizationalStructureType;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.service.StructureService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mulugetak
 */
@Component
@SpringViewScope
public class CreateRootOfStructureBean implements Serializable {

    private OrganizationalStructure rootOfOrgStructure;
    private List<OrganizationalStructureType> orgStructureTypesList;
    private List<SelectItem> orgStructureTypesSelectItemsList;

    @Autowired
    private StructureService strService;

    public OrganizationalStructure getRootOfOrgStructure() {
        return rootOfOrgStructure;
    }

    public void setRootOfOrgStructure(OrganizationalStructure rootOfOrgStructure) {
        this.rootOfOrgStructure = rootOfOrgStructure;
    }

    public List<SelectItem> getOrgStructureTypesSelectItemsList() {
        return orgStructureTypesSelectItemsList;
    }

    public void setOrgStructureTypesSelectItemsList(List<SelectItem> orgStructureTypesSelectItemsList) {
        this.orgStructureTypesSelectItemsList = orgStructureTypesSelectItemsList;
    }

    @PostConstruct
    public void init() {
        rootOfOrgStructure = new OrganizationalStructure();
        orgStructureTypesSelectItemsList = new ArrayList<>();

        orgStructureTypesList = strService.listAllOrganizationalStructureTypes();

        for (OrganizationalStructureType type : orgStructureTypesList) {
            orgStructureTypesSelectItemsList.add(new SelectItem(type.getId(), type.getName()));
        }
    }

    public void btnCreateRootOfOrganizationalStructure_Handler() {
        if (strService.isRootOfStructureCreated()) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Create Root of Organizational Structure", "Unable to create Root of Organizational Structure. The root of the structure is already created.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else if (strService.createRootOfOrganizationalStructure(rootOfOrgStructure)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Create Root of Organizational Structure", "Root of Organizational Structure Created.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Create Root of Organizational Structure", "Unable to create Root of Organizational Structure.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
