/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.ExternalVacancApplicants;
import com.dashen.hrms.Vacancy;
import com.dashen.hrms.service.ExternalVacancApplicantsService;
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
public class ExternalVacancApplicantsBean {
    //===========Users==================================//
    
    //GrantedAuthority maker = new SimpleGrantedAuthority(UserAuthority.MAKER);
    //GrantedAuthority checker = new SimpleGrantedAuthority(UserAuthority.CHECKER);
    
    //===========Auto Wired Objects=====================//
    
    @Autowired
    ExternalVacancApplicantsService extVacAppService;
    @Autowired
    VacancyService vacService;
    
    //===========Common Variables=======================//
    
    private boolean pnlForRegPageHeadingRender;
    private boolean pnlForAppPageHeadingRender;
    private List<ExternalVacancApplicants> appExtVacAppts;
    
    //===========Maker Variables========================//
    
    private boolean pnlForInputFieldsOnMakerSideRender;
    private ExternalVacancApplicants extVacApp;
    private List<SelectItem> vacSelectItem;
    private boolean pnlForUnappExtVacApptsDataTblOnMakerSideRender;
    private List<ExternalVacancApplicants> unappExtVacAppts;
    private ExternalVacancApplicants selectedRowInUnappExtVacApptsDataTblOnMakerSide;
    private boolean pnlForAppExtVacApptsDataTblOnMakerSideRender;
    private ExternalVacancApplicants selectedRowInappExtVacApptsDataTblOnMakerSide;
    private boolean pnlForBtnsOnMakerSideRender;
    
    //===========Checker Variables======================//
    
    private boolean pnlForExtVacApptsToBeReviewedByCheckerDataTblAndBtnsRender;
    private List<ExternalVacancApplicants> toBeReviewdExtVacAppts;
    private List<ExternalVacancApplicants> selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl;
    private boolean pnlForAppExtVacApptsDataTblOnCheckerSideRender;
    private List<ExternalVacancApplicants> selectedRowsInAppExtVacApptsDataTblOnCheckerSide;
    
    //===========Init Method============================//
    
    @PostConstruct
    public void init() {
        extVacApp = new ExternalVacancApplicants();
        if(CurrentUser.getCurrentUser().getUsername().equals("mulugetak")) {
            pnlForRegPageHeadingRender = true;
            pnlForInputFieldsOnMakerSideRender = true;
            pnlForUnappExtVacApptsDataTblOnMakerSideRender = true;
            pnlForAppExtVacApptsDataTblOnMakerSideRender = true;
            pnlForBtnsOnMakerSideRender = true;
            
            pnlForAppPageHeadingRender = false;
            pnlForExtVacApptsToBeReviewedByCheckerDataTblAndBtnsRender = false;
            pnlForAppExtVacApptsDataTblOnCheckerSideRender = false;
            
            List<Vacancy> vacList  = vacService.fetchAllApproved();
            vacSelectItem = new ArrayList<>();
            for (int i = 0; i < vacList.size(); i++)
            {
               vacSelectItem.add(new SelectItem(vacList.get(i).getId(), vacList.get(i).getVacancyNumber()));
            }
            appExtVacAppts = extVacAppService.fetchAllApproved();
            unappExtVacAppts = extVacAppService.fetchAllUnsubmittedOrEditable();
            for(int i = 0; i < unappExtVacAppts.size(); i++)
            {
                if(!"-".equals(unappExtVacAppts.get(i).getUnapprovedChange()))
                {
                    unappExtVacAppts.get(i).setUnapprovedVacancy(vacService.getByVacancyId(unappExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]));
                    unappExtVacAppts.get(i).setUnapprovedVacancyArrow(true);
                    unappExtVacAppts.get(i).setUnapprovedVacancyDisplay(true);
                    unappExtVacAppts.get(i).setUnapprovedFirstName(unappExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]);
                    unappExtVacAppts.get(i).setUnapprovedFirstNameArrow(true);
                    unappExtVacAppts.get(i).setUnapprovedFirstNameDisplay(true);
                    unappExtVacAppts.get(i).setUnapprovedMiddleName(unappExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]);
                    unappExtVacAppts.get(i).setUnapprovedMiddleNameArrow(true);
                    unappExtVacAppts.get(i).setUnapprovedMiddleNameDisplay(true);
                    unappExtVacAppts.get(i).setUnapprovedLastName(unappExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    unappExtVacAppts.get(i).setUnapprovedLastNameArrow(true);
                    unappExtVacAppts.get(i).setUnapprovedLastNameDisplay(true);
                    unappExtVacAppts.get(i).setUnapprovedGender(unappExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[4]);
                    unappExtVacAppts.get(i).setUnapprovedGenderArrow(true);
                    unappExtVacAppts.get(i).setUnapprovedGenderDisplay(true);
                    unappExtVacAppts.get(i).setUnapprovedApplicationDate(unappExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[5]);
                    unappExtVacAppts.get(i).setUnapprovedApplicationDateArrow(true);
                    unappExtVacAppts.get(i).setUnapprovedApplicationDateDisplay(true);
                    unappExtVacAppts.get(i).setUnapprovedApplciationStatus(unappExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[6]);
                    unappExtVacAppts.get(i).setUnapprovedApplicationStatusArrow(true);
                    unappExtVacAppts.get(i).setUnapprovedApplicationStatusDisplay(true);
                    unappExtVacAppts.get(i).setUnapprovedOfferStaus(unappExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[7]);
                    unappExtVacAppts.get(i).setUnapprovedOfferStatusArrow(true);
                    unappExtVacAppts.get(i).setUnapprovedOfferStatusDisplay(true);
                }
            }
        }
        if(CurrentUser.getCurrentUser().getUsername().equals("dawits")) {
            pnlForAppPageHeadingRender = true;
            pnlForExtVacApptsToBeReviewedByCheckerDataTblAndBtnsRender = true;
            pnlForAppExtVacApptsDataTblOnCheckerSideRender = true;
            
            pnlForRegPageHeadingRender = false;
            pnlForInputFieldsOnMakerSideRender = false;
            pnlForUnappExtVacApptsDataTblOnMakerSideRender = false;
            pnlForAppExtVacApptsDataTblOnMakerSideRender = false;
            pnlForBtnsOnMakerSideRender = false;
            
            appExtVacAppts = extVacAppService.fetchAllApproved();
            toBeReviewdExtVacAppts = extVacAppService.fetchAllSubmitted();
            for(int i = 0; i < toBeReviewdExtVacAppts.size(); i++)
            {
                if(!"-".equals(toBeReviewdExtVacAppts.get(i).getUnapprovedChange()))
                {
                    toBeReviewdExtVacAppts.get(i).setUnapprovedVacancy(vacService.getByVacancyId(toBeReviewdExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]));
                    toBeReviewdExtVacAppts.get(i).setUnapprovedVacancyArrow(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedVacancyDisplay(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedFirstName(toBeReviewdExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedFirstNameArrow(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedFirstNameDisplay(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedMiddleName(toBeReviewdExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedMiddleNameArrow(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedMiddleNameDisplay(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedLastName(toBeReviewdExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedLastNameArrow(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedLastNameDisplay(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedGender(toBeReviewdExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[4]);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedGenderArrow(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedGenderDisplay(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedApplicationDate(toBeReviewdExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[5]);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedApplicationDateArrow(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedApplicationDateDisplay(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedApplciationStatus(toBeReviewdExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[6]);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedApplicationStatusArrow(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedApplicationStatusDisplay(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedOfferStaus(toBeReviewdExtVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[7]);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedOfferStatusArrow(true);
                    toBeReviewdExtVacAppts.get(i).setUnapprovedOfferStatusDisplay(true);
                }
            }
        }
    }
    
    //===========Getter and Setter=====================//
    
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

    public List<ExternalVacancApplicants> getAppExtVacAppts() {
        return appExtVacAppts;
    }

    public void setAppExtVacAppts(List<ExternalVacancApplicants> appExtVacAppts) {
        this.appExtVacAppts = appExtVacAppts;
    }

    public boolean isPnlForInputFieldsOnMakerSideRender() {
        return pnlForInputFieldsOnMakerSideRender;
    }

    public void setPnlForInputFieldsOnMakerSideRender(boolean pnlForInputFieldsOnMakerSideRender) {
        this.pnlForInputFieldsOnMakerSideRender = pnlForInputFieldsOnMakerSideRender;
    }

    public ExternalVacancApplicants getExtVacApp() {
        return extVacApp;
    }

    public void setExtVacApp(ExternalVacancApplicants extVacApp) {
        this.extVacApp = extVacApp;
    }

    public List<SelectItem> getVacSelectItem() {
        return vacSelectItem;
    }

    public void setVacSelectItem(List<SelectItem> vacSelectItem) {
        this.vacSelectItem = vacSelectItem;
    }

    public boolean isPnlForUnappExtVacApptsDataTblOnMakerSideRender() {
        return pnlForUnappExtVacApptsDataTblOnMakerSideRender;
    }

    public void setPnlForUnappExtVacApptsDataTblOnMakerSideRender(boolean pnlForUnappExtVacApptsDataTblOnMakerSideRender) {
        this.pnlForUnappExtVacApptsDataTblOnMakerSideRender = pnlForUnappExtVacApptsDataTblOnMakerSideRender;
    }

    public List<ExternalVacancApplicants> getUnappExtVacAppts() {
        return unappExtVacAppts;
    }

    public void setUnappExtVacAppts(List<ExternalVacancApplicants> unappExtVacAppts) {
        this.unappExtVacAppts = unappExtVacAppts;
    }

    public ExternalVacancApplicants getSelectedRowInUnappExtVacApptsDataTblOnMakerSide() {
        return selectedRowInUnappExtVacApptsDataTblOnMakerSide;
    }

    public void setSelectedRowInUnappExtVacApptsDataTblOnMakerSide(ExternalVacancApplicants selectedRowInUnappExtVacApptsDataTblOnMakerSide) {
        this.selectedRowInUnappExtVacApptsDataTblOnMakerSide = selectedRowInUnappExtVacApptsDataTblOnMakerSide;
    }

    public boolean isPnlForAppExtVacApptsDataTblOnMakerSideRender() {
        return pnlForAppExtVacApptsDataTblOnMakerSideRender;
    }

    public void setPnlForAppExtVacApptsDataTblOnMakerSideRender(boolean pnlForAppExtVacApptsDataTblOnMakerSideRender) {
        this.pnlForAppExtVacApptsDataTblOnMakerSideRender = pnlForAppExtVacApptsDataTblOnMakerSideRender;
    }

    public ExternalVacancApplicants getSelectedRowInappExtVacApptsDataTblOnMakerSide() {
        return selectedRowInappExtVacApptsDataTblOnMakerSide;
    }

    public void setSelectedRowInappExtVacApptsDataTblOnMakerSide(ExternalVacancApplicants selectedRowInappExtVacApptsDataTblOnMakerSide) {
        this.selectedRowInappExtVacApptsDataTblOnMakerSide = selectedRowInappExtVacApptsDataTblOnMakerSide;
    }

    public boolean isPnlForBtnsOnMakerSideRender() {
        return pnlForBtnsOnMakerSideRender;
    }

    public void setPnlForBtnsOnMakerSideRender(boolean pnlForBtnsOnMakerSideRender) {
        this.pnlForBtnsOnMakerSideRender = pnlForBtnsOnMakerSideRender;
    }

    public boolean isPnlForExtVacApptsToBeReviewedByCheckerDataTblAndBtnsRender() {
        return pnlForExtVacApptsToBeReviewedByCheckerDataTblAndBtnsRender;
    }

    public void setPnlForExtVacApptsToBeReviewedByCheckerDataTblAndBtnsRender(boolean pnlForExtVacApptsToBeReviewedByCheckerDataTblAndBtnsRender) {
        this.pnlForExtVacApptsToBeReviewedByCheckerDataTblAndBtnsRender = pnlForExtVacApptsToBeReviewedByCheckerDataTblAndBtnsRender;
    }

    public List<ExternalVacancApplicants> getToBeReviewdExtVacAppts() {
        return toBeReviewdExtVacAppts;
    }

    public void setToBeReviewdExtVacAppts(List<ExternalVacancApplicants> toBeReviewdExtVacAppts) {
        this.toBeReviewdExtVacAppts = toBeReviewdExtVacAppts;
    }

    public List<ExternalVacancApplicants> getSelectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl() {
        return selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl;
    }

    public void setSelectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl(List<ExternalVacancApplicants> selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl) {
        this.selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl = selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl;
    }

    public boolean isPnlForAppExtVacApptsDataTblOnCheckerSideRender() {
        return pnlForAppExtVacApptsDataTblOnCheckerSideRender;
    }

    public void setPnlForAppExtVacApptsDataTblOnCheckerSideRender(boolean pnlForAppExtVacApptsDataTblOnCheckerSideRender) {
        this.pnlForAppExtVacApptsDataTblOnCheckerSideRender = pnlForAppExtVacApptsDataTblOnCheckerSideRender;
    }

    public List<ExternalVacancApplicants> getSelectedRowsInAppExtVacApptsDataTblOnCheckerSide() {
        return selectedRowsInAppExtVacApptsDataTblOnCheckerSide;
    }

    public void setSelectedRowsInAppExtVacApptsDataTblOnCheckerSide(List<ExternalVacancApplicants> selectedRowsInAppExtVacApptsDataTblOnCheckerSide) {
        this.selectedRowsInAppExtVacApptsDataTblOnCheckerSide = selectedRowsInAppExtVacApptsDataTblOnCheckerSide;
    }

    //===========Maker Methods==========================//
    public void unappExtVacApptsDataTblOnMakerSideRowSelectedListener() {
        if("reversed entry".equals(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getStatus()) && !"-".equals(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedChange()))
        {
            extVacApp.setId(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getId());
            extVacApp.setVacancy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedVacancy());
            extVacApp.setFirstName(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedFirstName());
            extVacApp.setMiddleName(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedMiddleName());
            extVacApp.setLastName(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedLastName());
            extVacApp.setGender(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedGender());
            try{extVacApp.setApplicationDateInDate(new SimpleDateFormat("dd-MMM-yyyy").parse(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedApplicationDate()));}catch (ParseException ex){FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getLocalizedMessage()));}
            extVacApp.setApplicationStatus(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedApplciationStatus());
            extVacApp.setOfferStatus(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedOfferStaus());
            extVacApp.setCreatedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getCreatedBy());
            extVacApp.setCreationTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getCreationTimeStamp());
            extVacApp.setEditedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getEditedBy());
            extVacApp.setEditingTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getEditingTimeStamp());
            extVacApp.setApprovedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApprovedBy());
            extVacApp.setApprovedTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApprovedTimeStamp());
            extVacApp.setDeletedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getDeletedBy());
            extVacApp.setDeletionTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getDeletionTimeStamp());
            extVacApp.setUnapprovedChange(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedChange());
            extVacApp.setStatus(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getStatus());
        }
        else if(("reversed entry".equals(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getStatus()) && "-".equals(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedChange())) || ("entry".equals(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getStatus())))
        {
            extVacApp.setId(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getId());
            extVacApp.setVacancy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getVacancy());
            extVacApp.setFirstName(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getFirstName());
            extVacApp.setMiddleName(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getMiddleName());
            extVacApp.setLastName(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getLastName());
            extVacApp.setGender(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getGender());
            try{extVacApp.setApplicationDateInDate(new SimpleDateFormat("dd-MMM-yyyy").parse(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApplicationDate()));}catch (ParseException ex){FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getLocalizedMessage()));}
            extVacApp.setApplicationStatus(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApplicationStatus());
            extVacApp.setOfferStatus(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getOfferStatus());
            extVacApp.setCreatedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getCreatedBy());
            extVacApp.setCreationTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getCreationTimeStamp());
            extVacApp.setEditedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getEditedBy());
            extVacApp.setEditingTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getEditingTimeStamp());
            extVacApp.setApprovedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApprovedBy());
            extVacApp.setApprovedTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApprovedTimeStamp());
            extVacApp.setDeletedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getDeletedBy());
            extVacApp.setDeletionTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getDeletionTimeStamp());
            extVacApp.setUnapprovedChange(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedChange());
            extVacApp.setStatus(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getStatus());
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Unexpected data status found. Please, Consult your system administrator."));
    }
    
    public void appExtVacApptsDataTblOnMakerSideRowSelectedListener() {
        
    }
    
    public void btnForResetExtVacApptOnMakerSideHandler() {
        selectedRowInUnappExtVacApptsDataTblOnMakerSide = null;
        selectedRowInappExtVacApptsDataTblOnMakerSide = null;
        init();
    }
    
    public void btnForSaveExtVacApptOnMakerSideHandler() {
        if(selectedRowInUnappExtVacApptsDataTblOnMakerSide == null)
        {
            extVacApp.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
            extVacApp.setCreationTimeStamp(new Date().toString());
            extVacApp.setEditedBy("-");
            extVacApp.setEditingTimeStamp("-");
            extVacApp.setApprovedBy("-");
            extVacApp.setApprovedTimeStamp("-");
            extVacApp.setDeletedBy("-");
            extVacApp.setDeletionTimeStamp("-");
            extVacApp.setUnapprovedChange("-");
            extVacApp.setStatus("entry");
            if(extVacAppService.save(extVacApp))
            {
                btnForResetExtVacApptOnMakerSideHandler();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully saved the data.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to save your data. Please, consult your system adminstrator."));
        }
        else
        {
            switch (selectedRowInUnappExtVacApptsDataTblOnMakerSide.getStatus()) {
                case "entry":
                    extVacApp.setId(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getId());
                    extVacApp.setCreatedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getCreatedBy());
                    extVacApp.setCreationTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getCreationTimeStamp());
                    extVacApp.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    extVacApp.setEditingTimeStamp(new Date().toString());
                    extVacApp.setApprovedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApprovedBy());
                    extVacApp.setApprovedTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApprovedTimeStamp());
                    extVacApp.setDeletedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getDeletedBy());
                    extVacApp.setDeletionTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getDeletionTimeStamp());
                    extVacApp.setUnapprovedChange(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedChange());
                    extVacApp.setStatus(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getStatus());
                    if(extVacAppService.save(extVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetExtVacApptOnMakerSideHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                case "reversed entry":
                    extVacApp.setUnapprovedChange(extVacApp.getVacancy().getId()+"|"+extVacApp.getFirstName()+"|"+extVacApp.getMiddleName()+"|"+extVacApp.getLastName()+"|"+extVacApp.getGender()+"|"+extVacApp.getApplicationDate()+"|"+extVacApp.getApplicationStatus()+"|"+extVacApp.getOfferStatus());
                    extVacApp.setId(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getId());
                    extVacApp.setVacancy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getVacancy());
                    extVacApp.setFirstName(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getFirstName());
                    extVacApp.setMiddleName(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getMiddleName());
                    extVacApp.setLastName(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getLastName());
                    extVacApp.setGender(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getGender());
                    extVacApp.setApplicationDate(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApplicationDate());
                    extVacApp.setApplicationStatus(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApplicationStatus());
                    extVacApp.setOfferStatus(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getOfferStatus());
                    extVacApp.setCreatedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getCreatedBy());
                    extVacApp.setCreationTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getCreationTimeStamp());
                    extVacApp.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    extVacApp.setEditingTimeStamp(new Date().toString());
                    extVacApp.setApprovedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApprovedBy());
                    extVacApp.setApprovedTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApprovedTimeStamp());
                    extVacApp.setDeletedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getDeletedBy());
                    extVacApp.setDeletionTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getDeletionTimeStamp());
                    extVacApp.setStatus(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getStatus());
                    if(extVacAppService.save(extVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetExtVacApptOnMakerSideHandler();
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
    
    public void btnForSubmitExtVacApptOnMakerSideHandler() {
        if(selectedRowInUnappExtVacApptsDataTblOnMakerSide != null)
        {
            switch (selectedRowInUnappExtVacApptsDataTblOnMakerSide.getStatus()) {
                case "entry":
                    extVacApp.setId(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getId());
                    extVacApp.setCreatedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getCreatedBy());
                    extVacApp.setCreationTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getCreationTimeStamp());
                    extVacApp.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    extVacApp.setEditingTimeStamp(new Date().toString());
                    extVacApp.setApprovedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApprovedBy());
                    extVacApp.setApprovedTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApprovedTimeStamp());
                    extVacApp.setDeletedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getDeletedBy());
                    extVacApp.setDeletionTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getDeletionTimeStamp());
                    extVacApp.setUnapprovedChange(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getUnapprovedChange());
                    extVacApp.setStatus("submitted");
                    if(extVacAppService.save(extVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetExtVacApptOnMakerSideHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                case "reversed entry":
                    extVacApp.setUnapprovedChange(extVacApp.getVacancy().getId()+"|"+extVacApp.getFirstName()+"|"+extVacApp.getMiddleName()+"|"+extVacApp.getLastName()+"|"+extVacApp.getGender()+"|"+extVacApp.getApplicationDate()+"|"+extVacApp.getApplicationStatus()+"|"+extVacApp.getOfferStatus());
                    extVacApp.setId(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getId());
                    extVacApp.setVacancy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getVacancy());
                    extVacApp.setFirstName(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getFirstName());
                    extVacApp.setMiddleName(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getMiddleName());
                    extVacApp.setLastName(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getLastName());
                    extVacApp.setGender(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getGender());
                    extVacApp.setApplicationDate(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApplicationDate());
                    extVacApp.setApplicationStatus(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApplicationStatus());
                    extVacApp.setOfferStatus(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getOfferStatus());
                    extVacApp.setCreatedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getCreatedBy());
                    extVacApp.setCreationTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getCreationTimeStamp());
                    extVacApp.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    extVacApp.setEditingTimeStamp(new Date().toString());
                    extVacApp.setApprovedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApprovedBy());
                    extVacApp.setApprovedTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getApprovedTimeStamp());
                    extVacApp.setDeletedBy(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getDeletedBy());
                    extVacApp.setDeletionTimeStamp(selectedRowInUnappExtVacApptsDataTblOnMakerSide.getDeletionTimeStamp());
                    extVacApp.setStatus("reversed submission");
                    if(extVacAppService.save(extVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetExtVacApptOnMakerSideHandler();
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
    
    public void btnForDeleteExtVacApptOnMakerSideHandler() {
        
    }
    
    //===========Checker Methods========================//
    
    public void btnForApproveSelectedExtVacApptRowsOnCheckerSideHandler() {
        if(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.size() > 0)
        {
            int i;
            for(i= 0; i < selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.size(); i++)
            {
                if("submitted".equals(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getStatus()) || ("reversed submission".equals(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getStatus()) && "-".equals(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange())))
                {
                    extVacApp.setId(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getId());
                    extVacApp.setVacancy(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getVacancy());
                    extVacApp.setFirstName(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getFirstName());
                    extVacApp.setMiddleName(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getMiddleName());
                    extVacApp.setLastName(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getLastName());
                    extVacApp.setGender(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getGender());
                    extVacApp.setApplicationDate(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getApplicationDate());
                    extVacApp.setApplicationStatus(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getApplicationStatus());
                    extVacApp.setOfferStatus(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getOfferStatus());
                    extVacApp.setCreatedBy(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                    extVacApp.setCreationTimeStamp(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                    extVacApp.setEditedBy(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                    extVacApp.setEditingTimeStamp(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                    extVacApp.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                    extVacApp.setApprovedTimeStamp(new Date().toString());
                    extVacApp.setDeletedBy(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                    extVacApp.setDeletionTimeStamp(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                    extVacApp.setUnapprovedChange("-");
                    extVacApp.setStatus("approved");
                    if(extVacAppService.save(extVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                        break;
                }
                else if("reversed submission".equals(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getStatus()) && !"-".equals(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange()))
                {
                    extVacApp.setId(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getId());
                    Vacancy vac = new Vacancy();vac.setId(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]);
                    extVacApp.setVacancy(vac);
                    extVacApp.setFirstName(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]);
                    extVacApp.setMiddleName(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]);
                    extVacApp.setLastName(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    extVacApp.setGender(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[4]);
                    extVacApp.setApplicationDate(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[5]);
                    extVacApp.setApplicationStatus(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[6]);
                    extVacApp.setOfferStatus(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[7]);
                    extVacApp.setCreatedBy(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                    extVacApp.setCreationTimeStamp(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                    extVacApp.setEditedBy(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                    extVacApp.setEditingTimeStamp(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                    extVacApp.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                    extVacApp.setApprovedTimeStamp(new Date().toString());
                    extVacApp.setDeletedBy(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                    extVacApp.setDeletionTimeStamp(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                    extVacApp.setUnapprovedChange("-");
                    extVacApp.setStatus("approved");
                    if(extVacAppService.save(extVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                        break;
                }
                else
                    break;
            }
            if(i==selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.size())
            {
                appExtVacAppts = extVacAppService.fetchAllApproved();
                toBeReviewdExtVacAppts = extVacAppService.fetchAllSubmitted();
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
    
    public void btnForMakeSelectedExtVacApptRowsEditableOnCheckerSideHandler() {
        if(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.size()> 0)
        {
            int i;
            boolean outerBreak = false;
            for (i= 0; i < selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.size(); i++) {
                extVacApp.setId(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getId());
                extVacApp.setVacancy(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getVacancy());
                extVacApp.setFirstName(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getFirstName());
                extVacApp.setMiddleName(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getMiddleName());
                extVacApp.setLastName(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getLastName());
                extVacApp.setGender(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getGender());
                extVacApp.setApplicationDate(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getApplicationDate());
                extVacApp.setApplicationStatus(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getApplicationStatus());
                extVacApp.setOfferStatus(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getOfferStatus());
                extVacApp.setCreatedBy(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                extVacApp.setCreationTimeStamp(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                extVacApp.setEditedBy(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                extVacApp.setEditingTimeStamp(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                extVacApp.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                extVacApp.setApprovedTimeStamp(new Date().toString());
                extVacApp.setDeletedBy(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                extVacApp.setDeletionTimeStamp(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                extVacApp.setUnapprovedChange(selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange());
                switch (selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.get(i).getStatus()) {
                    case "submitted":
                        extVacApp.setStatus("entry");
                        break;
                    case "reversed submission":
                        extVacApp.setStatus("reversed entry");
                        break;
                    default:
                        outerBreak = true;
                        break;
                }
                if(outerBreak)
                    break;
                else
                {
                    if(extVacAppService.save(extVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                        break;
                }
            }
            if(i==selectedRowsInExtVacApptsToBeReviewedByCheckerDataTbl.size())
            {
                appExtVacAppts = extVacAppService.fetchAllApproved();
                toBeReviewdExtVacAppts = extVacAppService.fetchAllSubmitted();
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
    
    public void btnForDeleteSelectedExtVacApptRowsOnCheckerSide() {
        
    }
}
