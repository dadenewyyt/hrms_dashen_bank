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
@Table(name = "tbl_allowance_type_quantitativ")
public class AllowanceTypeQuantitative {
    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String ID;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "allowance_type_id", updatable = true)
    private AllowanceType allowanceType;
    
    @Column(name = "unit_price")
    private Float unitPrice;
    
    @Column(name = "measurement_unit")
    private String measurementUnit;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public AllowanceType getAllowanceType() {
        return allowanceType;
    }

    public void setAllowanceType(AllowanceType allowanceType) {
        this.allowanceType = allowanceType;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
    
    public AllowanceTypeQuantitative() {
        ID = "";
        allowanceType = new AllowanceType();
        measurementUnit = "";
    }
}
