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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;
/**
 *
 * @author dawits
 */
@Entity
@Table(name = "TBL_TRANSFER")
public class Transfer implements Serializable {
    static DateFormat dateFormater = new SimpleDateFormat("dd-MMM-yyyy");
    @Id
    @GenericGenerator(name = "traSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "traSeqGenerator")
    @Column(name = "ID")
    private String id;
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_SERIAL_ID", foreignKey = @ForeignKey(name = "TBL_TRANSFER_FK1"))
    private Employee employee;
    @Transient
    private Employee unapprovedEmployee;
    @Transient
    private boolean unapprovedEmployeeArrow;
    @Transient
    private boolean unapprovedEmployeeDisplay;
    @ManyToOne
    @JoinColumn(name = "ORG_STR_ID", foreignKey = @ForeignKey(name = "TBL_TRANSFER_FK2"))
    private OrganizationalStructure organizationalStructure;
    @Transient
    private OrganizationalStructure unapprovedOrganizationalStructure;
    @Transient
    private boolean unapprovedOrganizationalStructureArrow;
    @Transient
    private boolean unapprovedOrganizationalStructureDisplay;
    @ManyToOne
    @JoinColumn(name = "POSITION_ID", foreignKey = @ForeignKey(name = "TBL_TRANSFER_FK3"))
    private Position position;
    @Transient
    private Position unapprovedPosition;
    @Transient
    private boolean unapprovedPositionArrow;
    @Transient
    private boolean unapprovedPositionDisplay;
    @Column(name = "REQUESTED_DATE")
    private String requestedDate;
    @Transient
    private Date requestedDateInDate;
    @Transient
    private String unapprovedRequestedDate;
    @Transient
    private boolean unapprovedRequestedDateArrow;
    @Transient
    private boolean unapprovedRequestedDateDisplay;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATION_TIMESTAMP")
    private String creationTimeStamp;
    @Column(name = "EDITED_BY")
    private String editedBy;
    @Column(name = "EDITING_TIMESTAMP")
    private String editingTimeStamp;
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Column(name = "APPROVED_TIMESTAMP")
    private String approvedTimeStamp;
    @Column(name = "DELETED_BY")
    private String deletedBy;
    @Column(name = "DELETION_TIMESTAMP")
    private String deletionTimeStamp;
    @Column(name = "UNAPPROVED_CHANGE")
    private String unapprovedChange;
    @Column(name = "STATUS")
    private String status;

    public Transfer() {
        this.id = "";
        this.employee = new Employee();
        this.unapprovedEmployee = new Employee();
        this.unapprovedEmployeeArrow = false;
        this.unapprovedEmployeeDisplay = false;
        this.organizationalStructure = new OrganizationalStructure();
        this.unapprovedOrganizationalStructure = new OrganizationalStructure();
        this.unapprovedOrganizationalStructureArrow = false;
        this.unapprovedOrganizationalStructureDisplay = false;
        this.position = new Position();
        this.unapprovedPosition = new Position();
        this.unapprovedPositionArrow = false;
        this.unapprovedPositionDisplay = false;
        this.requestedDate = "";
        this.requestedDateInDate = new Date();
        this.unapprovedRequestedDate = "";
        this.unapprovedRequestedDateArrow = false;
        this.unapprovedRequestedDateDisplay = false;
        this.createdBy = "";
        this.creationTimeStamp = "";
        this.editedBy = "";
        this.editingTimeStamp = "";
        this.approvedBy = "";
        this.approvedTimeStamp = "";
        this.deletedBy = "";
        this.deletionTimeStamp = "";
        this.unapprovedChange = "";
        this.status = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getUnapprovedEmployee() {
        return unapprovedEmployee;
    }

    public void setUnapprovedEmployee(Employee unapprovedEmployee) {
        this.unapprovedEmployee = unapprovedEmployee;
    }

    public boolean isUnapprovedEmployeeArrow() {
        return unapprovedEmployeeArrow;
    }

    public void setUnapprovedEmployeeArrow(boolean unapprovedEmployeeArrow) {
        this.unapprovedEmployeeArrow = unapprovedEmployeeArrow;
    }

    public boolean isUnapprovedEmployeeDisplay() {
        return unapprovedEmployeeDisplay;
    }

    public void setUnapprovedEmployeeDisplay(boolean unapprovedEmployeeDisplay) {
        this.unapprovedEmployeeDisplay = unapprovedEmployeeDisplay;
    }

    public OrganizationalStructure getOrganizationalStructure() {
        return organizationalStructure;
    }

    public void setOrganizationalStructure(OrganizationalStructure organizationalStructure) {
        this.organizationalStructure = organizationalStructure;
    }

    public OrganizationalStructure getUnapprovedOrganizationalStructure() {
        return unapprovedOrganizationalStructure;
    }

    public void setUnapprovedOrganizationalStructure(OrganizationalStructure unapprovedOrganizationalStructure) {
        this.unapprovedOrganizationalStructure = unapprovedOrganizationalStructure;
    }

    public boolean isUnapprovedOrganizationalStructureArrow() {
        return unapprovedOrganizationalStructureArrow;
    }

    public void setUnapprovedOrganizationalStructureArrow(boolean unapprovedOrganizationalStructureArrow) {
        this.unapprovedOrganizationalStructureArrow = unapprovedOrganizationalStructureArrow;
    }

    public boolean isUnapprovedOrganizationalStructureDisplay() {
        return unapprovedOrganizationalStructureDisplay;
    }

    public void setUnapprovedOrganizationalStructureDisplay(boolean unapprovedOrganizationalStructureDisplay) {
        this.unapprovedOrganizationalStructureDisplay = unapprovedOrganizationalStructureDisplay;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getUnapprovedPosition() {
        return unapprovedPosition;
    }

    public void setUnapprovedPosition(Position unapprovedPosition) {
        this.unapprovedPosition = unapprovedPosition;
    }

    public boolean isUnapprovedPositionArrow() {
        return unapprovedPositionArrow;
    }

    public void setUnapprovedPositionArrow(boolean unapprovedPositionArrow) {
        this.unapprovedPositionArrow = unapprovedPositionArrow;
    }

    public boolean isUnapprovedPositionDisplay() {
        return unapprovedPositionDisplay;
    }

    public void setUnapprovedPositionDisplay(boolean unapprovedPositionDisplay) {
        this.unapprovedPositionDisplay = unapprovedPositionDisplay;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Date getRequestedDateInDate() {
        return requestedDateInDate;
    }

    public void setRequestedDateInDate(Date requestedDateInDate) {
        this.requestedDateInDate = requestedDateInDate;
        this.requestedDate = dateFormater.format(requestedDateInDate);
    }

    public String getUnapprovedRequestedDate() {
        return unapprovedRequestedDate;
    }

    public void setUnapprovedRequestedDate(String unapprovedRequestedDate) {
        this.unapprovedRequestedDate = unapprovedRequestedDate;
    }

    public boolean isUnapprovedRequestedDateArrow() {
        return unapprovedRequestedDateArrow;
    }

    public void setUnapprovedRequestedDateArrow(boolean unapprovedRequestedDateArrow) {
        this.unapprovedRequestedDateArrow = unapprovedRequestedDateArrow;
    }

    public boolean isUnapprovedRequestedDateDisplay() {
        return unapprovedRequestedDateDisplay;
    }

    public void setUnapprovedRequestedDateDisplay(boolean unapprovedRequestedDateDisplay) {
        this.unapprovedRequestedDateDisplay = unapprovedRequestedDateDisplay;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(String creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public String getEditingTimeStamp() {
        return editingTimeStamp;
    }

    public void setEditingTimeStamp(String editingTimeStamp) {
        this.editingTimeStamp = editingTimeStamp;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedTimeStamp() {
        return approvedTimeStamp;
    }

    public void setApprovedTimeStamp(String approvedTimeStamp) {
        this.approvedTimeStamp = approvedTimeStamp;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getDeletionTimeStamp() {
        return deletionTimeStamp;
    }

    public void setDeletionTimeStamp(String deletionTimeStamp) {
        this.deletionTimeStamp = deletionTimeStamp;
    }

    public String getUnapprovedChange() {
        return unapprovedChange;
    }

    public void setUnapprovedChange(String unapprovedChange) {
        this.unapprovedChange = unapprovedChange;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}