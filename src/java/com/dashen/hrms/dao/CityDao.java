/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.City;
import com.dashen.hrms.TempStatus;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author biniamt
 */

@Repository
@Transactional
public class CityDao {
    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(City j) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(j);
    }

    public void update(City j) {
        Session session = sessionFactory.getCurrentSession();
        session.update(j);
    }

    public List<City> listTmpCities() {
        List<City> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from City where TMP_STATUS = '" + TempStatus.SUBMITTED + "'");
        list = qr.list();
        return list;
    }

    public City getByCityID(String id) {
        Session session = sessionFactory.getCurrentSession();
        City city = (City) session.get(City.class, id);
        return city;
    }

    public List<City> getByMakerID(String id) {
        List<City> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp Cities of made my a single user
        Query qr = session.createQuery("from City where CREATED_BY = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public void delete(City cityTmp) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(cityTmp);
    }

    public int cityPendingEditCount(String id) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from City where id = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        return ((Long) qr.uniqueResult()).intValue();
    }

    public List<City> listTmpCitiesForMaker(String makerID) {
        List<City> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from City where CREATED_BY = '" + makerID + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public List<City> listCities() {
        List<City> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from City where TMP_STATUS = '" + TempStatus.APPROVED + "' order by name");
        list = qr.list();
        return list;
    }
    
    public City getByID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        City city = (City)session.get(City.class, ID);        
        return city;
    }

    public City getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from City c where c.name = '"+name+"' and c.tmpStatus='" + TempStatus.APPROVED + "'");
        List<City> l = qr.list();
        return l.get(0);
    }


    public boolean isCityExistsWithName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from City c where c.name = '" + name + "' AND c.tmpStatus='" + TempStatus.APPROVED + "'");
        return ((Long) qr.uniqueResult()).intValue() > 0;
    }
}

