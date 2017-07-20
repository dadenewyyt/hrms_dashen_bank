/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.LeaveRequest;
import com.dashen.hrms.LeaveUtilizationReport;
import com.dashen.hrms.TempStatus;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.ParameterMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel A
 */
@Repository
@Transactional
public class LeaveRequestDao {

    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(LeaveRequest q) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(q);
    }

    public void update(LeaveRequest q) {
        Session session = sessionFactory.getCurrentSession();
        session.update(q);
    }

    public List<LeaveRequest> listAll() {
        List<LeaveRequest> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from LeaveRequest");
        list = qr.list();
        return list;
    }

    public LeaveRequest getByLeaverequestID(String id) {
        Session session = sessionFactory.getCurrentSession();
        LeaveRequest ql = (LeaveRequest) session.get(LeaveRequest.class, id);
        return ql;
    }

    public List<LeaveRequest> getByEmployeeSerialID(String id) {
        List<LeaveRequest> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the qualifications of an employee
        Query qr = session.createQuery("from LeaveRequest where employee_serial_id = '" + id + "'");
        list = qr.list();
        return list;
    }
    
    public List<LeaveRequest> listAllLeaveRequestForMaker(String makerID) {
        List<LeaveRequest> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from LeaveRequest where MAKER_USER_ID = '" + makerID + "' and ( MAKER_CHECKER_STATUS = '" + TempStatus.EDITABLE + "' or MAKER_CHECKER_STATUS ='" + TempStatus.SUBMITTED + "')");        
        list = qr.list();
        return list;
    }
    
    public List<LeaveRequest> listPendingLeaveRequestForMaker(String makerID)
    {
        List<LeaveRequest> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from LeaveRequest where MAKER_USER_ID = '" + makerID + "' and ( MAKER_CHECKER_STATUS = '" + TempStatus.EDITABLE  +"')");
        list = qr.list();
        return list;
    }
    
    public List<LeaveRequest> listLeaveRequestForChecker(String checkerID)
    {
         List<LeaveRequest> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from LeaveRequest where MAKER_CHECKER_STATUS ='" + TempStatus.SUBMITTED + "'");
        list = qr.list();
        return list;
    }

    public List<LeaveRequest> listAllForMakerRole() {

        List<LeaveRequest> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from LeaveRequest where MAKER_CHECKER_STATUS ='" + TempStatus.EDITABLE + "'");
        list = qr.list();
        
       // System.out.println(list.get(0).getLeaveRequestOwner().getEmployeeID());
        return list;
    }
    
    public List<LeaveUtilizationReport> getAllLeaveUtilizationReport() {

        List<LeaveUtilizationReport> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from LeaveUtilizationReport");
        list = qr.list();
        
       // System.out.println(list.get(0).getLeaveRequestOwner().getEmployeeID());
        return list;
    }
    
    
    public List<LeaveUtilizationReport> call_procedure() {
       
       try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@192.168.12.170:1521:xe";
            Connection con = DriverManager.getConnection(url, "hr_user", "pass");
            
            String command = "{call sp_calculate_leave(?)}";
            CallableStatement cstmt = con.prepareCall(command);
            cstmt.setString("EMPLOYEE_SERIAL_ID", null);
            cstmt.execute();  
            cstmt.close();
            List<LeaveUtilizationReport> list ;
            Session session = sessionFactory.openSession().getSession();
            Query qr = session.createQuery("from LeaveUtilizationReport");
            list = qr.list();
            System.out.println("SUCCESS: ");
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    
    }
    
    public static void main(String[] args) {

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@192.168.12.170:1521:xe";
            Connection con = DriverManager.getConnection(url, "hr_user", "pass");
            System.out.println("Executing PROCEDURE");
            String command = "{call sp_calculate_leave(?)}";
            CallableStatement cstmt = con.prepareCall(command);
            cstmt.setString("EMPLOYEE_SERIAL_ID", null);
            cstmt.execute();  
            cstmt.close();
            System.out.println("SUCCESS: ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(LeaveRequest leaveRequest) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(leaveRequest);
    }
}
