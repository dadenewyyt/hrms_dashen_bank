/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author mulugetak
 */
@Component
@SessionScope
public class MenuBean implements Serializable {

    private TreeNode root;

    @PostConstruct
    public void init() {
        root = new DefaultTreeNode(new MenuLink("/", "Menu"), null);
        
        TreeNode accountsManagement = new DefaultTreeNode(new MenuLink("", "Accounts Management"), root);
        
        TreeNode node12 = new DefaultTreeNode("document", new MenuLink("/HRMS/accounts/Users.xhtml", "Users"), accountsManagement);
        TreeNode node13 = new DefaultTreeNode("document", new MenuLink("/HRMS/accounts/Roles.xhtml", "Roles"), accountsManagement);
        
        
        TreeNode employeeProfileManageemnt = new DefaultTreeNode(new MenuLink("", "Employee Profile Management"), root);

        TreeNode node2 = new DefaultTreeNode("document", new MenuLink("/HRMS/profile/ListAllEmployees.xhtml", "List of Employees"), employeeProfileManageemnt);
        TreeNode node5 = new DefaultTreeNode("document", new MenuLink("/HRMS/profile/edit/EditEmployees.xhtml", "Edit Employees"), employeeProfileManageemnt);
        TreeNode node3 = new DefaultTreeNode("document", new MenuLink("/HRMS/profile/approve/EmployeePendingItems.xhtml", "Employee Pending Items"), employeeProfileManageemnt);
        TreeNode node6 = new DefaultTreeNode("document", new MenuLink("/HRMS/profile/approve/QualificationPendingItems.xhtml", "Qualification Pending Items"), employeeProfileManageemnt);
        TreeNode expNode = new DefaultTreeNode("document", new MenuLink("/HRMS/profile/approve/ExperiencePendingItems.xhtml", "Experience Pending Items"), employeeProfileManageemnt);
        TreeNode refNode = new DefaultTreeNode("document", new MenuLink("/HRMS/profile/approve/RefereePendingItems.xhtml", "Referee Pending Items"), employeeProfileManageemnt);
        
        TreeNode hrAdmin = new DefaultTreeNode(new MenuLink("", "HR Admin"), root);

        TreeNode node4 = new DefaultTreeNode("document", new MenuLink("/HRMS/hr/Institution.xhtml", "Institutions"), hrAdmin);      
        TreeNode node42 = new DefaultTreeNode("document", new MenuLink("/HRMS/hr/OrganizationalStructureType.xhtml", "Organizational Structure Types"), hrAdmin);      
        TreeNode node422 = new DefaultTreeNode("document", new MenuLink("/HRMS/hr/OrganizationalStructure.xhtml", "Organizational Structure"), hrAdmin);      
        TreeNode node423 = new DefaultTreeNode("document", new MenuLink("/HRMS/hr/OrganizationalStructureTypeTiers.xhtml", "Organizational Structure Type Tiers"), hrAdmin);      
        TreeNode node424 = new DefaultTreeNode("document", new MenuLink("/HRMS/hr/OrganizationStructure.xhtml", "Organization Structure"), hrAdmin); 
             
        TreeNode leaveManagemnt = new DefaultTreeNode(new MenuLink("", "Employee Leave Management"), root);
        TreeNode node44 = new DefaultTreeNode("document", new MenuLink("/HRMS/leave/LeaveRequest.xhtml", "Requested Leaves"), leaveManagemnt);
        TreeNode node66= new DefaultTreeNode("document", new MenuLink("/HRMS/leave/ListAllLeaveRequests.xhtml", "All Leaves Requested "), leaveManagemnt);
        TreeNode node77 = new DefaultTreeNode("document", new MenuLink("/HRMS/leave/LeaveType.xhtml", "Manage LeaveTypes "), leaveManagemnt);
        TreeNode node88 = new DefaultTreeNode("document", new MenuLink("/HRMS/leave/approve/LeaveRequestPendingItems.xhtml", "Pending Leave Requests "), leaveManagemnt);
        TreeNode node55 = new DefaultTreeNode("document", new MenuLink("/HRMS/leave/LeaveUtilized.xhtml", "Leave Utilization Report"), leaveManagemnt);


        TreeNode salaryTreeNode = new DefaultTreeNode(new MenuLink("", "Salary"), root);
        
        // TreeNode salaryNode1 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/Department.xhtml", "Department"), salaryTreeNode);
        
        TreeNode salaryNode2 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/JobGrades.xhtml", "Job Grades"), salaryTreeNode);
        TreeNode salaryNode3 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/EditJobGrades.xhtml", "Edit Job Grades"), salaryTreeNode);
        TreeNode salaryNode4 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/JobGradePendingApproval.xhtml", "Job Grade Pending Approval"), salaryTreeNode);
        
        TreeNode salaryNode5 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/JobLevels.xhtml", "Job Levels"), salaryTreeNode);
        TreeNode salaryNode6 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/EditJobLevels.xhtml", "Edit Job Levels"), salaryTreeNode);
        TreeNode salaryNode7 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/JobLevelPendingApproval.xhtml", "Job Level Pending Approval"), salaryTreeNode);
        
        TreeNode salaryNode8 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/Positions.xhtml", "Positions"), salaryTreeNode);
        TreeNode salaryNode9 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/EditPositions.xhtml", "Edit Positions"), salaryTreeNode);
        TreeNode salaryNode10 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/PositionPendingApproval.xhtml", "Position Pending Approval"), salaryTreeNode);
        
        TreeNode salaryNode21 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/EmployeePositions.xhtml", "Employee Positions"), salaryTreeNode);
        TreeNode salaryNode22 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/EditEmployeePositions.xhtml", "Edit Employee Positions"), salaryTreeNode);
        TreeNode salaryNode23 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/EmployeePositionPendingApproval.xhtml", "Employe Position Pending Approval"), salaryTreeNode);

        TreeNode salaryNode11 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/SalaryScales.xhtml", "Salary Scales"), salaryTreeNode);
        TreeNode salaryNode12 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/EditSalaryScales.xhtml", "Edit Salary Scales"), salaryTreeNode);
        TreeNode salaryNode13 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/SalaryScalePendingApproval.xhtml", "Salary Scale Pending Approval"), salaryTreeNode);
        
        TreeNode salaryNode20 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/SalaryRaise.xhtml", "Salary Raise"), salaryTreeNode);
        
        TreeNode salaryNode24 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/Cities.xhtml", "Cities"), salaryTreeNode);
        TreeNode salaryNode25 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/EditCities.xhtml", "Edit Cities"), salaryTreeNode);
        TreeNode salaryNode26 = new DefaultTreeNode("document", new MenuLink("/HRMS/salary/CityPendingApproval.xhtml", "City Pending Approval"), salaryTreeNode);

        TreeNode allowanceTreeNode = new DefaultTreeNode(new MenuLink("", "Allowance"), root);
        
        TreeNode allowanceNode1 = new DefaultTreeNode("document", new MenuLink("/HRMS/allowance/AllowanceTypes.xhtml", "Allowance Types"), allowanceTreeNode);
        TreeNode allowanceNode2 = new DefaultTreeNode("document", new MenuLink("/HRMS/allowance/EditAllowanceTypes.xhtml", "Edit Allowance Types"), allowanceTreeNode);
        TreeNode allowanceNode3 = new DefaultTreeNode("document", new MenuLink("/HRMS/allowance/AllowanceTypePendingApproval.xhtml", "Allowance Type Pending Approval"), allowanceTreeNode);
        TreeNode allowanceNode4 = new DefaultTreeNode("document", new MenuLink("/HRMS/allowance/Allowances.xhtml", "Allowances"), allowanceTreeNode);
        TreeNode allowanceNode5 = new DefaultTreeNode("document", new MenuLink("/HRMS/allowance/EditAllowances.xhtml", "Edit Allowances"), allowanceTreeNode);
        TreeNode allowanceNode6 = new DefaultTreeNode("document", new MenuLink("/HRMS/allowance/AllowancePendingApproval.xhtml", "Allowance Pending Approval"), allowanceTreeNode);
        
        TreeNode strManPowTreeNode = new DefaultTreeNode(new MenuLink("", "Structure Man Power"), root);
            TreeNode strManPowNode1 = new DefaultTreeNode("document", new MenuLink("/HRMS/Structure Man Power/StructureManPower.xhtml", "Structure Man Power Info"), strManPowTreeNode);
            
        
        TreeNode transferTreeNode = new DefaultTreeNode(new MenuLink("", "Transfer"), root);
            TreeNode transferNode1 = new DefaultTreeNode("document", new MenuLink("/HRMS/Transfer/Transfer.xhtml", "Non - Vacant Transfer Info"), transferTreeNode);
            TreeNode transferNode2 = new DefaultTreeNode("document", new MenuLink("/HRMS/Transfer/VacancyTransfer.xhtml", "Vacancy Transfer Info"), transferTreeNode);
        
        TreeNode vacancyTreeNode = new DefaultTreeNode(new MenuLink("", "Vacancy"), root);
            TreeNode vacancyNode1 = new DefaultTreeNode("document", new MenuLink("/HRMS/Vacancy/Vacancy.xhtml", "Vacancy Info"), vacancyTreeNode);
            TreeNode vacancyNode2 = new DefaultTreeNode("document", new MenuLink("/HRMS/Vacancy/ExternalVacancyApplicants.xhtml", "External Vacancy Applicants Info"), vacancyTreeNode);
            TreeNode vacancyNode3 = new DefaultTreeNode("document", new MenuLink("/HRMS/Vacancy/InternalVacancyApplicants.xhtml", "Internal Vacancy Applicants Info"), vacancyTreeNode);
            
    }

    public TreeNode getRoot() {
        return root;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(((MenuLink) event.getTreeNode().getData()).link);
        } catch (IOException ex) {
            Logger.getLogger(MenuBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onNodeUnSelect(NodeUnselectEvent event) {

    }

    public void onNodeExpand(NodeExpandEvent event) {

    }

    public void onNodeCollapse(NodeCollapseEvent event) {

    }

    public void logOut(ActionEvent actionEvent) {

        
    }

    public static class MenuLink implements Serializable {

        private String link = "";
        private String description = "";

        public MenuLink(String link, String desc) {
            this.link = link;
            this.description = desc;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
