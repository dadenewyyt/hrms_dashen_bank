/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.service.LeaveService;
import com.dashen.hrms.LeaveRequest;
import com.dashen.hrms.CustomUserDetailsService;
import com.dashen.hrms.Employee;
import com.dashen.hrms.LeaveEnum;
import com.dashen.hrms.LeaveType;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.LeaveRequestDao;
import com.dashen.hrms.service.EmployeePositionService;
import com.dashen.hrms.service.EmployeeService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Daniel Adenew
 */
@Component
@SpringViewScope
public class LeaveRequestBean {

    private LeaveRequest leaveRequesModel;

    private String ltype;
    private List<SelectItem> leaveType;
    private List<SelectItem> employees;

    @Autowired(required = true)
    LeaveRequestDao dao;

    @Autowired(required = true)
    CustomUserDetailsService userService;

    @Autowired(required = true)
    EmployeePositionService empPosService;

    @Autowired(required = true)
    EmployeeService empService;

    @Autowired(required = true)
    LeaveService leaveService;

    private LeaveRequestFileUploadBean leaveRequestFileUploadBean;

    private List<LeaveRequest> leaveRequests;

    private LeaveRequest selectedleaveRequest;

    private String displayFullNameCss = "display:none;";

    private String approverFullName = "";

    @Autowired
    LeaveService service;

    @PostConstruct
    public void init() {

        MyUser currentUserDetails = LeaveService.getCurrentUser();

        leaveRequests = service.listPendingLeaveRequestForMaker(currentUserDetails.getUsername());

        leaveRequesModel = new LeaveRequest();

        leaveType = new ArrayList<>();

        employees = new ArrayList<>();

        List<LeaveType> leaveTypes = new ArrayList<>();

        leaveTypes = leaveService.getLeaveTypes();

        List<Employee> employeeList = new ArrayList<>();

        String currentUserEmployeeId = currentUserDetails.getEmployee().getEmployeeSerialID(); //TODO vchange

        Employee currentUserEmployeeObject = currentUserDetails.getEmployee();

        employeeList = empPosService.findEmployeesWithTheSameOrgStructByEmpId("EMP_304");

        System.out.println(employeeList.size());

        employeeList.remove(currentUserEmployeeObject);

        for (Employee jbCl : employeeList) {
            employees.add(new SelectItem(jbCl.getEmployeeSerialID(), "EMP_ID (" + jbCl.getEmployeeID() + ") Full Name(" + jbCl.getFullName() + ")"));
        }
        for (LeaveType jbCl : leaveTypes) {
            leaveType.add(new SelectItem(jbCl.getID(), jbCl.getTitle()));
        }

    }

    public void refresh_leave() {

        MyUser currentUserDetails = LeaveService.getCurrentUser();
        leaveRequests = service.listPendingLeaveRequestForMaker(currentUserDetails.getUsername());

        System.out.println("Our Print Ln" + leaveRequests.size());
    }

    public void SaveNewLeaveRequest() {

        leaveRequestFileUploadBean = getCurrentLeaveRequestFileUploadBean();

        System.out.println(leaveRequestFileUploadBean.nextSessionId());
        
         System.out.println("hello");

       /* if (leaveRequesModel.getId() != null && !leaveRequesModel.getId().isEmpty()) {
            //is updating existing data
            MyUser currentUserDetails = LeaveService.getCurrentUser();
            leaveRequesModel.setMaker_user_id(currentUserDetails.getUsername());

            leaveRequesModel.setMaker_checker_status(TempStatus.EDITABLE);
            leaveRequesModel.setLeave_status(LeaveEnum.APPROVED);
            leaveRequesModel.setAttachemnt(leaveRequestFileUploadBean.getPreviewImage());
            leaveService.updateLeaveRequest(leaveRequesModel);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Leave Request", "Leave request is updated.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {//new data

            leaveRequesModel.getApprover_first_name();
            MyUser currentUserDetails = LeaveService.getCurrentUser();
            leaveRequesModel.setMaker_user_id(currentUserDetails.getUsername());
            leaveRequesModel.setMaker_checker_status(TempStatus.EDITABLE);
            leaveRequesModel.setLeave_status(LeaveEnum.APPROVED);
            leaveRequesModel.setAttachemnt(leaveRequestFileUploadBean.getPreviewImage());
            leaveService.saveNewLeaveRequest(leaveRequesModel);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Leave Request", "Leave Request is saved.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }

        leaveRequesModel = new LeaveRequest();

        refresh_leave();*/

        return;

    }

    public void btnDeleteLeaveRequest_Handler() {
        if (selectedleaveRequest != null) {
            if (leaveService.deleteLeaveRequest(selectedleaveRequest)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Leave Request", "Leave Request is deleted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                leaveRequesModel = new LeaveRequest();
                leaveRequests.remove(selectedleaveRequest);
            } else {
                //show delete has failed message
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete Leave Request", "Leave Request  is not deleted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Leave Request", "No Leave Request is selected.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void submit_leave() {

        //System.out.println(selectedleaveRequest);
        if (null == selectedleaveRequest) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rows Selected", "Please select rows!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {

            System.out.println("Approve");
            if (selectedleaveRequest.getMaker_checker_status() == TempStatus.EDITABLE) {
                System.out.println(selectedleaveRequest);
                selectedleaveRequest.setMaker_checker_status(TempStatus.SUBMITTED);
                if (service.updateLeaveRequest(selectedleaveRequest)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "LeaveRequests pending items approved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

                refresh_leave();

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void approveSelectedRows() {
        System.out.println(selectedleaveRequest);
        if (null == selectedleaveRequest) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No rows Selected", "Please select rows!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {

            System.out.println("Approve");
            if (selectedleaveRequest.getMaker_checker_status() == TempStatus.EDITABLE) {
                System.out.println(selectedleaveRequest);
                selectedleaveRequest.setMaker_checker_status(TempStatus.SUBMITTED);
                if (service.updateLeaveRequest(selectedleaveRequest)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved", "LeaveRequests pending items approved.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

                refresh_leave();

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approved Failed", "The selected row is not submitted for approval.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }

    }

    public void selectedApprover() {

        Employee approverDetails = empService.getByEmployeeSerialID(leaveRequesModel.getApproverRequestOwner().getEmployeeSerialID());

        // System.out.println(FacesContext.getCurrentInstance().getViewRoot().findComponent("inputForm:approverEmployeeSelect").getAttributes().get("label"));
        if (approverDetails.getEmployeeSerialID() != null) {

            approverFullName = approverDetails.getFullName();

            displayFullNameCss = "display:block;";
        } else {

            approverFullName = approverDetails.getFullName();

            displayFullNameCss = "display:block;";

        }

        leaveRequesModel.setApproverRequestOwner(approverDetails);

    }

    public LeaveRequestFileUploadBean getCurrentLeaveRequestFileUploadBean() {

        ELContext danLeavecontext = FacesContext.getCurrentInstance().getELContext();

        return (LeaveRequestFileUploadBean) danLeavecontext.getELResolver().getValue(danLeavecontext, null, "leaveRequestFileUploadBean");
    }

    public String getDisplayFullNameCss() {
        return displayFullNameCss;
    }

    public void setDisplayFullNameCss(String displayFullNameCss) {
        this.displayFullNameCss = displayFullNameCss;
    }

    public String getApproverFullName() {
        return approverFullName;
    }

    public void setApproverFullName(String approverFullName) {
        this.approverFullName = approverFullName;
    }

    public void setLeaveRequest(List<LeaveRequest> leaveRequest) {
        this.leaveRequests = leaveRequest;
    }

    public LeaveService getService() {
        return service;
    }

    public void setService(LeaveService service) {
        this.service = service;
    }

    public List<LeaveRequest> getLeaveRequests() {
        return leaveRequests;
    }

    public void setLeaveRequests(List<LeaveRequest> leaveRequests) {
        this.leaveRequests = leaveRequests;
    }

    public LeaveRequest getSelectedleaveRequest() {

        if (selectedleaveRequest == null) {
            selectedleaveRequest = new LeaveRequest();
        }
        return selectedleaveRequest;

    }

    public void setSelectedleaveRequest(LeaveRequest selectedleaveRequest) {
        this.selectedleaveRequest = selectedleaveRequest;
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Record Changed", "Old Value: " + oldValue + ",The New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public LeaveRequestDao getDao() {
        return dao;
    }

    public void setDao(LeaveRequestDao dao) {
        this.dao = dao;
    }

    public void leaveRequestDataTable_rowSelected(SelectEvent evt) {

        leaveRequesModel = selectedleaveRequest;

    }

    public LeaveRequestBean() {

    }

    public LeaveRequest getLeaveModel() {
        return leaveRequesModel;
    }

    public void setLeaveModel(LeaveRequest leaveModel) {
        this.leaveRequesModel = leaveModel;
    }

    public LeaveRequest getLeaveRequesModel() {
        return leaveRequesModel;
    }

    public void setLeaveRequesModel(LeaveRequest leaveRequesModel) {
        this.leaveRequesModel = leaveRequesModel;
    }

    public List<SelectItem> getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(List<SelectItem> leaveType) {
        this.leaveType = leaveType;
    }

    public CustomUserDetailsService getUserService() {
        return userService;
    }

    public void setUserService(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    public String getLtype() {
        return ltype;
    }

    public void setLtype(String ltype) {
        this.ltype = ltype;
    }

    public void calculate_leavehours() {

        Double hours = Double.parseDouble(leaveRequesModel.getTime_span_hours());
        hours *= 8.0;

        leaveRequesModel.setTime_span_hours(hours.toString());

    }

    public List<SelectItem> getEmployees() {
        return employees;
    }

    public void setEmployees(List<SelectItem> employees) {
        this.employees = employees;
    }

    public EmployeePositionService getEmpPosService() {
        return empPosService;
    }

    public void setEmpPosService(EmployeePositionService empPosService) {
        this.empPosService = empPosService;
    }

    public LeaveService getLeaveService() {
        return leaveService;
    }

    public void setLeaveService(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    public void LeaveRequestBean() {
        leaveRequesModel = new LeaveRequest();
    }

    public List<LeaveRequest> getLeaveRequest() {
        return leaveRequests;
    }

}
