/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.Permission;
import com.dashen.hrms.Role;
import com.dashen.hrms.MyUser;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mulugetak
 */
@Repository
@Transactional
public class AccountsDao {

    @Autowired
    SessionFactory sessionFactory;

    public MyUser findByUserName(String username) {

        List<MyUser> users = new ArrayList<MyUser>();

        users = sessionFactory.getCurrentSession()
                .createQuery("from MyUser where username=:username")
                .setParameter("username", username)
                .list();

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public void saveNewUser(MyUser u) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(u);
    }

    public void update(MyUser u) {
        Session session = sessionFactory.getCurrentSession();
        session.update(u);
    }

    public boolean usernameExists(String username) {
        username = username.trim();
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from MyUser where username = :username").setParameter("username", username).list().size() > 0;
    }

    public boolean roleNameExists(String roleName) {
        roleName = roleName.trim();
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Role where name = :name").setParameter("name", roleName).list().size() > 0;
    }

    public void saveNewRole(Role r) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(r);
    }

    public void updateRole(Role r) {
        Session session = sessionFactory.getCurrentSession();
        session.update(r);
    }
    
    public List<Role> listAllRoles() {
        List<Role> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Role");
        list = qr.list();
        return list;
    }

    public List<Permission> listAllPermissions() {
        List<Permission> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Permission");
        list = qr.list();
        return list;
    }
}
