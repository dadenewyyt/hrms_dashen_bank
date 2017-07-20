/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.Employee;
import com.dashen.hrms.EmployeePositionOrganizationalStructure;
import com.dashen.hrms.EmployeePosition;
import com.dashen.hrms.EmployeePositionSalaryScale;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.OrganizationalStructure;
import com.dashen.hrms.OrganizationalStructureType;
import com.dashen.hrms.Position;
import com.dashen.hrms.SalaryScale;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.service.EmployeePositionService;
import com.dashen.hrms.service.EmployeeService;
import com.dashen.hrms.service.PositionService;
import com.dashen.hrms.service.SalaryScaleService;
import com.dashen.hrms.service.StructureService;
import com.dashen.hrms.Tier;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author biniamt
 */

@Component
@SpringViewScope
public class EmployeePositionBean implements Serializable {
    private EmployeePosition currentTempEmpPos;
    private EmployeePositionOrganizationalStructure currentEmpPositionOrgStruct;
    private EmployeePositionSalaryScale currentEmpPosSalaryScale;
    private Position currentPosition;

    private OrganizationalStructureType currentOrgStructType;
    private OrganizationalStructure currentOrgStruct;
    private Tier currentTier;

    @Autowired
    private EmployeePositionService employeePositionService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PositionService positionService;
    
    @Autowired
    private StructureService structureService;
    
    @Autowired
    private SalaryScaleService salaryScaleService;

    private List<EmployeePosition> employeePositionsList;
    private EmployeePosition selectedEmployeePosition;

    private List<EmployeePosition> tempEmployeePositionsList;
    private EmployeePosition selectedTempEmployeePosition;

    private List<SelectItem> employeeList;
    private List<SelectItem> positionList;

    private List<SelectItem> orgStructList;
    
    private List<SelectItem> salaryScaleList;
    private List<SelectItem> organizationalStructureList;
    private List<SelectItem> tierList;
    private boolean orgStructureHasNoTier;
    private List<SelectItem> organizationalStructureTypeList;

    private List<OrganizationalStructure> availableOrgStructList;
    private List<SalaryScale> availableSalaryScaleList;
    private List<Tier> availableTiersList;
    private List<OrganizationalStructure> availabePositionFilterOrgStructList;
    private List<Position> availablePositionsList;

    private boolean positionHasOrgStruct;

    public OrganizationalStructureType getCurrentOrgStructType() {
        return currentOrgStructType;
    }

    public void setCurrentOrgStructType(OrganizationalStructureType currentOrgStructType) {
        this.currentOrgStructType = currentOrgStructType;
    }

    public OrganizationalStructure getCurrentOrgStruct() {
        return currentOrgStruct;
    }

    public void setCurrentOrgStruct(OrganizationalStructure currentOrgStruct) {
        this.currentOrgStruct = currentOrgStruct;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position p) {
        this.currentPosition = p;
    }

    public Tier getCurrentTier() {
        return currentTier;
    }

    public void setCurrentTier(Tier currentTier) {
        this.currentTier = currentTier;
    }

    public EmployeePosition getCurrentTempEmpPos() {
        return currentTempEmpPos;
    }

    public void setCurrentTempEmpPos(EmployeePosition currentTempEmpPos) {
        this.currentTempEmpPos = currentTempEmpPos;
    }

    public EmployeePositionOrganizationalStructure getCurrentEmpPositionOrgStruct() {
        return currentEmpPositionOrgStruct;
    }

    public void setCurrentEmpPositionOrgStruct(EmployeePositionOrganizationalStructure currentEmpPositionOrgStruct) {
        this.currentEmpPositionOrgStruct = currentEmpPositionOrgStruct;
    }

    public EmployeePositionSalaryScale getCurrentEmpPosSalaryScale() {
        return currentEmpPosSalaryScale;
    }

    public void setCurrentEmpPosSalaryScale(EmployeePositionSalaryScale currentEmpPosSalaryScale) {
        this.currentEmpPosSalaryScale = currentEmpPosSalaryScale;
    }

    public void setEmployeePositionService(EmployeePositionService employeePositionService) {
        this.employeePositionService = employeePositionService;
    }

    public List<EmployeePosition> getEmployeePositionsList() {
        return employeePositionsList;
    }

    public void setEmployeePositionsList(List<EmployeePosition> employeePositionsList) {
        this.employeePositionsList = employeePositionsList;
    }

    public EmployeePosition getSelectedEmployeePosition() {
        return selectedEmployeePosition;
    }

    public void setSelectedEmployeePosition(EmployeePosition selectedEmployeePosition) {
        this.selectedEmployeePosition = selectedEmployeePosition;
    }

    public List<EmployeePosition> getTempEmployeePositionsList() {
        return tempEmployeePositionsList;
    }

    public void setTempEmployeePositionsList(List<EmployeePosition> tempEmployeePositionsList) {
        this.tempEmployeePositionsList = tempEmployeePositionsList;
    }

    public EmployeePosition getSelectedTempEmployeePosition() {
        return selectedTempEmployeePosition;
    }

    public void setSelectedTempEmployeePosition(EmployeePosition selectedTempEmployeePosition) {
        this.selectedTempEmployeePosition = selectedTempEmployeePosition;
    }

    public List<SelectItem> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<SelectItem> employeeList) {
        this.employeeList = employeeList;
    }

    public List<SelectItem> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<SelectItem> positionList) {
        this.positionList = positionList;
    }

    public boolean isPositionHasOrgStruct() {
        return positionHasOrgStruct;
    }

    public void setPositionHasOrgStruct(boolean positionHasOrgStruct) {
        this.positionHasOrgStruct = positionHasOrgStruct;
    }

    public List<SelectItem> getOrgStructList() {
        return orgStructList;
    }

    public void setOrgStructList(List<SelectItem> orgStructList) {
        this.orgStructList = orgStructList;
    }

    public List<SelectItem> getSalaryScaleList() {
        return salaryScaleList;
    }

    public void setSalaryScaleList(List<SelectItem> salaryScaleList) {
        this.salaryScaleList = salaryScaleList;
    }

    public List<SelectItem> getOrganizationalStructureList() {
        return organizationalStructureList;
    }

    public void setOrganizationalStructureList(List<SelectItem> organizationalStructureList) {
        this.organizationalStructureList = organizationalStructureList;
    }

    public List<SelectItem> getTierList() {
        return tierList;
    }

    public void setTierList(List<SelectItem> tierList) {
        this.tierList = tierList;
    }

    public boolean isOrgStructureHasNoTier() {
        return orgStructureHasNoTier;
    }

    public void setOrgStructureHasNoTier(boolean orgStructureHasNoTier) {
        this.orgStructureHasNoTier = orgStructureHasNoTier;
    }

    public List<SelectItem> getOrganizationalStructureTypeList() {
        return organizationalStructureTypeList;
    }

    public void setOrganizationalStructureTypeList(List<SelectItem> organizationalStructureTypeList) {
        this.organizationalStructureTypeList = organizationalStructureTypeList;
    }

    @PostConstruct
    public void init() {
        currentTempEmpPos = new EmployeePosition();
        currentEmpPosSalaryScale = new EmployeePositionSalaryScale();
        currentEmpPositionOrgStruct = new EmployeePositionOrganizationalStructure();

        currentPosition = new Position();

        currentOrgStructType = new OrganizationalStructureType();
        currentOrgStruct = new OrganizationalStructure();
        currentTier = new Tier();

        employeePositionsList = employeePositionService.listEmployeePositions();
        tempEmployeePositionsList = new ArrayList<>();

        positionHasOrgStruct = true;

        employeeList = new ArrayList<>();
        positionList = new ArrayList<>();

        List<Employee> eList = employeeService.listAll();
        for(Employee e : eList) {
            employeeList.add(new SelectItem(e.getEmployeeSerialID(), e.getFullName()));
        }

        List<Position> pList = positionService.listPositions();
        for (Position p : pList) {
            positionList.add(new SelectItem(p.getID(), p.getTitle()));
        }

        organizationalStructureList = new ArrayList<>();
        
        List<OrganizationalStructure> sList = structureService.listAllOrganizationalStructures();
        sList.forEach((s) -> {
           organizationalStructureList.add(new SelectItem(s.getId(), s.getName()));
        });

        organizationalStructureTypeList = new ArrayList<>();
        
        List<OrganizationalStructureType> oList = structureService.listAllOrganizationalStructureTypes();
        oList.forEach((o) -> {
            organizationalStructureTypeList.add(new SelectItem(o.getId(), o.getName()));
        });

        orgStructureHasNoTier = false;
        tierList = new ArrayList();
        
        orgStructList = new ArrayList<>();
        
        salaryScaleList = new ArrayList<>();
    }

    public void positionFilterTypeSelectOneMenu_itemSelect() {
        tierList.clear();
        organizationalStructureList.clear();

        availableTiersList = structureService.listAllTiersForStructureType(currentOrgStructType.getId());
        for (Tier tr : availableTiersList) {
            tierList.add(new SelectItem(tr.getId(), tr.getName()));
        }
        if (availableTiersList.size() > 0) {
            //the selected type has Tiers and tier must be selected
            orgStructureHasNoTier = false;
            if (currentTier == null) {
                currentTier = new Tier();
            }
        } else {
            orgStructureHasNoTier = true;
        }

        if (currentOrgStructType.getId() != null) {
            availabePositionFilterOrgStructList = structureService.listOrganizationalStructuresOfAType(currentOrgStructType.getId());
            for (OrganizationalStructure o: availabePositionFilterOrgStructList) {
                organizationalStructureList.add(new SelectItem(o.getId(), o.getName()));
            }
        } else {
            availabePositionFilterOrgStructList = structureService.listAllOrganizationalStructures();
            availabePositionFilterOrgStructList.forEach((s) -> {
               organizationalStructureList.add(new SelectItem(s.getId(), s.getName()));
            }); 
        }
        positionFilterSelectOneMenu_itemSelect();
    }

    public void positionFilterSelectOneMenu_itemSelect() {
        positionList.clear();

        String tierId = currentTier.getId();
        String orgStructId = currentOrgStruct.getId();
        String orgStructTypeId = currentOrgStructType.getId();

        if (tierId != null || (orgStructId != null || orgStructTypeId != null)) {
            availablePositionsList = employeePositionService.filterPositions(tierId, orgStructId, orgStructTypeId);
            for (Position p: availablePositionsList) {
                positionList.add(new SelectItem(p.getID(), p.getTitle()));
            }
        }
    }

    public void typeSelectOneMenu_itemSelect() {
        orgStructList.clear();
        salaryScaleList.clear();
        
        Position position = positionService.getByID(currentTempEmpPos.getPosition().getID());
        currentPosition = position;
        
        availableSalaryScaleList = salaryScaleService.listSalaryScalesWithGradeID(position.getJobGrade().getID());
        for (SalaryScale s : availableSalaryScaleList) {
            salaryScaleList.add(new SelectItem(s.getID(), s.getLevel().getLevel()));
        }
        
        positionHasOrgStruct = position.isHasOrganizationalStructure();
        
        if (!positionHasOrgStruct) {
            availableOrgStructList = structureService.listAllOrganizationalStructures();
            for (OrganizationalStructure os : availableOrgStructList) {
                orgStructList.add(new SelectItem(os.getId(), os.getName()));
            }
            if (currentEmpPositionOrgStruct == null) {
                currentEmpPositionOrgStruct = new EmployeePositionOrganizationalStructure();
            }
        }
    }

    public void btnNewEmployeePosition_Handler() {
        currentTempEmpPos = new EmployeePosition();
        currentEmpPosSalaryScale = new EmployeePositionSalaryScale();
        currentEmpPositionOrgStruct = new EmployeePositionOrganizationalStructure();
        currentPosition = new Position();
    }

    public boolean isRowSubmitted() {
        if (currentTempEmpPos != null && currentTempEmpPos.getTmpStatus() == TempStatus.EDITABLE) {
            return false;
        }
        return true;
    }
    public void btnRefreshList_Handler() {
        MyUser user = CurrentUser.getCurrentUser();
        tempEmployeePositionsList = employeePositionService.listTmpEmployeePositionsForMaker(user.getUsername());
    }

    public boolean isEmpPosHasSalaryScale(EmployeePosition p) {
        return employeePositionService.isEmpPosHasSalaryScale(p);
    }

    public EmployeePositionSalaryScale getEmpPosSalaryScaleByID(String ID) {
        return employeePositionService.getEmpPosSalaryScaleByID(ID);
    }

    public boolean isEmpPositionHasOrgStruct(EmployeePosition p) {
        return employeePositionService.isEmpPositionHasOrgStruct(p);
    }

    public EmployeePositionOrganizationalStructure getEmpPositionOrgStructByID(String ID) {
        return employeePositionService.getEmpPositionOrgStructByID(ID);
    }

    public void tmpEmployeePositionsDataTableDataTable_rowSelected(SelectEvent evt) {
        currentTempEmpPos = selectedTempEmployeePosition;
        
        System.out.println("The id of current emp position is : " + currentTempEmpPos.getID());
        if (currentTempEmpPos.getPosition().isHasOrganizationalStructure() == false) {
            currentEmpPositionOrgStruct = employeePositionService.getEmpPositionOrgStructByID(currentTempEmpPos.getID());
            positionHasOrgStruct = false;
        } else {
            currentEmpPositionOrgStruct = new EmployeePositionOrganizationalStructure();
            positionHasOrgStruct = true;
        }
        typeSelectOneMenu_itemSelect();
        
        if (employeePositionService.isEmpPosHasSalaryScale(currentTempEmpPos)) {
            currentEmpPosSalaryScale = employeePositionService.getEmpPosSalaryScaleByID(currentTempEmpPos.getID());
        } else {
            currentEmpPosSalaryScale = new EmployeePositionSalaryScale();
        }
    }

    public void btnSaveEmployeePosition_Handler() {
        FacesMessage msg;
        if (currentTempEmpPos.getTmpStatus() == TempStatus.EDITABLE) {
            if (currentTempEmpPos.getID() != null && !currentTempEmpPos.getID().isEmpty()) {
                //is updating existing Temp EmployeePosition record
                if (employeePositionService.updateTmpEmployeePosition(currentTempEmpPos, currentEmpPositionOrgStruct, currentEmpPosSalaryScale, positionHasOrgStruct)) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Employee Position update", "Employee Position updates saved.");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Employee Position update", "Update Failed.");
                }
            } else if (currentTempEmpPos.getSourceId() != null && !currentTempEmpPos.getSourceId().isEmpty()) {//editing existing EmployeePosition, tmp object is new
                if (employeePositionService.saveNewTmpEmployeePosition(currentTempEmpPos, ActionType.UPDATE, currentEmpPositionOrgStruct, currentEmpPosSalaryScale, positionHasOrgStruct)) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Employee Position update", "Employee Position updates saved.");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Employee Position update", "Update Failed.");
                }
            } else//new EmployeePosition, and new tmp object
            {
                if (employeePositionService.saveNewTmpEmployeePosition(currentTempEmpPos, ActionType.CREATE, currentEmpPositionOrgStruct, currentEmpPosSalaryScale, positionHasOrgStruct)) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Employee Position", "New Employee Position added.");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Employee Position", "Save Failed.");
                }
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Employee Position", "The selected row is already submitted.");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        currentTempEmpPos = new EmployeePosition();
        currentEmpPosSalaryScale = new EmployeePositionSalaryScale();
        currentEmpPositionOrgStruct = new EmployeePositionOrganizationalStructure();
    }

    public void btnSubmit_Handler() {
        if (selectedTempEmployeePosition != null) {
            if (selectedTempEmployeePosition.getTmpStatus() != TempStatus.SUBMITTED) {
                selectedTempEmployeePosition.setTmpStatus(TempStatus.SUBMITTED);
                if (employeePositionService.updateTmpEmployeePosition(selectedTempEmployeePosition, currentEmpPositionOrgStruct, currentEmpPosSalaryScale, positionHasOrgStruct)) {
                    //update successful   
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "Row submitted.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    //update has failed, undo the change
                    selectedTempEmployeePosition.setTmpStatus(TempStatus.EDITABLE);
                }
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "The selected row is already submitted.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Submit Row", "No row selected.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void btnDeleteRow_Handler() {
        FacesMessage msg;
        if (selectedTempEmployeePosition != null) {
            if (selectedTempEmployeePosition.getTmpStatus() != TempStatus.SUBMITTED) {
                if (employeePositionService.delete(selectedTempEmployeePosition)) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "Row is deleted.");
                    currentTempEmpPos = new EmployeePosition();
                    currentEmpPosSalaryScale = new EmployeePositionSalaryScale();
                    currentEmpPositionOrgStruct = new EmployeePositionOrganizationalStructure();
                    tempEmployeePositionsList.remove(selectedTempEmployeePosition);
                } else {
                    //delete failed
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete Row", "Row is not deleted.");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete Row", "Submitted rows cannot be deleted.");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Row", "No row selected.");
            
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void btnEditSelectedEmployeePosition_Handler() {
        EmployeePosition employeePositionTmp = new EmployeePosition();
        if (selectedEmployeePosition != null) {
            if (!employeePositionService.checkIfEmployeePositionHasPendingEdit(selectedEmployeePosition.getID())) {
                employeePositionTmp.setSourceId(selectedEmployeePosition.getID());
                employeePositionTmp.setEmployee(selectedEmployeePosition.getEmployee());
                employeePositionTmp.setPosition(selectedEmployeePosition.getPosition());
                currentTempEmpPos = employeePositionTmp;

                if (employeePositionService.isEmpPosHasSalaryScale(selectedEmployeePosition)) {
                    EmployeePositionSalaryScale prevEmpPosSalaryScale = employeePositionService.getEmpPosSalaryScaleByID(selectedEmployeePosition.getID());
                    EmployeePositionSalaryScale s = new EmployeePositionSalaryScale();
                    s.setEmployeePosition(currentTempEmpPos);
                    s.setSalaryScale(prevEmpPosSalaryScale.getSalaryScale());
                    currentEmpPosSalaryScale = s;
                } else {
                    currentEmpPosSalaryScale = new EmployeePositionSalaryScale();
                }

                if (currentTempEmpPos.getPosition().isHasOrganizationalStructure() == false) {
                    EmployeePositionOrganizationalStructure prevEmpPositionOrgStruct = employeePositionService.getEmpPositionOrgStructByID(selectedEmployeePosition.getID());
                    EmployeePositionOrganizationalStructure e = new EmployeePositionOrganizationalStructure();
                    e.setEmployeePosition(currentTempEmpPos);
                    e.setOrganizationalStructure(prevEmpPositionOrgStruct.getOrganizationalStructure());
                    currentEmpPositionOrgStruct = e;
                    positionHasOrgStruct = false;
                } else {
                    currentEmpPositionOrgStruct = new EmployeePositionOrganizationalStructure();
                    positionHasOrgStruct = true;
                }
                typeSelectOneMenu_itemSelect();

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit EmployeePosition", "The selected employee position has pending changes. Please approve or cancel the changes and try again.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }
}
