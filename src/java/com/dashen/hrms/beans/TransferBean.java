/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.Employee;
import com.dashen.hrms.OrganizationalStructure;
import com.dashen.hrms.OrganizationalStructureType;
import com.dashen.hrms.Position;
import com.dashen.hrms.PositionHavingOrganizationalStructure;
import com.dashen.hrms.PositionTier;
import com.dashen.hrms.Transfer;
import com.dashen.hrms.service.EmployeeService;
import com.dashen.hrms.service.PositionService;
import com.dashen.hrms.service.StructureService;
import com.dashen.hrms.service.TransferService;
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
public class TransferBean {
    
    //===========Users=====================//
    
    //GrantedAuthority maker = new SimpleGrantedAuthority(UserAuthority.MAKER);
    //GrantedAuthority approver = new SimpleGrantedAuthority(UserAuthority.CHECKER);
    
    //===========Auto Wired Objects=====================//
    
    @Autowired
    TransferService traService;
    @Autowired
    EmployeeService empService;
    @Autowired
    StructureService orgStrService;
    @Autowired
    PositionService posService;
    
    //===========Maker Variables=====================//
    
    private boolean pnlRegPageHeadingRender;
    private boolean pnlAppPageHeadingRender;
    private boolean pnlInputFieldsRender;
    private Transfer newTra;
    private List<SelectItem> empSelectItem;
    private List<SelectItem> orgStrSelectItem;
    private List<SelectItem> posSelectItem;
    private boolean pnlMakerDataTblRender;
    private List<Transfer> objLstOfTransferRequests;//for maker and cheker
    private Transfer selectedRow;
    private boolean pnlMakerApprovedReqtsDataTblRender;
    private List<Transfer> objAppLstOfTransferRequests;//for maker and cheker
    private Transfer appReqtsSelectedRow;
    private boolean pnlBtnRender;
    
    //===========Approver Variables=====================//
    
    private boolean pnlApproverDataTblRender;
    private List<Transfer> selectedRows;
    private boolean pnlApproverApprovedReqtsDataTblRender;
    private List<Transfer> appselectedRows;
    
    //===========Init Method=====================//
    
    @PostConstruct
    public void init() {
        newTra = new Transfer();
        if(CurrentUser.getCurrentUser().getUsername().equals("mulugetak")) {
            pnlRegPageHeadingRender = true;
            pnlInputFieldsRender = true;
            pnlMakerDataTblRender = true;
            pnlMakerApprovedReqtsDataTblRender = true;
            pnlBtnRender = true;
            
            pnlAppPageHeadingRender = false;
            pnlApproverDataTblRender = false;
            pnlApproverApprovedReqtsDataTblRender = false;
            
            List<Employee> empList = empService.listAll();
            empSelectItem = new ArrayList<>();
            for (int i = 0; i < empList.size(); i++)
            {
               empSelectItem.add(new SelectItem(empList.get(i).getEmployeeSerialID(), empList.get(i).getFullName()));
            }
            List<OrganizationalStructure> orgStruList = orgStrService.listAllOrganizationalStructures();
            orgStrSelectItem = new ArrayList<>();
            for (int i = 0; i < orgStruList.size(); i++)
            {
               orgStrSelectItem.add(new SelectItem(orgStruList.get(i).getId(), orgStruList.get(i).getName()));
            }
            objAppLstOfTransferRequests = traService.fetchAllApproved();
            objLstOfTransferRequests = traService.fetchAllUnsubmittedOrEditable();
            for(int i = 0; i < objLstOfTransferRequests.size(); i++)
            {
                if(!"-".equals(objLstOfTransferRequests.get(i).getUnapprovedChange()))
                {
                    objLstOfTransferRequests.get(i).setUnapprovedEmployee(empService.getByEmployeeSerialID(objLstOfTransferRequests.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]));
                    objLstOfTransferRequests.get(i).setUnapprovedEmployeeArrow(true);
                    objLstOfTransferRequests.get(i).setUnapprovedEmployeeDisplay(true);
                    objLstOfTransferRequests.get(i).setUnapprovedOrganizationalStructure(orgStrService.getByOrganizationalStructureId(objLstOfTransferRequests.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]));
                    objLstOfTransferRequests.get(i).setUnapprovedOrganizationalStructureArrow(true);
                    objLstOfTransferRequests.get(i).setUnapprovedOrganizationalStructureDisplay(true);
                    objLstOfTransferRequests.get(i).setUnapprovedPosition(posService.getByID(objLstOfTransferRequests.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]));
                    objLstOfTransferRequests.get(i).setUnapprovedPositionArrow(true);
                    objLstOfTransferRequests.get(i).setUnapprovedPositionDisplay(true);
                    objLstOfTransferRequests.get(i).setUnapprovedRequestedDate(objLstOfTransferRequests.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    objLstOfTransferRequests.get(i).setUnapprovedRequestedDateArrow(true);
                    objLstOfTransferRequests.get(i).setUnapprovedRequestedDateDisplay(true);
                }
            }
        }
        if(CurrentUser.getCurrentUser().getUsername().equals("dawits")) {
            pnlAppPageHeadingRender = true;
            pnlApproverDataTblRender = true;
            pnlApproverApprovedReqtsDataTblRender = true;
           
            pnlRegPageHeadingRender = false;
            pnlInputFieldsRender = false;
            pnlMakerDataTblRender = false;
            pnlMakerApprovedReqtsDataTblRender = false;
            pnlBtnRender = false;
            
            objAppLstOfTransferRequests = traService.fetchAllApproved();
            objLstOfTransferRequests = traService.fetchAllSubmitted();
            for(int i = 0; i < objLstOfTransferRequests.size(); i++)
            {
                if(!"-".equals(objLstOfTransferRequests.get(i).getUnapprovedChange()))
                {
                    objLstOfTransferRequests.get(i).setUnapprovedEmployee(empService.getByEmployeeSerialID(objLstOfTransferRequests.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]));
                    objLstOfTransferRequests.get(i).setUnapprovedEmployeeArrow(true);
                    objLstOfTransferRequests.get(i).setUnapprovedEmployeeDisplay(true);
                    objLstOfTransferRequests.get(i).setUnapprovedOrganizationalStructure(orgStrService.getByOrganizationalStructureId(objLstOfTransferRequests.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]));
                    objLstOfTransferRequests.get(i).setUnapprovedOrganizationalStructureArrow(true);
                    objLstOfTransferRequests.get(i).setUnapprovedOrganizationalStructureDisplay(true);
                    objLstOfTransferRequests.get(i).setUnapprovedPosition(posService.getByID(objLstOfTransferRequests.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]));
                    objLstOfTransferRequests.get(i).setUnapprovedPositionArrow(true);
                    objLstOfTransferRequests.get(i).setUnapprovedPositionDisplay(true);
                    objLstOfTransferRequests.get(i).setUnapprovedRequestedDate(objLstOfTransferRequests.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    objLstOfTransferRequests.get(i).setUnapprovedRequestedDateArrow(true);
                    objLstOfTransferRequests.get(i).setUnapprovedRequestedDateDisplay(true);
                }
            }
        }
    }
    
    //===========Getter and Setter=====================//

    public boolean isPnlRegPageHeadingRender() {
        return pnlRegPageHeadingRender;
    }

    public void setPnlRegPageHeadingRender(boolean pnlRegPageHeadingRender) {
        this.pnlRegPageHeadingRender = pnlRegPageHeadingRender;
    }

    public boolean isPnlAppPageHeadingRender() {
        return pnlAppPageHeadingRender;
    }

    public void setPnlAppPageHeadingRender(boolean pnlAppPageHeadingRender) {
        this.pnlAppPageHeadingRender = pnlAppPageHeadingRender;
    }

    public boolean isPnlInputFieldsRender() {
        return pnlInputFieldsRender;
    }

    public void setPnlInputFieldsRender(boolean pnlInputFieldsRender) {
        this.pnlInputFieldsRender = pnlInputFieldsRender;
    }
    
    public Transfer getNewTra() {
        return newTra;
    }

    public void setNewTra(Transfer newTra) {
        this.newTra = newTra;
    }

    public List<SelectItem> getEmpSelectItem() {
        return empSelectItem;
    }

    public void setEmpSelectItem(List<SelectItem> empSelectItem) {
        this.empSelectItem = empSelectItem;
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

    public boolean isPnlMakerDataTblRender() {
        return pnlMakerDataTblRender;
    }

    public void setPnlMakerDataTblRender(boolean pnlMakerDataTblRender) {
        this.pnlMakerDataTblRender = pnlMakerDataTblRender;
    }

    public List<Transfer> getObjLstOfTransferRequests() {
        return objLstOfTransferRequests;
    }

    public void setObjLstOfTransferRequests(List<Transfer> objLstOfTransferRequests) {
        this.objLstOfTransferRequests = objLstOfTransferRequests;
    }

    public Transfer getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(Transfer selectedRow) {
        this.selectedRow = selectedRow;
    }

    public boolean isPnlMakerApprovedReqtsDataTblRender() {
        return pnlMakerApprovedReqtsDataTblRender;
    }

    public void setPnlMakerApprovedReqtsDataTblRender(boolean pnlMakerApprovedReqtsDataTblRender) {
        this.pnlMakerApprovedReqtsDataTblRender = pnlMakerApprovedReqtsDataTblRender;
    }

    public List<Transfer> getObjAppLstOfTransferRequests() {
        return objAppLstOfTransferRequests;
    }

    public void setObjAppLstOfTransferRequests(List<Transfer> objAppLstOfTransferRequests) {
        this.objAppLstOfTransferRequests = objAppLstOfTransferRequests;
    }

    public Transfer getAppReqtsSelectedRow() {
        return appReqtsSelectedRow;
    }

    public void setAppReqtsSelectedRow(Transfer appReqtsSelectedRow) {
        this.appReqtsSelectedRow = appReqtsSelectedRow;
    }

    public boolean isPnlBtnRender() {
        return pnlBtnRender;
    }

    public void setPnlBtnRender(boolean pnlBtnRender) {
        this.pnlBtnRender = pnlBtnRender;
    }

    public boolean isPnlApproverDataTblRender() {
        return pnlApproverDataTblRender;
    }

    public void setPnlApproverDataTblRender(boolean pnlApproverDataTblRender) {
        this.pnlApproverDataTblRender = pnlApproverDataTblRender;
    }

    public List<Transfer> getSelectedRows() {
        return selectedRows;
    }

    public void setSelectedRows(List<Transfer> selectedRows) {
        this.selectedRows = selectedRows;
    }

    public boolean isPnlApproverApprovedReqtsDataTblRender() {
        return pnlApproverApprovedReqtsDataTblRender;
    }

    public void setPnlApproverApprovedReqtsDataTblRender(boolean pnlApproverApprovedReqtsDataTblRender) {
        this.pnlApproverApprovedReqtsDataTblRender = pnlApproverApprovedReqtsDataTblRender;
    }

    public List<Transfer> getAppselectedRows() {
        return appselectedRows;
    }

    public void setAppselectedRows(List<Transfer> appselectedRows) {
        this.appselectedRows = appselectedRows;
    }
    
    //===========Maker Methods=====================//
    
    public void orgStrDrpDnOnMakerSideValueChangeListener() {
        OrganizationalStructureType ost = orgStrService.getByOrganizationalStructureTypeId(orgStrService.getByOrganizationalStructureId(newTra.getOrganizationalStructure().getId()).getOrganizationalStructureTypeID());
        List<Position> posList = posService.getByOrganizationalStructureTypeId(ost.getId());
        posSelectItem = new ArrayList<>();
        for (int i = 0; i < posList.size(); i++)
        {
            if(posList.get(i).isHasOrganizationalStructure()) {
                PositionHavingOrganizationalStructure phos = posService.getPositionHavingOrganizationalStructureByID(posList.get(i).getID());
                if(phos.getOrganizationalStructure().getId().equals(newTra.getOrganizationalStructure().getId()))
                    posSelectItem.add(new SelectItem(posList.get(i).getID(), posList.get(i).getTitle()));
            }
            else if(posList.get(i).isHasTier()) {
                PositionTier pt = posService.getPositionTierByID(posList.get(i).getID());
                if(pt.getID().equals(ost.getId()))
                    posSelectItem.add(new SelectItem(posList.get(i).getID(), posList.get(i).getTitle()));
            }
            else
                posSelectItem.add(new SelectItem(posList.get(i).getID(), posList.get(i).getTitle()));
        }
    }
    
    public void makerDataTblRowSelected() {
        if("reversed entry".equals(selectedRow.getStatus()) && !"-".equals(selectedRow.getUnapprovedChange()))
        {
            newTra.setId(selectedRow.getId());
            newTra.setEmployee(selectedRow.getUnapprovedEmployee());
            newTra.setOrganizationalStructure(selectedRow.getUnapprovedOrganizationalStructure());
            newTra.setPosition(selectedRow.getUnapprovedPosition());
            try{newTra.setRequestedDateInDate(new SimpleDateFormat("dd-MMM-yyyy").parse(selectedRow.getUnapprovedRequestedDate()));}catch (ParseException ex){FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getLocalizedMessage()));}
            newTra.setCreatedBy(selectedRow.getCreatedBy());
            newTra.setCreationTimeStamp(selectedRow.getCreationTimeStamp());
            newTra.setEditedBy(selectedRow.getEditedBy());
            newTra.setEditingTimeStamp(selectedRow.getEditingTimeStamp());
            newTra.setApprovedBy(selectedRow.getApprovedBy());
            newTra.setApprovedTimeStamp(selectedRow.getApprovedTimeStamp());
            newTra.setDeletedBy(selectedRow.getDeletedBy());
            newTra.setDeletionTimeStamp(selectedRow.getDeletionTimeStamp());
            newTra.setUnapprovedChange(selectedRow.getUnapprovedChange());
            newTra.setStatus(selectedRow.getStatus());
        }
        else if(("reversed entry".equals(selectedRow.getStatus()) && "-".equals(selectedRow.getUnapprovedChange())) || ("entry".equals(selectedRow.getStatus())))
        {
            newTra.setId(selectedRow.getId());
            newTra.setEmployee(selectedRow.getEmployee());
            newTra.setOrganizationalStructure(selectedRow.getOrganizationalStructure());
            newTra.setPosition(selectedRow.getPosition());
            try{newTra.setRequestedDateInDate(new SimpleDateFormat("dd-MMM-yyyy").parse(selectedRow.getRequestedDate()));}catch (ParseException ex){FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getLocalizedMessage()));}
            newTra.setCreatedBy(selectedRow.getCreatedBy());
            newTra.setCreationTimeStamp(selectedRow.getCreationTimeStamp());
            newTra.setEditedBy(selectedRow.getEditedBy());
            newTra.setEditingTimeStamp(selectedRow.getEditingTimeStamp());
            newTra.setApprovedBy(selectedRow.getApprovedBy());
            newTra.setApprovedTimeStamp(selectedRow.getApprovedTimeStamp());
            newTra.setDeletedBy(selectedRow.getDeletedBy());
            newTra.setDeletionTimeStamp(selectedRow.getDeletionTimeStamp());
            newTra.setUnapprovedChange(selectedRow.getUnapprovedChange());
            newTra.setStatus(selectedRow.getStatus());
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Unexpected data status found. Please, Consult your system administrator."));
    }
    
    public void makerApprovedReqtsDataTblRowSelected() {
        
    }
    
    public void btnNewTransferHandler() {
        selectedRow = null;
        init();
    }
    
    public void btnSaveTransferHandler() {
        if(selectedRow == null)
        {
            newTra.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
            newTra.setCreationTimeStamp(new Date().toString());
            newTra.setEditedBy("-");
            newTra.setEditingTimeStamp("-");
            newTra.setApprovedBy("-");
            newTra.setApprovedTimeStamp("-");
            newTra.setDeletedBy("-");
            newTra.setDeletionTimeStamp("-");
            newTra.setUnapprovedChange("-");
            newTra.setStatus("entry");
            if(traService.save(newTra))
            {
                btnNewTransferHandler();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully saved the data.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to save your data. Please, consult your system adminstrator."));
        }
        else
        {
            switch (selectedRow.getStatus()) {
                case "entry":
                    newTra.setId(selectedRow.getId());
                    newTra.setCreatedBy(selectedRow.getCreatedBy());
                    newTra.setCreationTimeStamp(selectedRow.getCreationTimeStamp());
                    newTra.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    newTra.setEditingTimeStamp(new Date().toString());
                    newTra.setApprovedBy(selectedRow.getApprovedBy());
                    newTra.setApprovedTimeStamp(selectedRow.getApprovedTimeStamp());
                    newTra.setDeletedBy(selectedRow.getDeletedBy());
                    newTra.setDeletionTimeStamp(selectedRow.getDeletionTimeStamp());
                    newTra.setUnapprovedChange(selectedRow.getUnapprovedChange());
                    newTra.setStatus(selectedRow.getStatus());
                    if(traService.save(newTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnNewTransferHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                case "reversed entry":
                    newTra.setUnapprovedChange(newTra.getEmployee().getEmployeeSerialID()+"|"+newTra.getOrganizationalStructure().getId()+"|"+newTra.getPosition().getID()+"|"+newTra.getRequestedDate());
                    newTra.setId(selectedRow.getId());
                    newTra.setEmployee(selectedRow.getEmployee());
                    newTra.setOrganizationalStructure(selectedRow.getOrganizationalStructure());
                    newTra.setPosition(selectedRow.getPosition());
                    newTra.setRequestedDate(selectedRow.getRequestedDate());
                    newTra.setCreatedBy(selectedRow.getCreatedBy());
                    newTra.setCreationTimeStamp(selectedRow.getCreationTimeStamp());
                    newTra.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    newTra.setEditingTimeStamp(new Date().toString());
                    newTra.setApprovedBy(selectedRow.getApprovedBy());
                    newTra.setApprovedTimeStamp(selectedRow.getApprovedTimeStamp());
                    newTra.setDeletedBy(selectedRow.getDeletedBy());
                    newTra.setDeletionTimeStamp(selectedRow.getDeletionTimeStamp());
                    newTra.setStatus(selectedRow.getStatus());
                    if(traService.save(newTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnNewTransferHandler();
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
    
    public void btnSubmitTransferHandler() {
        if(selectedRow != null)
        {
            switch (selectedRow.getStatus()) {
                case "entry":
                    newTra.setId(selectedRow.getId());
                    newTra.setCreatedBy(selectedRow.getCreatedBy());
                    newTra.setCreationTimeStamp(selectedRow.getCreationTimeStamp());
                    newTra.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    newTra.setEditingTimeStamp(new Date().toString());
                    newTra.setApprovedBy(selectedRow.getApprovedBy());
                    newTra.setApprovedTimeStamp(selectedRow.getApprovedTimeStamp());
                    newTra.setDeletedBy(selectedRow.getDeletedBy());
                    newTra.setDeletionTimeStamp(selectedRow.getDeletionTimeStamp());
                    newTra.setUnapprovedChange(selectedRow.getUnapprovedChange());
                    newTra.setStatus("submitted");
                    if(traService.save(newTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnNewTransferHandler();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "You have successfully updated the data.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update your data. Please, consult your system adminstrator."));
                    break;
                case "reversed entry":
                    newTra.setUnapprovedChange(newTra.getEmployee().getEmployeeSerialID()+"|"+newTra.getOrganizationalStructure().getId()+"|"+newTra.getPosition().getID()+"|"+newTra.getRequestedDate());
                    newTra.setId(selectedRow.getId());
                    newTra.setEmployee(selectedRow.getEmployee());
                    newTra.setOrganizationalStructure(selectedRow.getOrganizationalStructure());
                    newTra.setPosition(selectedRow.getPosition());
                    newTra.setRequestedDate(selectedRow.getRequestedDate());
                    newTra.setCreatedBy(selectedRow.getCreatedBy());
                    newTra.setCreationTimeStamp(selectedRow.getCreationTimeStamp());
                    newTra.setEditedBy(CurrentUser.getCurrentUser().getUsername());
                    newTra.setEditingTimeStamp(new Date().toString());
                    newTra.setApprovedBy(selectedRow.getApprovedBy());
                    newTra.setApprovedTimeStamp(selectedRow.getApprovedTimeStamp());
                    newTra.setDeletedBy(selectedRow.getDeletedBy());
                    newTra.setDeletionTimeStamp(selectedRow.getDeletionTimeStamp());
                    newTra.setStatus("reversed submission");
                    if(traService.save(newTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                        btnNewTransferHandler();
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
    
    public void btnDeleteTransferHandler() {
        
    }
    
    //===========Approver Methods=====================//
    
    public void approveSelectedRows() {
        if(selectedRows.size() > 0)
        {
            int i;
            for(i= 0; i < selectedRows.size(); i++)
            {
                if("submitted".equals(selectedRows.get(i).getStatus()) || ("reversed submission".equals(selectedRows.get(i).getStatus()) && "-".equals(selectedRows.get(i).getUnapprovedChange())))
                {
                    newTra.setId(selectedRows.get(i).getId());
                    newTra.setEmployee(selectedRows.get(i).getEmployee());
                    newTra.setOrganizationalStructure(selectedRows.get(i).getOrganizationalStructure());
                    newTra.setPosition(selectedRows.get(i).getPosition());
                    newTra.setRequestedDate(selectedRows.get(i).getRequestedDate());
                    newTra.setCreatedBy(selectedRows.get(i).getCreatedBy());
                    newTra.setCreationTimeStamp(selectedRows.get(i).getCreationTimeStamp());
                    newTra.setEditedBy(selectedRows.get(i).getEditedBy());
                    newTra.setEditingTimeStamp(selectedRows.get(i).getEditingTimeStamp());
                    newTra.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                    newTra.setApprovedTimeStamp(new Date().toString());
                    newTra.setDeletedBy(selectedRows.get(i).getDeletedBy());
                    newTra.setDeletionTimeStamp(selectedRows.get(i).getDeletionTimeStamp());
                    newTra.setUnapprovedChange("-");
                    newTra.setStatus("approved");
                    if(traService.save(newTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                       break; 
                }
                else if("reversed submission".equals(selectedRows.get(i).getStatus()) && !"-".equals(selectedRows.get(i).getUnapprovedChange()))
                {
                    newTra.setId(selectedRows.get(i).getId());
                    Employee emp = new Employee();emp.setEmployeeSerialID(selectedRows.get(i).getUnapprovedChange().split(Pattern.quote("|"))[0]);
                    newTra.setEmployee(emp);
                    OrganizationalStructure orgStr = new OrganizationalStructure();orgStr.setId(selectedRows.get(i).getUnapprovedChange().split(Pattern.quote("|"))[1]);
                    newTra.setOrganizationalStructure(orgStr);
                    Position pos = new Position();pos.setID(selectedRows.get(i).getUnapprovedChange().split(Pattern.quote("|"))[2]);
                    newTra.setPosition(pos);
                    newTra.setRequestedDate(selectedRows.get(i).getUnapprovedChange().split(Pattern.quote("|"))[3]);
                    newTra.setCreatedBy(selectedRows.get(i).getCreatedBy());
                    newTra.setCreationTimeStamp(selectedRows.get(i).getCreationTimeStamp());
                    newTra.setEditedBy(selectedRows.get(i).getEditedBy());
                    newTra.setEditingTimeStamp(selectedRows.get(i).getEditingTimeStamp());
                    newTra.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                    newTra.setApprovedTimeStamp(new Date().toString());
                    newTra.setDeletedBy(selectedRows.get(i).getDeletedBy());
                    newTra.setDeletionTimeStamp(selectedRows.get(i).getDeletionTimeStamp());
                    newTra.setUnapprovedChange("-");
                    newTra.setStatus("approved");
                    if(traService.save(newTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                       break;
                }
                else
                    break;
            }
            if(i==selectedRows.size())
            {
                objAppLstOfTransferRequests = traService.fetchAllApproved();
                objLstOfTransferRequests = traService.fetchAllSubmitted();
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
    
    public void makeEditableByMaker() {
        if(selectedRows.size()> 0)
        {
            int i;
            boolean outerBreak = false;
            for (i= 0; i < selectedRows.size(); i++) {
                newTra.setId(selectedRows.get(i).getId());
                newTra.setEmployee(selectedRows.get(i).getEmployee());
                newTra.setOrganizationalStructure(selectedRows.get(i).getOrganizationalStructure());
                newTra.setPosition(selectedRows.get(i).getPosition());
                newTra.setRequestedDate(selectedRows.get(i).getRequestedDate());
                newTra.setCreatedBy(selectedRows.get(i).getCreatedBy());
                newTra.setCreationTimeStamp(selectedRows.get(i).getCreationTimeStamp());
                newTra.setEditedBy(selectedRows.get(i).getEditedBy());
                newTra.setEditingTimeStamp(selectedRows.get(i).getEditingTimeStamp());
                newTra.setApprovedBy(CurrentUser.getCurrentUser().getUsername());
                newTra.setApprovedTimeStamp(new Date().toString());
                newTra.setDeletedBy(selectedRows.get(i).getDeletedBy());
                newTra.setDeletionTimeStamp(selectedRows.get(i).getDeletionTimeStamp());
                newTra.setUnapprovedChange(selectedRows.get(i).getUnapprovedChange());
                switch (selectedRows.get(i).getStatus()) {
                    case "submitted":
                        newTra.setStatus("entry");
                        break;
                    case "reversed submission":
                        newTra.setStatus("reversed entry");
                        break;
                    default:
                        outerBreak = true;
                        break;
                }
                if(outerBreak)
                    break;
                else
                {
                    if(traService.save(newTra))
                    {
                        //TO DO - insert History into HistoryLog Table
                    }
                    else
                        break;
                }
            }
            if(i==selectedRows.size())
            {
                objAppLstOfTransferRequests = traService.fetchAllApproved();
                objLstOfTransferRequests = traService.fetchAllSubmitted();
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
    
    public void deleteSelectedRows() {
        
    }
}