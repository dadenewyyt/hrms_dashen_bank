/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.Employee;
import com.dashen.hrms.Role;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.UserRole;
import com.dashen.hrms.service.AccountsService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author mulugetak
 */
@Component
@SpringViewScope
public class UsersBean implements Serializable {

    @Autowired
    private AccountsService accService;

    @Autowired
    private PasswordEncoder passwrdEncoder;

    private MyUser newUser;
    private String newUsername;
    private String newPassword;
    private String confirmNewPassword;
    private List<Role> allRoles;
    private DualListModel<String> rolesDualList;

    @Autowired
    private LazyDataModel<Employee> lazyEmployeeModel;

    private Employee selectedEmployee;

    public LazyDataModel<Employee> getLazyEmployeeModel() {
        return lazyEmployeeModel;
    }

    public MyUser getNewUser() {
        return newUser;
    }

    public void setNewUser(MyUser newUser) {
        this.newUser = newUser;
    }

    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employee selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public DualListModel<String> getRolesDualList() {
        return rolesDualList;
    }

    public void setRolesDualList(DualListModel<String> rolesDualList) {
        this.rolesDualList = rolesDualList;
    }

    @PostConstruct
    public void init() {
        allRoles = accService.listAllRoles();

        List<String> rolesSource = new ArrayList<>();
        for (Role r : allRoles) {
            rolesSource.add(r.getName());
        }
        List<String> rolesTarget = new ArrayList<>();
        rolesDualList = new DualListModel<>(rolesSource, rolesTarget);
    }

    public void employeesDataTable_rowSelected(SelectEvent event) {
        Employee e = (Employee) event.getObject();

    }

    public void btnCreateNewUser_Handler() {
        if (newUsername != null) {
            if (accService.usernameExists(newUsername)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Create New User", "Username already exists. Please enter a different username.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            if (newPassword.equals(confirmNewPassword)) {
                //create the user
                if (selectedEmployee == null) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Create New User", "Employee must be slected.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return;
                } else {
                    MyUser newUser = new MyUser();
                    newUser.setUsername(newUsername);
                    newUser.setEmployee(selectedEmployee);
                    newUser.setPassword(passwrdEncoder.encode(newPassword));
                    newUser.setAccountExpired(false);
                    newUser.setAccountLocked(false);
                    newUser.setCredentialsExpired(false);
                    newUser.setDisabled(false);

                    Set<UserRole> newUserRoles = new HashSet<>();
                    for (String roleName : rolesDualList.getTarget()) {
                        UserRole ur = new UserRole();
                        ur.setUser(newUser);
                        ur.setRole(getRoleFromList(roleName));
                        newUserRoles.add(ur);
                    }

                    newUser.setUserRoles(newUserRoles);
                    if (accService.saveNewUser(newUser)) {
                        newUsername = null;
                        newPassword = null;
                        confirmNewPassword = null;
                        resetRolesPickList();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Create New User", "New user created successfully.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Create New User", "New user not created.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Create New User", "Password and Confirm password do not match.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }

        }
    }

    private Role getRoleFromList(String roleName) {
        for (Role r : allRoles) {
            if (r.getName().equalsIgnoreCase(roleName)) {
                return r;
            }
        }
        return null;
    }

    private void resetRolesPickList() {
        List<String> rolesSource = new ArrayList<>();
        for (Role r : allRoles) {
            rolesSource.add(r.getName());
        }
        List<String> rolesTarget = new ArrayList<>();
        rolesDualList = new DualListModel<>(rolesSource, rolesTarget);
    }
}
