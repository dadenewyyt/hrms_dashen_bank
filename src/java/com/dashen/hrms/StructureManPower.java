/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
@Table(name = "TBL_STRUCTURE_MAN_POWER")
public class StructureManPower implements Serializable {
    static DateFormat dateFormater = new SimpleDateFormat("dd-MMM-yyyy");
    @Id
    @GenericGenerator(name = "strManPowSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "strManPowSeqGenerator")
    @Column(name = "ID")
    private String id;
    @ManyToOne
    @JoinColumn(name = "ORG_STR_ID", foreignKey = @ForeignKey(name = "TBL_STRUCTURE_MAN_POWER_FK1"))
    private OrganizationalStructure organizationalStructure;
    @Transient
    private OrganizationalStructure unapprovedOrganizationalStructure;
    @Transient
    private boolean unapprovedOrganizationalStructureArrow;
    @Transient
    private boolean unapprovedOrganizationalStructureDisplay;
    @ManyToOne
    @JoinColumn(name = "POSITION_ID", foreignKey = @ForeignKey(name = "TBL_STRUCTURE_MAN_POWER_FK2"))
    private Position position;
    @Transient
    private Position unapprovedPosition;
    @Transient
    private boolean unapprovedPositionArrow;
    @Transient
    private boolean unapprovedPositionDisplay;
    @Column(name = "REQUIRED_NO_OF_WORK_FORCE")
    private int requiredNoOfWorkForce;
    @Transient
    private String unapprovedRequiredNoOfWorkForce;
    @Transient
    private boolean unapprovedRequiredNoOfWorkForceArrow;
    @Transient
    private boolean unapprovedRequiredNoOfWorkForceDisplay;
    @Column(name = "CURRENTLY_AVAILABLE_SPACE")
    private int currentlyAvailableSpace;
    @Transient
    private String unapprovedCurrentlyAvailableSpace;
    @Transient
    private boolean unapprovedCurrentlyAvailableSpaceArrow;
    @Transient
    private boolean unapprovedCurrentlyAvailableSpaceDisplay;
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

    public StructureManPower() {
        this.id = "";
        this.organizationalStructure = new OrganizationalStructure();
        this.unapprovedOrganizationalStructure = new OrganizationalStructure();
        this.unapprovedOrganizationalStructureArrow = false;
        this.unapprovedOrganizationalStructureDisplay = false;
        this.position = new Position();
        this.unapprovedPosition = new Position();
        this.unapprovedPositionArrow = false;
        this.unapprovedPositionDisplay = false;
        this.requiredNoOfWorkForce = 0;
        this.unapprovedRequiredNoOfWorkForce = "";
        this.unapprovedRequiredNoOfWorkForceArrow = false;
        this.unapprovedRequiredNoOfWorkForceDisplay = false;
        this.currentlyAvailableSpace = 0;
        this.unapprovedCurrentlyAvailableSpace = "";
        this.unapprovedCurrentlyAvailableSpaceArrow = false;
        this.unapprovedCurrentlyAvailableSpaceDisplay = false;
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

    public int getRequiredNoOfWorkForce() {
        return requiredNoOfWorkForce;
    }

    public void setRequiredNoOfWorkForce(int requiredNoOfWorkForce) {
        this.requiredNoOfWorkForce = requiredNoOfWorkForce;
    }

    public String getUnapprovedRequiredNoOfWorkForce() {
        return unapprovedRequiredNoOfWorkForce;
    }

    public void setUnapprovedRequiredNoOfWorkForce(String unapprovedRequiredNoOfWorkForce) {
        this.unapprovedRequiredNoOfWorkForce = unapprovedRequiredNoOfWorkForce;
    }

    public boolean isUnapprovedRequiredNoOfWorkForceArrow() {
        return unapprovedRequiredNoOfWorkForceArrow;
    }

    public void setUnapprovedRequiredNoOfWorkForceArrow(boolean unapprovedRequiredNoOfWorkForceArrow) {
        this.unapprovedRequiredNoOfWorkForceArrow = unapprovedRequiredNoOfWorkForceArrow;
    }

    public boolean isUnapprovedRequiredNoOfWorkForceDisplay() {
        return unapprovedRequiredNoOfWorkForceDisplay;
    }

    public void setUnapprovedRequiredNoOfWorkForceDisplay(boolean unapprovedRequiredNoOfWorkForceDisplay) {
        this.unapprovedRequiredNoOfWorkForceDisplay = unapprovedRequiredNoOfWorkForceDisplay;
    }

    public int getCurrentlyAvailableSpace() {
        return currentlyAvailableSpace;
    }

    public void setCurrentlyAvailableSpace(int currentlyAvailableSpace) {
        this.currentlyAvailableSpace = currentlyAvailableSpace;
    }

    public String getUnapprovedCurrentlyAvailableSpace() {
        return unapprovedCurrentlyAvailableSpace;
    }

    public void setUnapprovedCurrentlyAvailableSpace(String unapprovedCurrentlyAvailableSpace) {
        this.unapprovedCurrentlyAvailableSpace = unapprovedCurrentlyAvailableSpace;
    }

    public boolean isUnapprovedCurrentlyAvailableSpaceArrow() {
        return unapprovedCurrentlyAvailableSpaceArrow;
    }

    public void setUnapprovedCurrentlyAvailableSpaceArrow(boolean unapprovedCurrentlyAvailableSpaceArrow) {
        this.unapprovedCurrentlyAvailableSpaceArrow = unapprovedCurrentlyAvailableSpaceArrow;
    }

    public boolean isUnapprovedCurrentlyAvailableSpaceDisplay() {
        return unapprovedCurrentlyAvailableSpaceDisplay;
    }

    public void setUnapprovedCurrentlyAvailableSpaceDisplay(boolean unapprovedCurrentlyAvailableSpaceDisplay) {
        this.unapprovedCurrentlyAvailableSpaceDisplay = unapprovedCurrentlyAvailableSpaceDisplay;
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