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
@Table(name = "TBL_VACANCY")
public class Vacancy implements Serializable {
    static DateFormat dateFormater = new SimpleDateFormat("dd-MMM-yyyy");
    @Id
    @GenericGenerator(name = "vacSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "vacSeqGenerator")
    @Column(name = "ID")
    private String id;
    @ManyToOne
    @JoinColumn(name = "ORG_STR_ID", foreignKey = @ForeignKey(name = "TBL_VACANCY_FK1"))
    private OrganizationalStructure organizationalStructure;
    @Transient
    private OrganizationalStructure unapprovedOrganizationalStructure;
    @Transient
    private boolean unapprovedOrganizationalStructureArrow;
    @Transient
    private boolean unapprovedOrganizationalStructureDisplay;
    @ManyToOne
    @JoinColumn(name = "POSITION_ID", foreignKey = @ForeignKey(name = "TBL_VACANCY_FK2"))
    private Position position;
    @Transient
    private Position unapprovedPosition;
    @Transient
    private boolean unapprovedPositionArrow;
    @Transient
    private boolean unapprovedPositionDisplay;
    @Column(name = "VACANCY_NUMBER")
    private String vacancyNumber;
    @Transient
    private String unapprovedVacancyNumber;
    @Transient
    private boolean unapprovedVacancyNumberArrow;
    @Transient
    private boolean unapprovedVacancyNumberDisplay;
    @Column(name = "QUALIFICATION")
    private String qualification;
    @Transient
    private String unapprovedQualification;
    @Transient
    private boolean unapprovedQualificationArrow;
    @Transient
    private boolean unapprovedQualificationDisplay;
    @Column(name = "TRAINING_OR_SKILL")
    private String trainingOrSkill;
    @Transient
    private String unapprovedTrainingOrSkill;
    @Transient
    private boolean unapprovedTrainingOrSkillArrow;
    @Transient
    private boolean unapprovedTrainingOrSkillDisplay;
    @Column(name = "EXPERIENCE")
    private String experience;
    @Transient
    private String unapprovedExperience;
    @Transient
    private boolean unapprovedExperienceArrow;
    @Transient
    private boolean unapprovedExperienceDisplay;
    @Column(name = "ELIGIBILITY")
    private String eligibility;
    @Transient
    private String unapprovedEligibility;
    @Transient
    private boolean unapprovedEligibilityArrow;
    @Transient
    private boolean unapproveEligibilityDisplay;
    @Column(name = "OPEN_FROM")
    private String openFrom;
    @Transient
    private Date openFromInDate;
    @Transient
    private String unapprovedOpenFrom;
    @Transient
    private boolean unapprovedOpenFromArrow;
    @Transient
    private boolean unapprovedOpenFromDisplay;
    @Column(name = "OPEN_TO")
    private String openTo;
    @Transient
    private Date openToInDate;
    @Transient
    private String unapprovedOpenTo;
    @Transient
    private boolean unapprovedOpenToArrow;
    @Transient
    private boolean unapprovedOpenToDisplay;
    @Column(name = "REQUIRED_NO")
    private int requiredNo;
    @Transient
    private String unapprovedRequiredNo;
    @Transient
    private boolean unapprovedRequiredNoArrow;
    @Transient
    private boolean unapprovedRequiredNoDisplay;
    @Column(name = "REMARK")
    private String remark;
    @Transient
    private String unapprovedRemark;
    @Transient
    private boolean unapprovedRemarkArrow;
    @Transient
    private boolean unapproveRemarkDisplay;
    @Column(name = "VACANCY_TYPE")
    private String vacancyType;
    @Transient
    private String unapprovedVacancyType;
    @Transient
    private boolean unapprovedVacancyTypeArrow;
    @Transient
    private boolean unapproveVacancyTypeDisplay;
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

    public Vacancy() {
        this.id = "";
        this.organizationalStructure = new OrganizationalStructure();
        this.unapprovedOrganizationalStructure = new OrganizationalStructure();
        this.unapprovedOrganizationalStructureArrow = false;
        this.unapprovedOrganizationalStructureDisplay = false;
        this.position = new Position();
        this.unapprovedPosition = new Position();
        this.unapprovedPositionArrow = false;
        this.unapprovedPositionDisplay = false;
        this.vacancyNumber = "";
        this.unapprovedVacancyNumber = "";
        this.unapprovedVacancyNumberArrow = false;
        this.unapprovedVacancyNumberDisplay = false;
        this.qualification = "";
        this.unapprovedQualification = "";
        this.unapprovedQualificationArrow = false;
        this.unapprovedQualificationDisplay = false;
        this.trainingOrSkill = "";
        this.unapprovedTrainingOrSkill = "";
        this.unapprovedTrainingOrSkillArrow = false;
        this.unapprovedTrainingOrSkillDisplay = false;
        this.experience = "";
        this.unapprovedExperience = "";
        this.unapprovedExperienceArrow = false;
        this.unapprovedExperienceDisplay = false;
        this.eligibility = "";
        this.unapprovedEligibility = "";
        this.unapprovedEligibilityArrow = false;
        this.unapproveEligibilityDisplay = false;
        this.openFrom = "";
        this.openFromInDate = new Date();
        this.unapprovedOpenFrom = "";
        this.unapprovedOpenFromArrow = false;
        this.unapprovedOpenFromDisplay = false;
        this.openTo = "";
        this.openToInDate = new Date();
        this.unapprovedOpenTo = "";
        this.unapprovedOpenToArrow = false;
        this.unapprovedOpenToDisplay = false;
        this.requiredNo = 0;
        this.unapprovedRequiredNo = "";
        this.unapprovedRequiredNoArrow = false;
        this.unapprovedRequiredNoDisplay = false;
        this.remark = "";
        this.unapprovedRemark = "";
        this.unapprovedRemarkArrow = false;
        this.unapproveRemarkDisplay = false;
        this.vacancyType = "";
        this.unapprovedVacancyType = "";
        this.unapprovedVacancyTypeArrow = false;
        this.unapproveVacancyTypeDisplay = false;
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

    public String getVacancyNumber() {
        return vacancyNumber;
    }

    public void setVacancyNumber(String vacancyNumber) {
        this.vacancyNumber = vacancyNumber;
    }

    public String getUnapprovedVacancyNumber() {
        return unapprovedVacancyNumber;
    }

    public void setUnapprovedVacancyNumber(String unapprovedVacancyNumber) {
        this.unapprovedVacancyNumber = unapprovedVacancyNumber;
    }

    public boolean isUnapprovedVacancyNumberArrow() {
        return unapprovedVacancyNumberArrow;
    }

    public void setUnapprovedVacancyNumberArrow(boolean unapprovedVacancyNumberArrow) {
        this.unapprovedVacancyNumberArrow = unapprovedVacancyNumberArrow;
    }

    public boolean isUnapprovedVacancyNumberDisplay() {
        return unapprovedVacancyNumberDisplay;
    }

    public void setUnapprovedVacancyNumberDisplay(boolean unapprovedVacancyNumberDisplay) {
        this.unapprovedVacancyNumberDisplay = unapprovedVacancyNumberDisplay;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getUnapprovedQualification() {
        return unapprovedQualification;
    }

    public void setUnapprovedQualification(String unapprovedQualification) {
        this.unapprovedQualification = unapprovedQualification;
    }

    public boolean isUnapprovedQualificationArrow() {
        return unapprovedQualificationArrow;
    }

    public void setUnapprovedQualificationArrow(boolean unapprovedQualificationArrow) {
        this.unapprovedQualificationArrow = unapprovedQualificationArrow;
    }

    public boolean isUnapprovedQualificationDisplay() {
        return unapprovedQualificationDisplay;
    }

    public void setUnapprovedQualificationDisplay(boolean unapprovedQualificationDisplay) {
        this.unapprovedQualificationDisplay = unapprovedQualificationDisplay;
    }

    public String getTrainingOrSkill() {
        return trainingOrSkill;
    }

    public void setTrainingOrSkill(String trainingOrSkill) {
        this.trainingOrSkill = trainingOrSkill;
    }

    public String getUnapprovedTrainingOrSkill() {
        return unapprovedTrainingOrSkill;
    }

    public void setUnapprovedTrainingOrSkill(String unapprovedTrainingOrSkill) {
        this.unapprovedTrainingOrSkill = unapprovedTrainingOrSkill;
    }

    public boolean isUnapprovedTrainingOrSkillArrow() {
        return unapprovedTrainingOrSkillArrow;
    }

    public void setUnapprovedTrainingOrSkillArrow(boolean unapprovedTrainingOrSkillArrow) {
        this.unapprovedTrainingOrSkillArrow = unapprovedTrainingOrSkillArrow;
    }

    public boolean isUnapprovedTrainingOrSkillDisplay() {
        return unapprovedTrainingOrSkillDisplay;
    }

    public void setUnapprovedTrainingOrSkillDisplay(boolean unapprovedTrainingOrSkillDisplay) {
        this.unapprovedTrainingOrSkillDisplay = unapprovedTrainingOrSkillDisplay;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getUnapprovedExperience() {
        return unapprovedExperience;
    }

    public void setUnapprovedExperience(String unapprovedExperience) {
        this.unapprovedExperience = unapprovedExperience;
    }

    public boolean isUnapprovedExperienceArrow() {
        return unapprovedExperienceArrow;
    }

    public void setUnapprovedExperienceArrow(boolean unapprovedExperienceArrow) {
        this.unapprovedExperienceArrow = unapprovedExperienceArrow;
    }

    public boolean isUnapprovedExperienceDisplay() {
        return unapprovedExperienceDisplay;
    }

    public void setUnapprovedExperienceDisplay(boolean unapprovedExperienceDisplay) {
        this.unapprovedExperienceDisplay = unapprovedExperienceDisplay;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getUnapprovedEligibility() {
        return unapprovedEligibility;
    }

    public void setUnapprovedEligibility(String unapprovedEligibility) {
        this.unapprovedEligibility = unapprovedEligibility;
    }

    public boolean isUnapprovedEligibilityArrow() {
        return unapprovedEligibilityArrow;
    }

    public void setUnapprovedEligibilityArrow(boolean unapprovedEligibilityArrow) {
        this.unapprovedEligibilityArrow = unapprovedEligibilityArrow;
    }

    public boolean isUnapproveEligibilityDisplay() {
        return unapproveEligibilityDisplay;
    }

    public void setUnapproveEligibilityDisplay(boolean unapproveEligibilityDisplay) {
        this.unapproveEligibilityDisplay = unapproveEligibilityDisplay;
    }

    public String getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(String openFrom) {
        this.openFrom = openFrom;
    }

    public Date getOpenFromInDate() {
        return openFromInDate;
    }

    public void setOpenFromInDate(Date openFromInDate) {
        this.openFromInDate = openFromInDate;
        this.openFrom = dateFormater.format(openFromInDate);
    }

    public String getUnapprovedOpenFrom() {
        return unapprovedOpenFrom;
    }

    public void setUnapprovedOpenFrom(String unapprovedOpenFrom) {
        this.unapprovedOpenFrom = unapprovedOpenFrom;
    }

    public boolean isUnapprovedOpenFromArrow() {
        return unapprovedOpenFromArrow;
    }

    public void setUnapprovedOpenFromArrow(boolean unapprovedOpenFromArrow) {
        this.unapprovedOpenFromArrow = unapprovedOpenFromArrow;
    }

    public boolean isUnapprovedOpenFromDisplay() {
        return unapprovedOpenFromDisplay;
    }

    public void setUnapprovedOpenFromDisplay(boolean unapprovedOpenFromDisplay) {
        this.unapprovedOpenFromDisplay = unapprovedOpenFromDisplay;
    }

    public String getOpenTo() {
        return openTo;
    }

    public void setOpenTo(String openTo) {
        this.openTo = openTo;
    }

    public Date getOpenToInDate() {
        return openToInDate;
    }

    public void setOpenToInDate(Date openToInDate) {
        this.openToInDate = openToInDate;
        this.openTo = dateFormater.format(openToInDate);
    }

    public String getUnapprovedOpenTo() {
        return unapprovedOpenTo;
    }

    public void setUnapprovedOpenTo(String unapprovedOpenTo) {
        this.unapprovedOpenTo = unapprovedOpenTo;
    }

    public boolean isUnapprovedOpenToArrow() {
        return unapprovedOpenToArrow;
    }

    public void setUnapprovedOpenToArrow(boolean unapprovedOpenToArrow) {
        this.unapprovedOpenToArrow = unapprovedOpenToArrow;
    }

    public boolean isUnapprovedOpenToDisplay() {
        return unapprovedOpenToDisplay;
    }

    public void setUnapprovedOpenToDisplay(boolean unapprovedOpenToDisplay) {
        this.unapprovedOpenToDisplay = unapprovedOpenToDisplay;
    }

    public int getRequiredNo() {
        return requiredNo;
    }

    public void setRequiredNo(int requiredNo) {
        this.requiredNo = requiredNo;
    }

    public String getUnapprovedRequiredNo() {
        return unapprovedRequiredNo;
    }

    public void setUnapprovedRequiredNo(String unapprovedRequiredNo) {
        this.unapprovedRequiredNo = unapprovedRequiredNo;
    }

    public boolean isUnapprovedRequiredNoArrow() {
        return unapprovedRequiredNoArrow;
    }

    public void setUnapprovedRequiredNoArrow(boolean unapprovedRequiredNoArrow) {
        this.unapprovedRequiredNoArrow = unapprovedRequiredNoArrow;
    }

    public boolean isUnapprovedRequiredNoDisplay() {
        return unapprovedRequiredNoDisplay;
    }

    public void setUnapprovedRequiredNoDisplay(boolean unapprovedRequiredNoDisplay) {
        this.unapprovedRequiredNoDisplay = unapprovedRequiredNoDisplay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUnapprovedRemark() {
        return unapprovedRemark;
    }

    public void setUnapprovedRemark(String unapprovedRemark) {
        this.unapprovedRemark = unapprovedRemark;
    }

    public boolean isUnapprovedRemarkArrow() {
        return unapprovedRemarkArrow;
    }

    public void setUnapprovedRemarkArrow(boolean unapprovedRemarkArrow) {
        this.unapprovedRemarkArrow = unapprovedRemarkArrow;
    }

    public boolean isUnapproveRemarkDisplay() {
        return unapproveRemarkDisplay;
    }

    public void setUnapproveRemarkDisplay(boolean unapproveRemarkDisplay) {
        this.unapproveRemarkDisplay = unapproveRemarkDisplay;
    }

    public String getVacancyType() {
        return vacancyType;
    }

    public void setVacancyType(String vacancyType) {
        this.vacancyType = vacancyType;
    }

    public String getUnapprovedVacancyType() {
        return unapprovedVacancyType;
    }

    public void setUnapprovedVacancyType(String unapprovedVacancyType) {
        this.unapprovedVacancyType = unapprovedVacancyType;
    }

    public boolean isUnapprovedVacancyTypeArrow() {
        return unapprovedVacancyTypeArrow;
    }

    public void setUnapprovedVacancyTypeArrow(boolean unapprovedVacancyTypeArrow) {
        this.unapprovedVacancyTypeArrow = unapprovedVacancyTypeArrow;
    }

    public boolean isUnapproveVacancyTypeDisplay() {
        return unapproveVacancyTypeDisplay;
    }

    public void setUnapproveVacancyTypeDisplay(boolean unapproveVacancyTypeDisplay) {
        this.unapproveVacancyTypeDisplay = unapproveVacancyTypeDisplay;
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