/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author biniamt
 */

@Entity
@Table(name = "tbl_emp_position_salary_scale")
public class EmployeePositionSalaryScale implements Serializable {

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String ID;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_POSITION_ID", updatable = true)
    private EmployeePosition employeePosition;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SALARY_SCALE_ID", updatable = true)
    private SalaryScale salaryScale;
    
    @Column(name = "start_date")
    private Date startDate;
    
    @Column(name = "end_date")
    private Date endDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "row_status")
    private RowStatus rowStatus;
     
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public EmployeePosition getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(EmployeePosition employeePosition) {
        this.employeePosition = employeePosition;
    }

    public SalaryScale getSalaryScale() {
        return salaryScale;
    }

    public void setSalaryScale(SalaryScale salaryScale) {
        this.salaryScale = salaryScale;
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

    public RowStatus getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(RowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }

    public EmployeePositionSalaryScale() {
        ID = "";
        employeePosition = new EmployeePosition();
        salaryScale = new SalaryScale();
        startDate = new Date();
        endDate = null;
        rowStatus = RowStatus.ACTIVE;
    }
}
