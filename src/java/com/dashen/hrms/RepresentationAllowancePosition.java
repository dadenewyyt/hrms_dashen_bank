/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author biniamt
 */
public class RepresentationAllowancePosition {
    
    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String ID;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id", updatable = true)
    private RepresentationAllowance representationAllowance;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id", updatable = true)
    private Position position;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public RepresentationAllowance getRepresentationAllowance() {
        return representationAllowance;
    }

    public void setRepresentationAllowance(RepresentationAllowance representationAllowance) {
        this.representationAllowance = representationAllowance;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
    public RepresentationAllowancePosition() {
        ID = "";
        representationAllowance = new RepresentationAllowance();
        position = new Position();
    }
    
}
