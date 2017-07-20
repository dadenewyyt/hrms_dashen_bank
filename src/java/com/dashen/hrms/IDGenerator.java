/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 *
 * @author MulugetaK
 */
public class IDGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor ssci, Object o) throws HibernateException {

        //set prefix and query
        String prefix = "";
        String type = o.getClass().getName();
        String query = "";
        if (type.equalsIgnoreCase("com.dashen.hrms.Employee")) {

            prefix = "EMP_";
            query = "SELECT Employee_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.EmployeeTmp")) {

            prefix = "EMP_TMP_";
            query = "SELECT Employee_Tmp_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.Experience")) {

            prefix = "EXP_";
            query = "SELECT experience_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.JobClassification")) {

            prefix = "CLA_";
            query = "SELECT Job_Classification_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.Institution")) {

            prefix = "INS_";
            query = "SELECT Institution_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.Qualification")) {

            prefix = "QUA_";
            query = "SELECT Qualification_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.QualificationTmp")) {

            prefix = "QUA_TMP_";
            query = "SELECT Qualification_tmp_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.Experience")) {
            prefix = "EXP_";
            query = "SELECT Experience_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.ExperienceTmp")) {
            prefix = "EXP_TMP_";
            query = "SELECT Experience_tmp_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.Referee")) {
            prefix = "REF_";
            query = "SELECT Referee_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.RefereeTmp")) {
            prefix = "REF_TMP_";
            query = "SELECT Referee_tmp_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.Position")) {
            prefix = "POS-";
            query = "SELECT position_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.JobLevel")) {
            prefix = "JOBLVL-";
            query = "SELECT job_level_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.Department")) {
            prefix = "DEP-";
            query = "SELECT department_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.JobGrade")) {
            prefix = "JOBGRD-";
            query = "SELECT job_grade_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.Salary")) {
        } else if (type.equalsIgnoreCase("com.dashen.hrms.SalaryScale")) {
            prefix = "SALSCL-";
            query = "SELECT salary_scale_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.EmployeeSalaryScale")) {
            prefix = "EMPSALSCL-";
            query = "SELECT emp_salary_scale_id_sequence.nextval from dual";
        }
        else if (type.equalsIgnoreCase("com.dashen.hrms.Salary")) {
            prefix = "SAL-";
            query = "SELECT salary_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.SalaryDetail")) {
            prefix = "SALDTL-";
            query = "SELECT salary_detail_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.EmployeeSalary")) {
            prefix = "EMPSAL-";
            query = "SELECT employee_salary_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.TempDetail")) {
            prefix = "TMPDTL-";
            query = "SELECT temp_detail_id_sequence.nextval from dual";
        }
        else if (type.equalsIgnoreCase("com.dashen.hrms.PositionHavingOrganizationalStructure")) {
            prefix = "PHOS-";
            query = "Select PositionHavingOrgS_ID_SEQUENCE.nextval from dual";
        }
        else if (type.equalsIgnoreCase("com.dashen.hrms.PositionTier")) {
            prefix = "POSTIER-";
            query = "Select POSITION_TIER_ID_SEQUENCE.nextval from dual";
        }
        else if (type.equalsIgnoreCase("com.dashen.hrms.EmployeePosition")) {
            prefix = "EMPOS-";
            query = "Select EMPLOYEE_POSITION_ID_SEQUENCE.nextval from dual";
        }
        else if (type.equalsIgnoreCase("com.dashen.hrms.EmployeePositionSalaryScale")) {
            prefix = "EMPOSALSCL-";
            query = "Select EMP_POS_SAL_SCALE_ID_SEQUENCE.nextval from dual";
        }
        else if (type.equalsIgnoreCase("com.dashen.hrms.EmployeePositionOrganizationalStructure")) {
            prefix = "EMPSTR-";
            query = "Select EMP_POS_ORG_STRUCT_ID_SEQUENCE.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.City")) {
            prefix = "CTY-";
            query = "SELECT CITY_ID_SEQUENCE.nextval from dual";
        }
        
        else if (type.equalsIgnoreCase("com.dashen.hrms.AllowanceType")) {
            prefix = "ALWSTYP-";
            query = "SELECT allowance_type_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.Allowance")) {
            prefix = "ALWS-";
            query = "SELECT allowance_id_sequence.nextval from dual";
        }

        else if (type.equalsIgnoreCase("com.dashen.hrms.AllowancePosition")) {
            prefix = "ALWSPOS-";
            query = "SELECT allowance_position_id_sequence.nextval from dual";
        }

        else if ( type.equalsIgnoreCase("com.dashen.hrms.AllowanceLocation")) {
            prefix ="ALWSLOC-";
            query = "SELECT allowance_location_id_sequence.nextval from dual";
        }

        else if (type.equalsIgnoreCase("com.dashen.hrms.AllowanceEmploymentCenter")) {
            prefix ="ALWSEMPCNTR-";
            query = "SELECT allowance_empcentr_id_sequence.nextval from dual";
        }

        else if ( type.equalsIgnoreCase("com.dashen.hrms.AllowanceTypeQuantitative")) {
            prefix = "ALWSTYPQUANT-";
            query = "select ALLOWANCE_TYPE_QUA_ID_SEQUENCE.nextval from dual";
        }

        else if (type.equalsIgnoreCase("com.dashen.hrms.OrganizationalStructureType")) {
            prefix = "ORG_STR_TYPE_";
            query = "SELECT ORG_Structure_Type_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.OrganizationalStructure")) {
            prefix = "ORG_STR_";
            query = "SELECT ORG_Structure_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.OrganizationalStructureTier")) {
            prefix = "ORG_STR_TIER_";
            query = "SELECT ORG_Structure_Tier_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.Tier")) {
            prefix = "TIER_";
            query = "SELECT Tier_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.Role")) {
            prefix = "ROLE_";
            query = "SELECT Role_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.RolePermission")) {
            prefix = "ROLE_PRM_";
            query = "SELECT Role_Permission_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.UserRole")) {
            prefix = "USER_ROLE_";
            query = "SELECT User_Role_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.ExternalVacancApplicants")) {
            prefix = "EXT_VAC_APP_";
            query = "SELECT Ext_Vac_App_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.HistoryLog")) {
            prefix = "HIS_LOG_";
            query = "SELECT His_Log_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.InternalVacancApplicants")) {
            prefix = "INT_VAC_APP_";
            query = "SELECT Int_Vac_App_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.StructureManPower")) {
            prefix = "STR_MAN_POW_";
            query = "SELECT Str_Man_Pow_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.Transfer")) {
            prefix = "TRA_";
            query = "SELECT Tra_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.Vacancy")) {
            prefix = "VAC_";
            query = "SELECT Vac_id_sequence.nextval from dual";
        } else if (type.equalsIgnoreCase("com.dashen.hrms.VacancyTransfer")) {
            prefix = "VAC_TRA_";
            query = "SELECT Vac_Tra_id_sequence.nextval from dual";
        } else if (o.getClass().equals(LeaveRequest.class)) {

            prefix = "LEAVE_";
            query = "SELECT leave_request_id_sequence.nextval from dual";
        } else if (o.getClass().equals(LeaveType.class)) {

            prefix = "LT";
            query = "SELECT LEAVE_TYPE_ID_SEQUENCE.nextval from dual";
        } else {
            return null;
        }
        Connection connection = ssci.connection();

        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                int nextVal = rs.getInt(1);
                String generatedId = prefix + nextVal;
                //System.out.println("Generated Id: " + generatedId);
                return generatedId;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return null;
        }
        return null;
    }

}
