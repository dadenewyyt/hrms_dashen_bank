/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author mulugetak
 */
@Entity
@Table(name = "TBL_REFEREE")
public class Referee {
    
    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String id;
    
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_SERIAL_ID", insertable = false, updatable = false)
    private Employee refereeOwner;
    
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

    public Employee getRefereeOwner() {
        return refereeOwner;
    }

    public void setRefereeOwner(Employee refereeOwner) {
        this.refereeOwner = refereeOwner;
    }
           
}
