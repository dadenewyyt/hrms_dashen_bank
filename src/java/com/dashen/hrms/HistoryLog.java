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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author dawits
 */
@Entity
@Table(name = "TBL_HISTORY_LOG")
public class HistoryLog implements Serializable {
    static DateFormat dateFormater = new SimpleDateFormat("dd-MMM-yyyy");
    @Id
    @GenericGenerator(name = "hisLogSeqGenerator", strategy = "com.dashen.hrms.IDGenerator")
    @GeneratedValue(generator = "hisLogSeqGenerator")
    @Column(name = "ID")
    private String id;
    @Column(name = "DATA_ID")
    private String dataId;
    @Column(name = "LOG_DATE")
    private String logDate;
    @Transient
    private Date logDateInDate;
    @Column(name = "ROW_DATA")
    private String rowData;

    public HistoryLog() {
        this.id = "";
        this.dataId = "";
        logDate = "";
        logDateInDate = new Date();
        this.rowData = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public Date getLogDateInDate() {
        return logDateInDate;
    }

    public void setLogDateInDate(Date logDateInDate) {
        this.logDateInDate = logDateInDate;
        this.logDate = dateFormater.format(logDateInDate);
    }

    public String getRowData() {
        return rowData;
    }

    public void setRowData(String rowData) {
        this.rowData = rowData;
    }
}