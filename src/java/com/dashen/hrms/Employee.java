/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author mulugetak
 */
@Entity
@Table(name = "tbl_employee")
public class Employee implements Serializable {

    static DateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy");

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String employeeSerialID;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FILE_INDEX_NUMBER")
    private String fileIndexNumber;

    @Column(name = "EMPLOYEE_ID")
    private String employeeID;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "DATE_OF_EMPLOYMENT")
    private Date dateOfEmployement;

    @Column(name = "JOB_CLASSIFICATION_ID")
    private String jobClasificationID;

    @Column(name = "EMERGENCY_CONTACT")
    private String emergecyContact;

    @Column(name = "PHOTO_FILE_NAME")
    private String photoFileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private EmployeeStatus status;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_DATE")
    private Date lastModifiedDate;

    @Column(name = "APPROVED_BY")
    private String approvedBy;

    @Column(name = "APPROVED_DATE")
    private Date approvedDate;

    @Column(name = "DATE_PERMANENT")
    private Date datePermanent;

    @Column(name = "MARITAL_STATUS")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "MARITAL_STATUS_FILE_NAME")
    private String maritalStatusFileName;

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "qualificationOwner", cascade = CascadeType.REMOVE)
    private List<Qualification> qualifications;

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "experienceOwner", cascade = CascadeType.REMOVE)
    private List<Experience> experiences;

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "refereeOwner", cascade = CascadeType.REMOVE)
    private List<Referee> referees;

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "leaveRequestOwner", cascade = CascadeType.REMOVE)
    private List<LeaveRequest> leaveRequests;
    
     
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMaritalStatusFileName() {
        return maritalStatusFileName;
    }

    public void setMaritalStatusFileName(String maritalStatusFileName) {
        this.maritalStatusFileName = maritalStatusFileName;
    }

    public Date getDatePermanent() {
        return datePermanent;
    }

    public void setDatePermanent(Date datePermanent) {
        this.datePermanent = datePermanent;
    }
    public String getEmployeeSerialID() {
        return employeeSerialID;
    }

    public void setEmployeeSerialID(String employeeSerialID) {
        this.employeeSerialID = employeeSerialID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getFileIndexNumber() {
        return fileIndexNumber;
    }

    public void setFileIndexNumber(String fileIndexNumber) {
        this.fileIndexNumber = fileIndexNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getJobClasificationID() {
        return jobClasificationID;
    }

    public void setJobClasificationID(String jobClasificationID) {
        this.jobClasificationID = jobClasificationID;
    }

    public String getEmergecyContact() {
        return emergecyContact;
    }

    public void setEmergecyContact(String emergecyContact) {
        this.emergecyContact = emergecyContact;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public static DateFormat getDateFormater() {
        return dateFormater;
    }

    public static void setDateFormater(DateFormat dateFormater) {
        Employee.dateFormater = dateFormater;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfEmployement() {
        return dateOfEmployement;
    }

    public void setDateOfEmployement(Date dateOfEmployement) {
        this.dateOfEmployement = dateOfEmployement;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<Qualification> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Referee> getReferees() {
        return referees;
    }

    public void setReferees(List<Referee> referees) {
        this.referees = referees;
    }

    public Employee() {
        employeeSerialID = "";
        firstName = "";
        middleName = "";
        lastName = "";
        employeeID = "";
        fileIndexNumber = "";
        gender = Gender.MALE;
        dateOfBirth = new Date();
        dateOfEmployement = new Date();
        jobClasificationID = "";
        emergecyContact = "";
        photoFileName = "";
        status = EmployeeStatus.PROBATION;
        createdBy = "";
        createdDate = new Date();
        lastModifiedBy = "";
        lastModifiedDate = new Date();
        approvedBy = "";
        approvedDate = new Date();
        qualifications = new ArrayList<>();
        experiences = new ArrayList<>();

    }

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

    public List<LeaveRequest> getLeaveRequests() {
        return leaveRequests;
    }

    public void setLeaveRequests(List<LeaveRequest> leaveRequests) {
        this.leaveRequests = leaveRequests;
    }

}
