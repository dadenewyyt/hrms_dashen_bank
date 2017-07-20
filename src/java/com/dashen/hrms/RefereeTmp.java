/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 *
 * @author mulugetak
 */
@Entity
@Table(name = "TBL_REFEREE_TMP")
public class RefereeTmp {

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String id;

    @Column(name = "REFEREE_ID")
    private String refereeID;

    @Column(name = "EMPLOYEE_SERIAL_ID")
    private String employeeSerialID;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "SALARY")
    private double salary;

    @Column(name = "KEBELE_ID")
    private String kebeleId;

    @Column(name = "RESIDENTIAL_ADDRESS")
    private String residentialAddress;

    @Column(name = "EMPLOYER")
    private String employer;

    @Column(name = "EMPLOYER_ADDRESS")
    private String employerAddress;

    @Column(name = "FILE_NAME")
    private String fileName;

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
    @Column(name = "TMP_DOCUMENT_UPLOADED")
    private boolean tmpDocumentUploaded;

    @Column(name = "TMP_DOCUMENT_FILE_NAME")
    private String tmpDocumentFileName;

    @Column(name = "CHECKER_ID")
    private String checkerID;

    @Column(name = "CHECKER_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date checkerDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefereeID() {
        return refereeID;
    }

    public void setRefereeID(String refereeID) {
        this.refereeID = refereeID;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getKebeleId() {
        return kebeleId;
    }

    public void setKebeleId(String kebeleId) {
        this.kebeleId = kebeleId;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    public void setEmployerAddress(String employerAddress) {
        this.employerAddress = employerAddress;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public boolean isTmpDocumentUploaded() {
        return tmpDocumentUploaded;
    }

    public void setTmpDocumentUploaded(boolean tmpDocumentUploaded) {
        this.tmpDocumentUploaded = tmpDocumentUploaded;
    }

    public String getTmpDocumentFileName() {
        return tmpDocumentFileName;
    }

    public void setTmpDocumentFileName(String tmpDocumentFileName) {
        this.tmpDocumentFileName = tmpDocumentFileName;
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

    public String getFullName() {
        return fileName + " " + middleName + " " + lastName;
    }

    public RefereeTmp() {
        tmpStatus = TempStatus.EDITABLE;
    }
}
