/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author MulugetaK
 */
@Configuration
public class MyCustomScopeConfigurer implements ApplicationContextAware  {

    ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
        AnnotationConfigWebApplicationContext acwc = (AnnotationConfigWebApplicationContext)applicationContext;
        ViewScope vscop = new ViewScope();
        acwc.getBeanFactory().registerScope("view",vscop);
    }

}
