/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.AllowancePaymentOptions;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.AllowanceType;
import com.dashen.hrms.AllowanceTypeQuantitative;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.AllowanceTypeService;
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
public class AllowanceTypeBean implements Serializable {

    private AllowanceType currentTempAllowanceType;
    private AllowanceTypeQuantitative currentAllowanceTypeQuantitative;

    @Autowired
    private AllowanceTypeService allowanceTypeService;

    private List<AllowanceType> allowanceTypesList;
    private AllowanceType selectedAllowanceType;
    
    private List<SelectItem> paymentOptionsList;

    private List<AllowanceType> tempAllowanceTypesList;
    private AllowanceType selectedTempAllowanceType;

    private boolean typeIsQuantitative;
    
    public boolean isAllowanceTypeQuantitative(AllowanceType at) {
        return at.getPaymentMethod().equals(AllowancePaymentOptions.QUANTITATIVE);
    }

    public AllowanceType getCurrentTempAllowanceType() {
        return currentTempAllowanceType;
    }

    public void setCurrentTempAllowanceType(AllowanceType currentTempAllowanceType) {
        this.currentTempAllowanceType = currentTempAllowanceType;
    }

    public AllowanceTypeQuantitative getCurrentAllowanceTypeQuantitative() {
        return currentAllowanceTypeQuantitative;
    }

    public void setCurrentAllowanceTypeQuantitative(AllowanceTypeQuantitative currentAllowanceTypeQuantitative) {
        this.currentAllowanceTypeQuantitative = currentAllowanceTypeQuantitative;
    }

    public void setAllowanceTypeService(AllowanceTypeService allowanceTypeService) {
        this.allowanceTypeService = allowanceTypeService;
    }

    public List<AllowanceType> getAllowanceTypesList() {
        return allowanceTypesList;
    }

    public void setAllowanceTypesList(List<AllowanceType> allowanceTypesList) {
        this.allowanceTypesList = allowanceTypesList;
    }

    public AllowanceType getSelectedAllowanceType() {
        return selectedAllowanceType;
    }

    public void setSelectedAllowanceType(AllowanceType selectedAllowanceType) {
        this.selectedAllowanceType = selectedAllowanceType;
    }

    public List<AllowanceType> getTempAllowanceTypesList() {
        return tempAllowanceTypesList;
    }

    public void setTempAllowanceTypesList(List<AllowanceType> tempAllowanceTypesList) {
        this.tempAllowanceTypesList = tempAllowanceTypesList;
    }

    public AllowanceType getSelectedTempAllowanceType() {
        return selectedTempAllowanceType;
    }

    public void setSelectedTempAllowanceType(AllowanceType selectedTempAllowanceType) {
        this.selectedTempAllowanceType = selectedTempAllowanceType;
    }

    public List<SelectItem> getPaymentOptionsList() {
        return paymentOptionsList;
    }

    public void setPaymentOptionsList(List<SelectItem> paymentOptionsList) {
        this.paymentOptionsList = paymentOptionsList;
    }

    public boolean isTypeIsQuantitative() {
        return typeIsQuantitative;
    }

    public void setTypeIsQuantitative(boolean typeIsQuantitative) {
        this.typeIsQuantitative = typeIsQuantitative;
    }
    
    public AllowanceTypeQuantitative getAllowanceTypeQuantitativeByTypeID(String typeID) {
        return allowanceTypeService.getAllowanceTypeQuantitativeByTypeID(typeID);
    }

    @PostConstruct
    public void init() {

        currentTempAllowanceType = new AllowanceType();
        currentAllowanceTypeQuantitative = new AllowanceTypeQuantitative();
        typeIsQuantitative = false;

        allowanceTypesList = allowanceTypeService.listAllowanceTypes();
        tempAllowanceTypesList = new ArrayList<>();
        
        paymentOptionsList = new ArrayList<>();
        
        for (AllowancePaymentOptions o : AllowancePaymentOptions.values()) {
            paymentOptionsList.add(new SelectItem(o.name(), o.toString()));
        }
    }

    public void paymentOptionSelectOneMenu_itemSelect() {
        if (currentTempAllowanceType.getPaymentMethod().equals(AllowancePaymentOptions.QUANTITATIVE)) {
            typeIsQuantitative = true;
        } else {
            typeIsQuantitative = false;
        }
    }

    public void btnNewAllowanceType_Handler() {
        currentTempAllowanceType = new AllowanceType();
        currentAllowanceTypeQuantitative = new AllowanceTypeQuantitative();
        typeIsQuantitative = false;
    }

    public boolean isRowSubmitted() {
        if (currentTempAllowanceType != null && currentTempAllowanceType.getTmpStatus() == TempStatus.EDITABLE) {
            return false;
        }
        return true;
    }
    public void btnRefreshList_Handler() {
        MyUser user = CurrentUser.getCurrentUser();
        tempAllowanceTypesList = allowanceTypeService.listTmpAllowanceTypesForMaker(user.getUsername());
    }

    public void tmpAllowanceTypesDataTableDataTable_rowSelected(SelectEvent evt) {
        currentTempAllowanceType = selectedTempAllowanceType;

        if (currentTempAllowanceType.getPaymentMethod().equals(AllowancePaymentOptions.QUANTITATIVE)) {
            currentAllowanceTypeQuantitative = allowanceTypeService.getAllowanceTypeQuantitativeByTypeID(currentTempAllowanceType.getID());
            typeIsQuantitative = true;
        } else {
            currentAllowanceTypeQuantitative = new AllowanceTypeQuantitative();
            typeIsQuantitative = false;
        }
    }

    public void btnSaveAllowanceType_Handler() {
        if (currentTempAllowanceType.getTmpStatus() == TempStatus.EDITABLE) {
            if (currentTempAllowanceType.getID() != null && !currentTempAllowanceType.getID().isEmpty()) {
                //is updating existing Temp AllowanceType record
                if (allowanceTypeService.updateTmpAllowanceType(currentTempAllowanceType, currentAllowanceTypeQuantitative)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save allowance type update", "Allowance type updates saved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save allowance type update", "Update Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else if (currentTempAllowanceType.getSourceId()!= null && !currentTempAllowanceType.getSourceId().isEmpty()) {//editing existing allowance type, tmp object is new
                if (allowanceTypeService.saveNewTmpAllowanceType(currentTempAllowanceType, currentAllowanceTypeQuantitative, ActionType.UPDATE)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save allowance type update", "Allowance type updates saved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save allowance type update", "Update Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else//new AllowanceType, and new tmp object
            {
                if (allowanceTypeService.saveNewTmpAllowanceType(currentTempAllowanceType, currentAllowanceTypeQuantitative, ActionType.CREATE)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New allowance type", "New allowance type added.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New allowance type", "Save Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
            currentTempAllowanceType = new AllowanceType();
            currentAllowanceTypeQuantitative = new AllowanceTypeQuantitative();
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update allowance type", "The selected row is already submitted.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnSubmit_Handler() {
        if (selectedTempAllowanceType != null) {
            if (selectedTempAllowanceType.getTmpStatus() != TempStatus.SUBMITTED) {
                selectedTempAllowanceType.setTmpStatus(TempStatus.SUBMITTED);
                if (allowanceTypeService.updateTmpAllowanceType(selectedTempAllowanceType, currentAllowanceTypeQuantitative)) {
                    //update successful   
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "Row submitted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    //update has failed, undo the change
                    selectedTempAllowanceType.setTmpStatus(TempStatus.EDITABLE);
                }
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "The selected row is already submitted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "No row selected.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnDeleteRow_Handler() {
        if (selectedTempAllowanceType != null) {
            if (selectedTempAllowanceType.getTmpStatus() != TempStatus.SUBMITTED) {
                if (allowanceTypeService.delete(selectedTempAllowanceType)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "Row is deleted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    currentTempAllowanceType = new AllowanceType();
                    currentAllowanceTypeQuantitative = new AllowanceTypeQuantitative();
                    tempAllowanceTypesList.remove(selectedTempAllowanceType);
                } else {
                    //delete failed
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete Row", "Row is not deleted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete Row", "Submitted rows cannot be deleted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "No row selected.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnEditSelectedAllowanceType_Handler() {
        AllowanceType allowanceTypeTmp = new AllowanceType();
        if (selectedAllowanceType != null) {
            if (!allowanceTypeService.checkIfAllowanceTypeHasPendingEdit(selectedAllowanceType.getID())) {
                allowanceTypeTmp.setSourceId(selectedAllowanceType.getID());
                allowanceTypeTmp.setName(selectedAllowanceType.getName());
                allowanceTypeTmp.setDependsOnPosition(selectedAllowanceType.isDependsOnPosition());
                allowanceTypeTmp.setDependsOnLocation(selectedAllowanceType.isDependsOnLocation());
                allowanceTypeTmp.setDependsOnEmploymentCenter(selectedAllowanceType.isDependsOnEmploymentCenter());
                currentTempAllowanceType = allowanceTypeTmp;
            } else {
                System.out.println("Allowance type has pending changes");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit allowance type", "The selected allowance type has pending changes. Please approve or cancel the changes and try again.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }
}
