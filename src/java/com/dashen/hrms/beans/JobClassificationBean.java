/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.JobClassification;
import com.dashen.hrms.service.JobClassificationService;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 *
 * @author MulugetaK
 */
@Component
@RequestScope
public class JobClassificationBean {

    @Autowired
    private JobClassificationService jobClService;

    private JobClassification newJobClassification;

    public JobClassification getNewJobClassification() {
        return newJobClassification;
    }

    public void setNewJobClassification(JobClassification newJobClassification) {
        this.newJobClassification = newJobClassification;
    }

    public void setJobClService(JobClassificationService jobClService) {
        this.jobClService = jobClService;
    }

    
    @PostConstruct
    public void init() {
        newJobClassification = new JobClassification();
    }

    public void btnSaveNewJobClassification_Handler() {
        jobClService.saveNewJobClassification(newJobClassification);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Job Classification", "New Job Classification is added.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
