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
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 *
 * @author mulugetak
 */
@Entity
@Table(name = "TBL_QUALIFICATION_TMP")
public class QualificationTmp implements Serializable {

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String id;

    @Column(name = "QUALIFICATION_ID")
    private String qualificationId;

    @Column(name = "EMPLOYEE_SERIAL_ID")
    private String employeeSerialId;

    @Column(name = "QUALIFICATION")
    private String qualification;

    @Column(name = "QUALIFICATION_LEVEL")
    private String qualificationLevel;

    @Column(name = "CGPA")
    private Double CGPA;

    @Column(name = "INSTITUTION_ID")
    private String institutionID;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "DATE_OF_GRADUATION")
    private Date dateOfGraduation;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "STATUS")
    private String status;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_SERIAL_ID", insertable = false, updatable = false)
    private Employee ownerEmployee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INSTITUTION_ID", insertable = false, updatable = false)
    protected Institution institution;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(String qualificationId) {
        this.qualificationId = qualificationId;
    }

    public String getEmployeeSerialId() {
        return employeeSerialId;
    }

    public void setEmployeeSerialId(String employeeSerialId) {
        this.employeeSerialId = employeeSerialId;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getQualificationLevel() {
        return qualificationLevel;
    }

    public void setQualificationLevel(String qualificationLevel) {
        this.qualificationLevel = qualificationLevel;
    }

    public Double getCGPA() {
        return CGPA;
    }

    public void setCGPA(Double CGPA) {
        this.CGPA = CGPA;
    }

    public String getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(String institutionID) {
        this.institutionID = institutionID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDateOfGraduation() {
        return dateOfGraduation;
    }

    public void setDateOfGraduation(Date dateOfGraduation) {
        this.dateOfGraduation = dateOfGraduation;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public Employee getOwnerEmployee() {
        return ownerEmployee;
    }

    public void setOwnerEmployee(Employee ownerEmployee) {
        this.ownerEmployee = ownerEmployee;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
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

    public QualificationTmp() {
        qualificationId = "";
        employeeSerialId = "";
        qualification = "";
        qualificationLevel = "";
        CGPA = 0.0;
        institutionID = "";
        startDate = new Date();
        dateOfGraduation = new Date();
        fileName = "";
        status = "STATUS";
        makerID = "";
        makerDate = new Date();
        actionType = ActionType.CREATE;
        tmpStatus = TempStatus.EDITABLE;
        tmpDocumentUploaded = false;
        tmpDocumentFileName = "";
        checkerID = "";
        checkerDate = new Date();

    }

}
