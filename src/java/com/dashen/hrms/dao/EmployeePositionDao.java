/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.Employee;
import com.dashen.hrms.EmployeePositionOrganizationalStructure;
import com.dashen.hrms.EmployeePosition;
import com.dashen.hrms.EmployeePositionSalaryScale;
import com.dashen.hrms.Position;
import com.dashen.hrms.RowStatus;
import com.dashen.hrms.TempStatus;
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
 * @author biniamt
 */

@Repository
@Transactional
public class EmployeePositionDao {
    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(EmployeePosition p) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(p);
    }

    public void update(EmployeePosition p) {
        Session session = sessionFactory.getCurrentSession();
        session.update(p);
    }

    public List<EmployeePosition> listTmpEmployeePositions() {
        List<EmployeePosition> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from EmployeePosition where TMP_STATUS = '" + TempStatus.SUBMITTED + "'");
        list = qr.list();
        return list;
    }

    public EmployeePosition getByID(String id) {
        Session session = sessionFactory.getCurrentSession();
        EmployeePosition empPosition = (EmployeePosition) session.get(EmployeePosition.class, id);
        return empPosition;
    }

    public List<EmployeePosition> getByMakerID(String id) {
        List<EmployeePosition> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp positions of made my a single user
        Query qr = session.createQuery("from EmployeePosition where CREATED_BY = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public void delete(EmployeePosition empPositionTmp) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(empPositionTmp);
    }

    public int employeePositionPendingEditCount(String id) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from EmployeePosition where id = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        return ((Long) qr.uniqueResult()).intValue();
    }

    public List<EmployeePosition> listTmpEmployeePositionsForMaker(String makerID) {
        List<EmployeePosition> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from EmployeePosition where CREATED_BY = '" + makerID + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public List<EmployeePosition> listEmployeePositions() {
        List<EmployeePosition> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from EmployeePosition where TMP_STATUS = '" + TempStatus.APPROVED + "'");
        list = qr.list();
        return list;
    }
    
    
    public void saveNewEmpPositionOrgStruct(EmployeePositionOrganizationalStructure empOrgStruct) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(empOrgStruct);
    }
    public void saveNewEmpPosSalaryScale(EmployeePositionSalaryScale empPosSalaryScale) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(empPosSalaryScale);
    }

    public void updateEmpPositionOrgStruct(EmployeePositionOrganizationalStructure empOrgStruct) {
        Session session = sessionFactory.getCurrentSession();
        session.update(empOrgStruct);
    }
    public void updateEmpPosSalaryScale(EmployeePositionSalaryScale empPosSalaryScale) {
        Session session = sessionFactory.getCurrentSession();
        session.update(empPosSalaryScale);
    }
    
    public List<EmployeePositionSalaryScale> listEmployeePositionSalaryScales() {
        List<EmployeePositionSalaryScale> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from EmployeePositionSalaryScale s where s.employeePosition.tmpStatus ='" + TempStatus.APPROVED + "' and s.rowStatus = '" + RowStatus.ACTIVE + "'");
        list = qr.list();
        return list;
    }

    public EmployeePositionOrganizationalStructure getEmpPositionOrgStructByID(String ID) {
        // get employee position organizational structure by its employee position id
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from EmployeePositionOrganizationalStructure p where p.employeePosition.ID = '"+ID+"'" );
        List<EmployeePositionOrganizationalStructure> l = qr.list();
        return l.get(0);
    }

    public EmployeePositionSalaryScale getEmpPosSalaryScaleByID(String ID) {
        // get employee position salary scale by its employee position id
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from EmployeePositionSalaryScale p where p.employeePosition.ID = '"+ID+"' and p.rowStatus = '" + RowStatus.ACTIVE + "'");
        List<EmployeePositionSalaryScale> l = qr.list();
        return l.get(0);
    }

    public void deleteEmpPositionOrgStruct(EmployeePositionOrganizationalStructure p) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(p);
    }

    public void deleteEmpPosSalaryScale(EmployeePositionSalaryScale p) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(p);
    }

    public boolean isEmpPosHasSalaryScale(EmployeePosition empPos) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from EmployeePositionSalaryScale p where p.employeePosition.ID = '" + empPos.getID() + "' and p.rowStatus = '" + RowStatus.ACTIVE + "'");
        return ((Long) qr.uniqueResult()).intValue() > 0;
    }

    public boolean isEmpPositionHasOrgStruct(EmployeePosition empPos) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from EmployeePositionOrganizationalStructure p where p.employeePosition.ID = '" + empPos.getID() + "')");
        return ((Long) qr.uniqueResult()).intValue() > 0;
    }

    public List<Position> filterPositions(String tierId, String orgStructId, String orgStructTypeId) {
        List<Position> list;
        Session session = sessionFactory.getCurrentSession();
        // String query = "select p from Position as p, PositionTier as pt, PositionHavingOrganizationalStructure as os where p.tmpStatus = 'APPROVED'  and os.organizationalStructure.id = 'ORG_STR_17' and p.organizationalStructureType.id= 'ORG_STR_TYPE_14'";
        String query = null;

        if (tierId != null) {
            if (orgStructId != null) {
                // tier and organizational structure are both selected
                query = "select p from Position as p left join PositionTier as pt on p.ID = pt.position.ID left join PositionHavingOrganizationalStructure as os on p.ID=os.position.ID  where os.organizationalStructure.id = '" +orgStructId+"' and pt.tier.id = '" + tierId +"' and p.tmpStatus = '"+ TempStatus.APPROVED +"'";
            } else {
                // tier is selected but organizational structure is not selected
                query = "select p from Position as p left join PositionTier as pt on p.ID = pt.position.ID where pt.tier.id = '" + tierId + "' and p.tmpStatus = '"+ TempStatus.APPROVED +"'";
            }
        } else if (orgStructId != null) {
            // tier is not selected but organizational structure is selected
            query = "select p from Position as p left join PositionHavingOrganizationalStructure as os on p.ID = os.position.ID where p.tmpStatus = '"+ TempStatus.APPROVED +"' and os.organizationalStructure.id = '" +orgStructId+"'";
        }
        if (orgStructTypeId != null) {
            if (query != null) {
                // organizational structure type is selected and at least one of tier or organizational structure is selected
                query += " and p.organizationalStructureType.id='"+orgStructTypeId+"'";
            } else {
                // organizational structure type is selected, but neither tier nor organizational structure is selected
                query = "from Position as p where p.tmpStatus='"+ TempStatus.APPROVED +"' and p.organizationalStructureType.id='"+orgStructTypeId+"'";
            }
        }

        System.out.println(query);

        Query qr = session.createQuery(query);
        list = qr.list();
        return list;
    }
    
    public List<Employee> findEmployeesWithTheSameOrgStructByEmpId(String Id) {
        
        List<Employee> list1;
        List<Employee> list2;
        List<Employee> list3;
        List<Employee> list4;
        Session session = sessionFactory.getCurrentSession();


        // String query = "select ep.employee from EmployeePosition ep where ep.position.ID in (select opos.position.ID from PositionHavingOrganizationalStructure as opos where opos.organizationalStructure.id in (select ipos.organizationalStructure.id from PositionHavingOrganizationalStructure ipos where ipos.position.ID in (select iep.position.ID from EmployeePosition iep where iep.employee.employeeSerialID = '" + Id + "' and iep.tmpStatus = '" + TempStatus.APPROVED +"') and ipos.position.tmpStatus = '" + TempStatus.APPROVED + "') and opos.position.tmpStatus = '" + TempStatus.APPROVED + "') and ep.tmpStatus = '" + TempStatus.APPROVED + "'";

        String query1 = "select ep.employee from EmployeePosition ep where ep.position.ID in (select opos.position.ID from PositionHavingOrganizationalStructure as opos where opos.organizationalStructure.id in (select ipos.organizationalStructure.id from PositionHavingOrganizationalStructure ipos where ipos.position.ID in (select iep.position.ID from EmployeePosition iep where iep.employee.employeeSerialID = '" + Id + "' and iep.tmpStatus = '" + TempStatus.APPROVED +"') and ipos.position.tmpStatus = '" + TempStatus.APPROVED + "') and opos.position.tmpStatus = '" + TempStatus.APPROVED + "') and ep.tmpStatus = '" + TempStatus.APPROVED + "'";

        String query2 = "select ep.employee from EmployeePosition ep where ep.ID in (select epos.employeePosition.ID from EmployeePositionOrganizationalStructure as epos where epos.organizationalStructure.id in (select ipos.organizationalStructure.id from PositionHavingOrganizationalStructure ipos where ipos.position.ID in (select iep.position.ID from EmployeePosition iep where iep.employee.employeeSerialID = '" + Id + "' and iep.tmpStatus = '" + TempStatus.APPROVED +"') and ipos.position.tmpStatus = '" + TempStatus.APPROVED + "')  ) and ep.tmpStatus = '" + TempStatus.APPROVED + "'";

        Query qr1 = session.createQuery(query1);
        Query qr2 = session.createQuery(query2);

        list1 = qr1.list();
        list2 = qr2.list();
        list1.addAll(list2);

        // String query2 = "select ep.employee from EmployeePosition ep where ep.ID in (select epos.employeePosition.ID from EmployeePositionOrganizationalStructure as epos where epos.organizationalStructure.id in (select iepos.organizationalStructure.id from EmployeePositionOrganizationalStructure iepos where iepos.employeePosition.ID in (select iep.ID from EmployeePosition iep where iep.employee.employeeSerialID = '" + Id + "' and iep.tmpStatus = '" + TempStatus.APPROVED + "')) ) and ep.tmpStatus = '" + TempStatus.APPROVED + "'";

        String query3 = "select ep.employee from EmployeePosition ep where ep.ID in (select epos.employeePosition.ID from EmployeePositionOrganizationalStructure as epos where epos.organizationalStructure.id in (select iepos.organizationalStructure.id from EmployeePositionOrganizationalStructure iepos where iepos.employeePosition.ID in (select iep.ID from EmployeePosition iep where iep.employee.employeeSerialID = '" + Id + "' and iep.tmpStatus = '" + TempStatus.APPROVED + "')) ) and ep.tmpStatus = '" + TempStatus.APPROVED + "'";

        String query4 = "select ep.employee from EmployeePosition ep where ep.position.ID in (select opos.position.ID from PositionHavingOrganizationalStructure as opos where opos.organizationalStructure.id in (select iepos.organizationalStructure.id from EmployeePositionOrganizationalStructure iepos where iepos.employeePosition.ID in (select iep.ID from EmployeePosition iep where iep.employee.employeeSerialID = '" + Id + "' and iep.tmpStatus = '" + TempStatus.APPROVED + "')) and opos.position.tmpStatus = '" + TempStatus.APPROVED + "') and ep.tmpStatus = '" + TempStatus.APPROVED + "'";

       Query qr3 = session.createQuery(query3);
       Query qr4 = session.createQuery(query4);

       list3 = qr3.list();
       list4 = qr4.list();

       list3.addAll(list4);


       list1.addAll(list3);

        return list1;
    }
}
