/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author biniamt
 */

@Entity
@Table(name = "tbl_emp_position_org_structure")
public class EmployeePositionOrganizationalStructure implements Serializable {

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String ID;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_POSITION_ID", updatable = true)
    private EmployeePosition employeePosition;
    
   @OneToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "ORGANIZATIONAL_STRUCTURE_ID", updatable = true)
   private OrganizationalStructure organizationalStructure;
    
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

   public OrganizationalStructure getOrganizationalStructure() {
       return organizationalStructure;
   }

   public void setOrganizationalStructure(OrganizationalStructure organizationalStructure) {
       this.organizationalStructure = organizationalStructure;
   }

    public EmployeePositionOrganizationalStructure() {
        ID = "";
        employeePosition = new EmployeePosition();
       organizationalStructure = new OrganizationalStructure();
    }
}
