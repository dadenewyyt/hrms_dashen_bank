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
import com.dashen.hrms.VacancyTransfer;
import com.dashen.hrms.service.EmployeeService;
import com.dashen.hrms.service.InternalVacancApplicantsService;
import com.dashen.hrms.service.VacancyService;
import com.dashen.hrms.service.VacancyTransferService;
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
public class VacancyTransferBean {
    //===========Users==================================//
    
    //GrantedAuthority maker = new SimpleGrantedAuthority(UserAuthority.MAKER);
    //GrantedAuthority checker = new SimpleGrantedAuthority(UserAuthority.CHECKER);
    
    //===========Auto Wired Objects=====================//
    
    @Autowired
    VacancyTransferService vacTraService;
    @Autowired
    VacancyService vacService;
    @Autowired
    EmployeeService empService;
    
    @Autowired
    InternalVacancApplicantsService intVacAppService;
    
    //===========Common Variables=====================//
    
    private boolean pnlForRegPageHeadingRender;
    private boolean pnlForAppPageHeadingRender;
    private List<VacancyTransfer> appReqts;
    
    //===========Maker Variables=====================//
    
    private boolean pnlForInputFieldsOnMakerSideRender;
    private VacancyTransfer vacTra;
    private List<SelectItem> vacSelectItem;
    private List<SelectItem> empSelectItem;
    private boolean pnlForUnappReqtsDataTblOnMakerSideRender;
    private List<VacancyTransfer> unappReqts;
    private VacancyTransfer selectedRowInUnappReqtsDataTblOnMakerSide;
    private boolean pnlForAppReqtsDataTblOnMakerSideRender;
    private VacancyTransfer selectedRowInAppReqtsDataTblOnMakerSide;
    private boolean pnlForBtnsOnMakerSideRender;
    
    //===========Checker Variables=====================//
    
    private boolean pnlForReqtsToBeReviewedByCheckerDataTblAndBtnsRender;
    private List<VacancyTransfer> toBeReviewdReqts;
    private List<VacancyTransfer> selectedRowsInReqtsToBeReviewedByCheckerDataTbl;
    private boolean pnlForAppReqtsDataTblOnCheckerSideRender;
    private List<VacancyTransfer> selectedRowsInAppReqtsDataTblOnCheckerSide;
    
    //===========Init Method=====================//
    
    @PostConstruct
    public void init() {
        vacTra = new VacancyTransfer();
        if(CurrentUser.getCurrentUser().getUsername().equals("mulugetak")) {
            pnlForRegPageHeadingRender = true;
            pnlForInputFieldsOnMakerSideRender = true;
            pnlForUnappReqtsDataTblOnMakerSideRender = true;
            pnlForAppReqtsDataTblOnMakerSideRender = true;
            pnlForBtnsOnMakerSideRender = true;
            
            pnlForAppPageHeadingRender = false;
            pnlForReqtsToBeReviewedByCheckerDataTblAndBtnsRender = false;
            pnlForAppReqtsDataTblOnCheckerSideRender = false;
            
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
            appReqts = vacTraService.fetchAllApproved();
            unappReqts = vacTraService.fetchAllUnsubmittedOrEditable();
            for(int i = 0; i < unappReqts.size(); i++)
            {
                if(!"-".equals(unappReqts.get(i).getUnapprovedChange()))
                {
                    unappReqts.get(i).setUnapprovedVacancy(vacService.getByVacancyId(unappReqts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]));
                    unappReqts.get(i).setUnapprovedVacancyArrow(true);
                    unappReqts.get(i).setUnapprovedVacancyDisplay(true);
                    unappReqts.get(i).setUnapprovedEmployee(empService.getByEmployeeSerialID(unappReqts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]));
                    unappReqts.get(i).setUnapprovedEmployeeArrow(true);
                    unappReqts.get(i).setUnapprovedEmployeeDisplay(true);
                }
            }
        }
        if(CurrentUser.getCurrentUser().getUsername().equals("dawits")) {
            pnlForAppPageHeadingRender = true;
            pnlForReqtsToBeReviewedByCheckerDataTblAndBtnsRender = true;
            pnlForAppReqtsDataTblOnCheckerSideRender = true;
            
            pnlForRegPageHeadingRender = false;
            pnlForInputFieldsOnMakerSideRender = false;
            pnlForUnappReqtsDataTblOnMakerSideRender = false;
            pnlForAppReqtsDataTblOnMakerSideRender = false;
            pnlForBtnsOnMakerSideRender = false;
            
            appReqts = vacTraService.fetchAllApproved();
            toBeReviewdReqts = vacTraService.fetchAllSubmitted();
            for(int i = 0; i < toBeReviewdReqts.size(); i++)
            {
                if(!"-".equals(toBeReviewdReqts.get(i).getUnapprovedChange()))
                {
                    toBeReviewdReqts.get(i).setUnapprovedVacancy(vacService.getByVacancyId(toBeReviewdReqts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]));
                    toBeReviewdReqts.get(i).setUnapprovedVacancyArrow(true);
                    toBeReviewdReqts.get(i).setUnapprovedVacancyDisplay(true);
                    toBeReviewdReqts.get(i).setUnapprovedEmployee(empService.getByEmployeeSerialID(toBeReviewdReqts.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]));
                    toBeReviewdReqts.get(i).setUnapprovedEmployeeArrow(true);
                    toBeReviewdReqts.get(i).setUnapprovedEmployeeDisplay(true);
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

    public List<VacancyTransfer> getAppReqts() {
        return appReqts;
    }

    public void setAppReqts(List<VacancyTransfer> appReqts) {
        this.appReqts = appReqts;
    }
    
    public boolean isPnlForInputFieldsOnMakerSideRender() {
        return pnlForInputFieldsOnMakerSideRender;
    }

    public void setPnlForInputFieldsOnMakerSideRender(boolean pnlForInputFieldsOnMakerSideRender) {
        this.pnlForInputFieldsOnMakerSideRender = pnlForInputFieldsOnMakerSideRender;
    }

    public VacancyTransfer getVacTra() {
        return vacTra;
    }

    public void setVacTra(VacancyTransfer vacTra) {
        this.vacTra = vacTra;
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

    public boolean isPnlForUnappReqtsDataTblOnMakerSideRender() {
        return pnlForUnappReqtsDataTblOnMakerSideRender;
    }

    public void setPnlForUnappReqtsDataTblOnMakerSideRender(boolean pnlForUnappReqtsDataTblOnMakerSideRender) {
        this.pnlForUnappReqtsDataTblOnMakerSideRender = pnlForUnappReqtsDataTblOnMakerSideRender;
    }

    public List<VacancyTransfer> getUnappReqts() {
        return unappReqts;
    }

    public void setUnappReqts(List<VacancyTransfer> unappReqts) {
        this.unappReqts = unappReqts;
    }

    public VacancyTransfer getSelectedRowInUnappReqtsDataTblOnMakerSide() {
        return selectedRowInUnappReqtsDataTblOnMakerSide;
    }

    public void setSelectedRowInUnappReqtsDataTblOnMakerSide(VacancyTransfer selectedRowInUnappReqtsDataTblOnMakerSide) {
        this.selectedRowInUnappReqtsDataTblOnMakerSide = selectedRowInUnappReqtsDataTblOnMakerSide;
    }

    public boolean isPnlForAppReqtsDataTblOnMakerSideRender() {
        return pnlForAppReqtsDataTblOnMakerSideRender;
    }

    public void setPnlForAppReqtsDataTblOnMakerSideRender(boolean pnlForAppReqtsDataTblOnMakerSideRender) {
        this.pnlForAppReqtsDataTblOnMakerSideRender = pnlForAppReqtsDataTblOnMakerSideRender;
    }

    public VacancyTransfer getSelectedRowInAppReqtsDataTblOnMakerSide() {
        return selectedRowInAppReqtsDataTblOnMakerSide;
    }

    public void setSelectedRowInAppReqtsDataTblOnMakerSide(VacancyTransfer selectedRowInAppReqtsDataTblOnMakerSide) {
        this.selectedRowInAppReqtsDataTblOnMakerSide = selectedRowInAppReqtsDataTblOnMakerSide;
    }

    public boolean isPnlForBtnsOnMakerSideRender() {
        return pnlForBtnsOnMakerSideRender;
    }

    public void setPnlForBtnsOnMakerSideRender(boolean pnlForBtnsOnMakerSideRender) {
        this.pnlForBtnsOnMakerSideRender = pnlForBtnsOnMakerSideRender;
    }

    public boolean isPnlForReqtsToBeReviewedByCheckerDataTblAndBtnsRender() {
        return pnlForReqtsToBeReviewedByCheckerDataTblAndBtnsRender;
    }

    public void setPnlForReqtsToBeReviewedByCheckerDataTblAndBtnsRender(boolean pnlForReqtsToBeReviewedByCheckerDataTblAndBtnsRender) {
        this.pnlForReqtsToBeReviewedByCheckerDataTblAndBtnsRender = pnlForReqtsToBeReviewedByCheckerDataTblAndBtnsRender;
    }

    public List<VacancyTransfer> getToBeReviewdReqts() {
        return toBeReviewdReqts;
    }

    public void setToBeReviewdReqts(List<VacancyTransfer> toBeReviewdReqts) {
        this.toBeReviewdReqts = toBeReviewdReqts;
    }

    public List<VacancyTransfer> getSelectedRowsInReqtsToBeReviewedByCheckerDataTbl() {
        return selectedRowsInReqtsToBeReviewedByCheckerDataTbl;
    }

    public void setSelectedRowsInReqtsToBeReviewedByCheckerDataTbl(List<VacancyTransfer> selectedRowsInReqtsToBeReviewedByCheckerDataTbl) {
        this.selectedRowsInReqtsToBeReviewedByCheckerDataTbl = selectedRowsInReqtsToBeReviewedByCheckerDataTbl;
    }

    public boolean isPnlForAppReqtsDataTblOnCheckerSideRender() {
        return pnlForAppReqtsDataTblOnCheckerSideRender;
    }

    public void setPnlForAppReqtsDataTblOnCheckerSideRender(boolean pnlForAppReqtsDataTblOnCheckerSideRender) {
        this.pnlForAppReqtsDataTblOnCheckerSideRender = pnlForAppReqtsDataTblOnCheckerSideRender;
    }

    public List<VacancyTransfer> getSelectedRowsInAppReqtsDataTblOnCheckerSide() {
        return selectedRowsInAppReqtsDataTblOnCheckerSide;
    }

    public void setSelectedRowsInAppReqtsDataTblOnCheckerSide(List<VacancyTransfer> selectedRowsInAppReqtsDataTblOnCheckerSide) {
        this.selectedRowsInAppReqtsDataTblOnCheckerSide = selectedRowsInAppReqtsDataTblOnCheckerSide;
    }
    
    //===========Maker Methods=====================//
    
    public void vacNoDrpDnOnMakerSideValueChangeListener() {
//        List<InternalVacancApplicants> intVacAppLis = intVacAppService.getByVacancyId(vacTra.getVacancy().getId());
//        empSelectItem = new ArrayList<>();
//        for (int i = 0; i < intVacAppLis.size(); i++)
//        {
//            if(intVacAppLis.get(i).getApplicationStatus().equals("Nominated") && intVacAppLis.get(i).getOfferStatus().equals("Accepted")) {
//                Employee emp = empService.getByEmployeeSerialID(intVacAppLis.get(i).getEmployee().getEmployeeSerialID());
//                empSelectItem.add(new SelectItem(emp.getEmployeeSerialID(), emp.getFullName()));
//            }
//        }
    }
    
    public void unappReqtsDataTblOnMakerSideRowSelectedListener() {
        if("reversed entry".equals(selectedRowInUnappReqtsDataTblOnMakerSide.getStatus()) && !"-".equals(selectedRowInUnappReqtsDataTblOnMakerSide.getUnapprovedChange()))
        {
            vacTra.setId(selectedRowInUnappReqtsDataTblOnMakerSide.getId());
            vacTra.setVacancy(selectedRowInUnappReqtsDataTblOnMakerSide.getUnapprovedVacancy());
            vacTra.setEmployee(selectedRowInUnappReqtsDataTblOnMakerSide.getUnapprovedEmployee());
            vacTra.setCreatedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getCreatedBy());
            vacTra.setCreationTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getCreationTimeStamp());
            vacTra.setEditedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getEditedBy());
            vacTra.setEditingTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getEditingTimeStamp());
            vacTra.setApprovedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getApprovedBy());
            vacTra.setApprovedTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getApprovedTimeStamp());
            vacTra.setDeletedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getDeletedBy());
            vacTra.setDeletionTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getDeletionTimeStamp());
            vacTra.setUnapprovedChange(selectedRowInUnappReqtsDataTblOnMakerSide.getUnapprovedChange());
            vacTra.setStatus(selectedRowInUnappReqtsDataTblOnMakerSide.getStatus());
        }
        else if(("reversed entry".equals(selectedRowInUnappReqtsDataTblOnMakerSide.getStatus()) && "-".equals(selectedRowInUnappReqtsDataTblOnMakerSide.getUnapprovedChange())) || ("entry".equals(selectedRowInUnappReqtsDataTblOnMakerSide.getStatus())))
        {
            vacTra.setId(selectedRowInUnappReqtsDataTblOnMakerSide.getId());
            vacTra.setVacancy(selectedRowInUnappReqtsDataTblOnMakerSide.getVacancy());
            vacTra.setEmployee(selectedRowInUnappReqtsDataTblOnMakerSide.getEmployee());
            vacTra.setCreatedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getCreatedBy());
            vacTra.setCreationTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getCreationTimeStamp());
            vacTra.setEditedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getEditedBy());
            vacTra.setEditingTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getEditingTimeStamp());
            vacTra.setApprovedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getApprovedBy());
            vacTra.setApprovedTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getApprovedTimeStamp());
            vacTra.setDeletedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getDeletedBy());
            vacTra.setDeletionTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getDeletionTimeStamp());
            vacTra.setUnapprovedChange(selectedRowInUnappReqtsDataTblOnMakerSide.getUnapprovedChange());
            vacTra.setStatus(selectedRowInUnappReqtsDataTblOnMakerSide.getStatus());
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Unexpected data status found. Please, Consult your system administrator."));
    }
    
    public void AppReqtsDataTblOnMakerSideRowSelectedListener() {
        
    }
    
    public void btnForResetVacTraOnMakerSideHandler() {
        selectedRowInUnappReqtsDataTblOnMakerSide = null;
        selectedRowInAppReqtsDataTblOnMakerSide = null;
        init();
    }
    
    public void btnForSaveVacTraOnMakerSideHandler() {
        if(selectedRowInUnappReqtsDataTblOnMakerSide == null)
        {
            vacTra.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
            vacTra.setCreationTimeStamp(new Date().toString());
            vacTra.setEditedBy("-");
            vacTra.setEditingTimeStamp("-");
            vacTra.setApprovedBy("-");
            vacTra.setApprovedTimeStamp("-");
            vacTra.setDeletedBy("-");
            vacTra.setDeletionTimeStamp("-");
            vacTra.setUnapprovedChange("-");
            vacTra.setStatus("entry");
            if(vacTraService.save(vacTra))
            {
                btnForResetVacTraOnMakerSideHandler();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully saved the data.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to save your data. Please, consult your system adminstrator."));
        }
        else
        {
            switch (selectedRowInUnappReqtsDataTblOnMakerSide.getStatus()) {
                case "entry":
                    vacTra.setId(selectedRowInUnappReqtsDataTblOnMakerSide.getId());
                    vacTra.setCreatedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getCreatedBy());
                    vacTra.setCreationTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getCreationTimeStamp());
                    vacTra.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    vacTra.setEditingTimeStamp(new Date().toString());
                    vacTra.setApprovedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getApprovedBy());
                    vacTra.setApprovedTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getApprovedTimeStamp());
                    vacTra.setDeletedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getDeletedBy());
                    vacTra.setDeletionTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getDeletionTimeStamp());
                    vacTra.setUnapprovedChange(selectedRowInUnappReqtsDataTblOnMakerSide.getUnapprovedChange());
                    vacTra.setStatus(selectedRowInUnappReqtsDataTblOnMakerSide.getStatus());
                    if(vacTraService.save(vacTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetVacTraOnMakerSideHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                case "reversed entry":
                    vacTra.setUnapprovedChange(vacTra.getVacancy().getId()+"|"+vacTra.getEmployee().getEmployeeSerialID());
                    vacTra.setId(selectedRowInUnappReqtsDataTblOnMakerSide.getId());
                    vacTra.setVacancy(selectedRowInUnappReqtsDataTblOnMakerSide.getVacancy());
                    vacTra.setEmployee(selectedRowInUnappReqtsDataTblOnMakerSide.getEmployee());
                    vacTra.setCreatedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getCreatedBy());
                    vacTra.setCreationTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getCreationTimeStamp());
                    vacTra.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    vacTra.setEditingTimeStamp(new Date().toString());
                    vacTra.setApprovedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getApprovedBy());
                    vacTra.setApprovedTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getApprovedTimeStamp());
                    vacTra.setDeletedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getDeletedBy());
                    vacTra.setDeletionTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getDeletionTimeStamp());
                    vacTra.setStatus(selectedRowInUnappReqtsDataTblOnMakerSide.getStatus());
                    if(vacTraService.save(vacTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetVacTraOnMakerSideHandler();
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
    
    public void btnForSubmitVacTraOnMakerSideHandler() {
        if(selectedRowInUnappReqtsDataTblOnMakerSide != null)
        {
            switch (selectedRowInUnappReqtsDataTblOnMakerSide.getStatus()) {
                case "entry":
                    vacTra.setId(selectedRowInUnappReqtsDataTblOnMakerSide.getId());
                    vacTra.setCreatedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getCreatedBy());
                    vacTra.setCreationTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getCreationTimeStamp());
                    vacTra.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    vacTra.setEditingTimeStamp(new Date().toString());
                    vacTra.setApprovedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getApprovedBy());
                    vacTra.setApprovedTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getApprovedTimeStamp());
                    vacTra.setDeletedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getDeletedBy());
                    vacTra.setDeletionTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getDeletionTimeStamp());
                    vacTra.setUnapprovedChange(selectedRowInUnappReqtsDataTblOnMakerSide.getUnapprovedChange());
                    vacTra.setStatus("submitted");
                    if(vacTraService.save(vacTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetVacTraOnMakerSideHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                case "reversed entry":
                    vacTra.setUnapprovedChange(vacTra.getVacancy().getId()+"|"+vacTra.getEmployee().getEmployeeSerialID());
                    vacTra.setId(selectedRowInUnappReqtsDataTblOnMakerSide.getId());
                    vacTra.setVacancy(selectedRowInUnappReqtsDataTblOnMakerSide.getVacancy());
                    vacTra.setEmployee(selectedRowInUnappReqtsDataTblOnMakerSide.getEmployee());
                    vacTra.setCreatedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getCreatedBy());
                    vacTra.setCreationTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getCreationTimeStamp());
                    vacTra.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    vacTra.setEditingTimeStamp(new Date().toString());
                    vacTra.setApprovedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getApprovedBy());
                    vacTra.setApprovedTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getApprovedTimeStamp());
                    vacTra.setDeletedBy(selectedRowInUnappReqtsDataTblOnMakerSide.getDeletedBy());
                    vacTra.setDeletionTimeStamp(selectedRowInUnappReqtsDataTblOnMakerSide.getDeletionTimeStamp());
                    vacTra.setStatus("reversed submission");
                    if(vacTraService.save(vacTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnForResetVacTraOnMakerSideHandler();
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
    
    public void btnForDeleteVacTraOnMakerSideHandler() {
        
    }
    
    //===========Checker Methods=====================//
    
    public void btnForApproveSelectedVacTraRowsOnCheckerSideHandler() {
        if(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.size() > 0)
        {
            int i;
            for(i= 0; i < selectedRowsInReqtsToBeReviewedByCheckerDataTbl.size(); i++)
            {
                if("submitted".equals(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getStatus()) || ("reversed submission".equals(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getStatus()) && "-".equals(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange())))
                {
                    vacTra.setId(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getId());
                    vacTra.setVacancy(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getVacancy());
                    vacTra.setEmployee(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getEmployee());
                    vacTra.setCreatedBy(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                    vacTra.setCreationTimeStamp(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                    vacTra.setEditedBy(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                    vacTra.setEditingTimeStamp(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                    vacTra.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                    vacTra.setApprovedTimeStamp(new Date().toString());
                    vacTra.setDeletedBy(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                    vacTra.setDeletionTimeStamp(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                    vacTra.setUnapprovedChange("-");
                    vacTra.setStatus("approved");
                    if(vacTraService.save(vacTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                       break;
                }
                else if("reversed submission".equals(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getStatus()) && !"-".equals(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange()))
                {
                    vacTra.setId(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getId());
                    Vacancy vac = new Vacancy();vac.setId(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]);
                    vacTra.setVacancy(vac);
                    Employee emp = new Employee();emp.setEmployeeSerialID(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]);
                    vacTra.setEmployee(emp);
                    vacTra.setCreatedBy(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                    vacTra.setCreationTimeStamp(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                    vacTra.setEditedBy(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                    vacTra.setEditingTimeStamp(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                    vacTra.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                    vacTra.setApprovedTimeStamp(new Date().toString());
                    vacTra.setDeletedBy(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                    vacTra.setDeletionTimeStamp(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                    vacTra.setUnapprovedChange("-");
                    vacTra.setStatus("approved");
                    if(vacTraService.save(vacTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                       break;
                }
                else
                    break;
            }
            if(i==selectedRowsInReqtsToBeReviewedByCheckerDataTbl.size())
            {
                appReqts = vacTraService.fetchAllApproved();
                toBeReviewdReqts = vacTraService.fetchAllSubmitted();
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
    
    public void btnForMakeSelectedVacTraRowsEditableOnCheckerSideHandler() {
        if(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.size()> 0)
        {
            int i;
            boolean outerBreak = false;
            for (i= 0; i < selectedRowsInReqtsToBeReviewedByCheckerDataTbl.size(); i++) {
                vacTra.setId(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getId());
                vacTra.setVacancy(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getVacancy());
                vacTra.setEmployee(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getEmployee());
                vacTra.setCreatedBy(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getCreatedBy());
                vacTra.setCreationTimeStamp(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getCreationTimeStamp());
                vacTra.setEditedBy(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getEditedBy());
                vacTra.setEditingTimeStamp(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getEditingTimeStamp());
                vacTra.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                vacTra.setApprovedTimeStamp(new Date().toString());
                vacTra.setDeletedBy(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getDeletedBy());
                vacTra.setDeletionTimeStamp(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getDeletionTimeStamp());
                vacTra.setUnapprovedChange(selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getUnapprovedChange());
                switch (selectedRowsInReqtsToBeReviewedByCheckerDataTbl.get(i).getStatus()) {
                    case "submitted":
                        vacTra.setStatus("entry");
                        break;
                    case "reversed submission":
                        vacTra.setStatus("reversed entry");
                        break;
                    default:
                        outerBreak = true;
                        break;
                }
                if(outerBreak)
                    break;
                else
                {
                    if(vacTraService.save(vacTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                        break;
                }
            }
            if(i==selectedRowsInReqtsToBeReviewedByCheckerDataTbl.size())
            {
                appReqts = vacTraService.fetchAllApproved();
                toBeReviewdReqts = vacTraService.fetchAllSubmitted();
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
    
    public void btnForDeleteSelectedVacTraRowsOnCheckerSide() {
        
    }
}
//<p:ajax event="itemSelect" listener="#{vacancyTransferBean.vacNoDrpDnOnMakerSideValueChangeListener}" update="frmForMaker:drpDnForEmpNamOnMakerSide" ></p:ajax>