/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.OrganizationalStructure;
import com.dashen.hrms.OrganizationalStructureType;
import com.dashen.hrms.Position;
import com.dashen.hrms.PositionHavingOrganizationalStructure;
import com.dashen.hrms.PositionTier;
import com.dashen.hrms.TempStatus;
import com.dashen.hrms.Tier;
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
public class PositionDao {
    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNew(Position p) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(p);
    }

    public void update(Position p) {
        Session session = sessionFactory.getCurrentSession();
        session.update(p);
    }

    public List<Position> listTmpPositions() {
        List<Position> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Position where TMP_STATUS = '" + TempStatus.SUBMITTED + "'");
        list = qr.list();
        return list;
    }

    public Position getByPositionID(String id) {
        Session session = sessionFactory.getCurrentSession();
        Position position = (Position) session.get(Position.class, id);
        return position;
    }

    public List<Position> getByMakerID(String id) {
        List<Position> list;
        Session session = sessionFactory.getCurrentSession();
        //query to get the temp positions of made my a single user
        Query qr = session.createQuery("from Position where CREATED_BY = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public void delete(Position positionTmp) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(positionTmp);
    }

    public int positionPendingEditCount(String id) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from Position where id = '" + id + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        return ((Long) qr.uniqueResult()).intValue();
    }

    public List<Position> listTmpPositionsForMaker(String makerID) {
        List<Position> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Position where CREATED_BY = '" + makerID + "' AND (TMP_STATUS='" + TempStatus.EDITABLE + "' or TMP_STATUS = '" + TempStatus.SUBMITTED + "')");
        list = qr.list();
        return list;
    }

    public List<Position> listPositions() {
        List<Position> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Position where TMP_STATUS = '" + TempStatus.APPROVED + "'");
        list = qr.list();
        return list;
    }
    
    public Position getByID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        Position position = (Position)session.get(Position.class, ID);        
        return position;
    }
    public void saveNewPositionHavingOrgStruct(PositionHavingOrganizationalStructure posHavingOrgStruct) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(posHavingOrgStruct);
    }
    public void saveNewPositionTier(PositionTier positionTier) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(positionTier);
    }

    public void updatePositionHavingOrgStruct(PositionHavingOrganizationalStructure posHavingOrgStruct) {
        Session session = sessionFactory.getCurrentSession();
        session.update(posHavingOrgStruct);
    }
    public void updatePositionTier(PositionTier positionTier) {
        Session session = sessionFactory.getCurrentSession();
        session.update(positionTier);
    }

    public PositionHavingOrganizationalStructure getPositionHavingOrganizationalStructureByID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from PositionHavingOrganizationalStructure p where p.position.ID = '"+ID+"'" );
        List<PositionHavingOrganizationalStructure> l = qr.list();
        return l.get(0);
    }

    public PositionTier getPositionTierByID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from PositionTier p where p.position.ID = '"+ID+"'" );
        List<PositionTier> l = qr.list();
        return l.get(0);
    }

    public void deletePositionHavingOrgStruct(PositionHavingOrganizationalStructure p) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(p);
    }

    public void deletePositionTier(PositionTier p) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(p);
    }

    public OrganizationalStructure getOrganizationalStructureForAPosition(Position position) {
        PositionHavingOrganizationalStructure  s = getPositionHavingOrganizationalStructureByID(position.getID());
        return s.getOrganizationalStructure();
    }

    public Tier getTierForAPosition(Position position) {
        PositionTier  pt = getPositionTierByID(position.getID());
        return pt.getTier();
    }
    public List<Position> getByOrganizationalStructureTypeId(String organizationalStructureTypeId) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Position P where P.organizationalStructureType.id = :organizationalStructureTypeId");
        qr.setParameter("organizationalStructureTypeId",organizationalStructureTypeId);
        return qr.list();
    }
}
