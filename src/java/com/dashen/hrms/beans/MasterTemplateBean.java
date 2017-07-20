/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.MyUser;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author MulugetaK
 */
@Component
@SessionScope
public class MasterTemplateBean implements Serializable {
    private MyUser loggedInUser = null;

    public MyUser getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(MyUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    
    @PostConstruct
    public void init()
    {
        loggedInUser = (MyUser)CurrentUser.getCurrentUser();
    }
}
