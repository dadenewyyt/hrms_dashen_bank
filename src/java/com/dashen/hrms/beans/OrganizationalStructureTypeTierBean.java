/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.OrganizationalStructureType;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.Tier;
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
public class OrganizationalStructureTypeTierBean implements Serializable {

    private Tier tier;
    private List<OrganizationalStructureType> organizationalStructureTypesList;
    private OrganizationalStructureType selectedOrganizationalStructureType;
    private List<Tier> tiers;//holds the list of tiers for the selected organizational structure type
    private Tier selectedTier;

    @Autowired
    private StructureService strService;

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public List<OrganizationalStructureType> getOrganizationalStructureTypesList() {
        return organizationalStructureTypesList;
    }

    public OrganizationalStructureType getSelectedOrganizationalStructureType() {
        return selectedOrganizationalStructureType;
    }

    public void setSelectedOrganizationalStructureType(OrganizationalStructureType selectedOrganizationalStructureType) {
        this.selectedOrganizationalStructureType = selectedOrganizationalStructureType;
    }

    public List<Tier> getTiers() {
        return tiers;
    }

    public void setTiers(List<Tier> tiers) {
        this.tiers = tiers;
    }

    public Tier getSelectedTier() {
        return selectedTier;
    }

    public void setSelectedTier(Tier selectedTier) {
        this.selectedTier = selectedTier;
    }

    @PostConstruct
    public void init() {
        tiers = new ArrayList<>();
        organizationalStructureTypesList = strService.listAllOrganizationalStructureTypes();
    }

    public void btnNewTier_Handler() {
        tier = new Tier();
    }

    public void organizationalStructureTypesDataTable_rowSelected(SelectEvent evt) {
        System.out.println(selectedOrganizationalStructureType.getName());
        tier = new Tier();
        tiers.clear();
        selectedTier = null;
        if (selectedOrganizationalStructureType != null) {
            if (selectedOrganizationalStructureType.isHasTier()) {
                tiers = strService.listAllTiersForStructureType(selectedOrganizationalStructureType.getId());
            }
        }

    }

    public void tiersDataTable_rowSelected(SelectEvent evt) {
        tier = selectedTier;
    }

    public void btnRefreshList_Handler() {
        if (selectedOrganizationalStructureType != null) {
            tiers = strService.listAllTiersForStructureType(selectedOrganizationalStructureType.getId());
        }
    }

    public void btnSaveTier_Handler() {
        if (selectedOrganizationalStructureType != null) {
            if (tier.getId() != null && !tier.getId().isEmpty()) {
                //is updating existing tier
                if (strService.updateTier(tier)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Tier", "Tier details updated.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed Update Tier", "Failed to update Tier details.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {//new tier
                tier.setOrganizationalStructureTypeID(selectedOrganizationalStructureType.getId());
                if (strService.saveNewTier(tier)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Tier", "New Tier added.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed Save Tier", "Failed to save Tier details.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
            tier = new Tier();
        }
    }
}
