/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.City;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.CityService;
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
 * @author biniamt
 */

@Component
@SpringViewScope
public class CityBean {
    private City currentTempCity;

    @Autowired
    private CityService cityService;

    private List<City> citiesList;
    private City selectedCity;

    private List<City> tempCitiesList;
    private City selectedTempCity;

    public City getCurrentTempCity() {
        return currentTempCity;
    }


    public void setCurrentTempCity(City currentTempCity) {
        this.currentTempCity = currentTempCity;
    }

    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    public List<City> getCitiesList() {
        return citiesList;
    }

    public void setCitiesList(List<City> citiesList) {
        this.citiesList = citiesList;
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

    public List<City> getTempCitiesList() {
        return tempCitiesList;
    }

    public void setTempCitiesList(List<City> tempCitiesList) {
        this.tempCitiesList = tempCitiesList;
    }

    public City getSelectedTempCity() {
        return selectedTempCity;
    }

    public void setSelectedTempCity(City selectedTempCity) {
        this.selectedTempCity = selectedTempCity;
    }

    @PostConstruct
    public void init() {

        currentTempCity = new City();

        citiesList = cityService.listCities();
        tempCitiesList = new ArrayList<>();
    }

    public void btnNewCity_Handler() {
        currentTempCity = new City();
    }

    public boolean isRowSubmitted() {
        if (currentTempCity != null && currentTempCity.getTmpStatus() == TempStatus.EDITABLE) {
            return false;
        }
        return true;
    }
    public void btnRefreshList_Handler() {
        MyUser user = CurrentUser.getCurrentUser();
        tempCitiesList = cityService.listTmpCitiesForMaker(user.getUsername());
    }

    public void tmpCitiesDataTableDataTable_rowSelected(SelectEvent evt) {
        currentTempCity = selectedTempCity;
    }

    public void btnSaveCity_Handler() {
        if (currentTempCity.getTmpStatus() == TempStatus.EDITABLE) {
            if (cityService.isCityExistsWithName(currentTempCity.getName())) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Failed", "City with your given name already exists.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                if (currentTempCity.getID() != null && !currentTempCity.getID().isEmpty()) {
                    //is updating existing Temp City record
                    if (cityService.updateTmpCity(currentTempCity)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save city update", "City updates saved.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save city update", "Update Failed.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                } else if (currentTempCity.getSourceId() != null && !currentTempCity.getSourceId().isEmpty()) {//editing existing city, tmp object is new
                    if (cityService.saveNewTmpCity(currentTempCity, ActionType.UPDATE)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save city update", "City updates saved.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save city update", "Update Failed.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                } else//new City, and new tmp object
                {
                    if (cityService.saveNewTmpCity(currentTempCity, ActionType.CREATE)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New city", "New city added.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New city", "Save Failed.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }
                currentTempCity = new City();
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update city", "The selected row is already submitted.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnSubmit_Handler() {
        if (selectedTempCity != null) {
            if (selectedTempCity.getTmpStatus() != TempStatus.SUBMITTED) {
                selectedTempCity.setTmpStatus(TempStatus.SUBMITTED);
                if (cityService.updateTmpCity(selectedTempCity)) {
                    //update successful   
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "Row submitted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    //update has failed, undo the change
                    selectedTempCity.setTmpStatus(TempStatus.EDITABLE);
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
        if (selectedTempCity != null) {
            if (selectedTempCity.getTmpStatus() != TempStatus.SUBMITTED) {
                if (cityService.delete(selectedTempCity)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "Row is deleted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    currentTempCity = new City();
                    tempCitiesList.remove(selectedTempCity);
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

    public void btnEditSelectedCity_Handler() {
        City cityTmp = new City();
        if (selectedCity != null) {
            if (!cityService.checkIfCityHasPendingEdit(selectedCity.getID())) {
                cityTmp.setSourceId(selectedCity.getID());
                cityTmp.setName(selectedCity.getName());
                currentTempCity = cityTmp;
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit city", "The selected city has pending changes. Please approve or cancel the changes and try again.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }
}
