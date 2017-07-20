/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.City;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.CityService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author biniamt
 */

@Component
@SpringViewScope
public class CitiesPendingApprovalBean {
    
    private List<City> pendingTmpCities;
    List<City> selectedCityTmps;

    @Autowired
    private CityService cityService;

    @PostConstruct
    public void init() {
        pendingTmpCities = cityService.listTmpCities();
    }

    public List<City> getPendingTmpCities() {
        return pendingTmpCities;
    }

    public void setPendingTmpCities(List<City> pendingTmpCities) {
        this.pendingTmpCities = pendingTmpCities;
    }

    public List<City> getSelectedCityTmps() {
        return selectedCityTmps;
    }

    public void setSelectedCityTmps(List<City> selectedCityTmps) {
        this.selectedCityTmps = selectedCityTmps;
    }

    public void approveSelectedRows() {
        if (selectedCityTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rows Selected", "Please select rows!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            //approve the selected rows
            for (City tmpCity : selectedCityTmps) {
                if (tmpCity.getTmpStatus() == TempStatus.SUBMITTED) {
                    if(cityService.isCityExistsWithName(tmpCity.getName())) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Failed", "City with name " + tmpCity.getName() + " already exist.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        if (tmpCity.getActionType() == ActionType.CREATE) {
                            cityService.saveNewCity(tmpCity);
                            pendingTmpCities.remove(tmpCity);
                        } else if (tmpCity.getActionType() == ActionType.UPDATE) //existing city was updated
                        {
                            cityService.updateCity(tmpCity);
                            pendingTmpCities.remove(tmpCity);
                        }
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "City pending items approved.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }

    public void makeEditableByMaker() {
        if (selectedCityTmps.size() < 1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No row selected", "Please select row!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            for (City tmpCity : selectedCityTmps) {
                TempStatus original = tmpCity.getTmpStatus();
                tmpCity.setTmpStatus(TempStatus.EDITABLE);
                if (cityService.updateTmpCity(tmpCity)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Status", "Selected row is made editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    tmpCity.setTmpStatus(original);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Change Status", "Unable to make the selected row editable.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }
}