/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author biniamt
 */


@Entity
@Table(name = "tbl_tmp_storage")
public class TmpStorage {
    @Id
    @GenericGenerator(name = "idSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "idSeqGenerator")
    @Column(name = "ID")
    private String ID;

    @Column(name = "MAKER_ID")
    private String makerId;
    
    @Column(name = "CHECKER_ID")
    private String checkerId;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "ACTION_DATE")
    private Date actionDate;

    @Column(name = "TABLE_ROW_ID")
    private String tableRowId;

    @Column(name = "TASK_JSON")
    private String taskJson;

    // for edit record the id of the edited row id otherwise null will be assigned
    @Column(name = "SOURCE_TABLE_ROW_ID")
    private String sourceTableRowId;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMakerId() {
        return makerId;
    }

    public void setMakerId(String makerId) {
        this.makerId = makerId;
    }

    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getTableRowId() {
        return tableRowId;
    }

    public void setTableRowId(String tableRowId) {
        this.tableRowId = tableRowId;
    }

    public String getTaskJson() {
        return taskJson;
    }

    public void setTaskJson(String taskJson) {
        this.taskJson = taskJson;
    }

    public String getSourceTableRowId() {
        return sourceTableRowId;
    }

    public void setSourceTableRowId(String rowId) {
        this.sourceTableRowId = rowId;
    }

    public TmpStorage() {
        ID = "";
        makerId = "";
        checkerId = "";
        status = "";
        createdDate = new Date();
        actionDate = new Date();
        tableRowId = "";
        taskJson = "";
        sourceTableRowId = "";
    }
}
