/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.LeaveRequest;
import com.dashen.hrms.service.LeaveService;
import com.sun.media.jfxmedia.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 *
 * @author MulugetaK
 */
@Component
@RequestScope
public class LeaveRequestDetailBean {
   
    @Value("#{request.getParameter('id')}")
    private String LeaveId;

    @Autowired
    LeaveService leaveService;

    LeaveRequest leaveRequest;

    public LeaveService getLeaveService() {
        return leaveService;
    }

    public void setLeaveService(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    public LeaveRequest getLeaveRequest() {
        return leaveRequest;
    }

    public void setLeaveRequest(LeaveRequest leaveRequest) {
        this.leaveRequest = leaveRequest;
    }

  

    @PostConstruct
    public void init() {
        try{
        if (LeaveId != null && !LeaveId.isEmpty()) {
            leaveRequest = leaveService.getLeaveRequestById(LeaveId);
        } else {
            //error
        }
        } catch(Exception  ex) {
         System.out.println("| Leave request Details | ERROR | " +ex.getMessage() );

        }
    }

    public String getLeaveId() {
        return LeaveId;
    }

    public void setLeaveId(String LeaveId) {
        this.LeaveId = LeaveId;
    }

 
    
    

}
