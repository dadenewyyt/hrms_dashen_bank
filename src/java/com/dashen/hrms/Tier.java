/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author mulugetak
 */
@Entity
@Table(name = "tbl_tier")
public class Tier implements Serializable{

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String id;
    
    @Column(name = "ORG_STRUCTURE_TYPE_ID")
    private String organizationalStructureTypeID;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "SOURCE_ID")
    private String sourceID;
    
    @Column(name = "MAKER_ID")
    private String makerID;

    @Column(name = "MAKER_DATE")
    private Date makerDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTION_TYPE")
    private ActionType actionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROW_STATUS")
    private RowStatus rowStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationalStructureTypeID() {
        return organizationalStructureTypeID;
    }

    public void setOrganizationalStructureTypeID(String organizationalStructureTypeID) {
        this.organizationalStructureTypeID = organizationalStructureTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getMakerID() {
        return makerID;
    }

    public void setMakerID(String makerID) {
        this.makerID = makerID;
    }

    public Date getMakerDate() {
        return makerDate;
    }

    public void setMakerDate(Date makerDate) {
        this.makerDate = makerDate;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public RowStatus getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(RowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }
    
    
}
