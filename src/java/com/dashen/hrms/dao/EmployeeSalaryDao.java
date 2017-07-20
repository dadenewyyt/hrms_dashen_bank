/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.EmployeeSalary;
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
public class EmployeeSalaryDao {
    @Autowired
    SessionFactory sessionFactory;    

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void save(EmployeeSalary employeeSalary)
    {
        Session session = sessionFactory.getCurrentSession();        
        session.saveOrUpdate(employeeSalary);
       
    }

    public List<EmployeeSalary> listAll()
    {
        List<EmployeeSalary> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from EmployeeSalary");
        list = qr.list();        
        return list;
    }
  
    public EmployeeSalary getByID(String ID)
    {
        Session session = sessionFactory.getCurrentSession();
        EmployeeSalary employeeSalary = (EmployeeSalary)session.get(EmployeeSalary.class, ID);        
        return employeeSalary;
    }
}