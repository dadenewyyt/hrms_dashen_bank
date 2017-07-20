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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 *
 * @author biniamt
 */

@Entity
@Table(name = "tbl_employee_position")
public class EmployeePosition implements Serializable {

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String ID;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_serial_id", updatable = true)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id", updatable = true)
    private Position position;
    
    @Column(name = "difference")
    private String difference;

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getDifference() {
        return difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
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

    public EmployeePosition() {
        ID = "";
        employee = new Employee();
        position = new Position();
        createdBy = "";
        createdDate = new Date();
        approvedBy = "";
        approvedDate = new Date();
        sourceId = "";
        actionType = ActionType.CREATE;
        tmpStatus = TempStatus.EDITABLE;
    }
}
