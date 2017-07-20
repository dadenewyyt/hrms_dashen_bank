package com.dashen.hrms.beans;


import com.dashen.hrms.service.LeaveService;
import com.dashen.hrms.LeaveUtilizationReport;
import com.dashen.hrms.SpringViewScope;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SpringViewScope
public class LeaveUtilizationBean implements Serializable {
    
    private List<LeaveUtilizationReport> reports ;

     
    private LeaveUtilizationReport SelectedLeaveUtilizationReport;

    public List<LeaveUtilizationReport> getReports() {
        return reports;
    }

    public void setReports(List<LeaveUtilizationReport> reports) {
        this.reports = reports;
    }

    public LeaveUtilizationReport getSelectedLeaveUtilizationReport() {
        return SelectedLeaveUtilizationReport;
    }

    public void setSelectedLeaveUtilizationReport(LeaveUtilizationReport SelectedLeaveUtilizationReport) {
        this.SelectedLeaveUtilizationReport = SelectedLeaveUtilizationReport;
    }
     
    @Autowired
    LeaveService service;
 
    @PostConstruct
    public void init() {

        reports =  service.generateReport();
       
        System.out.println("REPORT SIZE:"+reports.size());
       
    }

   
    public LeaveService getService() {
        return service;
    }

    public void setService(LeaveService service) {
        this.service = service;
    }
    
    public void btn_generate() {
       
        try {
             reports = service.call_sp();
            //Thread.sleep(2000);
            //service.generateReport();
        } catch (Exception ex) {
            Logger.getLogger(LeaveUtilizationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}