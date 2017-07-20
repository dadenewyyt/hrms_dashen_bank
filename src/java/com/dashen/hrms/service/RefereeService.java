/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.Referee;
import com.dashen.hrms.RefereeTmp;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.RefereeDao;
import com.dashen.hrms.dao.RefereeTmpDao;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mulugetak
 */
@Service
public class RefereeService {

    @Autowired
    RefereeDao refDao;

    @Autowired
    RefereeTmpDao refTmpDao;

    @Transactional
    public boolean saveNewReferee(RefereeTmp rT) {
        //new Qualification
        Referee newQ = new Referee();
        copyValuesFromTemp(newQ, rT);
        refDao.saveNew(newQ);

        //get current user (approver)
        MyUser myUsr = CurrentUser.getCurrentUser();
        rT.setTmpStatus(TempStatus.HISTORY);
        rT.setCheckerID(myUsr.getUsername());
        rT.setCheckerDate(new Date());

        refTmpDao.Update(rT);
        return true;
    }

    @Transactional
    public boolean updateReferee(RefereeTmp rT) {
        Referee rf = refDao.getByRefereeID(rT.getRefereeID());
        copyValuesFromTemp(rf, rT);
        Date approvedDate = new Date();
        refDao.update(rf);
        //get current user (approver)
        MyUser myUsr = CurrentUser.getCurrentUser();
        rT.setTmpStatus(TempStatus.HISTORY);
        rT.setCheckerID(myUsr.getUsername());
        rT.setCheckerDate(approvedDate);
        refTmpDao.Update(rT);
        return true;
    }

    @Transactional
    public List<Referee> listAll() {
        return refDao.listAll();
    }

    public void copyValuesFromTemp(Referee destR, RefereeTmp sourceRT) {
        destR.setId(sourceRT.getRefereeID());
        destR.setEmployeeSerialID(sourceRT.getEmployeeSerialID());
        destR.setFirstName(sourceRT.getFirstName());
        destR.setMiddleName(sourceRT.getMiddleName());
        destR.setLastName(sourceRT.getLastName());
        destR.setSalary(sourceRT.getSalary());
        destR.setKebeleId(sourceRT.getKebeleId());
        destR.setResidentialAddress(sourceRT.getResidentialAddress());
        destR.setEmployer(sourceRT.getEmployer());
        destR.setEmployerAddress(sourceRT.getEmployerAddress());
        destR.setFileName(sourceRT.getFileName());

    }

    @Transactional
    public List<Referee> listRefereesForEmployee(String empSerialId) {
        return refDao.getByEmployeeSerialID(empSerialId);
    }
}
