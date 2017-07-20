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
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 *
 * @author mulugetak
 */
@Entity
@Table(name = "TBL_WORK_EXPERIENCE_TMP")
public class ExperienceTmp {
    
    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String id;

    @Column(name = "WORK_EXPERIENCE_ID")
    private String experienceID;
    
    @Column(name = "EMPLOYEE_SERIAL_ID")
    private String employeeSerialID;

    @Column(name = "NAME_OF_EMPLOYER")
    private String nameOfEmployer;

    @Column(name = "EMPLOYER_ADDRESS")
    private String employerAddress;

    @Column(name = "POSITION")
    private String position;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "START_DATE")
    private Date startDate;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "TOTAL_YEARS_EXPERIENCE")
    private double totalYearsOfExperience;

    @Column(name = "REASON_FOR_RESIGNATION")
    private String reasonForResignation;

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
    @Temporal(TemporalType.DATE)
    private Date checkerDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExperienceID() {
        return experienceID;
    }

    public void setExperienceID(String experienceID) {
        this.experienceID = experienceID;
    }

    public String getEmployeeSerialID() {
        return employeeSerialID;
    }

    public void setEmployeeSerialID(String employeeSerialID) {
        this.employeeSerialID = employeeSerialID;
    }

    public String getNameOfEmployer() {
        return nameOfEmployer;
    }

    public void setNameOfEmployer(String nameOfEmployer) {
        this.nameOfEmployer = nameOfEmployer;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    public void setEmployerAddress(String employerAddress) {
        this.employerAddress = employerAddress;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTotalYearsOfExperience() {
        return totalYearsOfExperience;
    }

    public void setTotalYearsOfExperience(double totalYearsOfExperience) {
        this.totalYearsOfExperience = totalYearsOfExperience;
    }

    public String getReasonForResignation() {
        return reasonForResignation;
    }

    public void setReasonForResignation(String reasonForResignation) {
        this.reasonForResignation = reasonForResignation;
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
         
    public ExperienceTmp()
    {
        tmpStatus = TempStatus.EDITABLE;
    }
    
}
