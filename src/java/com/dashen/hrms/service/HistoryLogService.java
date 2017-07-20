/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.service;

import com.dashen.hrms.HistoryLog;
import com.dashen.hrms.dao.HistoryLogDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dawits
 */
@Service
public class HistoryLogService {
    @Autowired
    HistoryLogDao hisLogDao;
    
    public boolean save(HistoryLog HL) {  
        return hisLogDao.save(HL);
    }
    public boolean delete(HistoryLog HL) {
        return hisLogDao.delete(HL);
    }            
    public List<HistoryLog> fetchByDataId(String DataId)
    {
        return hisLogDao.fetchByDataId(DataId);
    }
}
