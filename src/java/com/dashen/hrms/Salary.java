/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author biniamt
 */


@Entity
@Table(name = "tbl_salary")
public class Salary {

    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String ID;

//    
//    @ManyToOne
//    @JoinColumn(name = "REQUESTING_POSITION_ID",
//            foreignKey = @ForeignKey(name = "TBL_TRANSFER_FK2")
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPARTMENT_ID", foreignKey = @ForeignKey(name = "TBL_SALARY_FK1"), updatable = false)
    private Department department;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GRADE_ID", updatable = false)
    private JobGrade grade;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LEVEL_ID", updatable = false)
    private JobLevel level;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POSITION_ID", updatable = false)
    private Position position;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public JobGrade getGrade() {
        return grade;
    }

    public void setGrade(JobGrade grade) {
        this.grade = grade;
    }

    public JobLevel getLevel() {
        return level;
    }

    public void setLevel(JobLevel level) {
        this.level = level;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Salary() {
        ID = "";
        department = new Department();
        level = new JobLevel();
        grade = new JobGrade();
        position = new Position();
    }
}
