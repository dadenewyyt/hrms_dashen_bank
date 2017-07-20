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

/**
 *
 * @author biniamt
 */

@Entity
@Table(name = "tbl_salary_scale")
public class SalaryScale implements Serializable {

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String ID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GRADE_ID", updatable = true)
    private JobGrade grade;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LEVEL_ID", updatable = true)
    private JobLevel level;
    
    @Column (name = "step_increment")
    private Float stepIncrement;
    
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved_date")
    private Date approvedDate;

    @Column(name = "salary_scale_id")
    private String salaryScaleId;

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

    public JobGrade getGrade() {
        return grade;
    }

    public void setGrade(JobGrade grade) {
        this.grade = grade;
    }

    public JobLevel getLevel() {
        return level;
    }

    public void setLevel(JobLevel level) {
        this.level = level;
    }
    
    public Float getStepIncrement() {
        return stepIncrement;
    }
    
    public void setStepIncrement(Float stepIncrement) {
        this.stepIncrement = stepIncrement;
    }

     public Float getSalaryAfterStepIncrement() {
       return grade.getBaseSalary() + stepIncrement;
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

   public String getLastModifiedBy() {
       return lastModifiedBy;
   }

   public void setLastModifiedBy(String lastModifiedBy) {
       this.lastModifiedBy = lastModifiedBy;
   }

   public Date getLastModifiedDate() {
       return lastModifiedDate;
   }

   public void setLastModifiedDate(Date lastModifiedDate) {
       this.lastModifiedDate = lastModifiedDate;
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

    public String getSalaryScaleId() {
        return salaryScaleId;
    }

    public void setSalaryScaleId(String salaryScaleId) {
        this.salaryScaleId = salaryScaleId;
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

    public SalaryScale() {
        ID = "";
        grade = new JobGrade();
        level = new JobLevel();
        createdBy = "";
        createdDate = new Date();
        lastModifiedBy = "";
        lastModifiedDate = new Date();
        approvedBy = "";
        approvedDate = new Date();
        salaryScaleId = "";
        actionType = ActionType.CREATE;
        tmpStatus = TempStatus.EDITABLE;
    }
}
