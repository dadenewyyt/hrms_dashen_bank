/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.City;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.CityDao;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author biniamt
 */

@Service
public class CityService {
    
    @Autowired
    CityDao cityDao;


    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }
    
    @Transactional
    public boolean saveNewCity(City cityTmp) {
        //new City
        City newCity = new City();
        copyValuesFromTemp(newCity, cityTmp);
        newCity.setCreatedBy(cityTmp.getCreatedBy());
        newCity.setCreatedDate(cityTmp.getCreatedDate());
        newCity.setTmpStatus(TempStatus.APPROVED);

        newCity.setApprovedBy(CurrentUser.getCurrentUser().getUsername());

        cityDao.saveNew(newCity);
        cityTmp.setTmpStatus(TempStatus.HISTORY);
        cityDao.update(cityTmp);
        return true;
    }

    @Transactional
    public boolean updateCity(City cityTmp) {
        City city = cityDao.getByCityID(cityTmp.getSourceId());
        copyValuesFromTemp(city, cityTmp);
        cityDao.update(city);
        cityTmp.setTmpStatus(TempStatus.HISTORY);
        cityDao.update(cityTmp);
        return true;
    }

    @Transactional
    public List<City> listTmpCities() {
        return cityDao.listTmpCities();
    }
    
    public void copyValuesFromTemp(City destCity, City sourceCity)
    {
        destCity.setID(sourceCity.getSourceId());
        destCity.setName(sourceCity.getName());
    }
      
    @Transactional
    public boolean saveNewTmpCity(City newTmpCity, ActionType acType) {
        newTmpCity.setActionType(acType);
        newTmpCity.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
        newTmpCity.setCreatedDate(new Date());
        newTmpCity.setTmpStatus(TempStatus.EDITABLE);
        cityDao.saveNew(newTmpCity);
        return true;
    }
    @Transactional
    public boolean updateTmpCity(City tmpCity) {
        cityDao.update(tmpCity);
        return true;
    }
    
    @Transactional
    public boolean delete(City cityTmp) {
        cityDao.delete(cityTmp);
        return true;
    }
    
    public boolean checkIfCityHasPendingEdit(String id) {
        return (cityDao.cityPendingEditCount(id) > 0);
    }
    
    @Transactional
    public List<City> listTmpCitiesForMaker(String makerID) {
        return cityDao.listTmpCitiesForMaker(makerID);
    }
    
    @Transactional
    public List<City> listCities() {
        return cityDao.listCities();
    }
    
    @Transactional
    public City getByID(String ID) {
        return cityDao.getByID(ID);
    }

    @Transactional
    public boolean isCityExistsWithName(String name) {
        return cityDao.isCityExistsWithName(name);
    }
    
}
