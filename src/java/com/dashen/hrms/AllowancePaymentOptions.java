/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

/**
 *
 * @author biniamt
 */
// public enum AllowancePaymentOptions {
//     BY_VALUE,
//     PERCENT_OF_SALARY,
//     QUANTITATIVE
// }

public enum AllowancePaymentOptions {

    BY_VALUE("By Value"),
    PERCENT_OF_SALARY("Percent Of Salary"),
    QUANTITATIVE("Quantitative");

    private String displayName;

    AllowancePaymentOptions(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() { return displayName; }

    // Optionally and/or additionally, toString.
    @Override public String toString() { return displayName; }
}
