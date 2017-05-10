/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dao;

import com.model.entity.DataTable;
import com.model.entity.RawData;
import com.model.entity.User;
import java.util.List;

/**
 *
 * @author Claudie
 */
public interface DataDAO{
    public List<RawData> getRawData();
    
    public RawData getRawDataById(int id);
    
    public void addRawData(RawData data);
    
    public void addBaseValue(DataTable data);
    
    public int getBaseValue();
    
    public List<DataTable> getAllBaseValue();

    public List<RawData> getRawDataByDate(String start, String end);
    
    public RawData getLatestData();
    
    public DataTable getTodayRecord();
    
    public DataTable getBestRecord();
    
    public DataTable getRecordbyDate(String date);
    
    public void setEmail(User user);
    
    public User getEmail();
    
    public void updateBaseValue(DataTable dataT);
}
