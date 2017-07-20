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
import com.dashen.hrms.Vacancy;
import com.dashen.hrms.service.PositionService;
import com.dashen.hrms.service.StructureService;
import com.dashen.hrms.service.VacancyService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class VacancyBean {
    //===========Users==================================//
    
    //GrantedAuthority maker = new SimpleGrantedAuthority(UserAuthority.MAKER);
    //GrantedAuthority checker = new SimpleGrantedAuthority(UserAuthority.CHECKER);
    
    //===========Auto Wired Objects=====================//
    
    @Autowired
    VacancyService vacService;
    @Autowired
    StructureService orgStrService;
    @Autowired
    PositionService posService;
    
    //===========Common Variables=======================//
    
    private boolean pnlForRegPageHeadingRender;
    private boolean pnlForAppPageHeadingRender;
    private List<Vacancy> appVacs;
    
    //===========Maker Variables========================//
    
    private boolean pnlForInputFieldsOnMakerSideRender;
    private Vacancy vac;
    private List<SelectItem> orgStrSelectItem;
    private List<SelectItem> posSelectItem;
    private boolean pnlForPopVacsDataTblOnMakerSideRender;
    private List<Vacancy> popVacs;
    private Vacancy selectedRowInPopVacsDataTblOnMakerSide;
    private boolean pnlForPopVacsBtnOnMakerSideRender;
    private boolean pnlForUnappVacsDataTblOnMakerSideRender;
    private List<Vacancy> unappVacs;
    private Vacancy selectedRowInUnappVacsDataTblOnMakerSide;
    private boolean pnlForAppVacsDataTblOnMakerSideRender;
    private Vacancy selectedRowInappVacsDataTblOnMakerSide;
    private boolean pnlForBtnsOnMakerSideRender;
    
    //===========Checker Variables======================//
    
    private boolean pnlForVacsToBeReviewedByCheckerDataTblAndBtnsRender;
    private List<Vacancy> toBeReviewdvacs;
    private List<Vacancy> selectedRowsInVacsToBeReviewedByCheckerDataTbl;
    private boolean pnlForAppVacsDataTblOnCheckerSideRender;
    private List<Vacancy> selectedRowsInAppVacsDataTblOnCheckerSide;
    
    //===========Init Method============================//
    
    @PostConstruct
    public void init() {
        vac = new Vacancy();
        if(CurrentUser.getCurrentUser().getUsername().equals("mulugetak")) {
            pnlForRegPageHeadingRender = true;
            pnlForInputFieldsOnMakerSideRender = true;
            pnlForPopVacsDataTblOnMakerSideRender = true;
            pnlForPopVacsBtnOnMakerSideRender = true;
            pnlForUnappVacsDataTblOnMakerSideRender = true;
            pnlForAppVacsDataTblOnMakerSideRender = true;
            pnlForBtnsOnMakerSideRender = true;
            
            pnlForAppPageHeadingRender = false;
            pnlForVacsToBeReviewedByCheckerDataTblAndBtnsRender = false;
            pnlForAppVacsDataTblOnCheckerSideRender = false;
            
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
            appVacs = vacService.fetchAllApproved();
            unappVacs = vacService.fetchAllUnsubmittedOrEditable();
            for(int i = 0; i < unappVacs.size(); i++)
            {
                if(!"-".equals(unappVacs.get(i).getUnapprovedChange()))
                {
                    unappVacs.get(i).setUnapprovedOrganizationalStructure(orgStrService.getByOrganizationalStructureId(unappVacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]));
                    unappVacs.get(i).setUnapprovedOrganizationalStructureArrow(true);
                    unappVacs.get(i).setUnapprovedOrganizationalStructureDisplay(true);
                    unappVacs.get(i).setUnapprovedPosition((posService.getByID(unappVacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1])));
                    unappVacs.get(i).setUnapprovedPositionArrow(true);
                    unappVacs.get(i).setUnapprovedPositionDisplay(true);
                    unappVacs.get(i).setUnapprovedVacancyNumber(unappVacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]);
                    unappVacs.get(i).setUnapprovedVacancyNumberArrow(true);
                    unappVacs.get(i).setUnapprovedVacancyNumberDisplay(true);
                    unappVacs.get(i).setUnapprovedQualification(unappVacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    unappVacs.get(i).setUnapprovedQualificationArrow(true);
                    unappVacs.get(i).setUnapprovedQualificationDisplay(true);
                    unappVacs.get(i).setUnapprovedTrainingOrSkill(unappVacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[4]);
                    unappVacs.get(i).setUnapprovedTrainingOrSkillArrow(true);
                    unappVacs.get(i).setUnapprovedTrainingOrSkillDisplay(true);
                    unappVacs.get(i).setUnapprovedExperience(unappVacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[5]);
                    unappVacs.get(i).setUnapprovedExperienceArrow(true);
                    unappVacs.get(i).setUnapprovedExperienceDisplay(true);
                    unappVacs.get(i).setUnapprovedEligibility(unappVacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[6]);
                    unappVacs.get(i).setUnapprovedEligibilityArrow(true);
                    unappVacs.get(i).setUnapproveEligibilityDisplay(true);
                    unappVacs.get(i).setUnapprovedOpenFrom(unappVacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[7]);
                    unappVacs.get(i).setUnapprovedOpenFromArrow(true);
                    unappVacs.get(i).setUnapprovedOpenFromDisplay(true);
                    unappVacs.get(i).setUnapprovedOpenTo(unappVacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[8]);
                    unappVacs.get(i).setUnapprovedOpenToArrow(true);
                    unappVacs.get(i).setUnapprovedOpenToDisplay(true);
                    unappVacs.get(i).setUnapprovedRequiredNo(unappVacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[9]);
                    unappVacs.get(i).setUnapprovedRequiredNoArrow(true);
                    unappVacs.get(i).setUnapprovedRequiredNoDisplay(true);
                    unappVacs.get(i).setUnapprovedRemark(unappVacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[10]);
                    unappVacs.get(i).setUnapprovedRemarkArrow(true);
                    unappVacs.get(i).setUnapproveRemarkDisplay(true);
                    unappVacs.get(i).setUnapprovedVacancyType(unappVacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[11]);
                    unappVacs.get(i).setUnapprovedVacancyTypeArrow(true);
                    unappVacs.get(i).setUnapproveVacancyTypeDisplay(true);
                }
            }
        }
        if(CurrentUser.getCurrentUser().getUsername().equals("dawits")) {
            pnlForAppPageHeadingRender = true;
            pnlForVacsToBeReviewedByCheckerDataTblAndBtnsRender = true;
            pnlForAppVacsDataTblOnCheckerSideRender = true;
            
            pnlForRegPageHeadingRender = false;
            pnlForInputFieldsOnMakerSideRender = false;
            pnlForPopVacsDataTblOnMakerSideRender = false;
            pnlForPopVacsBtnOnMakerSideRender = false;
            pnlForUnappVacsDataTblOnMakerSideRender = false;
            pnlForAppVacsDataTblOnMakerSideRender = false;
            pnlForBtnsOnMakerSideRender = false;
            
            appVacs = vacService.fetchAllApproved();
            toBeReviewdvacs = vacService.fetchAllSubmitted();
            for(int i = 0; i < toBeReviewdvacs.size(); i++)
            {
                if(!"-".equals(toBeReviewdvacs.get(i).getUnapprovedChange()))
                {
                    toBeReviewdvacs.get(i).setUnapprovedOrganizationalStructure(orgStrService.getByOrganizationalStructureId(toBeReviewdvacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]));
                    toBeReviewdvacs.get(i).setUnapprovedOrganizationalStructureArrow(true);
                    toBeReviewdvacs.get(i).setUnapprovedOrganizationalStructureDisplay(true);
                    toBeReviewdvacs.get(i).setUnapprovedPosition((posService.getByID(toBeReviewdvacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1])));
                    toBeReviewdvacs.get(i).setUnapprovedPositionArrow(true);
                    toBeReviewdvacs.get(i).setUnapprovedPositionDisplay(true);
                    toBeReviewdvacs.get(i).setUnapprovedVacancyNumber(toBeReviewdvacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]);
                    toBeReviewdvacs.get(i).setUnapprovedVacancyNumberArrow(true);
                    toBeReviewdvacs.get(i).setUnapprovedVacancyNumberDisplay(true);
                    toBeReviewdvacs.get(i).setUnapprovedQualification(toBeReviewdvacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    toBeReviewdvacs.get(i).setUnapprovedQualificationArrow(true);
                    toBeReviewdvacs.get(i).setUnapprovedQualificationDisplay(true);
                    toBeReviewdvacs.get(i).setUnapprovedTrainingOrSkill(toBeReviewdvacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[4]);
                    toBeReviewdvacs.get(i).setUnapprovedTrainingOrSkillArrow(true);
                    toBeReviewdvacs.get(i).setUnapprovedTrainingOrSkillDisplay(true);
                    toBeReviewdvacs.get(i).setUnapprovedExperience(toBeReviewdvacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[5]);
                    toBeReviewdvacs.get(i).setUnapprovedExperienceArrow(true);
                    toBeReviewdvacs.get(i).setUnapprovedExperienceDisplay(true);
                    toBeReviewdvacs.get(i).setUnapprovedEligibility(toBeReviewdvacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[6]);
                    toBeReviewdvacs.get(i).setUnapprovedEligibilityArrow(true);
                    toBeReviewdvacs.get(i).setUnapproveEligibilityDisplay(true);
                    toBeReviewdvacs.get(i).setUnapprovedOpenFrom(toBeReviewdvacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[7]);
                    toBeReviewdvacs.get(i).setUnapprovedOpenFromArrow(true);
                    toBeReviewdvacs.get(i).setUnapprovedOpenFromDisplay(true);
                    toBeReviewdvacs.get(i).setUnapprovedOpenTo(toBeReviewdvacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[8]);
                    toBeReviewdvacs.get(i).setUnapprovedOpenToArrow(true);
                    toBeReviewdvacs.get(i).setUnapprovedOpenToDisplay(true);
                    toBeReviewdvacs.get(i).setUnapprovedRequiredNo(toBeReviewdvacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[9]);
                    toBeReviewdvacs.get(i).setUnapprovedRequiredNoArrow(true);
                    toBeReviewdvacs.get(i).setUnapprovedRequiredNoDisplay(true);
                    toBeReviewdvacs.get(i).setUnapprovedRemark(toBeReviewdvacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[10]);
                    toBeReviewdvacs.get(i).setUnapprovedRemarkArrow(true);
                    toBeReviewdvacs.get(i).setUnapproveRemarkDisplay(true);
                    toBeReviewdvacs.get(i).setUnapprovedVacancyType(toBeReviewdvacs.get(i).getUnapprovedChange().split(Pattern.quote("|"))[11]);
                    toBeReviewdvacs.get(i).setUnapprovedVacancyTypeArrow(true);
                    toBeReviewdvacs.get(i).setUnapproveVacancyTypeDisplay(true);
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

    public List<Vacancy> getAppVacs() {
        return appVacs;
    }

    public void setAppVacs(List<Vacancy> appVacs) {
        this.appVacs = appVacs;
    }

    public boolean isPnlForInputFieldsOnMakerSideRender() {
        return pnlForInputFieldsOnMakerSideRender;
    }

    public void setPnlForInputFieldsOnMakerSideRender(boolean pnlForInputFieldsOnMakerSideRender) {
        this.pnlForInputFieldsOnMakerSideRender = pnlForInputFieldsOnMakerSideRender;
    }

    public Vacancy getVac() {
        return vac;
    }

    public void setVac(Vacancy vac) {
        this.vac = vac;
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

    public boolean isPnlForPopVacsDataTblOnMakerSideRender() {
        return pnlForPopVacsDataTblOnMakerSideRender;
    }

    public void setPnlForPopVacsDataTblOnMakerSideRender(boolean pnlForPopVacsDataTblOnMakerSideRender) {
        this.pnlForPopVacsDataTblOnMakerSideRender = pnlForPopVacsDataTblOnMakerSideRender;
    }

    public List<Vacancy> getPopVacs() {
        return popVacs;
    }

    public void setPopVacs(List<Vacancy> popVacs) {
        this.popVacs = popVacs;
    }

    public Vacancy getSelectedRowInPopVacsDataTblOnMakerSide() {
        return selectedRowInPopVacsDataTblOnMakerSide;
    }

    public void setSelectedRowInPopVacsDataTblOnMakerSide(Vacancy selectedRowInPopVacsDataTblOnMakerSide) {
        this.selectedRowInPopVacsDataTblOnMakerSide = selectedRowInPopVacsDataTblOnMakerSide;
    }

    public boolean isPnlForPopVacsBtnOnMakerSideRender() {
        return pnlForPopVacsBtnOnMakerSideRender;
    }

    public void setPnlForPopVacsBtnOnMakerSideRender(boolean pnlForPopVacsBtnOnMakerSideRender) {
        this.pnlForPopVacsBtnOnMakerSideRender = pnlForPopVacsBtnOnMakerSideRender;
    }

    public boolean isPnlForUnappVacsDataTblOnMakerSideRender() {
        return pnlForUnappVacsDataTblOnMakerSideRender;
    }

    public void setPnlForUnappVacsDataTblOnMakerSideRender(boolean pnlForUnappVacsDataTblOnMakerSideRender) {
        this.pnlForUnappVacsDataTblOnMakerSideRender = pnlForUnappVacsDataTblOnMakerSideRender;
    }

    public List<Vacancy> getUnappVacs() {
        return unappVacs;
    }

    public void setUnappVacs(List<Vacancy> unappVacs) {
        this.unappVacs = unappVacs;
    }

    public Vacancy getSelectedRowInUnappVacsDataTblOnMakerSide() {
        return selectedRowInUnappVacsDataTblOnMakerSide;
    }

    public void setSelectedRowInUnappVacsDataTblOnMakerSide(Vacancy selectedRowInUnappVacsDataTblOnMakerSide) {
        this.selectedRowInUnappVacsDataTblOnMakerSide = selectedRowInUnappVacsDataTblOnMakerSide;
    }

    public boolean isPnlForAppVacsDataTblOnMakerSideRender() {
        return pnlForAppVacsDataTblOnMakerSideRender;
    }

    public void setPnlForAppVacsDataTblOnMakerSideRender(boolean pnlForAppVacsDataTblOnMakerSideRender) {
        this.pnlForAppVacsDataTblOnMakerSideRender = pnlForAppVacsDataTblOnMakerSideRender;
    }

    public Vacancy getSelectedRowInappVacsDataTblOnMakerSide() {
        return selectedRowInappVacsDataTblOnMakerSide;
    }

    public void setSelectedRowInappVacsDataTblOnMakerSide(Vacancy selectedRowInappVacsDataTblOnMakerSide) {
        this.selectedRowInappVacsDataTblOnMakerSide = selectedRowInappVacsDataTblOnMakerSide;
    }

    public boolean isPnlForBtnsOnMakerSideRender() {
        return pnlForBtnsOnMakerSideRender;
    }

    public void setPnlForBtnsOnMakerSideRender(boolean pnlForBtnsOnMakerSideRender) {
        this.pnlForBtnsOnMakerSideRender = pnlForBtnsOnMakerSideRender;
    }

    public boolean isPnlForVacsToBeReviewedByCheckerDataTblAndBtnsRender() {
        return pnlForVacsToBeReviewedByCheckerDataTblAndBtnsRender;
    }

    public void setPnlForVacsToBeReviewedByCheckerDataTblAndBtnsRender(boolean pnlForVacsToBeReviewedByCheckerDataTblAndBtnsRender) {
        this.pnlForVacsToBeReviewedByCheckerDataTblAndBtnsRender = pnlForVacsToBeReviewedByCheckerDataTblAndBtnsRender;
    }

    public List<Vacancy> getToBeReviewdvacs() {
        return toBeReviewdvacs;
    }

    public void setToBeReviewdvacs(List<Vacancy> toBeReviewdvacs) {
        this.toBeReviewdvacs = toBeReviewdvacs;
    }

    public List<Vacancy> getSelectedRowsInVacsToBeReviewedByCheckerDataTbl() {
        return selectedRowsInVacsToBeReviewedByCheckerDataTbl;
    }

    public void setSelectedRowsInVacsToBeReviewedByCheckerDataTbl(List<Vacancy> selectedRowsInVacsToBeReviewedByCheckerDataTbl) {
        this.selectedRowsInVacsToBeReviewedByCheckerDataTbl = selectedRowsInVacsToBeReviewedByCheckerDataTbl;
    }

    public boolean isPnlForAppVacsDataTblOnCheckerSideRender() {
        return pnlForAppVacsDataTblOnCheckerSideRender;
    }

    public void setPnlForAppVacsDataTblOnCheckerSideRender(boolean pnlForAppVacsDataTblOnCheckerSideRender) {
        this.pnlForAppVacsDataTblOnCheckerSideRender = pnlForAppVacsDataTblOnCheckerSideRender;
    }

    public List<Vacancy> getSelectedRowsInAppVacsDataTblOnCheckerSide() {
        return selectedRowsInAppVacsDataTblOnCheckerSide;
    }
    
    public void setSelectedRowsInAppVacsDataTblOnCheckerSide(List<Vacancy> selectedRowsInAppVacsDataTblOnCheckerSide) {    
        this.selectedRowsInAppVacsDataTblOnCheckerSide = selectedRowsInAppVacsDataTblOnCheckerSide;
    }

    //===========Maker Methods==========================//
    
    public void orgStrDrpDnOnMakerSideValueChangeListener() {
//        OrganizationalStructureType ost = orgStrService.getByOrganizationalStructureTypeId(orgStrService.getByOrganizationalStructureId(vac.getOrganizationalStructure().getId()).getOrganizationalStructureTypeID());
//        List<Position> posList = posService.getByOrganizationalStructureTypeId(ost.getId());
//        posSelectItem = new ArrayList<>();
//        for (int i = 0; i < posList.size(); i++)
//        {
//            if(posList.get(i).isHasOrganizationalStructure()) {
//                PositionHavingOrganizationalStructure phos = posService.getPositionHavingOrganizationalStructureByID(posList.get(i).getID());
//                if(phos.getOrganizationalStructure().getId().equals(vac.getOrganizationalStructure().getId()))
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
    
    public void popVacsDataTblOnMakerSideRowSelectedListener() {
    }
    
    public void popVacsBtnOnMakeSideClickListener() {
        
    }
    
    public void unappVacsDataTblOnMakerSideRowSelectedListener() {
        if("reversed entry".equals(selectedRowInUnappVacsDataTblOnMakerSide.getStatus()) && !"-".equals(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedChange()))
        {
            vac.setId(selectedRowInUnappVacsDataTblOnMakerSide.getId());
            vac.setOrganizationalStructure(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedOrganizationalStructure());
            vac.setPosition(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedPosition());
            vac.setVacancyNumber(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedVacancyNumber());
            vac.setQualification(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedQualification());
            vac.setTrainingOrSkill(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedTrainingOrSkill());
            vac.setExperience(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedExperience());
            vac.setEligibility(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedEligibility());
            try{vac.setOpenFromInDate(new SimpleDateFormat("dd-MMM-yyyy").parse(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedOpenFrom()));}catch (ParseException ex){FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getLocalizedMessage()));}
            try{vac.setOpenToInDate(new SimpleDateFormat("dd-MMM-yyyy").parse(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedOpenTo()));}catch (ParseException ex){FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getLocalizedMessage()));}
            vac.setRequiredNo(Integer.parseInt(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedRequiredNo()));
            vac.setRemark(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedRemark());
            vac.setVacancyType(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedVacancyType());
            vac.setCreatedBy(selectedRowInUnappVacsDataTblOnMakerSide.getCreatedBy());
            vac.setCreationTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getCreationTimeStamp());
            vac.setEditedBy(selectedRowInUnappVacsDataTblOnMakerSide.getEditedBy());
            vac.setEditingTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getEditingTimeStamp());
            vac.setApprovedBy(selectedRowInUnappVacsDataTblOnMakerSide.getApprovedBy());
            vac.setApprovedTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getApprovedTimeStamp());
            vac.setDeletedBy(selectedRowInUnappVacsDataTblOnMakerSide.getDeletedBy());
            vac.setDeletionTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getDeletionTimeStamp());
            vac.setUnapprovedChange(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedChange());
            vac.setStatus(selectedRowInUnappVacsDataTblOnMakerSide.getStatus());
        }
        else if(("reversed entry".equals(selectedRowInUnappVacsDataTblOnMakerSide.getStatus()) && "-".equals(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedChange())) || ("entry".equals(selectedRowInUnappVacsDataTblOnMakerSide.getStatus())))
        {
            vac.setId(selectedRowInUnappVacsDataTblOnMakerSide.getId());
            vac.setOrganizationalStructure(selectedRowInUnappVacsDataTblOnMakerSide.getOrganizationalStructure());
            vac.setPosition(selectedRowInUnappVacsDataTblOnMakerSide.getPosition());
            vac.setVacancyNumber(selectedRowInUnappVacsDataTblOnMakerSide.getVacancyNumber());
            vac.setQualification(selectedRowInUnappVacsDataTblOnMakerSide.getQualification());
            vac.setTrainingOrSkill(selectedRowInUnappVacsDataTblOnMakerSide.getTrainingOrSkill());
            vac.setExperience(selectedRowInUnappVacsDataTblOnMakerSide.getExperience());
            vac.setEligibility(selectedRowInUnappVacsDataTblOnMakerSide.getEligibility());
            try{vac.setOpenFromInDate(new SimpleDateFormat("dd-MMM-yyyy").parse(selectedRowInUnappVacsDataTblOnMakerSide.getOpenFrom()));}catch (ParseException ex){FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getLocalizedMessage()));}
            try{vac.setOpenToInDate(new SimpleDateFormat("dd-MMM-yyyy").parse(selectedRowInUnappVacsDataTblOnMakerSide.getOpenTo()));}catch (ParseException ex){FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getLocalizedMessage()));}
            vac.setRequiredNo(selectedRowInUnappVacsDataTblOnMakerSide.getRequiredNo());
            vac.setRemark(selectedRowInUnappVacsDataTblOnMakerSide.getRemark());
            vac.setVacancyType(selectedRowInUnappVacsDataTblOnMakerSide.getVacancyType());
            vac.setCreatedBy(selectedRowInUnappVacsDataTblOnMakerSide.getCreatedBy());
            vac.setCreationTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getCreationTimeStamp());
            vac.setEditedBy(selectedRowInUnappVacsDataTblOnMakerSide.getEditedBy());
            vac.setEditingTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getEditingTimeStamp());
            vac.setApprovedBy(selectedRowInUnappVacsDataTblOnMakerSide.getApprovedBy());
            vac.setApprovedTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getApprovedTimeStamp());
            vac.setDeletedBy(selectedRowInUnappVacsDataTblOnMakerSide.getDeletedBy());
            vac.setDeletionTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getDeletionTimeStamp());
            vac.setUnapprovedChange(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedChange());
            vac.setStatus(selectedRowInUnappVacsDataTblOnMakerSide.getStatus());
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Unexpected data status found. Please, Consult your system administrator."));
    }
    
    public void appVacsDataTblOnMakerSideRowSelectedListener() {
        
    }
    
    public void btnForResetVacOnMakerSideHandler() {
        selectedRowInUnappVacsDataTblOnMakerSide = null;
        selectedRowInappVacsDataTblOnMakerSide = null;
        init();
    }
    
    public void btnForSaveVacOnMakerSideHandler() {
        if(selectedRowInUnappVacsDataTblOnMakerSide == null)
        {
            vac.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
            vac.setCreationTimeStamp(new Date().toString());
            vac.setEditedBy("-");
            vac.setEditingTimeStamp("-");
            vac.setApprovedBy("-");
            vac.setApprovedTimeStamp("-");
            vac.setDeletedBy("-");
            vac.setDeletionTimeStamp("-");
            vac.setUnapprovedChange("-");
            vac.setStatus("entry");
            if(vacService.save(vac))
            {
                btnForResetVacOnMakerSideHandler();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully saved the data.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to save your data. Please, consult your system adminstrator."));
        }
        else
        {
            switch (selectedRowInUnappVacsDataTblOnMakerSide.getStatus()) {
                case "entry":
                    vac.setId(selectedRowInUnappVacsDataTblOnMakerSide.getId());
                    vac.setCreatedBy(selectedRowInUnappVacsDataTblOnMakerSide.getCreatedBy());
                    vac.setCreationTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getCreationTimeStamp());
                    vac.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    vac.setEditingTimeStamp(new Date().toString());
                    vac.setApprovedBy(selectedRowInUnappVacsDataTblOnMakerSide.getApprovedBy());
                    vac.setApprovedTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getApprovedTimeStamp());
                    vac.setDeletedBy(selectedRowInUnappVacsDataTblOnMakerSide.getDeletedBy());
                    vac.setDeletionTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getDeletionTimeStamp());
                    vac.setUnapprovedChange(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedChange());
                    vac.setStatus(selectedRowInUnappVacsDataTblOnMakerSide.getStatus());
                    if(vacService.save(vac))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetVacOnMakerSideHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                case "reversed entry":
                    vac.setUnapprovedChange(vac.getOrganizationalStructure().getId()+"|"+vac.getPosition().getID()+"|"+vac.getVacancyNumber()+"|"+vac.getQualification()+"|"+vac.getTrainingOrSkill()+"|"+vac.getExperience()+"|"+vac.getEligibility()+"|"+vac.getOpenFrom()+"|"+vac.getOpenTo()+"|"+vac.getRequiredNo()+"|"+vac.getRemark()+"|"+vac.getVacancyType());
                    vac.setId(selectedRowInUnappVacsDataTblOnMakerSide.getId());
                    vac.setOrganizationalStructure(selectedRowInUnappVacsDataTblOnMakerSide.getOrganizationalStructure());
                    vac.setPosition(selectedRowInUnappVacsDataTblOnMakerSide.getPosition());
                    vac.setVacancyNumber(selectedRowInUnappVacsDataTblOnMakerSide.getVacancyNumber());
                    vac.setQualification(selectedRowInUnappVacsDataTblOnMakerSide.getQualification());
                    vac.setTrainingOrSkill(selectedRowInUnappVacsDataTblOnMakerSide.getTrainingOrSkill());
                    vac.setExperience(selectedRowInUnappVacsDataTblOnMakerSide.getExperience());
                    vac.setEligibility(selectedRowInUnappVacsDataTblOnMakerSide.getEligibility());
                    vac.setOpenFrom(selectedRowInUnappVacsDataTblOnMakerSide.getOpenFrom());
                    vac.setOpenTo(selectedRowInUnappVacsDataTblOnMakerSide.getOpenTo());
                    vac.setRequiredNo(selectedRowInUnappVacsDataTblOnMakerSide.getRequiredNo());
                    vac.setRemark(selectedRowInUnappVacsDataTblOnMakerSide.getRemark());
                    vac.setVacancyType(selectedRowInUnappVacsDataTblOnMakerSide.getVacancyType());
                    vac.setCreatedBy(selectedRowInUnappVacsDataTblOnMakerSide.getCreatedBy());
                    vac.setCreationTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getCreationTimeStamp());
                    vac.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    vac.setEditingTimeStamp(new Date().toString());
                    vac.setApprovedBy(selectedRowInUnappVacsDataTblOnMakerSide.getApprovedBy());
                    vac.setApprovedTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getApprovedTimeStamp());
                    vac.setDeletedBy(selectedRowInUnappVacsDataTblOnMakerSide.getDeletedBy());
                    vac.setDeletionTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getDeletionTimeStamp());
                    vac.setStatus(selectedRowInUnappVacsDataTblOnMakerSide.getStatus());
                    if(vacService.save(vac))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetVacOnMakerSideHandler();
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
    
    public void btnForSubmitVacOnMakerSideHandler() {
        if(selectedRowInUnappVacsDataTblOnMakerSide != null)
        {
            switch (selectedRowInUnappVacsDataTblOnMakerSide.getStatus()) {
                case "entry":
                    vac.setId(selectedRowInUnappVacsDataTblOnMakerSide.getId());
                    vac.setCreatedBy(selectedRowInUnappVacsDataTblOnMakerSide.getCreatedBy());
                    vac.setCreationTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getCreationTimeStamp());
                    vac.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    vac.setEditingTimeStamp(new Date().toString());
                    vac.setApprovedBy(selectedRowInUnappVacsDataTblOnMakerSide.getApprovedBy());
                    vac.setApprovedTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getApprovedTimeStamp());
                    vac.setDeletedBy(selectedRowInUnappVacsDataTblOnMakerSide.getDeletedBy());
                    vac.setDeletionTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getDeletionTimeStamp());
                    vac.setUnapprovedChange(selectedRowInUnappVacsDataTblOnMakerSide.getUnapprovedChange());
                    vac.setStatus("submitted");
                    if(vacService.save(vac))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetVacOnMakerSideHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                case "reversed entry":
                    vac.setUnapprovedChange(vac.getOrganizationalStructure().getId()+"|"+vac.getPosition().getID()+"|"+vac.getVacancyNumber()+"|"+vac.getQualification()+"|"+vac.getTrainingOrSkill()+"|"+vac.getExperience()+"|"+vac.getEligibility()+"|"+vac.getOpenFrom()+"|"+vac.getOpenTo()+"|"+vac.getRequiredNo()+"|"+vac.getRemark()+"|"+vac.getVacancyType());
                    vac.setId(selectedRowInUnappVacsDataTblOnMakerSide.getId());
                    vac.setOrganizationalStructure(selectedRowInUnappVacsDataTblOnMakerSide.getOrganizationalStructure());
                    vac.setPosition(selectedRowInUnappVacsDataTblOnMakerSide.getPosition());
                    vac.setVacancyNumber(selectedRowInUnappVacsDataTblOnMakerSide.getVacancyNumber());
                    vac.setQualification(selectedRowInUnappVacsDataTblOnMakerSide.getQualification());
                    vac.setTrainingOrSkill(selectedRowInUnappVacsDataTblOnMakerSide.getTrainingOrSkill());
                    vac.setExperience(selectedRowInUnappVacsDataTblOnMakerSide.getExperience());
                    vac.setEligibility(selectedRowInUnappVacsDataTblOnMakerSide.getEligibility());
                    vac.setOpenFrom(selectedRowInUnappVacsDataTblOnMakerSide.getOpenFrom());
                    vac.setOpenTo(selectedRowInUnappVacsDataTblOnMakerSide.getOpenTo());
                    vac.setRequiredNo(selectedRowInUnappVacsDataTblOnMakerSide.getRequiredNo());
                    vac.setRemark(selectedRowInUnappVacsDataTblOnMakerSide.getRemark());
                    vac.setVacancyType(selectedRowInUnappVacsDataTblOnMakerSide.getVacancyType());
                    vac.setCreatedBy(selectedRowInUnappVacsDataTblOnMakerSide.getCreatedBy());
                    vac.setCreationTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getCreationTimeStamp());
                    vac.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    vac.setEditingTimeStamp(new Date().toString());
                    vac.setApprovedBy(selectedRowInUnappVacsDataTblOnMakerSide.getApprovedBy());
                    vac.setApprovedTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getApprovedTimeStamp());
                    vac.setDeletedBy(selectedRowInUnappVacsDataTblOnMakerSide.getDeletedBy());
                    vac.setDeletionTimeStamp(selectedRowInUnappVacsDataTblOnMakerSide.getDeletionTimeStamp());
                    vac.setStatus("reversed submission");
                    if(vacService.save(vac))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetVacOnMakerSideHandler();
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
    
    public void btnForDeleteVacOnMakerSideHandler() {
        
    }
    //===========Checker Methods========================//
    
    public void btnForApproveSelectedVacRowsOnCheckerSideHandler() {
        if(selectedRowsInVacsToBeReviewedByCheckerDataTbl.size() > 0)
        {
            int i;
            for(i= 0; i < selectedRowsInVacsToBeReviewedByCheckerDataTbl.size(); i++)
            {
                if("submitted".equals(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getStatus()) || ("reversed submission".equals(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getStatus()) && "-".equals(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange())))
                {
                    vac.setId(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getId());
                    vac.setOrganizationalStructure(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getOrganizationalStructure());
                    vac.setPosition(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getPosition());
                    vac.setVacancyNumber(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getVacancyNumber());
                    vac.setQualification(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getQualification());
                    vac.setTrainingOrSkill(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getTrainingOrSkill());
                    vac.setExperience(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getExperience());
                    vac.setEligibility(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getEligibility());
                    vac.setOpenFrom(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getOpenFrom());
                    vac.setOpenTo(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getOpenTo());
                    vac.setRequiredNo(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getRequiredNo());
                    vac.setRemark(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getRemark());
                    vac.setVacancyType(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getVacancyType());
                    vac.setCreatedBy(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                    vac.setCreationTimeStamp(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                    vac.setEditedBy(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                    vac.setEditingTimeStamp(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                    vac.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                    vac.setApprovedTimeStamp(new Date().toString());
                    vac.setDeletedBy(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                    vac.setDeletionTimeStamp(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                    vac.setUnapprovedChange("-");
                    vac.setStatus("approved");
                    if(vacService.save(vac))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                       break; 
                }
                else if("reversed submission".equals(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getStatus()) && !"-".equals(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange()))
                {
                    vac.setId(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getId());
                    OrganizationalStructure orgStr = new OrganizationalStructure();orgStr.setId(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]);
                    vac.setOrganizationalStructure(orgStr);
                    Position pos = new Position();pos.setID(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]);
                    vac.setPosition(pos);
                    vac.setVacancyNumber(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]);
                    vac.setQualification(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    vac.setTrainingOrSkill(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[4]);
                    vac.setExperience(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[5]);
                    vac.setEligibility(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[6]);
                    vac.setOpenFrom(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[7]);
                    vac.setOpenTo(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[8]);
                    vac.setRequiredNo(Integer.parseInt(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[9]));
                    vac.setRemark(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[10]);
                    vac.setVacancyType(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[11]);
                    vac.setCreatedBy(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                    vac.setCreationTimeStamp(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                    vac.setEditedBy(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                    vac.setEditingTimeStamp(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                    vac.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                    vac.setApprovedTimeStamp(new Date().toString());
                    vac.setDeletedBy(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                    vac.setDeletionTimeStamp(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                    vac.setUnapprovedChange("-");
                    vac.setStatus("approved");
                    if(vacService.save(vac))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                       break;
                }
                else
                    break;
            }
            if(i==selectedRowsInVacsToBeReviewedByCheckerDataTbl.size())
            {
                appVacs = vacService.fetchAllApproved();
                toBeReviewdvacs = vacService.fetchAllSubmitted();
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
    
    public void btnForMakeSelectedVacRowsEditableOnCheckerSideHandler() {
        if(selectedRowsInVacsToBeReviewedByCheckerDataTbl.size()> 0)
        {
            int i;
            boolean outerBreak = false;
            for (i= 0; i < selectedRowsInVacsToBeReviewedByCheckerDataTbl.size(); i++) {
                vac.setId(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getId());
                vac.setOrganizationalStructure(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getOrganizationalStructure());
                vac.setPosition(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getPosition());
                vac.setVacancyNumber(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getVacancyNumber());
                vac.setQualification(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getQualification());
                vac.setTrainingOrSkill(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getTrainingOrSkill());
                vac.setExperience(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getExperience());
                vac.setEligibility(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getEligibility());
                vac.setOpenFrom(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getOpenFrom());
                vac.setOpenTo(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getOpenTo());
                vac.setRequiredNo(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getRequiredNo());
                vac.setRemark(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getRemark());
                vac.setVacancyType(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getVacancyType());
                vac.setCreatedBy(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                vac.setCreationTimeStamp(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                vac.setEditedBy(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                vac.setEditingTimeStamp(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                vac.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                vac.setApprovedTimeStamp(new Date().toString());
                vac.setDeletedBy(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                vac.setDeletionTimeStamp(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                vac.setUnapprovedChange(selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange());
                switch (selectedRowsInVacsToBeReviewedByCheckerDataTbl.get(i).getStatus()) {
                    case "submitted":
                        vac.setStatus("entry");
                        break;
                    case "reversed submission":
                        vac.setStatus("reversed entry");
                        break;
                    default:
                        outerBreak = true;
                        break;
                }
                if(outerBreak)
                    break;
                else
                {
                    if(vacService.save(vac))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                        break;
                }
            }
            if(i==selectedRowsInVacsToBeReviewedByCheckerDataTbl.size())
            {
                appVacs = vacService.fetchAllApproved();
                toBeReviewdvacs = vacService.fetchAllSubmitted();
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
    
    public void btnForDeleteSelectedVacRowsOnCheckerSide() {
        
    }
}
//<p:ajax event="itemSelect" listener="#{vacancyBean.orgStrDrpDnOnMakerSideValueChangeListener}" update="frmForMaker:drpDnForPosOnMakerSide" ></p:ajax>