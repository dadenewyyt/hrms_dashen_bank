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
public class EnumValues {

    public Gender[] getGenderValues() {
        return Gender.values();
    }

    public MaritalStatus[] getMaritalStatusValues() {
        return MaritalStatus.values();
    }

}
