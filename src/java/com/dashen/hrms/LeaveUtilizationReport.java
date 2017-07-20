/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
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
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Daniel Adenew
 */
@Entity
@Table(name = "tbl_leave_utilization")
public class LeaveUtilizationReport implements Serializable {

    private static final long serialVersionUID = 1L;
    
    static DateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy");
    
    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "LEAVE_UTIL_ID")
    private String ID;
    
    @Column(name = "EMP_ID")
    private String emp_id ;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "DAYS_AVAILABLE")
    private String days_avil;
   
    @Column(name = "DAYS_REMAINING")
    private String days_rem;
    
    @Column(name = "ACCRUD")
    private Float accrud_leave;
         
    @Column(name = "INCASH")
    private Float incash;
    
    @Column(name = "INCASH_WORKING")
    private Float incash_working;
    
    @Column(name = "UTILIZED")
    private Float utilized;
    
    @Column(name = "AVAILABLE")
    private String avialble;
    
    @Column(name = "DATE_PERMANENT")
    private Date date_permanent;
    
    @Column(name = "TOTALYEARS")
    private Integer total_years;
    
    @Column(name = "FULLNAME")
    private String full_name;
    
    @Column(name="Expires")
    private String expires;
    

    public static DateFormat getDateFormater() {
        return dateFormater;
    }

    public static void setDateFormater(DateFormat dateFormater) {
        LeaveUtilizationReport.dateFormater = dateFormater;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

  
    public String getDays_avil() {
        return days_avil;
    }

    public void setDays_avil(String days_avil) {
        this.days_avil = days_avil;
    }

    public String getDays_rem() {
        return days_rem;
    }

    public void setDays_rem(String days_rem) {
        this.days_rem = days_rem;
    }

    public Float getAccrud_leave() {
        return accrud_leave;
    }

    public void setAccrud_leave(Float accrud_leave) {
        this.accrud_leave = accrud_leave;
    }

    public Float getIncash() {
        return incash;
    }

    public void setIncash(Float incash) {
        this.incash = incash;
    }

    public Float getIncash_working() {
        return incash_working;
    }

    public void setIncash_working(Float incash_working) {
        this.incash_working = incash_working;
    }

   

    public Date getDate_permanent() {
        return date_permanent;
    }

    public void setDate_permanent(Date date_permanent) {
        this.date_permanent = date_permanent;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public Float getUtilized() {
        return utilized;
    }

    public void setUtilized(Float utilized) {
        this.utilized = utilized;
    }

    public String getAvialble() {
        return avialble;
    }

    public void setAvialble(String avialble) {
        this.avialble = avialble;
    }

    public Integer getTotal_years() {
        return total_years;
    }

    public void setTotal_years(Integer total_years) {
        this.total_years = total_years;
    }

    
}
