/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import com.dashen.hrms.service.EmployeeService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mulugetak
 */
@Component
@SpringViewScope
public class LazyEmployeeDataModel extends LazyDataModel<Employee> {

    @Autowired
    EmployeeService employeeService;

    private List<Employee> employees;

    @Override
    public Employee getRowData(String rowKey) {
        for (Employee emp : employees) {
            if (emp.getEmployeeSerialID().equalsIgnoreCase(rowKey)) {
                return emp;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Employee employee) {
        return employee.getEmployeeSerialID();
    }

    @Override
    public List<Employee> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        System.out.println("Called for first = " + first + " and  for pageSize = " + pageSize
                + "  sortField = " + sortField + "  SortOrder " + sortOrder + "  filters =   " + filters.toString());
        com.dashen.hrms.SortOrder so = null;
        if (sortOrder != null) {
            if (SortOrder.ASCENDING == sortOrder) {
                so = so.ASCENDING;
            } else if (SortOrder.DESCENDING == sortOrder) {
                so = so.DESCENDING;
            }
            if (SortOrder.UNSORTED == sortOrder) {
                so = so.UNSORTED;
            }
        }
        employees = employeeService.findEmployees(first, pageSize, sortField, so, filters);
        this.setRowCount(employeeService.countEmployees(filters).intValue());
        this.setPageSize(pageSize);
//        //paginate
//        if (dataSize > pageSize) {
//            try {
//                return employees.subList(first, first + pageSize);
//            } catch (IndexOutOfBoundsException e) {
//                return employees.subList(first, first + (dataSize % pageSize));
//            }
//        } else {
//            return employees;
//        }
        return employees;
    }

}
