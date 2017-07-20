/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.dao;

import com.dashen.hrms.Institution;
import com.dashen.hrms.OrganizationalStructure;
import com.dashen.hrms.OrganizationalStructureTier;
import com.dashen.hrms.OrganizationalStructureType;
import com.dashen.hrms.RowStatus;
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
 * @author mulugetak
 */
@Repository
@Transactional
public class StructureDao {

    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNewOrganizationalStructureType(OrganizationalStructureType ost) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(ost);
    }

    public void updateOrganizationalStructureType(OrganizationalStructureType ost) {
        Session session = sessionFactory.getCurrentSession();
        session.update(ost);
    }

    public void saveHistoryOrganizationalStructureType(OrganizationalStructureType ost) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(ost);
    }

    public void saveNewOrganizationalStructure(OrganizationalStructure os) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(os);
    }

    public void updateOrganizationalStructure(OrganizationalStructure os) {
        Session session = sessionFactory.getCurrentSession();
        session.update(os);
    }

    public void saveHistoryOrganizationalStructure(OrganizationalStructure os) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(os);
    }

    public void saveNewOrganizationalStructureTier(OrganizationalStructureTier osTier) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(osTier);
    }

    public void updateOrganizationalStructureTier(OrganizationalStructureTier osTier) {
        Session session = sessionFactory.getCurrentSession();
        session.update(osTier);
    }

    public void saveHistoryOrganizationalStructureTier(OrganizationalStructureTier osTier) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(osTier);
    }

    public void saveNewTier(Tier t) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(t);
    }

    public void updateTier(Tier t) {
        Session session = sessionFactory.getCurrentSession();
        session.update(t);
    }

    public void saveHistoryTier(Tier t) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(t);
    }

    public boolean isRootOfStructureCreated() {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("select count(*) from OrganizationalStructure as o where  o.rowStatus = '" + RowStatus.ACTIVE + "' and o.parentID = null");
        return ((Long) qr.uniqueResult()).intValue() > 0;
    }

    public OrganizationalStructure getRootOfStructure() {
        List<OrganizationalStructure> osList;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery(" from OrganizationalStructure as o where  o.rowStatus = '" + RowStatus.ACTIVE + "' and o.parentID = null");
        osList = qr.list();
        if (osList.size() > 0) {
            return osList.get(0);
        }
        return null;
    }

    public List<OrganizationalStructure> getChildrenOfStructure(String parentID) {
        List<OrganizationalStructure> chList;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery(" from OrganizationalStructure as o where  o.rowStatus = '" + RowStatus.ACTIVE + "' and o.parentID = '" + parentID + "'");
        chList = qr.list();
        return chList;
    }

    public List<OrganizationalStructureType> listAllOrganizationalStructureTypes() {
        List<OrganizationalStructureType> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from OrganizationalStructureType where row_status = '" + RowStatus.ACTIVE + "'");
        list = qr.list();
        return list;
    }

    public List<OrganizationalStructure> listAllOrganizationalStructures() {
        List<OrganizationalStructure> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from OrganizationalStructure where row_status = '" + RowStatus.ACTIVE + "'");
        list = qr.list();
        return list;
    }

    public List<Tier> listAllTiersForStructureType(String structureTypeID) {
        List<Tier> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from Tier as t where t.rowStatus = '" + RowStatus.ACTIVE + "' and t.organizationalStructureTypeID = '" + structureTypeID + "'");
        list = qr.list();
        return list;
    }

    public OrganizationalStructureTier getStructureTierForStructure(String structureID) {
        List<OrganizationalStructureTier> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from OrganizationalStructureTier as t where t.rowStatus = '" + RowStatus.ACTIVE + "' and t.organizationalStructureID = '" + structureID + "'");
        list = qr.list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public Tier getTierByID(String ID) {
        Session session = sessionFactory.getCurrentSession();
        Tier tier = (Tier)session.get(Tier.class, ID);
        return tier;
    }

    public List<OrganizationalStructure> listOrganizationalStructuresOfAType(String organizationalStructureTypeId) {
        List<OrganizationalStructure> list;
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery(" from OrganizationalStructure as o where  o.rowStatus = '" + RowStatus.ACTIVE + "' and o.organizationalStructureTypeID = '" + organizationalStructureTypeId + "'");
        list = qr.list();
        return list;
    }
    
    public OrganizationalStructure getByOrganizationalStructureId(String organizationalStructureId) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from OrganizationalStructure OS where OS.id = :organizationalStructureId");
        qr.setParameter("organizationalStructureId",organizationalStructureId);
        List<OrganizationalStructure> list = qr.list();
        if (list.size() > 0) return list.get(0); else return null;
    }
    public OrganizationalStructureType getByOrganizationalStructureTypeId(String organizationalStructureTypeId) {
        Session session = sessionFactory.getCurrentSession();
        Query qr = session.createQuery("from OrganizationalStructureType OST where OST.id = :organizationalStructureTypeId");
        qr.setParameter("organizationalStructureTypeId",organizationalStructureTypeId);
        List<OrganizationalStructureType> list = qr.list();
        if (list.size() > 0) return list.get(0); else return null;
    }

}
