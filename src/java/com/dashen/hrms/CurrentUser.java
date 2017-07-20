/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author mulugetak
 */
public class CurrentUser {

    public static MyUser getCurrentUser() {
        MyUser myUsr = null;
        try {
            SecurityContext sc = SecurityContextHolder.getContext();
            Object principal = sc.getAuthentication().getPrincipal();
            myUsr = (MyUser) principal;
        } catch (Exception ex) {

        }
        return myUsr;
    }
}
