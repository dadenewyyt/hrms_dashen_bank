/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.Employee;
import com.dashen.hrms.SpringViewScope;
import java.io.Serializable;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mulugetak
 */
@SpringViewScope
@Component
public class SearchEmployeeBean implements Serializable {

    @Autowired
    private LazyDataModel<Employee> lazyEmployeeModel;

    public LazyDataModel<Employee> getLazyEmployeeModel() {
        return lazyEmployeeModel;
    }
}
