/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author dawits
 */
@Entity
@Table(name = "TBL_VACANCY_TRANSFER")
public class VacancyTransfer implements Serializable {
    static DateFormat dateFormater = new SimpleDateFormat("dd-MMM-yyyy");
    @Id
    @GenericGenerator(name = "vacTraSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "vacTraSeqGenerator")
    @Column(name = "ID")
    private String id;
    @ManyToOne
    @JoinColumn(name = "VACANCY_ID", foreignKey = @ForeignKey(name = "TBL_VACANCY_TRANSFER_FK1"))
    private Vacancy vacancy;
    @Transient
    private Vacancy unapprovedVacancy;
    @Transient
    private boolean unapprovedVacancyArrow;
    @Transient
    private boolean unapprovedVacancyDisplay;
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_SERIAL_ID", foreignKey = @ForeignKey(name = "TBL_VACANCY_TRANSFER_FK2"))
    private Employee employee;
    @Transient
    private Employee unapprovedEmployee;
    @Transient
    private boolean unapprovedEmployeeArrow;
    @Transient
    private boolean unapprovedEmployeeDisplay;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATION_TIMESTAMP")
    private String creationTimeStamp;
    @Column(name = "EDITED_BY")
    private String editedBy;
    @Column(name = "EDITING_TIMESTAMP")
    private String editingTimeStamp;
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Column(name = "APPROVED_TIMESTAMP")
    private String approvedTimeStamp;
    @Column(name = "DELETED_BY")
    private String deletedBy;
    @Column(name = "DELETION_TIMESTAMP")
    private String deletionTimeStamp;
    @Column(name = "UNAPPROVED_CHANGE")
    private String unapprovedChange;
    @Column(name = "STATUS")
    private String status;

    public VacancyTransfer() {
        this.id = "";
        this.vacancy = new Vacancy();
        this.unapprovedVacancy = new Vacancy();
        this.unapprovedVacancyArrow = false;
        this.unapprovedVacancyDisplay = false;
        this.employee = new Employee();
        this.unapprovedEmployee = new Employee();
        this.unapprovedEmployeeArrow = false;
        this.unapprovedEmployeeDisplay = false;
        this.createdBy = "";
        this.creationTimeStamp = "";
        this.editedBy = "";
        this.editingTimeStamp = "";
        this.approvedBy = "";
        this.approvedTimeStamp = "";
        this.deletedBy = "";
        this.deletionTimeStamp = "";
        this.unapprovedChange = "";
        this.status = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public Vacancy getUnapprovedVacancy() {
        return unapprovedVacancy;
    }

    public void setUnapprovedVacancy(Vacancy unapprovedVacancy) {
        this.unapprovedVacancy = unapprovedVacancy;
    }

    public boolean isUnapprovedVacancyArrow() {
        return unapprovedVacancyArrow;
    }

    public void setUnapprovedVacancyArrow(boolean unapprovedVacancyArrow) {
        this.unapprovedVacancyArrow = unapprovedVacancyArrow;
    }

    public boolean isUnapprovedVacancyDisplay() {
        return unapprovedVacancyDisplay;
    }

    public void setUnapprovedVacancyDisplay(boolean unapprovedVacancyDisplay) {
        this.unapprovedVacancyDisplay = unapprovedVacancyDisplay;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getUnapprovedEmployee() {
        return unapprovedEmployee;
    }

    public void setUnapprovedEmployee(Employee unapprovedEmployee) {
        this.unapprovedEmployee = unapprovedEmployee;
    }

    public boolean isUnapprovedEmployeeArrow() {
        return unapprovedEmployeeArrow;
    }

    public void setUnapprovedEmployeeArrow(boolean unapprovedEmployeeArrow) {
        this.unapprovedEmployeeArrow = unapprovedEmployeeArrow;
    }

    public boolean isUnapprovedEmployeeDisplay() {
        return unapprovedEmployeeDisplay;
    }

    public void setUnapprovedEmployeeDisplay(boolean unapprovedEmployeeDisplay) {
        this.unapprovedEmployeeDisplay = unapprovedEmployeeDisplay;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(String creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public String getEditingTimeStamp() {
        return editingTimeStamp;
    }

    public void setEditingTimeStamp(String editingTimeStamp) {
        this.editingTimeStamp = editingTimeStamp;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedTimeStamp() {
        return approvedTimeStamp;
    }

    public void setApprovedTimeStamp(String approvedTimeStamp) {
        this.approvedTimeStamp = approvedTimeStamp;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getDeletionTimeStamp() {
        return deletionTimeStamp;
    }

    public void setDeletionTimeStamp(String deletionTimeStamp) {
        this.deletionTimeStamp = deletionTimeStamp;
    }

    public String getUnapprovedChange() {
        return unapprovedChange;
    }

    public void setUnapprovedChange(String unapprovedChange) {
        this.unapprovedChange = unapprovedChange;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}