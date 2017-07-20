/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author MulugetaK
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService uds;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(uds).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/javax.faces.resource/**", "/resources/**").permitAll()
                .antMatchers("/login.xhtml").permitAll()
                .antMatchers("/admin/**").hasAuthority(UserAuthority.ADMIN)
                
                //.antMatchers("/user/**").hasAuthority(UserAuthority.USER)
                
                .antMatchers("/accounts/**").hasAnyAuthority(
                        UserAuthority.CREATE_USER,
                        UserAuthority.UPDATE_USER,
                        UserAuthority.UPDATE_USER_ROLE,
                        UserAuthority.CREATE_ROLE,
                        UserAuthority.UPDATE_ROLE)
                
                .antMatchers("/profile/edit/**").hasAnyAuthority(
                UserAuthority.CREATE_EMPLOYEE,
                   UserAuthority.UPDATE_EMPLOYEE)
                
                .antMatchers("/profile/approve/**").hasAnyAuthority(UserAuthority.AUTHORIZE_EMPLOYEE)
        
//                .antMatchers("/leave/approve/**").hasAuthority(UserAuthority.PROFILE_CHECKER)
//                .antMatchers("/leave/LeaveType.xhtml").hasAuthority(UserAuthority.ADMIN)
//                .antMatchers("/salary/JobGradePendingApproval.xhtml").hasAuthority(UserAuthority.PROFILE_CHECKER)
//                .antMatchers("/salary/JobLevelPendingApproval.xhtml").hasAuthority(UserAuthority.PROFILE_CHECKER)
//                .antMatchers("/salary/PositionPendingApproval.xhtml").hasAuthority(UserAuthority.PROFILE_CHECKER)
//                .antMatchers("/salary/SalaryScalePendingApproval.xhtml").hasAuthority(UserAuthority.PROFILE_CHECKER)
//                .antMatchers("/salary/EmployeeSalaryScalePendingApproval.xhtml").hasAuthority(UserAuthority.PROFILE_CHECKER)
//                .antMatchers("/salary/EditJobGrades.xhtml").hasAuthority(UserAuthority.PROFILE_MAKER)
//                .antMatchers("/salary/EditJobLevels.xhtml").hasAuthority(UserAuthority.PROFILE_MAKER)
//                .antMatchers("/salary/EditPositions.xhtml").hasAuthority(UserAuthority.PROFILE_MAKER)
//                .antMatchers("/salary/EditSalaryScales.xhtml").hasAuthority(UserAuthority.PROFILE_MAKER)
//                .antMatchers("/salary/EditEmployeeSalaryScales.xhtml").hasAuthority(UserAuthority.PROFILE_MAKER)
                
                .antMatchers("/hr/Institution.xhtml").hasAnyAuthority(
                        UserAuthority.VIEW_INSTITUTION,
                        UserAuthority.CREATE_INSTITUTION,
                        UserAuthority.UPDATE_INSTITUTION,
                        UserAuthority.DELETE_INSTITUTION                        
                )
                .antMatchers("/hr/OrganizationalStructureType.xhtml", 
                        "/hr/OrganizationalStructure.xhtml",
                        "/hr/OrganizationalStructureTypeTiers.xhtml"                        
                        ).hasAnyAuthority(
                                UserAuthority.VIEW_STRUCTURE,
                                UserAuthority.CREATE_STRUCTURE,
                                UserAuthority.UPDATE_STRUCTURE,
                                UserAuthority.DELETE_STRUCTURE)
                
                .antMatchers("/leave/approve/**").hasAnyAuthority(UserAuthority.AUTHORIZE_EMPLOYEE)
                .antMatchers("/leave/LeaveType.xhtml").hasAuthority(UserAuthority.AUTHORIZE_EMPLOYEE)

                .antMatchers("/salary/JobGradePendingApproval.xhtml").hasAuthority(UserAuthority.APPROVE_JOB_GRADE)
                .antMatchers("/salary/JobLevelPendingApproval.xhtml").hasAuthority(UserAuthority.APPROVE_JOB_LEVEL)
                .antMatchers("/salary/PositionPendingApproval.xhtml").hasAuthority(UserAuthority.APPROVE_POSITION)
                .antMatchers("/salary/SalaryScalePendingApproval.xhtml").hasAuthority(UserAuthority.APPROVE_SALARY_SCALE)
                .antMatchers("/salary/EditJobGrades.xhtml").hasAuthority(UserAuthority.CREATE_JOB_GRADE)
                .antMatchers("/salary/EditJobLevels.xhtml").hasAuthority(UserAuthority.CREATE_JOB_LEVEL)
                .antMatchers("/salary/EditPositions.xhtml").hasAuthority(UserAuthority.CREATE_POSITION)
                .antMatchers("/salary/EditEmployeePositions.xhtml").hasAuthority(UserAuthority.CREATE_EMPLOYEE_POSITION)
                .antMatchers("/salary/EditSalaryScales.xhtml").hasAuthority(UserAuthority.CREATE_SALARY_SCALE)
                
                .antMatchers("/hr/**").hasAuthority(UserAuthority.HR_ADMIN)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.xhtml").permitAll()
                .failureUrl("/login.xhtml?error")
                .and()
                .logout()
                .logoutUrl("/logout.xhtml")
                .logoutSuccessUrl("/login.xhtml?logout")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/accessdenied.xhtml")
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}
