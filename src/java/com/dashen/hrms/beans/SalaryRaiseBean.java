/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.EmployeePosition;
import com.dashen.hrms.EmployeePositionSalaryScale;
import com.dashen.hrms.RowStatus;
import com.dashen.hrms.SalaryScale;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.service.EmployeePositionService;
import com.dashen.hrms.service.JobLevelService;
import com.dashen.hrms.service.SalaryScaleService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author biniamt
 */

@Component
@SpringViewScope
public class SalaryRaiseBean {
    
    @Autowired
    private EmployeePositionService employeePositionService;
    
    @Autowired
    private JobLevelService jobLevelService;
    
    @Autowired
    private SalaryScaleService salaryScaleService;
    
    private Integer raiseAmount;

    private List<SelectItem> levelRaiseList;
    
    public Integer getRaiseAmount() {
        return raiseAmount;
    }
    
    public void setRaiseAmount(Integer amount) {
        this.raiseAmount = amount;
    }

    public List<SelectItem> getLevelRaiseList() {
    	return levelRaiseList;
    }

    public void setLevelRaiseList(List<SelectItem> levelRaiseList) {
    	this.levelRaiseList = levelRaiseList;
    }

    @PostConstruct
    public void init() {
    	levelRaiseList = new ArrayList<>();

    	for (Integer i=1; i<5; i++) {
            levelRaiseList.add(new SelectItem(i, i.toString()));
    	}
    }
    
    public void btnSaveSalaryRaise_Handler() {
        
        String maxLevel = jobLevelService.getMaxLevel();
        int maxLevelCharValue = maxLevel.charAt(0);
        
        System.out.println("The maxium level available is : " + maxLevel + " it's char value is " + maxLevelCharValue);
        
        List<EmployeePositionSalaryScale> empPosSalaryScales = employeePositionService.listEmployeePositionSalaryScales();
        for (EmployeePositionSalaryScale empPosSalaryScale : empPosSalaryScales) {
            SalaryScale prevSalaryScale = empPosSalaryScale.getSalaryScale();
            String prevLevel = prevSalaryScale.getLevel().getLevel();
            int charValue = prevLevel.charAt(0);
            int nextCharValue = charValue + raiseAmount;
            String newLevel = String.valueOf( (char) (nextCharValue));
            if (nextCharValue > maxLevelCharValue) {
                System.out.println("Can't raise salary for " + empPosSalaryScale.getEmployeePosition().getEmployee().getFullName() + " because of after raise he's level will be out of level limit.");
            } else {
                
                // todo check the salary scale with the raised level exists with the employee positions grade
                String grade = empPosSalaryScale.getEmployeePosition().getPosition().getJobGrade().getGrade();
                SalaryScale newSalaryScale = salaryScaleService.getByGradeAndLevel(grade, newLevel);
                if (newSalaryScale != null) {
                    empPosSalaryScale.setEndDate(new Date());
                    empPosSalaryScale.setRowStatus(RowStatus.HISTORY);
                    
                    EmployeePositionSalaryScale newEmpPosSalaryScale = new EmployeePositionSalaryScale();
                    newEmpPosSalaryScale.setEmployeePosition(empPosSalaryScale.getEmployeePosition());
                    newEmpPosSalaryScale.setSalaryScale(newSalaryScale);
                    
                    employeePositionService.updateEmpPosSalaryScale(empPosSalaryScale);
                    employeePositionService.saveNewEmpPosSalaryScale(newEmpPosSalaryScale);
                } else {
                    System.out.println("The new level which is " + newLevel + " and " + empPosSalaryScale.getEmployeePosition().getEmployee().getFullName() + " position's grade which is " + grade + " have no matching salary scale");
                }
            }
            
        }
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Salary Raise", "You have successfully made salary raise.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
