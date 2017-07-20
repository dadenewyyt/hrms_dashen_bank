/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.Department;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author biniamt
 */


@Repository
@Transactional
public class DepartmentDao {
    @Autowired
    SessionFactory sessionFactory;    

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void save(Department department)
    {
        Session session = sessionFactory.getCurrentSession();        
        session.saveOrUpdate(department);  
       
    }

    public List<Department> listAll()
    {
        List<Department> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Department");
        list = qr.list();        
        return list;
    }
  
    public Department getByID(String ID)
    {
        Session session = sessionFactory.getCurrentSession();
        Department department = (Department)session.get(Department.class, ID);        
        return department;
    }

    public void delete(Department department) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(department);
    }
}