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
@Table(name = "tbl_leave_request")
public class LeaveRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    
    static DateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy");
    
    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String leaveID;
    
    @Column(name = "DATE_FROM")
    private Date date_from ;

    @Column(name = "DATE_TO")
    private Date date_to;

    @Column(name = "APPROVER_FIRST_NAME")
    private String approver_first_name;
   
    @Column(name = "APPROVER_MIDDLE_NAME")
    private String approver_middle_name;
    
    @Column(name = "APPROVER_LAST_NAME")
    private String approver_last_name;
    
    @Column(name = "ATTACHMENT")
    private String attachemnt;
         
    @Column(name = "CHECKER_USER_ID")
    private String checker_user_id;
    
    @Column(name = "APPROVED_DATE")
    private Date approved_date;
    
    @Column(name = "REASON")
    private String reason;
    
    @Column(name = "TIME_SPAN_HOURS")
    private String time_span_hours;
    
    @Column(name="NUMDAYS")
    private Double NumDays;
    
    @Column(name = "MAKER_USER_ID")
    private String maker_user_id;
    
    @Column(name = "checker_approved_date")
    private Date checker_approved_date;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_SERIAL_ID")
    private Employee leaveRequestOwner;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "APPROVER_EMP_ID")
    private Employee approverRequestOwner;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "MAKER_CHECKER_STATUS")
    private TempStatus maker_checker_status;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "leave_status")
    private LeaveEnum leave_status;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LEAVE_TYPE_ID")
    private LeaveType leaveType;
        
    public LeaveRequest() {
        this.leaveRequestOwner = new Employee();
        this.leaveType = new LeaveType();
       this.approverRequestOwner = new Employee();
    }
    public LeaveRequest(String employeeID, String approverEMpID,Date date_from, Date date_to, String approver_first_name, String approver_middle_name, String approver_last_name, String approver_user_id, Date approved_date, String reason, String time_span_hours, String maker_user_id, String cheker_user_id, String makerChekerStatus, Date checker_approved_date,String leaveTypeID) {
       
        this.leaveRequestOwner.setEmployeeID(employeeID);
        this.date_from = date_from;
        this.date_to = date_to;
        this.approver_first_name = approver_first_name;
        this.approver_middle_name = approver_middle_name;
        this.approver_last_name = approver_last_name;
        this.approved_date = approved_date;
        this.reason = reason;
        this.leaveType.setID(leaveTypeID);
        this.time_span_hours = time_span_hours;
        this.maker_user_id = maker_user_id;
        this.checker_user_id = cheker_user_id;        
        this.checker_approved_date = checker_approved_date;
        this.maker_checker_status = TempStatus.EDITABLE;
//      this.approverRequestOwner.setEmployeeID(approverEMpID);
    }

    public static DateFormat getDateFormater() {
        return dateFormater;
    }

    public static void setDateFormater(DateFormat dateFormater) {
        LeaveRequest.dateFormater = dateFormater;
    }

    public String getLeaveID() {
        return leaveID;
    }

    public void setLeaveID(String leaveID) {
        this.leaveID = leaveID;
    }

    public String getApprover_middle_name() {
        return approver_middle_name;
    }

    public void setApprover_middle_name(String approver_middle_name) {
        this.approver_middle_name = approver_middle_name;
    }

    public String getApprover_last_name() {
        return approver_last_name;
    }

    public void setApprover_last_name(String approver_last_name) {
        this.approver_last_name = approver_last_name;
    }

    public String getMaker_user_id() {
        return maker_user_id;
    }

    public void setMaker_user_id(String maker_user_id) {
        this.maker_user_id = maker_user_id;
    }

    public Date getChecker_approved_date() {
        return checker_approved_date;
    }

    public void setChecker_approved_date(Date checker_approved_date) {
        this.checker_approved_date = checker_approved_date;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
      this.date_from = date_from;   
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {     
     this.date_to = date_to;
    }

    public String getApprover_first_name() {
        return approver_first_name;
    }

    public void setApprover_first_name(String approver_full_name) {
        this.approver_first_name = approver_full_name;
    }

    public Date getApproved_date() {
        return approved_date;
    }

    public void setApproved_date(Date approved_date) {
        this.approved_date = approved_date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTime_span_hours() {
        return time_span_hours;
    }

    public void setTime_span_hours(String time_span_hours) {
        this.time_span_hours = time_span_hours;
    }

    public String getChecker_user_id() {
        return checker_user_id;
    }

    public void setChecker_user_id(String checker_user_id) {
        this.checker_user_id = checker_user_id;
    }

    public Double getNumDays() {
        return NumDays;
    }

    public void setNumDays(Double NumDays) {
        this.NumDays = NumDays;
    }   
    
    public String getId() {
        return leaveID;
    }

    public void setId(String leaveId) {
        this.leaveID = leaveId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (leaveID != null ? leaveID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LeaveRequest)) {
            return false;
        }
        LeaveRequest other = (LeaveRequest) object;
        if ((this.leaveID == null && other.leaveID != null) || (this.leaveID != null && !this.leaveID.equals(other.leaveID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dashen.hrms.Leave[ id=" + leaveID + " ]";
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

  
    public Employee getLeaveRequestOwner() {
        return leaveRequestOwner;
    }

    public void setLeaveRequestOwner(Employee leaveRequestOwner) {
        this.leaveRequestOwner = leaveRequestOwner;
    }

    public TempStatus getMaker_checker_status() {
        return maker_checker_status;
    }

    public void setMaker_checker_status(TempStatus maker_checker_status) {
        this.maker_checker_status = maker_checker_status;
    }

    public LeaveEnum getLeave_status() {
        return leave_status;
    }

    public void setLeave_status(LeaveEnum leave_status) {
        this.leave_status = leave_status;
    }

    public Employee getApproverRequestOwner() {
        return approverRequestOwner;
    }

    public void setApproverRequestOwner(Employee approverRequestOwner) {
        this.approverRequestOwner = approverRequestOwner;
    }

    public String getAttachemnt() {
        return attachemnt;
    }

    public void setAttachemnt(String attachemnt) {
        this.attachemnt = attachemnt;
    }
    
    
   
}
