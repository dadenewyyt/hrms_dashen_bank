/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@Table(name = "TBL_INTERNAL_VACANC_APPLICANTS")
public class InternalVacancApplicants implements Serializable {
    static DateFormat dateFormater = new SimpleDateFormat("dd-MMM-yyyy");
    @Id
    @GenericGenerator(name = "intVacAppSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "intVacAppSeqGenerator")
    @Column(name = "ID")
    private String id;
    @ManyToOne
    @JoinColumn(name = "VACANCY_ID", foreignKey = @ForeignKey(name = "TBL_INTERNAL_VACANC_APPLI_FK1"))
    private Vacancy vacancy;
    @Transient
    private Vacancy unapprovedVacancy;
    @Transient
    private boolean unapprovedVacancyArrow;
    @Transient
    private boolean unapprovedVacancyDisplay;
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_SERIAL_ID", foreignKey = @ForeignKey(name = "TBL_INTERNAL_VACANC_APPLI_FK2"))
    private Employee employee;
    @Transient
    private Employee unapprovedEmployee;
    @Transient
    private boolean unapprovedEmployeeArrow;
    @Transient
    private boolean unapprovedEmployeeDisplay;
    @Column(name = "APPLICATION_DATE")
    private String applicationDate;
    @Transient
    private Date applicationDateInDate;
    @Transient
    private String unapprovedApplicationDate;
    @Transient
    private boolean unapprovedApplicationDateArrow;
    @Transient
    private boolean unapprovedApplicationDateDisplay;
    @Column(name = "APPLICANT_STATUS")
    private String applicationStatus;
    @Transient
    private String unapprovedApplicationStatus;
    @Transient
    private boolean unapprovedApplicationStatusArrow;
    @Transient
    private boolean unapprovedApplicationStatusDisplay;
    @Column(name = "OFFER_STATUS")
    private String offerStatus;
    @Transient
    private String unapprovedOfferStatus;
    @Transient
    private boolean unapprovedOfferStatusArrow;
    @Transient
    private boolean unapprovedOfferStatusDisplay;
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

    public InternalVacancApplicants() {
        this.id = "";
        this.vacancy = new Vacancy();
        this.unapprovedVacancy = new Vacancy();
        this.unapprovedVacancyArrow = false;
        this.unapprovedVacancyDisplay = false;
        this.employee = new Employee();
        this.unapprovedEmployee = new Employee();
        this.unapprovedEmployeeArrow = false;
        this.unapprovedEmployeeDisplay = false;
        this.applicationDate = "";
        this.applicationDateInDate = new Date();
        this.unapprovedApplicationDate = "";
        this.unapprovedApplicationDateArrow = false;
        this.unapprovedApplicationDateDisplay = false;
        this.applicationStatus = "";
        this.unapprovedApplicationStatus = "";
        this.unapprovedApplicationStatusArrow = false;
        this.unapprovedApplicationStatusDisplay = false;
        this.offerStatus = "";
        this.unapprovedOfferStatus = "";
        this.unapprovedOfferStatusArrow = false;
        this.unapprovedOfferStatusDisplay = false;
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

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getApplicationDateInDate() {
        return applicationDateInDate;
    }

    public void setApplicationDateInDate(Date applicationDateInDate) {
        this.applicationDateInDate = applicationDateInDate;
        this.applicationDate = dateFormater.format(applicationDateInDate);
    }

    public String getUnapprovedApplicationDate() {
        return unapprovedApplicationDate;
    }

    public void setUnapprovedApplicationDate(String unapprovedApplicationDate) {
        this.unapprovedApplicationDate = unapprovedApplicationDate;
    }

    public boolean isUnapprovedApplicationDateArrow() {
        return unapprovedApplicationDateArrow;
    }

    public void setUnapprovedApplicationDateArrow(boolean unapprovedApplicationDateArrow) {
        this.unapprovedApplicationDateArrow = unapprovedApplicationDateArrow;
    }

    public boolean isUnapprovedApplicationDateDisplay() {
        return unapprovedApplicationDateDisplay;
    }

    public void setUnapprovedApplicationDateDisplay(boolean unapprovedApplicationDateDisplay) {
        this.unapprovedApplicationDateDisplay = unapprovedApplicationDateDisplay;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getUnapprovedApplicationStatus() {
        return unapprovedApplicationStatus;
    }

    public void setUnapprovedApplicationStatus(String unapprovedApplicationStatus) {
        this.unapprovedApplicationStatus = unapprovedApplicationStatus;
    }

    public boolean isUnapprovedApplicationStatusArrow() {
        return unapprovedApplicationStatusArrow;
    }

    public void setUnapprovedApplicationStatusArrow(boolean unapprovedApplicationStatusArrow) {
        this.unapprovedApplicationStatusArrow = unapprovedApplicationStatusArrow;
    }

    public boolean isUnapprovedApplicationStatusDisplay() {
        return unapprovedApplicationStatusDisplay;
    }

    public void setUnapprovedApplicationStatusDisplay(boolean unapprovedApplicationStatusDisplay) {
        this.unapprovedApplicationStatusDisplay = unapprovedApplicationStatusDisplay;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getUnapprovedOfferStatus() {
        return unapprovedOfferStatus;
    }

    public void setUnapprovedOfferStatus(String unapprovedOfferStatus) {
        this.unapprovedOfferStatus = unapprovedOfferStatus;
    }

    public boolean isUnapprovedOfferStatusArrow() {
        return unapprovedOfferStatusArrow;
    }

    public void setUnapprovedOfferStatusArrow(boolean unapprovedOfferStatusArrow) {
        this.unapprovedOfferStatusArrow = unapprovedOfferStatusArrow;
    }

    public boolean isUnapprovedOfferStatusDisplay() {
        return unapprovedOfferStatusDisplay;
    }

    public void setUnapprovedOfferStatusDisplay(boolean unapprovedOfferStatusDisplay) {
        this.unapprovedOfferStatusDisplay = unapprovedOfferStatusDisplay;
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