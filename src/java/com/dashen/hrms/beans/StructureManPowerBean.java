/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.OrganizationalStructure;
import com.dashen.hrms.OrganizationalStructureType;
import com.dashen.hrms.Position;
import com.dashen.hrms.PositionHavingOrganizationalStructure;
import com.dashen.hrms.PositionTier;
import com.dashen.hrms.StructureManPower;
import com.dashen.hrms.service.PositionService;
import com.dashen.hrms.service.StructureManPowerService;
import com.dashen.hrms.service.StructureService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 *
 * @author Dawits
 */
@RequestScope
@Component
public class StructureManPowerBean {
    //===========Users==================================//
    
    //GrantedAuthority maker = new SimpleGrantedAuthority(UserAuthority.MAKER);
    //GrantedAuthority checker = new SimpleGrantedAuthority(UserAuthority.CHECKER);
    
    //===========Auto Wired Objects=====================//
    @Autowired
    StructureManPowerService strManPowService;
    @Autowired
    StructureService orgStrService;
    @Autowired
    PositionService posService;
    
    //===========Common Variables=======================//
    
    private boolean pnlForRegPageHeadingRender;
    private boolean pnlForAppPageHeadingRender;
    private List<StructureManPower> appOrgStrManPow;
    
    //===========Maker Variables========================//
    
    private boolean pnlForInputFieldsOnMakerSideRender;
    private StructureManPower strManPow;
    private List<SelectItem> orgStrSelectItem;
    private List<SelectItem> posSelectItem;
    private boolean pnlForUnappOrgStrManPowDataTblOnMakerSideRender;
    private List<StructureManPower> unappOrgStrManPow;
    private StructureManPower selectedRowInUnappOrgStrManPowDataTblOnMakerSide;
    private boolean pnlForAppOrgStrManPowDataTblOnMakerSideRender;
    private StructureManPower selectedRowInappOrgStrManPowDataTblOnMakerSide;
    private boolean pnlForBtnsOnMakerSideRender;
    
    //===========Checker Variables======================//
    
    private boolean pnlForOrgStrManPowToBeReviewedByCheckerDataTblAndBtnsRender;
    private List<StructureManPower> toBeReviewdOrgStrManPow;
    private List<StructureManPower> selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl;
    private boolean pnlForAppOrgStrManPowDataTblOnCheckerSideRender;
    private List<StructureManPower> selectedRowsInAppOrgStrManPowDataTblOnCheckerSide;
    
    //===========Init Method============================//
    
    @PostConstruct
    public void init() {
        strManPow = new StructureManPower();
        if(CurrentUser.getCurrentUser().getUsername().equals("mulugetak")) {
            pnlForRegPageHeadingRender = true;
            pnlForInputFieldsOnMakerSideRender = true;
            pnlForUnappOrgStrManPowDataTblOnMakerSideRender = true;
            pnlForAppOrgStrManPowDataTblOnMakerSideRender = true;
            pnlForBtnsOnMakerSideRender = true;
            
            pnlForAppPageHeadingRender = false;
            pnlForOrgStrManPowToBeReviewedByCheckerDataTblAndBtnsRender = false;
            pnlForAppOrgStrManPowDataTblOnCheckerSideRender = false;
            
            List<OrganizationalStructure> orgStruList = orgStrService.listAllOrganizationalStructures();
            orgStrSelectItem = new ArrayList<>();
            for (int i = 0; i < orgStruList.size(); i++)
            {
               orgStrSelectItem.add(new SelectItem(orgStruList.get(i).getId(), orgStruList.get(i).getName()));
            }
            List<Position> posList = posService.listPositions();
            posSelectItem = new ArrayList<>();
            for (int i = 0; i < posList.size(); i++)
            {
               posSelectItem.add(new SelectItem(posList.get(i).getID(), posList.get(i).getTitle()));
            }
            appOrgStrManPow = strManPowService.fetchAllApproved();
            unappOrgStrManPow = strManPowService.fetchAllUnsubmittedOrEditable();
            for(int i = 0; i < unappOrgStrManPow.size(); i++)
            {
                if(!"-".equals(unappOrgStrManPow.get(i).getUnapprovedChange()))
                {
                    unappOrgStrManPow.get(i).setUnapprovedOrganizationalStructure(orgStrService.getByOrganizationalStructureId(unappOrgStrManPow.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]));
                    unappOrgStrManPow.get(i).setUnapprovedOrganizationalStructureArrow(true);
                    unappOrgStrManPow.get(i).setUnapprovedOrganizationalStructureDisplay(true);
                    unappOrgStrManPow.get(i).setUnapprovedPosition(posService.getByID(unappOrgStrManPow.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]));
                    unappOrgStrManPow.get(i).setUnapprovedPositionArrow(true);
                    unappOrgStrManPow.get(i).setUnapprovedPositionDisplay(true);
                    unappOrgStrManPow.get(i).setUnapprovedRequiredNoOfWorkForce(unappOrgStrManPow.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]);
                    unappOrgStrManPow.get(i).setUnapprovedRequiredNoOfWorkForceArrow(true);
                    unappOrgStrManPow.get(i).setUnapprovedRequiredNoOfWorkForceDisplay(true);
                    unappOrgStrManPow.get(i).setUnapprovedCurrentlyAvailableSpace(unappOrgStrManPow.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    unappOrgStrManPow.get(i).setUnapprovedCurrentlyAvailableSpaceArrow(true);
                    unappOrgStrManPow.get(i).setUnapprovedCurrentlyAvailableSpaceDisplay(true);
                }
            }
        }
        if(CurrentUser.getCurrentUser().getUsername().equals("dawits")) {
            pnlForAppPageHeadingRender = true;
            pnlForOrgStrManPowToBeReviewedByCheckerDataTblAndBtnsRender = true;
            pnlForAppOrgStrManPowDataTblOnCheckerSideRender = true;
            
            pnlForRegPageHeadingRender = false;
            pnlForInputFieldsOnMakerSideRender = false;
            pnlForUnappOrgStrManPowDataTblOnMakerSideRender = false;
            pnlForAppOrgStrManPowDataTblOnMakerSideRender = false;
            pnlForBtnsOnMakerSideRender = false;
            
            appOrgStrManPow = strManPowService.fetchAllApproved();
            toBeReviewdOrgStrManPow = strManPowService.fetchAllSubmitted();
            for(int i = 0; i < toBeReviewdOrgStrManPow.size(); i++)
            {
                if(!"-".equals(toBeReviewdOrgStrManPow.get(i).getUnapprovedChange()))
                {
                    toBeReviewdOrgStrManPow.get(i).setUnapprovedOrganizationalStructure(orgStrService.getByOrganizationalStructureId(toBeReviewdOrgStrManPow.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]));
                    toBeReviewdOrgStrManPow.get(i).setUnapprovedOrganizationalStructureArrow(true);
                    toBeReviewdOrgStrManPow.get(i).setUnapprovedOrganizationalStructureDisplay(true);
                    toBeReviewdOrgStrManPow.get(i).setUnapprovedPosition(posService.getByID(toBeReviewdOrgStrManPow.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]));
                    toBeReviewdOrgStrManPow.get(i).setUnapprovedPositionArrow(true);
                    toBeReviewdOrgStrManPow.get(i).setUnapprovedPositionDisplay(true);
                    toBeReviewdOrgStrManPow.get(i).setUnapprovedRequiredNoOfWorkForce(toBeReviewdOrgStrManPow.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]);
                    toBeReviewdOrgStrManPow.get(i).setUnapprovedRequiredNoOfWorkForceArrow(true);
                    toBeReviewdOrgStrManPow.get(i).setUnapprovedRequiredNoOfWorkForceDisplay(true);
                    toBeReviewdOrgStrManPow.get(i).setUnapprovedCurrentlyAvailableSpace(toBeReviewdOrgStrManPow.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    toBeReviewdOrgStrManPow.get(i).setUnapprovedCurrentlyAvailableSpaceArrow(true);
                    toBeReviewdOrgStrManPow.get(i).setUnapprovedCurrentlyAvailableSpaceDisplay(true);
                }
            }
        }
        
    }

    //===========Getter and Setter======================//

    public boolean isPnlForRegPageHeadingRender() {
        return pnlForRegPageHeadingRender;
    }

    public void setPnlForRegPageHeadingRender(boolean pnlForRegPageHeadingRender) {
        this.pnlForRegPageHeadingRender = pnlForRegPageHeadingRender;
    }

    public boolean isPnlForAppPageHeadingRender() {
        return pnlForAppPageHeadingRender;
    }

    public void setPnlForAppPageHeadingRender(boolean pnlForAppPageHeadingRender) {
        this.pnlForAppPageHeadingRender = pnlForAppPageHeadingRender;
    }

    public List<StructureManPower> getAppOrgStrManPow() {
        return appOrgStrManPow;
    }

    public void setAppOrgStrManPow(List<StructureManPower> appOrgStrManPow) {
        this.appOrgStrManPow = appOrgStrManPow;
    }

    public boolean isPnlForInputFieldsOnMakerSideRender() {
        return pnlForInputFieldsOnMakerSideRender;
    }

    public void setPnlForInputFieldsOnMakerSideRender(boolean pnlForInputFieldsOnMakerSideRender) {
        this.pnlForInputFieldsOnMakerSideRender = pnlForInputFieldsOnMakerSideRender;
    }

    public StructureManPower getStrManPow() {
        return strManPow;
    }

    public void setStrManPow(StructureManPower strManPow) {
        this.strManPow = strManPow;
    }

    public List<SelectItem> getOrgStrSelectItem() {
        return orgStrSelectItem;
    }

    public void setOrgStrSelectItem(List<SelectItem> orgStrSelectItem) {
        this.orgStrSelectItem = orgStrSelectItem;
    }

    public List<SelectItem> getPosSelectItem() {
        return posSelectItem;
    }

    public void setPosSelectItem(List<SelectItem> posSelectItem) {
        this.posSelectItem = posSelectItem;
    }

    public boolean isPnlForUnappOrgStrManPowDataTblOnMakerSideRender() {
        return pnlForUnappOrgStrManPowDataTblOnMakerSideRender;
    }

    public void setPnlForUnappOrgStrManPowDataTblOnMakerSideRender(boolean pnlForUnappOrgStrManPowDataTblOnMakerSideRender) {
        this.pnlForUnappOrgStrManPowDataTblOnMakerSideRender = pnlForUnappOrgStrManPowDataTblOnMakerSideRender;
    }

    public List<StructureManPower> getUnappOrgStrManPow() {
        return unappOrgStrManPow;
    }

    public void setUnappOrgStrManPow(List<StructureManPower> unappOrgStrManPow) {
        this.unappOrgStrManPow = unappOrgStrManPow;
    }

    public StructureManPower getSelectedRowInUnappOrgStrManPowDataTblOnMakerSide() {
        return selectedRowInUnappOrgStrManPowDataTblOnMakerSide;
    }

    public void setSelectedRowInUnappOrgStrManPowDataTblOnMakerSide(StructureManPower selectedRowInUnappOrgStrManPowDataTblOnMakerSide) {
        this.selectedRowInUnappOrgStrManPowDataTblOnMakerSide = selectedRowInUnappOrgStrManPowDataTblOnMakerSide;
    }

    public boolean isPnlForAppOrgStrManPowDataTblOnMakerSideRender() {
        return pnlForAppOrgStrManPowDataTblOnMakerSideRender;
    }

    public void setPnlForAppOrgStrManPowDataTblOnMakerSideRender(boolean pnlForAppOrgStrManPowDataTblOnMakerSideRender) {
        this.pnlForAppOrgStrManPowDataTblOnMakerSideRender = pnlForAppOrgStrManPowDataTblOnMakerSideRender;
    }

    public StructureManPower getSelectedRowInappOrgStrManPowDataTblOnMakerSide() {
        return selectedRowInappOrgStrManPowDataTblOnMakerSide;
    }

    public void setSelectedRowInappOrgStrManPowDataTblOnMakerSide(StructureManPower selectedRowInappOrgStrManPowDataTblOnMakerSide) {
        this.selectedRowInappOrgStrManPowDataTblOnMakerSide = selectedRowInappOrgStrManPowDataTblOnMakerSide;
    }

    public boolean isPnlForBtnsOnMakerSideRender() {
        return pnlForBtnsOnMakerSideRender;
    }

    public void setPnlForBtnsOnMakerSideRender(boolean pnlForBtnsOnMakerSideRender) {
        this.pnlForBtnsOnMakerSideRender = pnlForBtnsOnMakerSideRender;
    }

    public boolean isPnlForOrgStrManPowToBeReviewedByCheckerDataTblAndBtnsRender() {
        return pnlForOrgStrManPowToBeReviewedByCheckerDataTblAndBtnsRender;
    }

    public void setPnlForOrgStrManPowToBeReviewedByCheckerDataTblAndBtnsRender(boolean pnlForOrgStrManPowToBeReviewedByCheckerDataTblAndBtnsRender) {
        this.pnlForOrgStrManPowToBeReviewedByCheckerDataTblAndBtnsRender = pnlForOrgStrManPowToBeReviewedByCheckerDataTblAndBtnsRender;
    }

    public List<StructureManPower> getToBeReviewdOrgStrManPow() {
        return toBeReviewdOrgStrManPow;
    }

    public void setToBeReviewdOrgStrManPow(List<StructureManPower> toBeReviewdOrgStrManPow) {
        this.toBeReviewdOrgStrManPow = toBeReviewdOrgStrManPow;
    }

    public List<StructureManPower> getSelectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl() {
        return selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl;
    }

    public void setSelectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl(List<StructureManPower> selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl) {
        this.selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl = selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl;
    }

    public boolean isPnlForAppOrgStrManPowDataTblOnCheckerSideRender() {
        return pnlForAppOrgStrManPowDataTblOnCheckerSideRender;
    }

    public void setPnlForAppOrgStrManPowDataTblOnCheckerSideRender(boolean pnlForAppOrgStrManPowDataTblOnCheckerSideRender) {
        this.pnlForAppOrgStrManPowDataTblOnCheckerSideRender = pnlForAppOrgStrManPowDataTblOnCheckerSideRender;
    }

    public List<StructureManPower> getSelectedRowsInAppOrgStrManPowDataTblOnCheckerSide() {
        return selectedRowsInAppOrgStrManPowDataTblOnCheckerSide;
    }

    public void setSelectedRowsInAppOrgStrManPowDataTblOnCheckerSide(List<StructureManPower> selectedRowsInAppOrgStrManPowDataTblOnCheckerSide) {
        this.selectedRowsInAppOrgStrManPowDataTblOnCheckerSide = selectedRowsInAppOrgStrManPowDataTblOnCheckerSide;
    }
    
    //===========Maker Methods==========================//
    
    public void orgStrDrpDnOnMakerSideValueChangeListener() {
//        OrganizationalStructureType ost = orgStrService.getByOrganizationalStructureTypeId(orgStrService.getByOrganizationalStructureId(strManPow.getOrganizationalStructure().getId()).getOrganizationalStructureTypeID());
//        List<Position> posList = posService.getByOrganizationalStructureTypeId(ost.getId());
//        posSelectItem = new ArrayList<>();
//        for (int i = 0; i < posList.size(); i++)
//        {
//            if(posList.get(i).isHasOrganizationalStructure()) {
//                PositionHavingOrganizationalStructure phos = posService.getPositionHavingOrganizationalStructureByID(posList.get(i).getID());
//                if(phos.getOrganizationalStructure().getId().equals(strManPow.getOrganizationalStructure().getId()))
//                    posSelectItem.add(new SelectItem(posList.get(i).getID(), posList.get(i).getTitle()));
//            }
//            else if(posList.get(i).isHasTier()) {
//                PositionTier pt = posService.getPositionTierByID(posList.get(i).getID());
//                if(pt.getID().equals(ost.getId()))
//                    posSelectItem.add(new SelectItem(posList.get(i).getID(), posList.get(i).getTitle()));
//            }
//            else
//                posSelectItem.add(new SelectItem(posList.get(i).getID(), posList.get(i).getTitle()));
//        }
    }
    
    public void unappOrgStrManPowDataTblOnMakerSideRowSelectedListener() {
        if("reversed entry".equals(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getStatus()) && !"-".equals(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getUnapprovedChange()))
        {
            strManPow.setId(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getId());
            strManPow.setOrganizationalStructure(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getUnapprovedOrganizationalStructure());
            strManPow.setPosition(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getUnapprovedPosition());
            strManPow.setRequiredNoOfWorkForce( Integer.parseInt(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getUnapprovedRequiredNoOfWorkForce()));
            strManPow.setCurrentlyAvailableSpace(Integer.parseInt(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getUnapprovedCurrentlyAvailableSpace()));
            strManPow.setCreatedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCreatedBy());
            strManPow.setCreationTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCreationTimeStamp());
            strManPow.setEditedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getEditedBy());
            strManPow.setEditingTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getEditingTimeStamp());
            strManPow.setApprovedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getApprovedBy());
            strManPow.setApprovedTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getApprovedTimeStamp());
            strManPow.setDeletedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getDeletedBy());
            strManPow.setDeletionTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getDeletionTimeStamp());
            strManPow.setUnapprovedChange(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getUnapprovedChange());
            strManPow.setStatus(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getStatus());
        }
        else if(("reversed entry".equals(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getStatus()) && "-".equals(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getUnapprovedChange())) || ("entry".equals(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getStatus())))
        {
            strManPow.setId(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getId());
            strManPow.setOrganizationalStructure(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getOrganizationalStructure());
            strManPow.setPosition(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getPosition());
            strManPow.setRequiredNoOfWorkForce(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getRequiredNoOfWorkForce());
            strManPow.setCurrentlyAvailableSpace(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCurrentlyAvailableSpace());
            strManPow.setCreatedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCreatedBy());
            strManPow.setCreationTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCreationTimeStamp());
            strManPow.setEditedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getEditedBy());
            strManPow.setEditingTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getEditingTimeStamp());
            strManPow.setApprovedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getApprovedBy());
            strManPow.setApprovedTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getApprovedTimeStamp());
            strManPow.setDeletedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getDeletedBy());
            strManPow.setDeletionTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getDeletionTimeStamp());
            strManPow.setUnapprovedChange(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getUnapprovedChange());
            strManPow.setStatus(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getStatus());
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Unexpected data status found. Please, Consult your system administrator."));
    }
    
    public void appOrgStrManPowDataTblOnMakerSideRowSelectedListener() {
        
    }
    
    public void btnForResetOrgStrManPowOnMakerSideHandler() {
        selectedRowInUnappOrgStrManPowDataTblOnMakerSide = null;
        selectedRowInappOrgStrManPowDataTblOnMakerSide = null;
        init();
    }
    
    public void btnForSaveOrgStrManPowOnMakerSideHandler() {
        if(selectedRowInUnappOrgStrManPowDataTblOnMakerSide == null)
        {
            strManPow.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
            strManPow.setCreationTimeStamp(new Date().toString());
            strManPow.setEditedBy("-");
            strManPow.setEditingTimeStamp("-");
            strManPow.setApprovedBy("-");
            strManPow.setApprovedTimeStamp("-");
            strManPow.setDeletedBy("-");
            strManPow.setDeletionTimeStamp("-");
            strManPow.setUnapprovedChange("-");
            strManPow.setStatus("entry");
            if(strManPowService.save(strManPow))
            {
                btnForResetOrgStrManPowOnMakerSideHandler();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully saved the data.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to save your data. Please, consult your system adminstrator."));
        }
        else
        {
            switch (selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getStatus()) {
                case "entry":
                    strManPow.setId(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getId());
                    strManPow.setCreatedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCreatedBy());
                    strManPow.setCreationTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCreationTimeStamp());
                    strManPow.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    strManPow.setEditingTimeStamp(new Date().toString());
                    strManPow.setApprovedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getApprovedBy());
                    strManPow.setApprovedTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getApprovedTimeStamp());
                    strManPow.setDeletedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getDeletedBy());
                    strManPow.setDeletionTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getDeletionTimeStamp());
                    strManPow.setUnapprovedChange(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getUnapprovedChange());
                    strManPow.setStatus(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getStatus());
                    if(strManPowService.save(strManPow))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetOrgStrManPowOnMakerSideHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                case "reversed entry":
                    strManPow.setUnapprovedChange(strManPow.getOrganizationalStructure().getId()+"|"+strManPow.getPosition().getID()+"|"+strManPow.getRequiredNoOfWorkForce()+"|"+strManPow.getCurrentlyAvailableSpace());
                    strManPow.setId(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getId());
                    strManPow.setOrganizationalStructure(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getOrganizationalStructure());
                    strManPow.setPosition(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getPosition());
                    strManPow.setRequiredNoOfWorkForce(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getRequiredNoOfWorkForce());
                    strManPow.setCurrentlyAvailableSpace(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCurrentlyAvailableSpace());
                    strManPow.setCreatedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCreatedBy());
                    strManPow.setCreationTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCreationTimeStamp());
                    strManPow.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    strManPow.setEditingTimeStamp(new Date().toString());
                    strManPow.setApprovedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getApprovedBy());
                    strManPow.setApprovedTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getApprovedTimeStamp());
                    strManPow.setDeletedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getDeletedBy());
                    strManPow.setDeletionTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getDeletionTimeStamp());
                    strManPow.setStatus(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getStatus());
                    if(strManPowService.save(strManPow))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetOrgStrManPowOnMakerSideHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                default:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Unexpected data status found. Please, Consult your system administrator."));
                    break;
            } 
        }
    }
    
    public void btnForSubmitOrgStrManPowOnMakerSideHandler() {
        if(selectedRowInUnappOrgStrManPowDataTblOnMakerSide != null)
        {
            switch (selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getStatus()) {
                case "entry":
                    strManPow.setId(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getId());
                    strManPow.setCreatedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCreatedBy());
                    strManPow.setCreationTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCreationTimeStamp());
                    strManPow.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    strManPow.setEditingTimeStamp(new Date().toString());
                    strManPow.setApprovedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getApprovedBy());
                    strManPow.setApprovedTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getApprovedTimeStamp());
                    strManPow.setDeletedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getDeletedBy());
                    strManPow.setDeletionTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getDeletionTimeStamp());
                    strManPow.setUnapprovedChange(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getUnapprovedChange());
                    strManPow.setStatus("submitted");
                    if(strManPowService.save(strManPow))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetOrgStrManPowOnMakerSideHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                case "reversed entry":
                    strManPow.setUnapprovedChange(strManPow.getOrganizationalStructure().getId()+"|"+strManPow.getPosition().getID()+"|"+strManPow.getRequiredNoOfWorkForce()+"|"+strManPow.getCurrentlyAvailableSpace());
                    strManPow.setId(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getId());
                    strManPow.setOrganizationalStructure(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getOrganizationalStructure());
                    strManPow.setPosition(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getPosition());
                    strManPow.setRequiredNoOfWorkForce(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getRequiredNoOfWorkForce());
                    strManPow.setCurrentlyAvailableSpace(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCurrentlyAvailableSpace());
                    strManPow.setCreatedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCreatedBy());
                    strManPow.setCreationTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getCreationTimeStamp());
                    strManPow.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    strManPow.setEditingTimeStamp(new Date().toString());
                    strManPow.setApprovedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getApprovedBy());
                    strManPow.setApprovedTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getApprovedTimeStamp());
                    strManPow.setDeletedBy(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getDeletedBy());
                    strManPow.setDeletionTimeStamp(selectedRowInUnappOrgStrManPowDataTblOnMakerSide.getDeletionTimeStamp());
                    strManPow.setStatus("reversed submission");
                    if(strManPowService.save(strManPow))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetOrgStrManPowOnMakerSideHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                default:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Unexpected data status found. Please, Consult your system administrator."));
                    break;
            }
        }
        else
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Failure", "Nothing to Submit.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void btnForDeleteOrgStrManPowOnMakerSideHandler() {
        
    }
    //===========Checker Methods========================//
    
    public void btnForApproveSelectedOrgStrManPowRowsOnCheckerSideHandler() {
        if(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.size() > 0)
        {
            int i;
            for(i= 0; i < selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.size(); i++)
            {
                if("submitted".equals(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getStatus()) || ("reversed submission".equals(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getStatus()) && "-".equals(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange())))
                {
                    strManPow.setId(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getId());
                    strManPow.setOrganizationalStructure(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getOrganizationalStructure());
                    strManPow.setPosition(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getPosition());
                    strManPow.setRequiredNoOfWorkForce(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getRequiredNoOfWorkForce());
                    strManPow.setCurrentlyAvailableSpace(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getCurrentlyAvailableSpace());
                    strManPow.setCreatedBy(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                    strManPow.setCreationTimeStamp(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                    strManPow.setEditedBy(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                    strManPow.setEditingTimeStamp(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                    strManPow.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                    strManPow.setApprovedTimeStamp(new Date().toString());
                    strManPow.setDeletedBy(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                    strManPow.setDeletionTimeStamp(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                    strManPow.setUnapprovedChange("-");
                    strManPow.setStatus("approved");
                    if(strManPowService.save(strManPow))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                       break; 
                }
                else if("reversed submission".equals(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getStatus()) && !"-".equals(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange()))
                {
                    strManPow.setId(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getId());
                    OrganizationalStructure orgStr = new OrganizationalStructure();orgStr.setId(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]);
                    strManPow.setOrganizationalStructure(orgStr);
                    Position pos = new Position();pos.setID(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]);
                    strManPow.setPosition(pos);
                    strManPow.setRequiredNoOfWorkForce(Integer.parseInt(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]));
                    strManPow.setCurrentlyAvailableSpace(Integer.parseInt(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]));
                    strManPow.setCreatedBy(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                    strManPow.setCreationTimeStamp(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                    strManPow.setEditedBy(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                    strManPow.setEditingTimeStamp(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                    strManPow.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                    strManPow.setApprovedTimeStamp(new Date().toString());
                    strManPow.setDeletedBy(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                    strManPow.setDeletionTimeStamp(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                    strManPow.setUnapprovedChange("-");
                    strManPow.setStatus("approved");
                    if(strManPowService.save(strManPow))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                       break;
                }
                else
                    break;
            }
            if(i==selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.size())
            {
                appOrgStrManPow = strManPowService.fetchAllApproved();
                toBeReviewdOrgStrManPow = strManPowService.fetchAllSubmitted();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully approved the data.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else
            {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Some Error Happened. Please, Consult your System Administrator.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        else
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Please select at least one row.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void btnForMakeSelectedOrgStrManPowRowsEditableOnCheckerSideHandler() {
        if(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.size()> 0)
        {
            int i;
            boolean outerBreak = false;
            for (i= 0; i < selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.size(); i++) {
                strManPow.setId(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getId());
                strManPow.setOrganizationalStructure(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getOrganizationalStructure());
                strManPow.setPosition(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getPosition());
                strManPow.setRequiredNoOfWorkForce(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getRequiredNoOfWorkForce());
                strManPow.setCurrentlyAvailableSpace(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getCurrentlyAvailableSpace());
                strManPow.setCreatedBy(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                strManPow.setCreationTimeStamp(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                strManPow.setEditedBy(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                strManPow.setEditingTimeStamp(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                strManPow.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                strManPow.setApprovedTimeStamp(new Date().toString());
                strManPow.setDeletedBy(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                strManPow.setDeletionTimeStamp(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                strManPow.setUnapprovedChange(selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange());
                switch (selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.get(i).getStatus()) {
                    case "submitted":
                        strManPow.setStatus("entry");
                        break;
                    case "reversed submission":
                        strManPow.setStatus("reversed entry");
                        break;
                    default:
                        outerBreak = true;
                        break;
                }
                if(outerBreak)
                    break;
                else
                {
                    if(strManPowService.save(strManPow))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                        break;
                }
            }
            if(i==selectedRowsInOrgStrManPowToBeReviewedByCheckerDataTbl.size())
            {
                appOrgStrManPow = strManPowService.fetchAllApproved();
                toBeReviewdOrgStrManPow = strManPowService.fetchAllSubmitted();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully allowed the data to be editable.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else
            {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Some Error Happened. Please, Consult your System Administrator.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        else
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Please select at least one row.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void btnForDeleteSelectedOrgStrManPowRowsOnCheckerSide() {
        
    }
}
//<p:ajax event="itemSelect" listener="#{structureManPowerBean.orgStrDrpDnOnMakerSideValueChangeListener}" update="frmForMaker:drpDnForPosOnMakerSide" ></p:ajax>