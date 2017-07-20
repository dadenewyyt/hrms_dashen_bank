/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author MulugetaK
 */
public class Util {

    static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");

    public static String getDateString() {
        return df.format(new Date());
    }

    public static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
}
