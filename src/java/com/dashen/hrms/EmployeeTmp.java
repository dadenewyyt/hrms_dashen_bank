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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 *
 * @author MulugetaK
 */
@Entity
@Table(name = "tbl_employee_tmp")
public class EmployeeTmp implements Serializable {

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String serialID;

    @Column(name = "EMPLOYEE_SERIAL_ID")
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

    @Column(name = "MAKER_ID")
    private String makerID;

    @Column(name = "MAKER_DATE")
    private Date makerDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTION_TYPE")
    private ActionType actionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "TMP_STATUS")
    private TempStatus tmpStatus;

    @Type(type = "true_false")
    @Column(name = "TMP_PHOTO_UPLOADED")
    private boolean tmpPhotoUploaded;

    @Column(name = "TMP_PHOTO_FILE_NAME")
    private String tmpPhotoFileName;

    @Column(name = "CHECKER_ID")
    private String checkerID;

    @Column(name = "CHECKER_DATE")
    private Date checkerDate;

    @Column(name = "DATE_PERMANENT")
    private Date datePermanent;

    @Column(name = "MARITAL_STATUS")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "MARITAL_STATUS_FILE_NAME")
    private String maritalStatusFileName;

    @Column(name = "TMP_MARITAL_STATUS_FILE_NAME")
    private String tmpMaritalStatusFileName;

    @Type(type = "true_false")
    @Column(name = "TMP_MARITAL_STATUS_UPLOADED")
    private boolean tmpMaritalStatusUploaded;

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

    public String getTmpMaritalStatusFileName() {
        return tmpMaritalStatusFileName;
    }

    public void setTmpMaritalStatusFileName(String tmpMaritalStatusFileName) {
        this.tmpMaritalStatusFileName = tmpMaritalStatusFileName;
    }

    public boolean isTmpMaritalStatusUploaded() {
        return tmpMaritalStatusUploaded;
    }

    public void setTmpMaritalStatusUploaded(boolean tmpMaritalStatusUploaded) {
        this.tmpMaritalStatusUploaded = tmpMaritalStatusUploaded;
    }

    public String getSerialID() {
        return serialID;
    }

    public void setSerialID(String serialID) {
        this.serialID = serialID;
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

    public String getFileIndexNumber() {
        return fileIndexNumber;
    }

    public void setFileIndexNumber(String fileIndexNumber) {
        this.fileIndexNumber = fileIndexNumber;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public String getMakerID() {
        return makerID;
    }

    public void setMakerID(String makerID) {
        this.makerID = makerID;
    }

    public Date getMakerDate() {
        return makerDate;
    }

    public void setMakerDate(Date makerDate) {
        this.makerDate = makerDate;
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

    public boolean isTmpPhotoUploaded() {
        return tmpPhotoUploaded;
    }

    public void setTmpPhotoUploaded(boolean tmpPhotoUploaded) {
        this.tmpPhotoUploaded = tmpPhotoUploaded;
    }

    public String getTmpPhotoFileName() {
        return tmpPhotoFileName;
    }

    public void setTmpPhotoFileName(String tmpPhotoFileName) {
        this.tmpPhotoFileName = tmpPhotoFileName;
    }

    public String getCheckerID() {
        return checkerID;
    }

    public void setCheckerID(String checkerID) {
        this.checkerID = checkerID;
    }

    public Date getCheckerDate() {
        return checkerDate;
    }

    public void setCheckerDate(Date checkerDate) {
        this.checkerDate = checkerDate;
    }

    public Date getDatePermanent() {
        return datePermanent;
    }

    public void setDatePermanent(Date datePermanent) {
        this.datePermanent = datePermanent;
    }

    public EmployeeTmp() {
        serialID = "";
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
        makerID = "";
        makerDate = new Date();
        actionType = ActionType.CREATE;
        tmpStatus = TempStatus.EDITABLE;
        tmpPhotoUploaded = false;
        tmpPhotoFileName = "";
        checkerID = "";
        checkerDate = new Date();
    }

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

}
