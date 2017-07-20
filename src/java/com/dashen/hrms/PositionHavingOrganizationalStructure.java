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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author biniamt
 */

@Entity
@Table(name="TBL_POSITION_HAVING_ORG_STRUCT")
public class PositionHavingOrganizationalStructure {
    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String ID;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORG_STRUCTURE_ID", updatable=true)
    private OrganizationalStructure organizationalStructure;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POSITION_ID", updatable=true)
    private Position position;

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        this.ID = id;
    }

    public OrganizationalStructure getOrganizationalStructure() {
        return organizationalStructure;
    }

    public void setOrganizationalStructure(OrganizationalStructure organizationalStructure) {
        this.organizationalStructure = organizationalStructure;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
    public PositionHavingOrganizationalStructure() {
        ID = "";
        organizationalStructure = new OrganizationalStructure();
        position = new Position();
    }
}
