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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author mulugetak
 */
@Entity
@Table(name = "TBL_WORK_EXPERIENCE")
public class Experience {

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String id;

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
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_SERIAL_ID", insertable = false, updatable = false)
    private Employee experienceOwner;

    @PostConstruct
    public void init() {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Employee getExperienceOwner() {
        return experienceOwner;
    }

    public void setExperienceOwner(Employee experienceOwner) {
        this.experienceOwner = experienceOwner;
    }
}
