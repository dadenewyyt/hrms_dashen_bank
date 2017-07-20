/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 *
 * @author mulugetak
 */
@Component
@ApplicationScope
public class Configuration {

    public static String PROFILE_PHOTO_PATH = "/images/profile/";
    public static String QUALIFICATION_DOCUMENTS_PATH = "/images/qualification/";
    public static String EXPERIENCE_DOCUMENTS_PATH = "/images/experience/";
    public static String REFEREE_DOCUMENTS_PATH = "/images/referee/";
    public static String MARITAL_STATUS_DOCUMENTS_PATH = "/images/marital/";
    
    public static String LEAVE_REQUEST_ATTACHEMNT_UPLOAD_PATH = "/images/leaveImages/upload/";

    public String getPROFILE_PHOTO_PATH() {
        return PROFILE_PHOTO_PATH;
    }

    public String getQUALIFICATION_DOCUMENTS_PATH() {
        return QUALIFICATION_DOCUMENTS_PATH;
    }

    public String getEXPERIENCE_DOCUMENTS_PATH() {
        return EXPERIENCE_DOCUMENTS_PATH;
    }

    public String getREFEREE_DOCUMENTS_PATH() {
        return REFEREE_DOCUMENTS_PATH;
    }

    public String getMARITAL_STATUS_DOCUMENTS_PATH() {
        return MARITAL_STATUS_DOCUMENTS_PATH;
    }

    public static String getLEAVE_REQUEST_ATTACHEMNT_UPLOAD_PATH() {
        return LEAVE_REQUEST_ATTACHEMNT_UPLOAD_PATH;
    }

    public static void setLEAVE_REQUEST_ATTACHEMNT_UPLOAD_PATH(String LEAVE_REQUEST_ATTACHEMNT_UPLOAD_PATH) {
        Configuration.LEAVE_REQUEST_ATTACHEMNT_UPLOAD_PATH = LEAVE_REQUEST_ATTACHEMNT_UPLOAD_PATH;
    }
    
    
    
}
