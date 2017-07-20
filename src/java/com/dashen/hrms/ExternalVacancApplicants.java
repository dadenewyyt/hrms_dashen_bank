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
@Table(name = "TBL_EXTERNAL_VACANC_APPLICANTS")
public class ExternalVacancApplicants implements Serializable {
    static DateFormat dateFormater = new SimpleDateFormat("dd-MMM-yyyy");
    @Id
    @GenericGenerator(name = "extVacAppSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "extVacAppSeqGenerator")
    @Column(name = "ID")
    private String id;
    @ManyToOne
    @JoinColumn(name = "VACANCY_ID", foreignKey = @ForeignKey(name = "TBL_EXTERNAL_VACANC_APPLI_FK1"))
    private Vacancy vacancy;
    @Transient
    private Vacancy unapprovedVacancy;
    @Transient
    private boolean unapprovedVacancyArrow;
    @Transient
    private boolean unapprovedVacancyDisplay;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Transient
    private String unapprovedFirstName;
    @Transient
    private boolean unapprovedFirstNameArrow;
    @Transient
    private boolean unapprovedFirstNameDisplay;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Transient
    private String unapprovedMiddleName;
    @Transient
    private boolean unapprovedMiddleNameArrow;
    @Transient
    private boolean unapprovedMiddleNameDisplay;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Transient
    private String unapprovedLastName;
    @Transient
    private boolean unapprovedLastNameArrow;
    @Transient
    private boolean unapprovedLastNameDisplay;
    @Column(name = "GENDER")
    private String gender;
    @Transient
    private String unapprovedGender;
    @Transient
    private boolean unapprovedGenderArrow;
    @Transient
    private boolean unapprovedGenderDisplay;
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
    private String unapprovedApplciationStatus;
    @Transient
    private boolean unapprovedApplicationStatusArrow;
    @Transient
    private boolean unapprovedApplicationStatusDisplay;
    @Column(name = "OFFER_STATUS")
    private String offerStatus;
    @Transient
    private String unapprovedOfferStaus;
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

    public ExternalVacancApplicants() {
        this.id = "";
        this.vacancy = new Vacancy();
        this.unapprovedVacancy = new Vacancy();
        this.unapprovedVacancyArrow = false;
        this.unapprovedVacancyDisplay = false;
        this.firstName = "";
        this.unapprovedFirstName = "";
        this.unapprovedFirstNameArrow = false;
        this.unapprovedFirstNameDisplay = false;
        this.middleName = "";
        this.unapprovedMiddleName = "";
        this.unapprovedMiddleNameArrow = false;
        this.unapprovedMiddleNameDisplay = false;
        this.lastName = "";
        this.unapprovedLastName = "";
        this.unapprovedLastNameArrow = false;
        this.unapprovedLastNameDisplay = false;
        this.gender = "";
        this.unapprovedGender = "";
        this.unapprovedGenderArrow = false;
        this.unapprovedGenderDisplay = false;
        this.applicationDate = "";
        this.applicationDateInDate = new Date();
        this.unapprovedApplicationDate = "";
        this.unapprovedApplicationDateArrow = false;
        this.unapprovedApplicationDateDisplay = false;
        this.applicationStatus = "";
        this.unapprovedApplciationStatus = "";
        this.unapprovedApplicationStatusArrow = false;
        this.unapprovedApplicationStatusDisplay = false;
        this.offerStatus = "";
        this.unapprovedOfferStaus = "";
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUnapprovedFirstName() {
        return unapprovedFirstName;
    }

    public void setUnapprovedFirstName(String unapprovedFirstName) {
        this.unapprovedFirstName = unapprovedFirstName;
    }

    public boolean isUnapprovedFirstNameArrow() {
        return unapprovedFirstNameArrow;
    }

    public void setUnapprovedFirstNameArrow(boolean unapprovedFirstNameArrow) {
        this.unapprovedFirstNameArrow = unapprovedFirstNameArrow;
    }

    public boolean isUnapprovedFirstNameDisplay() {
        return unapprovedFirstNameDisplay;
    }

    public void setUnapprovedFirstNameDisplay(boolean unapprovedFirstNameDisplay) {
        this.unapprovedFirstNameDisplay = unapprovedFirstNameDisplay;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getUnapprovedMiddleName() {
        return unapprovedMiddleName;
    }

    public void setUnapprovedMiddleName(String unapprovedMiddleName) {
        this.unapprovedMiddleName = unapprovedMiddleName;
    }

    public boolean isUnapprovedMiddleNameArrow() {
        return unapprovedMiddleNameArrow;
    }

    public void setUnapprovedMiddleNameArrow(boolean unapprovedMiddleNameArrow) {
        this.unapprovedMiddleNameArrow = unapprovedMiddleNameArrow;
    }

    public boolean isUnapprovedMiddleNameDisplay() {
        return unapprovedMiddleNameDisplay;
    }

    public void setUnapprovedMiddleNameDisplay(boolean unapprovedMiddleNameDisplay) {
        this.unapprovedMiddleNameDisplay = unapprovedMiddleNameDisplay;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUnapprovedLastName() {
        return unapprovedLastName;
    }

    public void setUnapprovedLastName(String unapprovedLastName) {
        this.unapprovedLastName = unapprovedLastName;
    }

    public boolean isUnapprovedLastNameArrow() {
        return unapprovedLastNameArrow;
    }

    public void setUnapprovedLastNameArrow(boolean unapprovedLastNameArrow) {
        this.unapprovedLastNameArrow = unapprovedLastNameArrow;
    }

    public boolean isUnapprovedLastNameDisplay() {
        return unapprovedLastNameDisplay;
    }

    public void setUnapprovedLastNameDisplay(boolean unapprovedLastNameDisplay) {
        this.unapprovedLastNameDisplay = unapprovedLastNameDisplay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUnapprovedGender() {
        return unapprovedGender;
    }

    public void setUnapprovedGender(String unapprovedGender) {
        this.unapprovedGender = unapprovedGender;
    }

    public boolean isUnapprovedGenderArrow() {
        return unapprovedGenderArrow;
    }

    public void setUnapprovedGenderArrow(boolean unapprovedGenderArrow) {
        this.unapprovedGenderArrow = unapprovedGenderArrow;
    }

    public boolean isUnapprovedGenderDisplay() {
        return unapprovedGenderDisplay;
    }

    public void setUnapprovedGenderDisplay(boolean unapprovedGenderDisplay) {
        this.unapprovedGenderDisplay = unapprovedGenderDisplay;
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

    public String getUnapprovedApplciationStatus() {
        return unapprovedApplciationStatus;
    }

    public void setUnapprovedApplciationStatus(String unapprovedApplciationStatus) {
        this.unapprovedApplciationStatus = unapprovedApplciationStatus;
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

    public String getUnapprovedOfferStaus() {
        return unapprovedOfferStaus;
    }

    public void setUnapprovedOfferStaus(String unapprovedOfferStaus) {
        this.unapprovedOfferStaus = unapprovedOfferStaus;
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