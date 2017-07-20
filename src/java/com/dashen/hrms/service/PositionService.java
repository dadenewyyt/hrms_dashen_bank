/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.Tier;
import com.dashen.hrms.ActionType;
import com.dashen.hrms.CurrentUser;
import com.dashen.hrms.OrganizationalStructure;
import com.dashen.hrms.OrganizationalStructureType;
import com.dashen.hrms.Position;
import com.dashen.hrms.PositionHavingOrganizationalStructure;
import com.dashen.hrms.PositionTier;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.dao.PositionDao;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author biniamt
 */

@Service
public class PositionService {
    
    @Autowired
    PositionDao positionDao;

    public void setPositionDao(PositionDao positionDao) {
        this.positionDao = positionDao;
    }
    
    @Transactional
    public boolean saveNewPosition(Position positionTmp) {
        //new Position
        Position newPosition = new Position();
        copyValuesFromTemp(newPosition, positionTmp);
        newPosition.setCreatedBy(positionTmp.getCreatedBy());
        newPosition.setCreatedDate(positionTmp.getCreatedDate());
        newPosition.setTmpStatus(TempStatus.APPROVED);

        newPosition.setApprovedBy(CurrentUser.getCurrentUser().getUsername());

        positionDao.saveNew(newPosition);
        positionTmp.setTmpStatus(TempStatus.HISTORY);
        positionDao.update(positionTmp);
        copyPositionDetails(newPosition, positionTmp);
        return true;
    }

    @Transactional
    public boolean updatePosition(Position positionTmp) {
        Position position = positionDao.getByPositionID(positionTmp.getPositionId());
        copyValuesFromTemp(position, positionTmp);
        position.setLastModifiedDate(new Date());
        position.setLastModifiedBy(positionTmp.getCreatedBy());
        positionDao.update(position);
        positionTmp.setTmpStatus(TempStatus.HISTORY);
        positionDao.update(positionTmp);
        copyPositionDetails(position, positionTmp);
        return true;
    }

    @Transactional
    public List<Position> listTmpPositions() {
        return positionDao.listTmpPositions();
    }
    
    public void copyValuesFromTemp(Position destPosition, Position sourcePosition)
    {
        destPosition.setID(sourcePosition.getPositionId());
        destPosition.setTitle(sourcePosition.getTitle());
        destPosition.setOrganizationalStructureType(sourcePosition.getOrganizationalStructureType());
        destPosition.setJobGrade(sourcePosition.getJobGrade());

        destPosition.setHasTier(sourcePosition.isHasTier());
        destPosition.setHasOrganizationalStructure(sourcePosition.isHasOrganizationalStructure());
    }

    public void copyPositionDetails(Position destPosition, Position sourcePosition) {

        if (sourcePosition.isHasTier()) {

            PositionTier prevPositionTier = positionDao.getPositionTierByID(sourcePosition.getID());

            PositionTier pt = new PositionTier();
            pt.setPosition(destPosition);
            pt.setTier(prevPositionTier.getTier());
            positionDao.saveNewPositionTier(pt);
        }

        if (sourcePosition.isHasOrganizationalStructure()) {
            PositionHavingOrganizationalStructure prevPositionHavingOrgStruct = getPositionHavingOrganizationalStructureByID(sourcePosition.getID());
            PositionHavingOrganizationalStructure p = new PositionHavingOrganizationalStructure();
            p.setPosition(destPosition);
            p.setOrganizationalStructure(prevPositionHavingOrgStruct.getOrganizationalStructure());
            positionDao.saveNewPositionHavingOrgStruct(p);
        }
    }
      
    @Transactional
    public boolean saveNewTmpPosition(Position newTmpPosition, ActionType acType, 
                        PositionHavingOrganizationalStructure posHavingOrgStruct,
                        PositionTier currentPositionTier, boolean orgStructureHasNoTier) {
        newTmpPosition.setActionType(acType);
        newTmpPosition.setCreatedBy(CurrentUser.getCurrentUser().getUsername());
        newTmpPosition.setCreatedDate(new Date());
        newTmpPosition.setTmpStatus(TempStatus.EDITABLE);

        if (!orgStructureHasNoTier && currentPositionTier.getTier().getId() != null) {
            newTmpPosition.setHasTier(true);
        } else {
            newTmpPosition.setHasTier(false);
        }

        if (posHavingOrgStruct.getOrganizationalStructure().getId() != null) {
            newTmpPosition.setHasOrganizationalStructure(true);
        } else {
            newTmpPosition.setHasOrganizationalStructure(false);
        }

        positionDao.saveNew(newTmpPosition);
        
        if (!orgStructureHasNoTier && currentPositionTier.getTier().getId() != null) {
            currentPositionTier.setPosition(newTmpPosition);
            positionDao.saveNewPositionTier(currentPositionTier);
        }

        if (posHavingOrgStruct.getOrganizationalStructure().getId() != null) {
            posHavingOrgStruct.setPosition(newTmpPosition);
            positionDao.saveNewPositionHavingOrgStruct(posHavingOrgStruct);
        }
        
        return true;
    }
    @Transactional
    public boolean updateTmpPosition(Position tmpPosition, PositionHavingOrganizationalStructure posHavingOrgStruct,
                        PositionTier currentPositionTier, boolean orgStructureHasNoTier) {

        boolean prevHasTierStatus = tmpPosition.isHasTier();
        boolean prevHasOrgStruct = tmpPosition.isHasOrganizationalStructure();
        String prevPositionID = tmpPosition.getID();

        if (!orgStructureHasNoTier && currentPositionTier.getTier().getId() != null) {
            tmpPosition.setHasTier(true);
        } else {
            tmpPosition.setHasTier(false);
        }

        if (posHavingOrgStruct.getOrganizationalStructure().getId() != null) {
            tmpPosition.setHasOrganizationalStructure(true);
        } else {
            tmpPosition.setHasOrganizationalStructure(false);
        }

        positionDao.update(tmpPosition);


        if (prevHasTierStatus) {
            if (!orgStructureHasNoTier && currentPositionTier.getTier().getId() != null) {
                positionDao.updatePositionTier(currentPositionTier);
            } else {
                PositionTier pt = getPositionTierByID(prevPositionID);
                positionDao.deletePositionTier(pt);
            }
        } else {
            if (!orgStructureHasNoTier && currentPositionTier.getTier().getId() != null) {
                currentPositionTier.setPosition(tmpPosition);
                positionDao.saveNewPositionTier(currentPositionTier);
            }

        }

        if (prevHasOrgStruct){
            if (posHavingOrgStruct.getOrganizationalStructure().getId() != null) {
                posHavingOrgStruct.setPosition(tmpPosition);
                positionDao.updatePositionHavingOrgStruct(posHavingOrgStruct);
            } else {
                PositionHavingOrganizationalStructure p = getPositionHavingOrganizationalStructureByID(prevPositionID);
                positionDao.deletePositionHavingOrgStruct(p);
            }
        } else {
            if (posHavingOrgStruct.getOrganizationalStructure().getId() != null) {
                posHavingOrgStruct.setPosition(tmpPosition);
                positionDao.saveNewPositionHavingOrgStruct(posHavingOrgStruct);
            }
        }

        return true;
    }

    @Transactional
    public boolean makeTmpPositionEditableByMaker(Position tmpPosition) {
        positionDao.update(tmpPosition);
        return true;
    }
    
    @Transactional
    public boolean delete(Position positionTmp) {
        

        if (positionTmp.isHasOrganizationalStructure()) {
            PositionHavingOrganizationalStructure p = positionDao.getPositionHavingOrganizationalStructureByID(positionTmp.getID());
            positionDao.deletePositionHavingOrgStruct(p);
        }

        if (positionTmp.isHasTier()) {
            PositionTier p = positionDao.getPositionTierByID(positionTmp.getID());
            positionDao.deletePositionTier(p);
        }
        
        positionDao.delete(positionTmp);
        return true;
    }
    
    public boolean checkIfPositionHasPendingEdit(String id) {
        return (positionDao.positionPendingEditCount(id) > 0);
    }
    
    @Transactional
    public List<Position> listTmpPositionsForMaker(String makerID) {
        return positionDao.listTmpPositionsForMaker(makerID);
    }
    
    @Transactional
    public List<Position> listPositions() {
        return positionDao.listPositions();
    }
    
    @Transactional
    public Position getByID(String ID) {
        return positionDao.getByID(ID);
    }

    @Transactional
    public PositionHavingOrganizationalStructure getPositionHavingOrganizationalStructureByID(String ID) {
        return positionDao.getPositionHavingOrganizationalStructureByID(ID);
    }

    @Transactional
    public PositionTier getPositionTierByID(String ID) {
        return positionDao.getPositionTierByID(ID);
    }

    @Transactional
    public OrganizationalStructure getOrganizationalStructureForAPosition(Position position) {
        return positionDao.getOrganizationalStructureForAPosition(position);
    }
    @Transactional
    public Tier getTierForAPosition(Position position) {
        return positionDao.getTierForAPosition(position);
    }
    @Transactional
    public List<Position> getByOrganizationalStructureTypeId(String organizationalStructureTypeId) {
        return positionDao.getByOrganizationalStructureTypeId(organizationalStructureTypeId);
    }

    // @Transactional
    // public boolean saveToTmpStorage(Position newPosition) {
    //     String taskJson = "";
    //     taskJson += "{title: " + newPosition.getTitle() +"}";
    // }
    
}
