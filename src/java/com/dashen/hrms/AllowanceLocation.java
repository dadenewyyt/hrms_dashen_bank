/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author biniamt
 */

@Entity
@Table(name = "tbl_allowance_location")
public class AllowanceLocation {
    
    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String ID;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "allowance_id", updatable = true)
    private Allowance allowance;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", updatable = true)
    private City city;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Allowance getAllowance() {
        return allowance;
    }

    public void setAllowance(Allowance allowance) {
        this.allowance = allowance;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
    
    public AllowanceLocation () {
        ID = "";
        allowance = new Allowance();
        city = new City();
    }
    
}

