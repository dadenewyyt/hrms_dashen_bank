/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.JobLevel;
import com.dashen.hrms.service.JobLevelService;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 *
 * @author biniamt
 */

@Component
@RequestScope
public class NewJobLevelBean {
    
    private JobLevel newJobLevel;
    
    @Autowired
    public JobLevelService jobLevelService;
    
    public JobLevel getNewJobLevel() {
        return newJobLevel;
    }
    
    public void setNewJobLevel(JobLevel newJobLevel) {
        this.newJobLevel = newJobLevel;
    }

    @PostConstruct
    public void init() {
        newJobLevel = new JobLevel();
    }
    
    public void btnSaveNewJobLevel_Handler() {
        jobLevelService.saveNewJobLevel(newJobLevel);
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Job Level", "New job level is saved.");
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }
}
