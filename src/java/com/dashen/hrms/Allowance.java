/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author biniamt
 */

@Entity
@Table(name = "tbl_allowance")
public class Allowance {

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String ID;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "allowance_type_id", updatable = true)
    private AllowanceType allowanceType;

    @Column(name = "value")
    private Float value;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved_date")
    private Date approvedDate;

    @Column(name = "source_id")
    private String sourceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type")
    private ActionType actionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "tmp_status")
    private TempStatus tmpStatus;
    

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

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public TempStatus getTmpStatus() {
        return tmpStatus;
    }

    public void setTmpStatus(TempStatus tmpStatus) {
        this.tmpStatus = tmpStatus;
    }

    public Allowance() {
        ID = "";
        allowanceType = new AllowanceType();
        createdBy = "";
        createdDate = new Date();
        approvedBy = "";
        approvedDate = null;
        sourceId = "";
        actionType = ActionType.CREATE;
        tmpStatus = TempStatus.EDITABLE;
    }
}

