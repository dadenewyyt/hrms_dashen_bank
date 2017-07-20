/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.Allowance;
import com.dashen.hrms.AllowanceEmploymentCenter;
import com.dashen.hrms.AllowanceLocation;
import com.dashen.hrms.AllowancePaymentOptions;
import com.dashen.hrms.AllowancePosition;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.AllowanceService;
import com.dashen.hrms.AllowanceType;
import com.dashen.hrms.AllowanceTypeQuantitative;
import com.dashen.hrms.City;
import com.dashen.hrms.Position;
import com.dashen.hrms.service.AllowanceTypeService;
import com.dashen.hrms.service.CityService;
import com.dashen.hrms.service.PositionService;
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
public class AllowanceBean implements Serializable {

    private Allowance currentTempAllowance;
    private AllowancePosition currentAllowancePosition;
    private AllowanceLocation currentAllowanceLocation;
    private AllowanceEmploymentCenter currentAllowanceEmploymentCenter;

    private boolean dependsOnPosition;
    private boolean dependsOnLocation;
    private boolean dependsOnEmploymentCenter;

    @Autowired
    private AllowanceService allowanceService;
    
    @Autowired
    private CityService cityService;

    private List<Allowance> allowancesList;
    private List<SelectItem> locationList;
    private List<SelectItem> employmentCenterList;
    private Allowance selectedAllowance;
    
    @Autowired
    private AllowanceTypeService allowanceTypeService;
    @Autowired
    private PositionService positionService;

    private List<Allowance> tempAllowancesList;
    private Allowance selectedTempAllowance;
    
    private List<SelectItem> allowanceTypeList;
    private List<SelectItem> positionList;

    private AllowanceTypeQuantitative allowanceTypeQuantitative;

    public AllowanceTypeQuantitative getAllowanceTypeQuantitative() {
        return allowanceTypeQuantitative;
    }

    public void setAllowanceTypeQuantitative(AllowanceTypeQuantitative allowanceTypeQuantitative) {
        this.allowanceTypeQuantitative = allowanceTypeQuantitative;
    }

    public Allowance getCurrentTempAllowance() {
        return currentTempAllowance;
    }

    public void setCurrentTempAllowance(Allowance currentTempAllowance) {
        this.currentTempAllowance = currentTempAllowance;
    }

    public AllowancePosition getCurrentAllowancePosition() {
        return currentAllowancePosition;
    }

    public void setCurrentAllowancePosition(AllowancePosition currentAllowancePosition) {
        this.currentAllowancePosition = currentAllowancePosition;
    }

    public AllowanceLocation getCurrentAllowanceLocation() {
        return currentAllowanceLocation;
    }

    public void setCurrentAllowanceLocation(AllowanceLocation currentAllowanceLocation) {
        this.currentAllowanceLocation = currentAllowanceLocation;
    }

    public AllowanceEmploymentCenter getCurrentAllowanceEmploymentCenter() {
        return currentAllowanceEmploymentCenter;
    }

    public void setCurrentAllowanceEmploymentCenter(AllowanceEmploymentCenter currentAllowanceEmploymentCenter) {
        this.currentAllowanceEmploymentCenter = currentAllowanceEmploymentCenter;
    }

    public void setAllowanceService(AllowanceService allowanceService) {
        this.allowanceService = allowanceService;
    }
    
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    public List<Allowance> getAllowancesList() {
        return allowancesList;
    }

    public void setAllowancesList(List<Allowance> allowancesList) {
        this.allowancesList = allowancesList;
    }

    public List<SelectItem> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<SelectItem> locationList) {
        this.locationList = locationList;
    }

    public List<SelectItem> getEmploymentCenterList() {
        return employmentCenterList;
    }

    public void setEmploymentCenter(List<SelectItem> employmentCenterList) {
        this.employmentCenterList = employmentCenterList;
    }

    public Allowance getSelectedAllowance() {
        return selectedAllowance;
    }

    public void setSelectedAllowance(Allowance selectedAllowance) {
        this.selectedAllowance = selectedAllowance;
    }

    public List<Allowance> getTempAllowancesList() {
        return tempAllowancesList;
    }

    public void setTempAllowancesList(List<Allowance> tempAllowancesList) {
        this.tempAllowancesList = tempAllowancesList;
    }

    public Allowance getSelectedTempAllowance() {
        return selectedTempAllowance;
    }

    public void setSelectedTempAllowance(Allowance selectedTempAllowance) {
        this.selectedTempAllowance = selectedTempAllowance;
    }
    
    public List<SelectItem> getAllowanceTypeList() {
        return allowanceTypeList;
    }

    public void setAllowanceTypeList(List<SelectItem> allowanceTypeList) {
        this.allowanceTypeList = allowanceTypeList;
    }

    public List<SelectItem> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<SelectItem> positionList) {
        this.positionList = positionList;
    }

    public boolean isDependsOnPosition() {
        return dependsOnPosition;
    }

    public void setDependsOnPosition(boolean dependsOnPosition) {
        this.dependsOnPosition = dependsOnPosition;
    }

    public boolean isDependsOnLocation() {
        return dependsOnLocation;
    }

    public void setDependsOnLocation(boolean dependsOnLocation) {
        this.dependsOnLocation = dependsOnLocation;
    }

    public boolean isDependsOnEmploymentCenter() {
        return dependsOnEmploymentCenter;
    }

    public void setDependsOnEmploymentCenter(boolean dependsOnEmploymentCenter) {
        this.dependsOnEmploymentCenter = dependsOnEmploymentCenter;
    }

    public boolean isAllowanceTypeQuantitative(AllowanceType at) {
        return at.getPaymentMethod().equals(AllowancePaymentOptions.QUANTITATIVE);
    }

    public AllowanceTypeQuantitative getAllowanceTypeQuantitativeByTypeID(String typeID) {
        return allowanceTypeService.getAllowanceTypeQuantitativeByTypeID(typeID);
    }
    
    @PostConstruct
    public void init() {
        dependsOnPosition = false;
        dependsOnLocation = false;
        dependsOnEmploymentCenter = false;

        currentTempAllowance = new Allowance();
        currentAllowancePosition = new AllowancePosition();
        currentAllowanceLocation = new AllowanceLocation();
        currentAllowanceEmploymentCenter = new AllowanceEmploymentCenter();
        
        allowanceTypeQuantitative = new AllowanceTypeQuantitative();
        

        allowancesList = allowanceService.listAllowances();
        tempAllowancesList = new ArrayList<>();
        
        allowanceTypeList = new ArrayList<>();
        positionList = new ArrayList<>();
        
        List<AllowanceType> aList = allowanceTypeService.listAllowanceTypes();
        for (AllowanceType a : aList) {
            allowanceTypeList.add(new SelectItem(a.getID(), a.getName()));
        }
        
        List<Position> pList = positionService.listPositions();
        for (Position p : pList) {
            positionList.add(new SelectItem(p.getID(), p.getTitle()));
        }

        locationList = new ArrayList<>();
        employmentCenterList = new ArrayList<>();
        List<City> cList = cityService.listCities();
        for (City c : cList) {
            locationList.add(new SelectItem(c.getID(), c.getName()));
            employmentCenterList.add(new SelectItem(c.getID(), c.getName()));
        }
    }

    public void typeSelectOneMenu_itemSelect() {
        AllowanceType type = allowanceTypeService.getByID(currentTempAllowance.getAllowanceType().getID());
        
        if (type.getPaymentMethod().equals(AllowancePaymentOptions.QUANTITATIVE)) {
            allowanceTypeQuantitative = allowanceTypeService.getAllowanceTypeQuantitativeByTypeID(type.getID());
        } else {
            allowanceTypeQuantitative = new AllowanceTypeQuantitative();
        }

        if (type.isDependsOnPosition()) {
            dependsOnPosition = true;
        }
        
        if (type.isDependsOnLocation()) {
            dependsOnLocation = true;
        }
        
        if (type.isDependsOnEmploymentCenter()) {
            dependsOnEmploymentCenter = true;
        }
    }

    public void btnNewAllowance_Handler() {
        currentTempAllowance = new Allowance();
        currentAllowancePosition = new AllowancePosition();
        currentAllowanceLocation = new AllowanceLocation();
        currentAllowanceEmploymentCenter = new AllowanceEmploymentCenter();
        allowanceTypeQuantitative = new AllowanceTypeQuantitative();

        dependsOnPosition = false;
        dependsOnLocation = false;
        dependsOnEmploymentCenter = false;
    }

    public boolean isRowSubmitted() {
        if (currentTempAllowance != null && currentTempAllowance.getTmpStatus() == TempStatus.EDITABLE) {
            return false;
        }
        return true;
    }
    public void btnRefreshList_Handler() {
        tempAllowancesList = allowanceService.listTmpAllowancesForMaker(CurrentUser.getCurrentUser().getUsername());
    }

    public void tmpAllowancesDataTableDataTable_rowSelected(SelectEvent evt) {
        currentTempAllowance = selectedTempAllowance;

        AllowanceType type = currentTempAllowance.getAllowanceType();

        if (type.getPaymentMethod().equals(AllowancePaymentOptions.QUANTITATIVE)) {
            allowanceTypeQuantitative = allowanceTypeService.getAllowanceTypeQuantitativeByTypeID(type.getID());
        } else {
            allowanceTypeQuantitative = new AllowanceTypeQuantitative();
        }

        if (type.isDependsOnPosition()) {
            currentAllowancePosition = allowanceService.getAllowancePositionByAllowanceID(currentTempAllowance.getID());
            dependsOnPosition = true;
        } else {
            currentAllowancePosition = new AllowancePosition();
            dependsOnPosition = false;
        }

        if (type.isDependsOnLocation()) {
            currentAllowanceLocation = allowanceService.getAllowanceLocationByAllowanceID(currentTempAllowance.getID());
            dependsOnLocation = true;
        } else {
            currentAllowanceLocation = new AllowanceLocation();
            dependsOnLocation = false;
        }

        if (type.isDependsOnEmploymentCenter()) {
            currentAllowanceEmploymentCenter = allowanceService.getAllowanceEmploymentCenterByAllowanceID(currentTempAllowance.getID());
            dependsOnEmploymentCenter = true;
        } else {
            currentAllowanceEmploymentCenter = new AllowanceEmploymentCenter();
            dependsOnEmploymentCenter = false;
        }

    }

    public AllowancePosition getAllowancePositionByAllowanceID(String ID) {
        return allowanceService.getAllowancePositionByAllowanceID(ID);
    }

    public AllowanceLocation getAllowanceLocationByAllowanceID(String ID) {
        return allowanceService.getAllowanceLocationByAllowanceID(ID);
    }

    public AllowanceEmploymentCenter getAllowanceEmploymentCenterByAllowanceID(String ID) {
        return allowanceService.getAllowanceEmploymentCenterByAllowanceID(ID);
    }

    public void btnSaveAllowance_Handler() {
        if (currentTempAllowance.getTmpStatus() == TempStatus.EDITABLE) {
            if (currentTempAllowance.getID() != null && !currentTempAllowance.getID().isEmpty()) {
                //is updating existing Temp Allowance record
                if (allowanceService.updateTmpAllowance(currentTempAllowance, currentAllowancePosition, currentAllowanceLocation, currentAllowanceEmploymentCenter)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Allowance update", "Allowance updates saved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Allowance update", "Update Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else if (currentTempAllowance.getSourceId() != null && !currentTempAllowance.getSourceId().isEmpty()) {//editing existing Allowance, tmp object is new
                if (allowanceService.saveNewTmpAllowance(currentTempAllowance, currentAllowancePosition, currentAllowanceLocation, currentAllowanceEmploymentCenter, ActionType.UPDATE)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Allowance update", "Allowance updates saved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Allowance update", "Update Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else//new allowance, and new tmp object
            {
                if (allowanceService.saveNewTmpAllowance(currentTempAllowance, currentAllowancePosition, currentAllowanceLocation, currentAllowanceEmploymentCenter, ActionType.CREATE)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Allowance", "New Allowance added.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Allowance", "Save Failed.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Allowance", "The selected row is already submitted.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        currentTempAllowance = new Allowance();
        currentAllowancePosition = new AllowancePosition();
        currentAllowanceLocation = new AllowanceLocation();
        currentAllowanceEmploymentCenter = new AllowanceEmploymentCenter();
        allowanceTypeQuantitative = new AllowanceTypeQuantitative();
    }

    public void btnSubmit_Handler() {
        if (selectedTempAllowance != null) {
            if (selectedTempAllowance.getTmpStatus() != TempStatus.SUBMITTED) {
                selectedTempAllowance.setTmpStatus(TempStatus.SUBMITTED);
                if (allowanceService.updateTmpAllowance(selectedTempAllowance, currentAllowancePosition, currentAllowanceLocation, currentAllowanceEmploymentCenter)) {
                    //update successful   
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "Row submitted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    //update has failed, undo the change
                    selectedTempAllowance.setTmpStatus(TempStatus.EDITABLE);
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
        if (selectedTempAllowance != null) {
            if (selectedTempAllowance.getTmpStatus() != TempStatus.SUBMITTED) {
                if (allowanceService.delete(selectedTempAllowance)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "Row is deleted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    currentTempAllowance = new Allowance();
                    currentAllowancePosition = new AllowancePosition();
                    currentAllowanceLocation = new AllowanceLocation();
                    currentAllowanceEmploymentCenter = new AllowanceEmploymentCenter();
                    allowanceTypeQuantitative = new AllowanceTypeQuantitative();
                    tempAllowancesList.remove(selectedTempAllowance);
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

    public void btnEditSelectedAllowance_Handler() {
        Allowance allowanceTmp = new Allowance();
        if (selectedAllowance != null) {
            if (!allowanceService.checkIfAllowanceHasPendingEdit(selectedAllowance.getID())) {
                allowanceTmp.setSourceId(selectedAllowance.getID());
                allowanceTmp.setAllowanceType(selectedAllowance.getAllowanceType());
                allowanceTmp.setValue(selectedAllowance.getValue());
                allowanceTmp.setValue(selectedAllowance.getValue());
                currentTempAllowance = allowanceTmp;
            } else {
                System.out.println("Allowance has pending changes");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit Allowance", "The selected allowance has pending changes. Please approve or cancel the changes and try again.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }
}
