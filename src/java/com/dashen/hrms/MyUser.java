/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author mulugetak
 */
@Entity
@Table(name = "tbl_user")
public class MyUser implements UserDetails, Serializable {

    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Transient
    private List<GrantedAuthority> authorities;

    @Type(type = "true_false")
    @Column(name = "ACCOUNT_EXPIRED")
    private boolean accountExpired;

    @Type(type = "true_false")
    @Column(name = "ACCOUNT_LOCKED")
    private boolean accountLocked;

    @Type(type = "true_false")
    @Column(name = "CREDENTIALS_EXPIRED")
    private boolean credentialsExpired;

    @Type(type = "true_false")
    @Column(name = "DISABLED")
    private boolean disabled;

    @ManyToOne()
    @JoinColumn(name = "EMPLOYEE_SERIAL_ID", insertable = true, updatable = false)
    private Employee employee;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<UserRole> userRoles;

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> userAuthorities = new ArrayList<>();
        for (UserRole usRole : userRoles) {
            for (RolePermission rPrm : usRole.getRole().getRolePermissions()) {
                userAuthorities.add(new SimpleGrantedAuthority(rPrm.getPermission()));
            }
        }
        return userAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }

}
