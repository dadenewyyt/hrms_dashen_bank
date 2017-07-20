/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.Permission;
import com.dashen.hrms.Role;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.dao.AccountsDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mulugetak
 */
@Service
public class AccountsService {

    @Autowired
    AccountsDao accDao;

    @Transactional
    public boolean saveNewUser(MyUser newUser) {
        //MyUser myUsr = CurrentUser.getCurrentUser();
        //newInst.setCreatedBy(myUsr.getUsername());
        //newInst.setCreatedDate(new Date());        
        accDao.saveNewUser(newUser);
        return true;
    }

    @Transactional
    public boolean usernameExists(String username) {
        return accDao.usernameExists(username);
    }

    @Transactional
    public boolean saveNewRole(Role r) {
        accDao.saveNewRole(r);
        return true;
    }

    @Transactional
    public boolean roleNameExists(String roleName) {
        return accDao.roleNameExists(roleName);
    }

    @Transactional
    public List<Permission> listAllPermissions() {
        return accDao.listAllPermissions();
    }

    @Transactional
    public List<Role> listAllRoles() {
        return accDao.listAllRoles();
    }
}
