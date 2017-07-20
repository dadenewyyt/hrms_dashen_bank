/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.City;
import com.dashen.hrms.OrganizationalStructure;
import com.dashen.hrms.OrganizationalStructureTier;
import com.dashen.hrms.OrganizationalStructureType;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.Tier;
import com.dashen.hrms.service.CityService;
import com.dashen.hrms.service.StructureService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mulugetak
 */
@Component
@SpringViewScope
public class OrganizationalStructureBean implements Serializable {

    private boolean rootOfStructureNotCreated;
    private OrganizationalStructure orgStruct;
    private List<OrganizationalStructureType> orgStructureTypesList;
    private List<SelectItem> orgStructureTypesSelectItemsList;
    private List<SelectItem> orgStructureSelectItemsList;
    private List<Tier> availableTiersList;
    private List<SelectItem> availableTierSelectItemsList;
    private List<SelectItem> citySelectItemsList;
    private OrganizationalStructureTier orgStructTier;
    private boolean orgStructureHasNoTier;

    @Autowired
    private StructureService strService;
    
    @Autowired
    private CityService cityService;

    private List<OrganizationalStructure> organizationalStructuresList;
    private List<City> citiesList;
    private OrganizationalStructure selectedOrganizationalStructure;

    public boolean isRootOfStructureNotCreated() {
        return rootOfStructureNotCreated;
    }

    public OrganizationalStructure getOrgStruct() {
        return orgStruct;
    }

    public List<SelectItem> getOrgStructureTypesSelectItemsList() {
        return orgStructureTypesSelectItemsList;
    }

    public void setOrgStructureTypesSelectItemsList(List<SelectItem> orgStructureTypesSelectItemsList) {
        this.orgStructureTypesSelectItemsList = orgStructureTypesSelectItemsList;
    }

    public List<SelectItem> getOrgStructureSelectItemsList() {
        return orgStructureSelectItemsList;
    }

    public void setOrgStructureSelectItemsList(List<SelectItem> orgStructureSelectItemsList) {
        this.orgStructureSelectItemsList = orgStructureSelectItemsList;
    }

    public void setOrgStruct(OrganizationalStructure orgStruct) {
        this.orgStruct = orgStruct;
    }

    public List<OrganizationalStructure> getOrganizationalStructuresList() {
        return organizationalStructuresList;
    }

    public void setOrganizationalStructuresList(List<OrganizationalStructure> OrganizationalStructuresList) {
        this.organizationalStructuresList = OrganizationalStructuresList;
    }

    public List<City> getCitiesList() {
        return citiesList;
    }

    public void setCitiesList(List<City> citiesList) {
        this.citiesList = citiesList;
    }

    public OrganizationalStructure getSelectedOrganizationalStructure() {
        return selectedOrganizationalStructure;
    }

    public void setSelectedOrganizationalStructure(OrganizationalStructure selectedOrganizationalStructure) {
        this.selectedOrganizationalStructure = selectedOrganizationalStructure;
    }

    public List<SelectItem> getAvailableTierSelectItemsList() {
        return availableTierSelectItemsList;
    }

    public void setAvailableTierSelectItemsList(List<SelectItem> availableTierSelectItemsList) {
        this.availableTierSelectItemsList = availableTierSelectItemsList;
    }

    public List<SelectItem> getCitySelectItemsList() {
        return citySelectItemsList;
    }

    public void setCitySelectItemsList(List<SelectItem> citySelectItemsList) {
        this.citySelectItemsList = citySelectItemsList;
    }

    public OrganizationalStructureTier getOrgStructTier() {
        return orgStructTier;
    }

    public void setOrgStructTier(OrganizationalStructureTier orgStructTier) {
        this.orgStructTier = orgStructTier;
    }

    public boolean isOrgStructureHasNoTier() {
        return orgStructureHasNoTier;
    }

    public void setOrgStructureHasNoTier(boolean orgStructureHasNoTier) {
        this.orgStructureHasNoTier = orgStructureHasNoTier;
    }

    @PostConstruct
    public void init() {
        orgStruct = new OrganizationalStructure();
        orgStructTier = new OrganizationalStructureTier();
        rootOfStructureNotCreated = !strService.isRootOfStructureCreated();

        orgStructureHasNoTier = false;

        orgStructureTypesSelectItemsList = new ArrayList<>();
        orgStructureSelectItemsList = new ArrayList<>();
        availableTierSelectItemsList = new ArrayList<>();
        citySelectItemsList = new ArrayList<>();

        orgStructureTypesList = strService.listAllOrganizationalStructureTypes();
        organizationalStructuresList = strService.listAllOrganizationalStructures();
        citiesList = cityService.listCities();

        for (OrganizationalStructureType type : orgStructureTypesList) {
            orgStructureTypesSelectItemsList.add(new SelectItem(type.getId(), type.getName()));
        }

        for (OrganizationalStructure ost : organizationalStructuresList) {
            orgStructureSelectItemsList.add(new SelectItem(ost.getId(), ost.getName()));
        }
        
        for ( City c : citiesList) {
            citySelectItemsList.add(new SelectItem(c.getID(), c.getName()));
        }
    }

    public void btnNewOrganizationalStructure_Handler() {
        orgStruct = new OrganizationalStructure();
        orgStructTier = new OrganizationalStructureTier();
    }

    public void btnSaveOrganizationalStructure_Handler() {
        if (orgStruct.getId() != null && !orgStruct.getId().isEmpty()) {
            //is updating existing org structure
            if (strService.updateOrganizationalStructure(orgStruct, orgStructureHasNoTier, orgStructTier)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Organizational Structure", "Organizational Structure details updated.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed Update Organizational Structure", "Filed to update Organizational Structure details.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else//new org structure
        {
            OrganizationalStructureTier orgTier = null;
            if (orgStructureHasNoTier) {

            } else {
                orgTier = orgStructTier;
            }
            if (strService.saveNewOrganizationalStructure(orgStruct, orgTier)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Organizational Structure", "New Organizational Structure added.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed Add Organizational Structure", "Save Organizational Structure failed.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        btnNewOrganizationalStructure_Handler();
    }

    public void btnRefreshList_Handler() {
        organizationalStructuresList = strService.listAllOrganizationalStructures();
    }

    public void organizationalStructuresDataTable_rowSelected(SelectEvent evt) {
        orgStruct = selectedOrganizationalStructure;
        orgStructTier = strService.getStructureTierForStructure(orgStruct.getId());
        typeSelectOneMenu_itemSelect();
    }

    public void typeSelectOneMenu_itemSelect() {
        System.out.println("type slected");
        availableTierSelectItemsList.clear();
        availableTiersList = strService.listAllTiersForStructureType(orgStruct.getOrganizationalStructureTypeID());
        for (Tier tr : availableTiersList) {
            availableTierSelectItemsList.add(new SelectItem(tr.getId(), tr.getName()));
        }
        if (availableTiersList.size() > 0) {
            //the selected type has Tiers and tier must be selected
            orgStructureHasNoTier = false;
            if (orgStructTier == null) {
                orgStructTier = new OrganizationalStructureTier();
            }
        } else {
            orgStructureHasNoTier = true;
        }
    }
}
