/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.OrganizationalStructure;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.service.StructureService;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mulugetak
 */
@Component
@SpringViewScope
public class OrganizationStructureBean implements Serializable {

    private OrganigramNode rootNode;
    private OrganigramNode selection;

    @Autowired
    private StructureService strService;

    @PostConstruct
    public void init() {
        selection = new DefaultOrganigramNode();
        OrganizationalStructure rootOS = strService.getRootOfStructure();
        rootNode = new DefaultOrganigramNode(rootOS, null);
        //System.out.println("Called drawer. count = " + rootOS.getChildren().size());
        rootNode.setCollapsible(false);
        rootNode.setDroppable(true);
    }

    public OrganigramNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(OrganigramNode rootNode) {
        this.rootNode = rootNode;
    }

    public OrganigramNode getSelection() {
        return selection;
    }

    public void setSelection(OrganigramNode selection) {
        this.selection = selection;
    }

    public void drawStructure(OrganizationalStructure os, OrganigramNode oGNode) {
        List<OrganizationalStructure> children = os.getChildren();
        for (OrganizationalStructure osChild : children) {
            OrganigramNode chOG = new DefaultOrganigramNode(osChild, oGNode);
            System.out.println("Called drawer. count = " + os.getChildren().size());
            drawStructure(osChild, chOG);

        }

    }

    public void btnDrawStructure_Handler() {
        OrganizationalStructure rootOS = strService.getRootOfStructure();
        rootNode = new DefaultOrganigramNode(rootOS, null);
        rootNode.setCollapsible(false);
        rootNode.setDroppable(true);
        drawStructure(rootOS, rootNode);
    }
}
