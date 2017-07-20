/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.JobGrade;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.OrganizationalStructure;
import com.dashen.hrms.OrganizationalStructureType;
import com.dashen.hrms.Position;
import com.dashen.hrms.PositionHavingOrganizationalStructure;
import com.dashen.hrms.PositionTier;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.Tier;
import com.dashen.hrms.service.JobGradeService;
import com.dashen.hrms.service.PositionService;
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
 * @author biniamt
 */

@Component
@SpringViewScope
public class PositionBean implements Serializable {

    private Position currentTempPosition;
    private PositionHavingOrganizationalStructure currentPosHavingOrgStruct;
    private PositionTier currentPositionTier;

    @Autowired
    private PositionService positionService;
    
    @Autowired
    private StructureService structureService;

    @Autowired
    private JobGradeService jobGradeService;

    private List<Position> positionsList;
    private Position selectedPosition;

    private List<Position> tempPositionsList;
    private Position selectedTempPosition;
    
    private List<SelectItem> organizationalStructureTypeList;
    private List<SelectItem> jobGradeList;
    private List<SelectItem> organizationalStructureList;
    private List<SelectItem> tierList;
    
    private List<Tier> availableTiersList;
    
    private boolean orgStructureHasNoTier;

    public Position getCurrentTempPosition() {
        return currentTempPosition;
    }

    public void setCurrentTempPosition(Position currentTempPosition) {
        this.currentTempPosition = currentTempPosition;
    }

    public PositionHavingOrganizationalStructure getCurrentPosHavingOrgStruct() {
        return currentPosHavingOrgStruct;
    }

    public void setCurrentPosHavingOrgStruct(PositionHavingOrganizationalStructure currentPosHavingOrgStruct) {
        this.currentPosHavingOrgStruct = currentPosHavingOrgStruct;
    }

    public PositionTier getCurrentPositionTier() {
        return currentPositionTier;
    }

    public void setCurrentPositionTier(PositionTier currentPositionTier) {
        this.currentPositionTier = currentPositionTier;
    }

    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }

    public List<Position> getPositionsList() {
        return positionsList;
    }

    public void setPositionsList(List<Position> positionsList) {
        this.positionsList = positionsList;
    }

    public Position getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(Position selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public List<Position> getTempPositionsList() {
        return tempPositionsList;
    }

    public void setTempPositionsList(List<Position> tempPositionsList) {
        this.tempPositionsList = tempPositionsList;
    }

    public Position getSelectedTempPosition() {
        return selectedTempPosition;
    }

    public void setSelectedTempPosition(Position selectedTempPosition) {
        this.selectedTempPosition = selectedTempPosition;
    }

    public List<SelectItem> getOrganizationalStructureTypeList() {
        return organizationalStructureTypeList;
    }

    public void setOrganizationalStructureTypeList(List<SelectItem> organizationalStructureTypeList) {
        this.organizationalStructureTypeList = organizationalStructureTypeList;
    }

    public List<SelectItem> getOrganizationalStructureList() {
        return organizationalStructureList;
    }

    public void setOrganizationalStructureList(List<SelectItem> organizationalStructureList) {
        this.organizationalStructureList = organizationalStructureList;
    }

    public List<SelectItem> getTierList() {
        return tierList;
    }

    public void setTierList(List<SelectItem> tierList) {
        this.tierList = tierList;
    }

    public List<SelectItem> getJobGradeList() {
        return jobGradeList;
    }

    public void setJobGradeList(List<SelectItem> jobGradeList) {
        this.jobGradeList = jobGradeList;
    }

    public boolean isOrgStructureHasNoTier() {
        return orgStructureHasNoTier;
    }

    public void setOrgStructureHasNoTier(boolean orgStructureHasNoTier) {
        this.orgStructureHasNoTier = orgStructureHasNoTier;
    }

    @PostConstruct
    public void init() {

        currentTempPosition = new Position();
        currentPosHavingOrgStruct = new PositionHavingOrganizationalStructure();
        currentPositionTier = new PositionTier();
        
        orgStructureHasNoTier = false;

        positionsList = positionService.listPositions();
        tempPositionsList = new ArrayList<>();
        
        organizationalStructureTypeList = new ArrayList<>();
        
        List<OrganizationalStructureType> oList = structureService.listAllOrganizationalStructureTypes();
        oList.forEach((o) -> {
            organizationalStructureTypeList.add(new SelectItem(o.getId(), o.getName()));
        });

        jobGradeList = new ArrayList<>();

        List<JobGrade> jList = jobGradeService.listJobGrades();
        jList.forEach((j) -> {
            jobGradeList.add(new SelectItem(j.getID(), j.getGrade()));
        });
        
        organizationalStructureList = new ArrayList<>();
        
        List<OrganizationalStructure> sList = structureService.listAllOrganizationalStructures();
        sList.forEach((s) -> {
           organizationalStructureList.add(new SelectItem(s.getId(), s.getName()));
        });
        
        tierList = new ArrayList();
    }
    
    public void updateOrgStructList() {
        organizationalStructureList.clear();
        if (currentTempPosition.getOrganizationalStructureType().getId() != null) {
            for (OrganizationalStructure o: structureService.listOrganizationalStructuresOfAType(currentTempPosition.getOrganizationalStructureType().getId())) {
                organizationalStructureList.add(new SelectItem(o.getId(), o.getName()));
            }
        } else {
            structureService.listAllOrganizationalStructures().forEach((s) -> {
               organizationalStructureList.add(new SelectItem(s.getId(), s.getName()));
            }); 
        }
    }
    
    public void typeSelectOneMenu_itemSelect() {
        tierList.clear();
        availableTiersList = structureService.listAllTiersForStructureType(currentTempPosition.getOrganizationalStructureType().getId());
        for (Tier tr : availableTiersList) {
            tierList.add(new SelectItem(tr.getId(), tr.getName()));
        }
        if (availableTiersList.size() > 0) {
            //the selected type has Tiers and tier must be selected
            orgStructureHasNoTier = false;
            if (currentPositionTier == null) {
                currentPositionTier = new PositionTier();
            }
        } else {
            orgStructureHasNoTier = true;
        }
        updateOrgStructList();
    }

    public OrganizationalStructure getOrganizationalStructure(Position position) {
        OrganizationalStructure o = positionService.getOrganizationalStructureForAPosition(position);
        return o;
    }

    public Tier getTier(Position position) {
        Tier t = positionService.getTierForAPosition(position);
        return t;
    }    

    public void btnNewPosition_Handler() {
        currentTempPosition = new Position();
        currentPosHavingOrgStruct = new PositionHavingOrganizationalStructure();
        currentPositionTier = new PositionTier();
        updateOrgStructList();
    }

    public boolean isRowSubmitted() {
        if (currentTempPosition != null && currentTempPosition.getTmpStatus() == TempStatus.EDITABLE) {
            return false;
        }
        return true;
    }
    public void btnRefreshList_Handler() {
        MyUser user = CurrentUser.getCurrentUser();
        tempPositionsList = positionService.listTmpPositionsForMaker(user.getUsername());
    }

    public void tmpPositionsDataTableDataTable_rowSelected(SelectEvent evt) {
        currentTempPosition = selectedTempPosition;
        if (currentTempPosition.isHasOrganizationalStructure()) {
            currentPosHavingOrgStruct = positionService.getPositionHavingOrganizationalStructureByID(currentTempPosition.getID());
        } else {
            currentPosHavingOrgStruct = new PositionHavingOrganizationalStructure();
        }
        
        if (currentTempPosition.isHasTier()) {
            currentPositionTier = positionService.getPositionTierByID(currentTempPosition.getID());
            orgStructureHasNoTier = false;
            typeSelectOneMenu_itemSelect();
        } else {
            currentPositionTier = new PositionTier();
            orgStructureHasNoTier = true;
        }
    }

    public void btnSavePosition_Handler() {
        FacesMessage msg;
        if (currentTempPosition.getTmpStatus() == TempStatus.EDITABLE) {
            if (currentTempPosition.getID() != null && !currentTempPosition.getID().isEmpty()) {
                // is updating existing Temp Position record
                if (positionService.updateTmpPosition(currentTempPosition, currentPosHavingOrgStruct, currentPositionTier, orgStructureHasNoTier)) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save position update", "Position updates saved.");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save position update", "Update failed.");
                }
            } else if (currentTempPosition.getPositionId() != null && !currentTempPosition.getPositionId().isEmpty()) {
                // editing existing Position, tmp object is new
                if (positionService.saveNewTmpPosition(currentTempPosition, ActionType.UPDATE, currentPosHavingOrgStruct, 
                                currentPositionTier, orgStructureHasNoTier)) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save position update", "Position updates saved.");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save position update", "Update failed.");
                }
            } else//new position, and new tmp object
            {
                if (positionService.saveNewTmpPosition(currentTempPosition, ActionType.CREATE, currentPosHavingOrgStruct, currentPositionTier, orgStructureHasNoTier)) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New position", "New position added.");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New position", "Save failed.");
                }
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update position", "The selected row is already submitted.");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        currentTempPosition = new Position();
        currentPosHavingOrgStruct = new PositionHavingOrganizationalStructure();
        currentPositionTier = new PositionTier();
    }

    public void btnSubmit_Handler() {
        if (selectedTempPosition != null) {
            if (selectedTempPosition.getTmpStatus() != TempStatus.SUBMITTED) {
                selectedTempPosition.setTmpStatus(TempStatus.SUBMITTED);
                if (positionService.updateTmpPosition(selectedTempPosition, currentPosHavingOrgStruct, currentPositionTier, orgStructureHasNoTier)) {
                    //update successful   
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit row", "Row submitted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    //update has failed, undo the change
                    selectedTempPosition.setTmpStatus(TempStatus.EDITABLE);
                }
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit row", "The selected row is already submitted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit row", "No row selected.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnDeleteRow_Handler() {
        FacesMessage msg;
        if (selectedTempPosition != null) {
            if (selectedTempPosition.getTmpStatus() != TempStatus.SUBMITTED) {
                if (positionService.delete(selectedTempPosition)) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete row", "Row is deleted.");
                    currentTempPosition = new Position();
                    currentPosHavingOrgStruct = new PositionHavingOrganizationalStructure();
                    currentPositionTier = new PositionTier();
                    tempPositionsList.remove(selectedTempPosition);
                } else {
                    //delete failed
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete Row", "Row is not deleted.");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete Row", "Submitted rows cannot be deleted.");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "No row selected.");            
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void btnEditSelectedPosition_Handler() {
        Position positionTmp = new Position();
        if (selectedPosition != null) {
            if (!positionService.checkIfPositionHasPendingEdit(selectedPosition.getID())) {
                positionTmp.setPositionId(selectedPosition.getID());
                positionTmp.setTitle(selectedPosition.getTitle());
                positionTmp.setOrganizationalStructureType(selectedPosition.getOrganizationalStructureType());
                positionTmp.setHasOrganizationalStructure(selectedPosition.isHasOrganizationalStructure());
                positionTmp.setHasTier(selectedPosition.isHasTier());
                positionTmp.setJobGrade(selectedPosition.getJobGrade());
                currentTempPosition = positionTmp;

                if (selectedPosition.isHasOrganizationalStructure()) {
                    PositionHavingOrganizationalStructure prevPosHavingOrgStruct = positionService.getPositionHavingOrganizationalStructureByID(selectedPosition.getID());
                    PositionHavingOrganizationalStructure p = new PositionHavingOrganizationalStructure();
                    p.setPosition(prevPosHavingOrgStruct.getPosition());
                    p.setOrganizationalStructure(prevPosHavingOrgStruct.getOrganizationalStructure());
                    currentPosHavingOrgStruct = p;
                } else {
                    currentPosHavingOrgStruct = new PositionHavingOrganizationalStructure();
                }
                
                if (selectedPosition.isHasTier()) {
                    PositionTier prevPosTier = positionService.getPositionTierByID(selectedPosition.getID());
                    PositionTier t = new PositionTier();
                    t.setPosition(prevPosTier.getPosition());
                    t.setTier(prevPosTier.getTier());
                    currentPositionTier = t;
                    orgStructureHasNoTier = false;
                    typeSelectOneMenu_itemSelect();
                } else {
                    currentPositionTier = new PositionTier();
                    orgStructureHasNoTier = true;
                }

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit Position", "The selected position has pending changes. Please approve or cancel the changes and try again.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }
}
