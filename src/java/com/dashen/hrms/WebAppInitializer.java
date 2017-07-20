/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 *
 * @author Mulugeta
 */
public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        AnnotationConfigWebApplicationContext wacRoot = new AnnotationConfigWebApplicationContext();
        
        wacRoot.register(SpringCoreConfig.class);
        wacRoot.register(WebSecurityConfig.class);
        wacRoot.register(SpringOrmConfig.class);
        wacRoot.register(MyCustomScopeConfigurer.class);
        sc.addListener(new ContextLoaderListener(wacRoot));
    }  
       
}
