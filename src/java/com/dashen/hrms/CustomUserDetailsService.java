/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import com.dashen.hrms.dao.AccountsDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 *
 * @author MulugetaK
 */
@Component
@ApplicationScope
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountsDao userDao;

    private HashMap<String, MyUser> myusers;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MyUser usr = userDao.findByUserName(username);
        if (usr != null) {
            List<GrantedAuthority> authorities = buildGrantedAuthoritiesForUser(usr);

            //User usToReturn = new User(usr.getUsername(), usr.getPassword(), authorities);
            return usr;
        } else {
            System.out.println("Username \"" + username + "\" not found.");
            throw new UsernameNotFoundException("User name not found.");
        }
//        if (myusers.containsKey(username)) {
//            MyUser us = myusers.get(username);
//            MyUser usToReturn = new MyUser(us.getUsername(), us.getPassword(), us.getAuthorities());
//            return usToReturn;
//        } else {
//            System.out.println("Username \"" + username + "\" not found.");
//            throw new UsernameNotFoundException("User name not found.");
//        }
    }

    private List<GrantedAuthority> buildGrantedAuthoritiesForUser(MyUser usr) {
        Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();

        for (UserRole uRole : usr.getUserRoles()) {
            Role rol = uRole.getRole();
            for (RolePermission rolePermission : rol.getRolePermissions()) {
                auths.add(new SimpleGrantedAuthority(rolePermission.getPermission()));
            }
        }

        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>(auths);
        return grantedAuths;
    }

    public CustomUserDetailsService() {
        myusers = new HashMap<String, MyUser>();
    }

    @PostConstruct
    public void init() {
//        List<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>();
//        userAuthorities.add(new SimpleGrantedAuthority(UserAuthority.USER));
//        userAuthorities.add(new SimpleGrantedAuthority(UserAuthority.MAKER));
//        userAuthorities.add(new SimpleGrantedAuthority(UserAuthority.PROFILE_MAKER));
//        myusers.put("pmaker1", new MyUser("pmaker1", "pass", userAuthorities));
//        myusers.put("maker2", new MyUser("maker2", "pass", userAuthorities));
//        myusers.put("maker3", new MyUser("maker3", "pass", userAuthorities));
//
//        List<GrantedAuthority> chserAuthorities = new ArrayList<GrantedAuthority>();
//        chserAuthorities.add(new SimpleGrantedAuthority(UserAuthority.USER));
//        chserAuthorities.add(new SimpleGrantedAuthority(UserAuthority.CHECKER));
//        chserAuthorities.add(new SimpleGrantedAuthority(UserAuthority.PROFILE_CHECKER));
//        myusers.put("pchecker1", new MyUser("pchecker1", "pass", chserAuthorities));
//        myusers.put("checker2", new MyUser("checker2", "pass", chserAuthorities));
//        myusers.put("checker3", new MyUser("checker3", "pass", chserAuthorities));
//
//        List<GrantedAuthority> adminAuthorities = new ArrayList<GrantedAuthority>();
//        adminAuthorities.add(new SimpleGrantedAuthority(UserAuthority.USER));
//        adminAuthorities.add(new SimpleGrantedAuthority(UserAuthority.ADMIN));
//        adminAuthorities.add(new SimpleGrantedAuthority(UserAuthority.HR_ADMIN));
//        myusers.put("hradmin1", new MyUser("hradmin1", "pass", adminAuthorities));
//        myusers.put("admin2", new MyUser("admin2", "pass", adminAuthorities));
//        myusers.put("admin3", new MyUser("admin3", "pass", adminAuthorities));
    }

//    public Collection<MyUser> getUsersList() {
//        return myusers.values();
//    }
}
