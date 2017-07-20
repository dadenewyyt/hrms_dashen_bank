/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.Employee;
import com.dashen.hrms.InternalVacancApplicants;
import com.dashen.hrms.Vacancy;
import com.dashen.hrms.service.EmployeeService;
import com.dashen.hrms.service.InternalVacancApplicantsService;
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
public class InternalVacancApplicantsBean {
    //===========Users==================================//
    
    //GrantedAuthority maker = new SimpleGrantedAuthority(UserAuthority.MAKER);
    //GrantedAuthority checker = new SimpleGrantedAuthority(UserAuthority.CHECKER);
    
    //===========Auto Wired Objects=====================//
    
    @Autowired
    InternalVacancApplicantsService intVacAppService;
    @Autowired
    VacancyService vacService;
    @Autowired
    EmployeeService empService;
    
    //===========Common Variables=======================//
    
    private boolean pnlForRegPageHeadingRender;
    private boolean pnlForAppPageHeadingRender;
    private List<InternalVacancApplicants> appIntVacAppts;
    
    //===========Maker Variables========================//
    
    private boolean pnlForInputFieldsOnMakerSideRender;
    private InternalVacancApplicants intVacApp;
    private List<SelectItem> vacSelectItem;
    private List<SelectItem> empSelectItem;
    private boolean pnlForUnappIntVacApptsDataTblOnMakerSideRender;
    private List<InternalVacancApplicants> unappIntVacAppts;
    private InternalVacancApplicants selectedRowInUnappIntVacApptsDataTblOnMakerSide;
    private boolean pnlForAppIntVacApptsDataTblOnMakerSideRender;
    private InternalVacancApplicants selectedRowInappIntVacApptsDataTblOnMakerSide;
    private boolean pnlForBtnsOnMakerSideRender;
    
    //===========Checker Variables======================//
    
    private boolean pnlForIntVacApptsToBeReviewedByCheckerDataTblAndBtnsRender;
    private List<InternalVacancApplicants> toBeReviewdIntVacAppts;
    private List<InternalVacancApplicants> selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl;
    private boolean pnlForAppIntVacApptsDataTblOnCheckerSideRender;
    private List<InternalVacancApplicants> selectedRowsInAppIntVacApptsDataTblOnCheckerSide;
    
    //===========Init Method============================//
    
    @PostConstruct
    public void init() {
        intVacApp = new InternalVacancApplicants();
        if(CurrentUser.getCurrentUser().getUsername().equals("mulugetak")) {
            pnlForRegPageHeadingRender = true;
            pnlForInputFieldsOnMakerSideRender = true;
            pnlForUnappIntVacApptsDataTblOnMakerSideRender = true;
            pnlForAppIntVacApptsDataTblOnMakerSideRender = true;
            pnlForBtnsOnMakerSideRender = true;
            
            pnlForAppPageHeadingRender = false;
            pnlForIntVacApptsToBeReviewedByCheckerDataTblAndBtnsRender = false;
            pnlForAppIntVacApptsDataTblOnCheckerSideRender = false;
            
            List<Vacancy> vacList  = vacService.fetchAllApproved();
            vacSelectItem = new ArrayList<>();
            for (int i = 0; i < vacList.size(); i++)
            {
               vacSelectItem.add(new SelectItem(vacList.get(i).getId(), vacList.get(i).getVacancyNumber()));
            }
            List<Employee> empList = empService.listAll();
            empSelectItem = new ArrayList<>();
            for (int i = 0; i < empList.size(); i++)
            {
               empSelectItem.add(new SelectItem(empList.get(i).getEmployeeSerialID(), empList.get(i).getFullName()));
            }
            appIntVacAppts = intVacAppService.fetchAllApproved();
            unappIntVacAppts = intVacAppService.fetchAllUnsubmittedOrEditable();
            for(int i = 0; i < unappIntVacAppts.size(); i++)
            {
                if(!"-".equals(unappIntVacAppts.get(i).getUnapprovedChange()))
                {
                    unappIntVacAppts.get(i).setUnapprovedVacancy(vacService.getByVacancyId(unappIntVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]));
                    unappIntVacAppts.get(i).setUnapprovedVacancyArrow(true);
                    unappIntVacAppts.get(i).setUnapprovedVacancyDisplay(true);      
                    unappIntVacAppts.get(i).setUnapprovedEmployee(empService.getByEmployeeSerialID(unappIntVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]));
                    unappIntVacAppts.get(i).setUnapprovedEmployeeArrow(true);
                    unappIntVacAppts.get(i).setUnapprovedEmployeeDisplay(true);
                    unappIntVacAppts.get(i).setUnapprovedApplicationDate(unappIntVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]);
                    unappIntVacAppts.get(i).setUnapprovedApplicationDateArrow(true);
                    unappIntVacAppts.get(i).setUnapprovedApplicationDateDisplay(true);
                    unappIntVacAppts.get(i).setUnapprovedApplicationStatus(unappIntVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    unappIntVacAppts.get(i).setUnapprovedApplicationStatusArrow(true);
                    unappIntVacAppts.get(i).setUnapprovedApplicationStatusDisplay(true);
                    unappIntVacAppts.get(i).setUnapprovedOfferStatus(unappIntVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[4]);
                    unappIntVacAppts.get(i).setUnapprovedOfferStatusArrow(true);
                    unappIntVacAppts.get(i).setUnapprovedOfferStatusDisplay(true);
                }
            }
        }
        if(CurrentUser.getCurrentUser().getUsername().equals("dawits")) {
            pnlForAppPageHeadingRender = true;
            pnlForIntVacApptsToBeReviewedByCheckerDataTblAndBtnsRender = true;
            pnlForAppIntVacApptsDataTblOnCheckerSideRender = true;     
            
            pnlForRegPageHeadingRender = false;
            pnlForInputFieldsOnMakerSideRender = false;
            pnlForUnappIntVacApptsDataTblOnMakerSideRender = false;
            pnlForAppIntVacApptsDataTblOnMakerSideRender = false;
            pnlForBtnsOnMakerSideRender = false;
            
            appIntVacAppts = intVacAppService.fetchAllApproved();
            toBeReviewdIntVacAppts = intVacAppService.fetchAllSubmitted();
            for(int i = 0; i < toBeReviewdIntVacAppts.size(); i++)
            {
                if(!"-".equals(toBeReviewdIntVacAppts.get(i).getUnapprovedChange()))
                {
                    toBeReviewdIntVacAppts.get(i).setUnapprovedVacancy(vacService.getByVacancyId(toBeReviewdIntVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]));
                    toBeReviewdIntVacAppts.get(i).setUnapprovedVacancyArrow(true);
                    toBeReviewdIntVacAppts.get(i).setUnapprovedVacancyDisplay(true);      
                    toBeReviewdIntVacAppts.get(i).setUnapprovedEmployee(empService.getByEmployeeSerialID(toBeReviewdIntVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]));
                    toBeReviewdIntVacAppts.get(i).setUnapprovedEmployeeArrow(true);
                    toBeReviewdIntVacAppts.get(i).setUnapprovedEmployeeDisplay(true);
                    toBeReviewdIntVacAppts.get(i).setUnapprovedApplicationDate(toBeReviewdIntVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]);
                    toBeReviewdIntVacAppts.get(i).setUnapprovedApplicationDateArrow(true);
                    toBeReviewdIntVacAppts.get(i).setUnapprovedApplicationDateDisplay(true);
                    toBeReviewdIntVacAppts.get(i).setUnapprovedApplicationStatus(toBeReviewdIntVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    toBeReviewdIntVacAppts.get(i).setUnapprovedApplicationStatusArrow(true);
                    toBeReviewdIntVacAppts.get(i).setUnapprovedApplicationStatusDisplay(true);
                    toBeReviewdIntVacAppts.get(i).setUnapprovedOfferStatus(toBeReviewdIntVacAppts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[4]);
                    toBeReviewdIntVacAppts.get(i).setUnapprovedOfferStatusArrow(true);
                    toBeReviewdIntVacAppts.get(i).setUnapprovedOfferStatusDisplay(true);
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

    public List<InternalVacancApplicants> getAppIntVacAppts() {
        return appIntVacAppts;
    }

    public void setAppIntVacAppts(List<InternalVacancApplicants> appIntVacAppts) {
        this.appIntVacAppts = appIntVacAppts;
    }

    public boolean isPnlForInputFieldsOnMakerSideRender() {
        return pnlForInputFieldsOnMakerSideRender;
    }

    public void setPnlForInputFieldsOnMakerSideRender(boolean pnlForInputFieldsOnMakerSideRender) {
        this.pnlForInputFieldsOnMakerSideRender = pnlForInputFieldsOnMakerSideRender;
    }

    public InternalVacancApplicants getIntVacApp() {
        return intVacApp;
    }

    public void setIntVacApp(InternalVacancApplicants intVacApp) {
        this.intVacApp = intVacApp;
    }

    public List<SelectItem> getVacSelectItem() {
        return vacSelectItem;
    }

    public void setVacSelectItem(List<SelectItem> vacSelectItem) {
        this.vacSelectItem = vacSelectItem;
    }

    public List<SelectItem> getEmpSelectItem() {
        return empSelectItem;
    }

    public void setEmpSelectItem(List<SelectItem> empSelectItem) {
        this.empSelectItem = empSelectItem;
    }

    public boolean isPnlForUnappIntVacApptsDataTblOnMakerSideRender() {
        return pnlForUnappIntVacApptsDataTblOnMakerSideRender;
    }

    public void setPnlForUnappIntVacApptsDataTblOnMakerSideRender(boolean pnlForUnappIntVacApptsDataTblOnMakerSideRender) {
        this.pnlForUnappIntVacApptsDataTblOnMakerSideRender = pnlForUnappIntVacApptsDataTblOnMakerSideRender;
    }

    public List<InternalVacancApplicants> getUnappIntVacAppts() {
        return unappIntVacAppts;
    }

    public void setUnappIntVacAppts(List<InternalVacancApplicants> unappIntVacAppts) {
        this.unappIntVacAppts = unappIntVacAppts;
    }

    public InternalVacancApplicants getSelectedRowInUnappIntVacApptsDataTblOnMakerSide() {
        return selectedRowInUnappIntVacApptsDataTblOnMakerSide;
    }

    public void setSelectedRowInUnappIntVacApptsDataTblOnMakerSide(InternalVacancApplicants selectedRowInUnappIntVacApptsDataTblOnMakerSide) {
        this.selectedRowInUnappIntVacApptsDataTblOnMakerSide = selectedRowInUnappIntVacApptsDataTblOnMakerSide;
    }

    public boolean isPnlForAppIntVacApptsDataTblOnMakerSideRender() {
        return pnlForAppIntVacApptsDataTblOnMakerSideRender;
    }

    public void setPnlForAppIntVacApptsDataTblOnMakerSideRender(boolean pnlForAppIntVacApptsDataTblOnMakerSideRender) {
        this.pnlForAppIntVacApptsDataTblOnMakerSideRender = pnlForAppIntVacApptsDataTblOnMakerSideRender;
    }

    public InternalVacancApplicants getSelectedRowInappIntVacApptsDataTblOnMakerSide() {
        return selectedRowInappIntVacApptsDataTblOnMakerSide;
    }

    public void setSelectedRowInappIntVacApptsDataTblOnMakerSide(InternalVacancApplicants selectedRowInappIntVacApptsDataTblOnMakerSide) {
        this.selectedRowInappIntVacApptsDataTblOnMakerSide = selectedRowInappIntVacApptsDataTblOnMakerSide;
    }

    public boolean isPnlForBtnsOnMakerSideRender() {
        return pnlForBtnsOnMakerSideRender;
    }

    public void setPnlForBtnsOnMakerSideRender(boolean pnlForBtnsOnMakerSideRender) {
        this.pnlForBtnsOnMakerSideRender = pnlForBtnsOnMakerSideRender;
    }

    public boolean isPnlForIntVacApptsToBeReviewedByCheckerDataTblAndBtnsRender() {
        return pnlForIntVacApptsToBeReviewedByCheckerDataTblAndBtnsRender;
    }

    public void setPnlForIntVacApptsToBeReviewedByCheckerDataTblAndBtnsRender(boolean pnlForIntVacApptsToBeReviewedByCheckerDataTblAndBtnsRender) {
        this.pnlForIntVacApptsToBeReviewedByCheckerDataTblAndBtnsRender = pnlForIntVacApptsToBeReviewedByCheckerDataTblAndBtnsRender;
    }

    public List<InternalVacancApplicants> getToBeReviewdIntVacAppts() {
        return toBeReviewdIntVacAppts;
    }

    public void setToBeReviewdIntVacAppts(List<InternalVacancApplicants> toBeReviewdIntVacAppts) {
        this.toBeReviewdIntVacAppts = toBeReviewdIntVacAppts;
    }

    public List<InternalVacancApplicants> getSelectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl() {
        return selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl;
    }

    public void setSelectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl(List<InternalVacancApplicants> selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl) {
        this.selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl = selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl;
    }

    public boolean isPnlForAppIntVacApptsDataTblOnCheckerSideRender() {
        return pnlForAppIntVacApptsDataTblOnCheckerSideRender;
    }

    public void setPnlForAppIntVacApptsDataTblOnCheckerSideRender(boolean pnlForAppIntVacApptsDataTblOnCheckerSideRender) {
        this.pnlForAppIntVacApptsDataTblOnCheckerSideRender = pnlForAppIntVacApptsDataTblOnCheckerSideRender;
    }

    public List<InternalVacancApplicants> getSelectedRowsInAppIntVacApptsDataTblOnCheckerSide() {
        return selectedRowsInAppIntVacApptsDataTblOnCheckerSide;
    }

    //===========Getter and Setter=====================//
    public void setSelectedRowsInAppIntVacApptsDataTblOnCheckerSide(List<InternalVacancApplicants> selectedRowsInAppIntVacApptsDataTblOnCheckerSide) {
        this.selectedRowsInAppIntVacApptsDataTblOnCheckerSide = selectedRowsInAppIntVacApptsDataTblOnCheckerSide;
    }

    //===========Maker Methods==========================//

    public void unappIntVacApptsDataTblOnMakerSideRowSelectedListener() {
        if("reversed entry".equals(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getStatus()) && !"-".equals(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getUnapprovedChange()))
        {
            intVacApp.setId(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getId());
            intVacApp.setVacancy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getUnapprovedVacancy());
            intVacApp.setEmployee(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getUnapprovedEmployee());
            try{intVacApp.setApplicationDateInDate(new SimpleDateFormat("dd-MMM-yyyy").parse(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getUnapprovedApplicationDate()));}catch (ParseException ex){FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getLocalizedMessage()));}
            intVacApp.setApplicationStatus(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getUnapprovedApplicationStatus());
            intVacApp.setOfferStatus(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getUnapprovedOfferStatus());
            intVacApp.setCreatedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getCreatedBy());
            intVacApp.setCreationTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getCreationTimeStamp());
            intVacApp.setEditedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getEditedBy());
            intVacApp.setEditingTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getEditingTimeStamp());
            intVacApp.setApprovedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApprovedBy());
            intVacApp.setApprovedTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApprovedTimeStamp());
            intVacApp.setDeletedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getDeletedBy());
            intVacApp.setDeletionTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getDeletionTimeStamp());
            intVacApp.setUnapprovedChange(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getUnapprovedChange());
            intVacApp.setStatus(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getStatus());
        }
        else if(("reversed entry".equals(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getStatus()) && "-".equals(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getUnapprovedChange())) || ("entry".equals(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getStatus())))
        {
            intVacApp.setId(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getId());
            intVacApp.setVacancy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getVacancy());
            intVacApp.setEmployee(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getEmployee());
            try{intVacApp.setApplicationDateInDate(new SimpleDateFormat("dd-MMM-yyyy").parse(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApplicationDate()));}catch (ParseException ex){FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getLocalizedMessage()));}
            intVacApp.setApplicationStatus(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApplicationStatus());
            intVacApp.setOfferStatus(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getOfferStatus());
            intVacApp.setCreatedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getCreatedBy());
            intVacApp.setCreationTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getCreationTimeStamp());
            intVacApp.setEditedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getEditedBy());
            intVacApp.setEditingTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getEditingTimeStamp());
            intVacApp.setApprovedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApprovedBy());
            intVacApp.setApprovedTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApprovedTimeStamp());
            intVacApp.setDeletedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getDeletedBy());
            intVacApp.setDeletionTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getDeletionTimeStamp());
            intVacApp.setUnapprovedChange(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getUnapprovedChange());
            intVacApp.setStatus(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getStatus());
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Unexpected data status found. Please, Consult your system administrator."));
    }
    
    public void appIntVacApptsDataTblOnMakerSideRowSelectedListener() {
        
    }
    
    public void btnForResetIntVacApptOnMakerSideHandler() {
        selectedRowInUnappIntVacApptsDataTblOnMakerSide = null;
        selectedRowInappIntVacApptsDataTblOnMakerSide = null;
        init();
    }
    
    public void btnForSaveIntVacApptOnMakerSideHandler() {
        if(selectedRowInUnappIntVacApptsDataTblOnMakerSide == null)
        {
            intVacApp.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
            intVacApp.setCreationTimeStamp(new Date().toString());
            intVacApp.setEditedBy("-");
            intVacApp.setEditingTimeStamp("-");
            intVacApp.setApprovedBy("-");
            intVacApp.setApprovedTimeStamp("-");
            intVacApp.setDeletedBy("-");
            intVacApp.setDeletionTimeStamp("-");
            intVacApp.setUnapprovedChange("-");
            intVacApp.setStatus("entry");
            if(intVacAppService.save(intVacApp))
            {
                btnForResetIntVacApptOnMakerSideHandler();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully saved the data.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to save your data. Please, consult your system adminstrator."));
        }
        else
        {
            switch (selectedRowInUnappIntVacApptsDataTblOnMakerSide.getStatus()) {
                case "entry":
                    intVacApp.setId(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getId());
                    intVacApp.setCreatedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getCreatedBy());
                    intVacApp.setCreationTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getCreationTimeStamp());
                    intVacApp.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    intVacApp.setEditingTimeStamp(new Date().toString());
                    intVacApp.setApprovedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApprovedBy());
                    intVacApp.setApprovedTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApprovedTimeStamp());
                    intVacApp.setDeletedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getDeletedBy());
                    intVacApp.setDeletionTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getDeletionTimeStamp());
                    intVacApp.setUnapprovedChange(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getUnapprovedChange());
                    intVacApp.setStatus(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getStatus());
                    if(intVacAppService.save(intVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetIntVacApptOnMakerSideHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                case "reversed entry":
                    intVacApp.setUnapprovedChange(intVacApp.getVacancy().getId()+"|"+intVacApp.getEmployee().getEmployeeSerialID()+"|"+intVacApp.getApplicationDate()+"|"+intVacApp.getApplicationStatus()+"|"+intVacApp.getOfferStatus());
                    intVacApp.setId(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getId());
                    intVacApp.setVacancy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getVacancy());
                    intVacApp.setEmployee(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getEmployee());
                    intVacApp.setApplicationDate(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApplicationDate());
                    intVacApp.setApplicationStatus(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApplicationStatus());
                    intVacApp.setOfferStatus(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getOfferStatus());
                    intVacApp.setCreatedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getCreatedBy());
                    intVacApp.setCreationTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getCreationTimeStamp());
                    intVacApp.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    intVacApp.setEditingTimeStamp(new Date().toString());
                    intVacApp.setApprovedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApprovedBy());
                    intVacApp.setApprovedTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApprovedTimeStamp());
                    intVacApp.setDeletedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getDeletedBy());
                    intVacApp.setDeletionTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getDeletionTimeStamp());
                    intVacApp.setStatus(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getStatus());
                    if(intVacAppService.save(intVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetIntVacApptOnMakerSideHandler();
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
    
    public void btnForSubmitIntVacApptOnMakerSideHandler() {
        if(selectedRowInUnappIntVacApptsDataTblOnMakerSide != null)
        {
            switch (selectedRowInUnappIntVacApptsDataTblOnMakerSide.getStatus()) {
                case "entry":
                    intVacApp.setId(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getId());
                    intVacApp.setCreatedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getCreatedBy());
                    intVacApp.setCreationTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getCreationTimeStamp());
                    intVacApp.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    intVacApp.setEditingTimeStamp(new Date().toString());
                    intVacApp.setApprovedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApprovedBy());
                    intVacApp.setApprovedTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApprovedTimeStamp());
                    intVacApp.setDeletedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getDeletedBy());
                    intVacApp.setDeletionTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getDeletionTimeStamp());
                    intVacApp.setUnapprovedChange(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getUnapprovedChange());
                    intVacApp.setStatus("submitted");
                    if(intVacAppService.save(intVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetIntVacApptOnMakerSideHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                case "reversed entry":
                    intVacApp.setUnapprovedChange(intVacApp.getVacancy().getId()+"|"+intVacApp.getEmployee().getEmployeeSerialID()+"|"+intVacApp.getApplicationDate()+"|"+intVacApp.getApplicationStatus()+"|"+intVacApp.getOfferStatus());
                    intVacApp.setId(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getId());
                    intVacApp.setVacancy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getVacancy());
                    intVacApp.setEmployee(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getEmployee());
                    intVacApp.setApplicationDate(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApplicationDate());
                    intVacApp.setApplicationStatus(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApplicationStatus());
                    intVacApp.setOfferStatus(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getOfferStatus());
                    intVacApp.setCreatedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getCreatedBy());
                    intVacApp.setCreationTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getCreationTimeStamp());
                    intVacApp.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    intVacApp.setEditingTimeStamp(new Date().toString());
                    intVacApp.setApprovedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApprovedBy());
                    intVacApp.setApprovedTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getApprovedTimeStamp());
                    intVacApp.setDeletedBy(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getDeletedBy());
                    intVacApp.setDeletionTimeStamp(selectedRowInUnappIntVacApptsDataTblOnMakerSide.getDeletionTimeStamp());
                    intVacApp.setStatus("reversed submission");
                    if(intVacAppService.save(intVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetIntVacApptOnMakerSideHandler();
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
    
    public void btnForDeleteIntVacApptOnMakerSideHandler() {
        
    }
    
    //===========Checker Methods========================//
    
    public void btnForApproveSelectedIntVacApptRowsOnCheckerSideHandler() {
        if(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.size() > 0)
        {
            int i;
            for(i= 0; i < selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.size(); i++)
            {
                if("submitted".equals(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getStatus()) || ("reversed submission".equals(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getStatus()) && "-".equals(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange())))
                {
                    intVacApp.setId(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getId());
                    intVacApp.setVacancy(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getVacancy());
                    intVacApp.setEmployee(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getEmployee());
                    intVacApp.setApplicationDate(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getApplicationDate());
                    intVacApp.setApplicationStatus(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getApplicationStatus());
                    intVacApp.setOfferStatus(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getOfferStatus());
                    intVacApp.setCreatedBy(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                    intVacApp.setCreationTimeStamp(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                    intVacApp.setEditedBy(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                    intVacApp.setEditingTimeStamp(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                    intVacApp.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                    intVacApp.setApprovedTimeStamp(new Date().toString());
                    intVacApp.setDeletedBy(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                    intVacApp.setDeletionTimeStamp(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                    intVacApp.setUnapprovedChange("-");
                    intVacApp.setStatus("approved");
                    if(intVacAppService.save(intVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table 
                    }
                    else
                        break;
                }
                else if("reversed submission".equals(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getStatus()) && !"-".equals(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange()))
                {
                    intVacApp.setId(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getId());
                    Vacancy vac = new Vacancy();vac.setId(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]);
                    intVacApp.setVacancy(vac);
                    Employee emp = new Employee();emp.setEmployeeSerialID(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]);
                    intVacApp.setEmployee(emp);
                    intVacApp.setApplicationDate(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]);
                    intVacApp.setApplicationStatus(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    intVacApp.setOfferStatus(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[4]);
                    intVacApp.setCreatedBy(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                    intVacApp.setCreationTimeStamp(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                    intVacApp.setEditedBy(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                    intVacApp.setEditingTimeStamp(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                    intVacApp.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                    intVacApp.setApprovedTimeStamp(new Date().toString());
                    intVacApp.setDeletedBy(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                    intVacApp.setDeletionTimeStamp(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                    intVacApp.setUnapprovedChange("-");
                    intVacApp.setStatus("approved");
                    if(intVacAppService.save(intVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table 
                    }
                    else
                        break;
                }
                else
                    break;
            }
            if(i==selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.size())
            {
                appIntVacAppts = intVacAppService.fetchAllApproved();
                toBeReviewdIntVacAppts = intVacAppService.fetchAllSubmitted();
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
    
    public void btnForMakeSelectedIntVacApptRowsEditableOnCheckerSideHandler() {
        if(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.size()> 0)
        {
            int i;
            boolean outerBreak = false;
            for (i= 0; i < selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.size(); i++) {
                intVacApp.setId(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getId());
                intVacApp.setVacancy(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getVacancy());
                intVacApp.setEmployee(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getEmployee());
                intVacApp.setApplicationDate(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getApplicationDate());
                intVacApp.setApplicationStatus(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getApplicationStatus());
                intVacApp.setOfferStatus(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getOfferStatus());
                intVacApp.setCreatedBy(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                intVacApp.setCreationTimeStamp(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                intVacApp.setEditedBy(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                intVacApp.setEditingTimeStamp(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                intVacApp.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                intVacApp.setApprovedTimeStamp(new Date().toString());
                intVacApp.setDeletedBy(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                intVacApp.setDeletionTimeStamp(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                intVacApp.setUnapprovedChange(selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange());
                switch (selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.get(i).getStatus()) {
                    case "submitted":
                        intVacApp.setStatus("entry");
                        break;
                    case "reversed submission":
                        intVacApp.setStatus("reversed entry");
                        break;
                    default:
                        outerBreak = true;
                        break;
                }
                if(outerBreak)
                    break;
                else
                {
                    if(intVacAppService.save(intVacApp))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                        break;
                }
            }
            if(i==selectedRowsInIntVacApptsToBeReviewedByCheckerDataTbl.size())
            {
                appIntVacAppts = intVacAppService.fetchAllApproved();
                toBeReviewdIntVacAppts = intVacAppService.fetchAllSubmitted();
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
    
    public void btnForDeleteSelectedIntVacApptRowsOnCheckerSide() {
        
    }
}
