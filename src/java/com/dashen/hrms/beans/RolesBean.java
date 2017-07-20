/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.Permission;
import com.dashen.hrms.Role;
import com.dashen.hrms.RolePermission;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.service.AccountsService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author MulugetaK
 */
@Component
@SpringViewScope
public class RolesBean implements Serializable {

    @Autowired
    private AccountsService accService;

    private String newRoleName;
    private String newRoleDescription;
    private Set<RolePermission> newRolePermissions;
    private List<Permission> allPermissions;
    private DualListModel<String> permissions;

    public String getNewRoleName() {
        return newRoleName;
    }

    public void setNewRoleName(String newRoleName) {
        this.newRoleName = newRoleName;
    }

    public String getNewRoleDescription() {
        return newRoleDescription;
    }

    public void setNewRoleDescription(String newRoleDescription) {
        this.newRoleDescription = newRoleDescription;
    }

    public Set<RolePermission> getNewRolePermissions() {
        return newRolePermissions;
    }

    public void setNewRolePermissions(Set<RolePermission> newRolePermissions) {
        this.newRolePermissions = newRolePermissions;
    }

    public DualListModel<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(DualListModel<String> permissions) {
        this.permissions = permissions;
    }

    @PostConstruct
    public void init() {
        newRolePermissions = new HashSet<RolePermission>();
        allPermissions = accService.listAllPermissions();
        List<String> permisionsSource = new ArrayList<>();
        for (Permission p : allPermissions) {
            permisionsSource.add(p.getPermission());
        }
        List<String> permisionsTarget = new ArrayList<>();

        permissions = new DualListModel<>(permisionsSource, permisionsTarget);
    }

    public void btnCreateNewRole_Handler() {
        if (newRoleName != null) {
            if (accService.roleNameExists(newRoleName)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Create New Role", "Role name already exists. Please enter a different role name.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            if (permissions.getTarget().size() <= 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Create New Role", "Role must have at least one permission.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            Role newRole = new Role();
            for (String p : permissions.getTarget()) {
                RolePermission rp = new RolePermission();
                rp.setPermission(p);
                newRolePermissions.add(rp);
                rp.setRole(newRole);
            }

            newRole.setName(newRoleName);
            newRole.setDescription(newRoleDescription);
            newRole.setRolePermissions(newRolePermissions);

            if (accService.saveNewRole(newRole)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Create New Role", "New Role Created Successfully.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                
                newRoleName = null;
                newRoleDescription = null;
                newRolePermissions.clear();
                resetPermissionPickList();
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Create New Role", "Failed to create new role.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    private void resetPermissionPickList() {
        List<String> permisionsSource = new ArrayList<>();
        for (Permission p : allPermissions) {
            permisionsSource.add(p.getPermission());
        }
        List<String> permisionsTarget = new ArrayList<>();

        permissions = new DualListModel<>(permisionsSource, permisionsTarget);
    }

}
