package com.dashen.hrms.service;


import com.dashen.hrms.LeaveRequest;
import com.dashen.hrms.LeaveType;
import com.dashen.hrms.LeaveUtilizationReport;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.dao.LeaveRequestDao;
import com.dashen.hrms.dao.LeaveTypeDao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;


@ApplicationScope
@Service
@Transactional
public class LeaveService {
     
    @Autowired
    LeaveRequestDao dao; 
    
    @Autowired
    LeaveTypeDao leaveTypedao; 
    
    public LeaveService() {}
    
    public List<LeaveRequest> getLeaveRequest() {
        
        List<LeaveRequest> list;
        list = dao.listAllForMakerRole();

         return list;
   
        
    }
    
    public List<LeaveType> getLeaveTypes() {
        
        List<LeaveType> list;
        list = leaveTypedao.listAll();
        return list;

        
    }
    
    
    public LeaveRequest getLeaveRequestById(String leaveID ) {
        
        LeaveRequest leave;
        leave = dao.getByLeaverequestID(leaveID);
        return leave;
        
    }
    
    
    public boolean delete(LeaveType leaveType) {
        leaveTypedao.delete(leaveType);
        return true;
    }
    
    
    public boolean deleteLeaveRequest(LeaveRequest leaveRequest) {
        dao.delete(leaveRequest);
        return true;
    }
    
   
    public boolean saveNewLeaveType(LeaveType leaveType) {
        
        leaveTypedao.saveNew(leaveType);
        return true;
    }
    
    public boolean updateLeaveRequest(LeaveRequest leaveRequest) {
        Double numDays = Double.parseDouble(String.valueOf(getNumdays(leaveRequest.getDate_from(), leaveRequest.getDate_to())));
        leaveRequest.setNumDays(numDays);
        dao.update(leaveRequest);
        return true;
    }
    
    
    public boolean saveNewLeaveRequest(LeaveRequest leaveRequest) {
        Double numDays = Double.parseDouble(String.valueOf(getNumdays(leaveRequest.getDate_from(), leaveRequest.getDate_to())));
        leaveRequest.setNumDays(numDays);
        if(leaveRequest.getNumDays()==0){
          leaveRequest.setNumDays(0.5);
        }
        dao.saveNew(leaveRequest);
        return true;
    }
     
   

   
    public List<LeaveRequest> listPendingLeaveRequestForMaker(String makerID) {
        return dao.listPendingLeaveRequestForMaker(makerID);
    }

   
    public List<LeaveRequest> listLeaveRequestForChecker(String checkerID) {
        return dao.listLeaveRequestForChecker(checkerID);
    }
    
    
   
    public void updateLeaveType(LeaveType leaveType) {
       leaveTypedao.update(leaveType);
    }
    
    
 
    
    public static MyUser getCurrentUser() {
        MyUser myUsr = null;
        try {
            SecurityContext sc = SecurityContextHolder.getContext();
            Object principal = sc.getAuthentication().getPrincipal();
            myUsr = (MyUser) principal;
        } catch (Exception ex) {

        }
        return myUsr;
    }
    
    public Long getNumdays(Date from ,Date to) {
        long diff = 0l;
        try {
        SimpleDateFormat mFormat = new SimpleDateFormat("dd-MMM-yyyy");
            
            String date_from = mFormat.format(from);
            String date_to = mFormat.format(to);
                 
            
            Date date1 = mFormat.parse(date_from);
            Date date2 = mFormat.parse(date_to);
            diff = date2.getTime() - date1.getTime();
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            diff =  TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            
        } catch (ParseException ex) {
            Logger.getLogger(LeaveService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
       return diff;
    }
    
    
    public List<LeaveUtilizationReport> generateReport() {
        List<LeaveUtilizationReport> reports= dao.getAllLeaveUtilizationReport();
        return reports;
    }
    
     public List<LeaveUtilizationReport> call_sp() {
         
      return dao.call_procedure();           
    }
    
   
     public static void main(String args[]) {
    
     
         LeaveService le = new LeaveService();
         String date_to = "07-JUL-2017";
         String date_from = "05-JUL-2017";
         SimpleDateFormat mFormat = new SimpleDateFormat("dd-MMM-yyyy");
 
         Long numdays = le.getNumdays(new Date("05-JUL-2017"),new Date("07-JUL-2017"));
    }
  
    
}
  