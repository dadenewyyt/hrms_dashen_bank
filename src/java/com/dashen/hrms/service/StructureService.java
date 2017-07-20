/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.MyUser;
import com.dashen.hrms.OrganizationalStructure;
import com.dashen.hrms.OrganizationalStructureTier;
import com.dashen.hrms.OrganizationalStructureType;
import com.dashen.hrms.RowStatus;
import com.dashen.hrms.Tier;
import com.dashen.hrms.dao.StructureDao;
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
public class StructureService {

    @Autowired
    StructureDao strDao;

    @Transactional
    public boolean createRootOfOrganizationalStructure(OrganizationalStructure osRoot) {
        MyUser myUsr = CurrentUser.getCurrentUser();
        osRoot.setMakerID(myUsr.getUsername());
        osRoot.setMakerDate(new Date());
        osRoot.setActionType(ActionType.CREATE);
        osRoot.setRowStatus(RowStatus.ACTIVE);
        strDao.saveNewOrganizationalStructure(osRoot);

        OrganizationalStructure osRootHist = new OrganizationalStructure();
        osRootHist.setSourceID(osRoot.getId());
        osRootHist.setParentID(osRoot.getParentID());
        osRootHist.setName(osRoot.getName());
        osRootHist.setOrganizationalStructureTypeID(osRoot.getOrganizationalStructureTypeID());
        osRootHist.setCity(osRoot.getCity());
        osRootHist.setMakerID(osRoot.getMakerID());
        osRootHist.setMakerDate(osRoot.getMakerDate());
        osRootHist.setActionType(osRoot.getActionType());
        osRootHist.setRowStatus(RowStatus.HISTORY);
        strDao.saveHistoryOrganizationalStructure(osRootHist);
        return true;
    }

    public OrganizationalStructure getRootOfStructure() {
        return strDao.getRootOfStructure();
    }

    public List<OrganizationalStructure> getChildrenOfStructure(String parentID) {
        return strDao.getChildrenOfStructure(parentID);
    }
    
    @Transactional
    public boolean saveNewOrganizationalStructureType(OrganizationalStructureType newOST) {
        MyUser myUsr = CurrentUser.getCurrentUser();
        newOST.setMakerID(myUsr.getUsername());
        newOST.setMakerDate(new Date());
        newOST.setActionType(ActionType.CREATE);
        newOST.setRowStatus(RowStatus.ACTIVE);
        strDao.saveNewOrganizationalStructureType(newOST);

        OrganizationalStructureType ostHist = new OrganizationalStructureType();
        ostHist.setSourceID(newOST.getId());
        ostHist.setName(newOST.getName());
        ostHist.setHasTier(newOST.isHasTier());
        ostHist.setMakerID(newOST.getMakerID());
        ostHist.setMakerDate(newOST.getMakerDate());
        ostHist.setActionType(newOST.getActionType());
        ostHist.setRowStatus(RowStatus.HISTORY);
        strDao.saveHistoryOrganizationalStructureType(ostHist);
        return true;
    }

    @Transactional
    public boolean updateOrganizationalStructureType(OrganizationalStructureType ost) {
        MyUser myUsr = CurrentUser.getCurrentUser();
        ost.setMakerID(myUsr.getUsername());
        ost.setMakerDate(new Date());
        ost.setActionType(ActionType.UPDATE);
        strDao.updateOrganizationalStructureType(ost);

        OrganizationalStructureType ostHist = new OrganizationalStructureType();
        ostHist.setSourceID(ost.getId());
        ostHist.setName(ost.getName());
        ostHist.setHasTier(ost.isHasTier());
        ostHist.setMakerID(ost.getMakerID());
        ostHist.setMakerDate(ost.getMakerDate());
        ostHist.setActionType(ost.getActionType());
        ostHist.setRowStatus(RowStatus.HISTORY);
        strDao.saveHistoryOrganizationalStructureType(ostHist);
        return true;
    }

    @Transactional
    public boolean saveNewOrganizationalStructure(OrganizationalStructure newOS, OrganizationalStructureTier newOrgStructTier) {
        MyUser myUsr = CurrentUser.getCurrentUser();
        newOS.setMakerID(myUsr.getUsername());
        newOS.setMakerDate(new Date());
        newOS.setActionType(ActionType.CREATE);
        newOS.setRowStatus(RowStatus.ACTIVE);
        strDao.saveNewOrganizationalStructure(newOS);

        OrganizationalStructure osHist = new OrganizationalStructure();
        osHist.setSourceID(newOS.getId());
        osHist.setParentID(newOS.getParentID());
        osHist.setName(newOS.getName());
        osHist.setOrganizationalStructureTypeID(newOS.getOrganizationalStructureTypeID());
        osHist.setCity(newOS.getCity());
        osHist.setMakerID(newOS.getMakerID());
        osHist.setMakerDate(newOS.getMakerDate());
        osHist.setActionType(newOS.getActionType());
        osHist.setRowStatus(RowStatus.HISTORY);
        strDao.saveHistoryOrganizationalStructure(osHist);

        //check if tier is selected for the organizational structure, if so save it.
        if (newOrgStructTier != null) {
            newOrgStructTier.setOrganizationalStructureID(newOS.getId());
            saveNewOrganizationalStructureTier(newOrgStructTier);
        }
        return true;
    }

    @Transactional
    public boolean updateOrganizationalStructure(OrganizationalStructure os, boolean orgStructureHasNoTier, OrganizationalStructureTier osTier) {
        MyUser myUsr = CurrentUser.getCurrentUser();
        os.setMakerID(myUsr.getUsername());
        os.setMakerDate(new Date());
        os.setActionType(ActionType.UPDATE);
        strDao.updateOrganizationalStructure(os);

        OrganizationalStructure osHist = new OrganizationalStructure();
        osHist.setSourceID(os.getId());
        osHist.setParentID(os.getParentID());
        osHist.setName(os.getName());
        osHist.setOrganizationalStructureTypeID(os.getOrganizationalStructureTypeID());
        osHist.setCity(os.getCity());
        osHist.setMakerID(os.getMakerID());
        osHist.setMakerDate(os.getMakerDate());
        osHist.setActionType(os.getActionType());
        osHist.setRowStatus(RowStatus.HISTORY);
        strDao.saveHistoryOrganizationalStructure(osHist);

        if (orgStructureHasNoTier) {
            //check if the org structure have associated tier prviously, that shold be deleted
            if (osTier != null) {
                //delete the associated tier
                if (osTier.getId() != null && !osTier.getId().isEmpty()) {
                    deleteOrganizationalStructureTier(osTier);
                }
            }
        } else if (osTier != null) {
            if (osTier.getId() != null && !osTier.getId().isEmpty()) {
                //update existing org tier
                updateOrganizationalStructureTier(osTier);
            } else {
                //new org tier
                osTier.setOrganizationalStructureID(os.getId());
                saveNewOrganizationalStructureTier(osTier);
            }
        }
        return true;
    }

    @Transactional
    public boolean saveNewOrganizationalStructureTier(OrganizationalStructureTier newOSTier) {
        MyUser myUsr = CurrentUser.getCurrentUser();
        newOSTier.setMakerID(myUsr.getUsername());
        newOSTier.setMakerDate(new Date());
        newOSTier.setActionType(ActionType.CREATE);
        newOSTier.setRowStatus(RowStatus.ACTIVE);
        strDao.saveNewOrganizationalStructureTier(newOSTier);

        OrganizationalStructureTier osTierHist = new OrganizationalStructureTier();
        osTierHist.setSourceID(newOSTier.getId());
        osTierHist.setOrganizationalStructureID(newOSTier.getOrganizationalStructureID());
        osTierHist.setTierID(newOSTier.getTierID());
        osTierHist.setMakerID(newOSTier.getMakerID());
        osTierHist.setMakerDate(newOSTier.getMakerDate());
        osTierHist.setActionType(newOSTier.getActionType());
        osTierHist.setRowStatus(RowStatus.HISTORY);
        strDao.saveHistoryOrganizationalStructureTier(osTierHist);
        return true;
    }

    @Transactional
    public boolean updateOrganizationalStructureTier(OrganizationalStructureTier osTier) {
        MyUser myUsr = CurrentUser.getCurrentUser();
        osTier.setMakerID(myUsr.getUsername());
        osTier.setMakerDate(new Date());
        osTier.setActionType(ActionType.UPDATE);
        strDao.updateOrganizationalStructureTier(osTier);

        OrganizationalStructureTier osTierHist = new OrganizationalStructureTier();
        osTierHist.setSourceID(osTier.getId());
        osTierHist.setOrganizationalStructureID(osTier.getOrganizationalStructureID());
        osTierHist.setTierID(osTier.getTierID());
        osTierHist.setMakerID(osTier.getMakerID());
        osTierHist.setMakerDate(osTier.getMakerDate());
        osTierHist.setActionType(osTier.getActionType());
        osTierHist.setRowStatus(RowStatus.HISTORY);
        strDao.saveHistoryOrganizationalStructureTier(osTierHist);
        return true;
    }

    @Transactional
    public boolean deleteOrganizationalStructureTier(OrganizationalStructureTier osTier) {
        MyUser myUsr = CurrentUser.getCurrentUser();
        osTier.setMakerID(myUsr.getUsername());
        osTier.setMakerDate(new Date());
        osTier.setActionType(ActionType.DELETE);
        osTier.setRowStatus(RowStatus.HISTORY);
        strDao.updateOrganizationalStructureTier(osTier);
        return true;
    }

    @Transactional
    public boolean saveNewTier(Tier newTier) {
        MyUser myUsr = CurrentUser.getCurrentUser();
        newTier.setMakerID(myUsr.getUsername());
        newTier.setMakerDate(new Date());
        newTier.setActionType(ActionType.CREATE);
        newTier.setRowStatus(RowStatus.ACTIVE);
        strDao.saveNewTier(newTier);

        Tier trHist = new Tier();
        trHist.setSourceID(newTier.getId());
        trHist.setName(newTier.getName());
        trHist.setOrganizationalStructureTypeID(newTier.getOrganizationalStructureTypeID());
        trHist.setMakerID(newTier.getMakerID());
        trHist.setMakerDate(newTier.getMakerDate());
        trHist.setActionType(newTier.getActionType());
        trHist.setRowStatus(RowStatus.HISTORY);
        strDao.saveHistoryTier(trHist);
        return true;
    }

    @Transactional
    public boolean updateTier(Tier tr) {
        MyUser myUsr = CurrentUser.getCurrentUser();
        tr.setMakerID(myUsr.getUsername());
        tr.setMakerDate(new Date());
        tr.setActionType(ActionType.UPDATE);
        strDao.updateTier(tr);

        Tier trHist = new Tier();
        trHist.setSourceID(tr.getId());
        trHist.setName(tr.getName());
        trHist.setOrganizationalStructureTypeID(tr.getOrganizationalStructureTypeID());
        trHist.setMakerID(tr.getMakerID());
        trHist.setMakerDate(tr.getMakerDate());
        trHist.setActionType(tr.getActionType());
        trHist.setRowStatus(RowStatus.HISTORY);
        strDao.saveHistoryTier(trHist);
        return true;
    }

    public List<OrganizationalStructureType> listAllOrganizationalStructureTypes() {
        return strDao.listAllOrganizationalStructureTypes();

    }

    public List<OrganizationalStructure> listAllOrganizationalStructures() {
        return strDao.listAllOrganizationalStructures();

    }

    public List<Tier> listAllTiersForStructureType(String structureTypeID) {
        return strDao.listAllTiersForStructureType(structureTypeID);
    }

    public OrganizationalStructureTier getStructureTierForStructure(String structureID) {
        return strDao.getStructureTierForStructure(structureID);
    }

    public boolean isRootOfStructureCreated() {
        return strDao.isRootOfStructureCreated();
    }

    public List<OrganizationalStructure> listOrganizationalStructuresOfAType(String orgStructTypeId) {
        return strDao.listOrganizationalStructuresOfAType(orgStructTypeId);
    }
    
    @Transactional
    public Tier getTierByID(String ID) {
        return strDao.getTierByID(ID);
    }
    
    @Transactional
    public OrganizationalStructure getByOrganizationalStructureId(String organizationalStructureId) {
        return strDao.getByOrganizationalStructureId(organizationalStructureId);
    }
    
    @Transactional
    public OrganizationalStructureType  getByOrganizationalStructureTypeId(String organizationalStructureTypeId) {
        return strDao.getByOrganizationalStructureTypeId(organizationalStructureTypeId);
    }
}
