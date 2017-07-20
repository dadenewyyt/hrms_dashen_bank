/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**
 *
 * @author mulugetak
 */
@Entity
@Table(name = "TBL_ORGANIZATIONAL_STRUCTURE")
public class OrganizationalStructure implements Serializable {

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String id;

    @Column(name = "PARENT_ID")
    private String parentID;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ORG_STRUCTURE_TYPE_ID")
    private String organizationalStructureTypeID;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "CITY_ID", updatable=true)
    private City city;

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

    @Fetch(FetchMode.SELECT)
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "PARENT_ID", insertable = false, updatable = false, nullable = true)
    private OrganizationalStructure parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @Where(clause = "row_status='ACTIVE'")
    private List<OrganizationalStructure> children;

    public List<OrganizationalStructure> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationalStructure> children) {
        this.children = children;
    }

    public OrganizationalStructure getParent() {
        return parent;
    }

    public void setParent(OrganizationalStructure parent) {
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationalStructureTypeID() {
        return organizationalStructureTypeID;
    }

    public void setOrganizationalStructureTypeID(String organizationalStructureTypeID) {
        this.organizationalStructureTypeID = organizationalStructureTypeID;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
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

    public OrganizationalStructure() {
        id = "";
        parentID = "";
        name = "";
        organizationalStructureTypeID = "";
        city = new City();
        sourceID = "";
        makerID = "";
        makerDate = new Date();
        actionType = ActionType.CREATE;
        rowStatus = RowStatus.ACTIVE;
    }
}
