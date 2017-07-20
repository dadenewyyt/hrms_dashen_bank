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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 *
 * @author biniamt
 */


@Entity
@Table(name = "tbl_allowance_type")
public class AllowanceType {

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String ID;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private AllowancePaymentOptions paymentMethod;
    
    @Type(type = "true_false")
    @Column(name = "depends_on_position")
    private boolean dependsOnPosition;
    
    @Type(type = "true_false")
    @Column(name = "depends_on_location")
    private boolean dependsOnLocation;
        
    @Type(type = "true_false")
    @Column(name = "depends_on_employment_center")
    private boolean dependsOnEmploymentCenter;
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AllowancePaymentOptions getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(AllowancePaymentOptions paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isDependsOnPosition() {
        return dependsOnPosition;
    }

    public void setDependsOnPosition(boolean dependsOnPosition) {
        this.dependsOnPosition = dependsOnPosition;
    }

    public boolean isDependsOnLocation() {
        return dependsOnLocation;
    }

    public void setDependsOnLocation(boolean dependsOnLocation) {
        this.dependsOnLocation = dependsOnLocation;
    }

    public boolean isDependsOnEmploymentCenter() {
        return dependsOnEmploymentCenter;
    }

    public void setDependsOnEmploymentCenter(boolean dependsOnEmploymentCenter) {
        this.dependsOnEmploymentCenter = dependsOnEmploymentCenter;
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

    public AllowanceType() {
        ID = "";
        name = "";
        dependsOnPosition = false;
        dependsOnLocation = false;
        dependsOnEmploymentCenter = false;
        createdBy = "";
        createdDate = new Date();
        approvedBy = "";
        approvedDate = null;
        sourceId = "";
        actionType = ActionType.CREATE;
        tmpStatus = TempStatus.EDITABLE;
    }
}
