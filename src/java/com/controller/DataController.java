/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.model.dao.DataDAO;
import com.model.entity.DataTable;
import com.model.entity.Greeting;
import com.model.entity.HelloMessage;
import com.model.entity.RawData;
import com.model.entity.User;
import com.service.EmailVerification;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 *
 * @author Claudie
 */
@RestController
public class DataController {
    @Autowired
    private DataDAO dataDao;
    @Autowired
    private EmailVerification emailVerification;
    /*
    * Raw Data Records
    */
    
    @RequestMapping(value = "/data/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllData(){
        List<RawData> allData = dataDao.getRawData();
        return new ResponseEntity<List<RawData>>(allData, HttpStatus.OK);
    }
    @RequestMapping(value = "/data/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity addData(@RequestBody RawData rawdata, UriComponentsBuilder ucBuilder){
        //get email
        //emailVerification.sendNotification();
        int baseValue = dataDao.getBaseValue();
        dataDao.addRawData(rawdata);
        if(rawdata.isEmail() && (rawdata.getHeatindex1() > baseValue)){
            
            int hi = rawdata.getHeatindex1();
            int recommendedTime = (hi - baseValue) * 20;
            String targetEmail = dataDao.getEmail().getEmail();
            emailVerification.sendNotification(targetEmail, baseValue, hi, recommendedTime);
        }
        return new ResponseEntity<RawData>(rawdata, HttpStatus.OK);
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity getFromDateRange(@RequestParam("start") String start, @RequestParam("end") String end){
        //String str = start + " * " + end;
        List<RawData> rawDataByDate = dataDao.getRawDataByDate(start, end);
        return new ResponseEntity<List<RawData>>(rawDataByDate, HttpStatus.OK);  
    }
    
    @RequestMapping(value = "/data/latest/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getLatestReadings(){
        RawData latestReading = dataDao.getLatestData();
        return new ResponseEntity<RawData>(latestReading, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/email/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity sendEmail(){
        String targetEmail = dataDao.getEmail().getEmail();
        emailVerification.sendNotification(targetEmail, 45, 50, 58);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    
    
    /*
    * Base values
    */
    @RequestMapping(value = "/base/all/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllBaseValue(){
        
        List<DataTable> allBaseValue = dataDao.getAllBaseValue();
        return new ResponseEntity<List<DataTable>>(allBaseValue, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/base/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity getBaseValue(){
        int baseValue = dataDao.getBaseValue();
        return new ResponseEntity<Integer>(baseValue, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/base/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity setBaseValue(@RequestBody DataTable datatable, UriComponentsBuilder ucBuilder){
        dataDao.addBaseValue(datatable);
        return new ResponseEntity<DataTable>(datatable, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/base", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setOptimumHeatIndex(@RequestParam("optimum") int optimum){
        DataTable todayRecord = dataDao.getTodayRecord();
        if(todayRecord == null){
            //no record for today
            DataTable today = new DataTable(optimum, 0);
            dataDao.addBaseValue(today);
            todayRecord = today;
        } else { 
            //save or update
            todayRecord.setHeatindex(optimum);
            dataDao.updateBaseValue(todayRecord);
//            todayRecord.setTimestamp(todayRecord.getTimestamp());
//            dataDao.addBaseValue(todayRecord);
        }
        return new ResponseEntity<DataTable>(todayRecord, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/optimum", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setEggProduction(@RequestParam("egg") int egg, @RequestParam("date") String date) throws ParseException{
        DataTable targetDate = dataDao.getRecordbyDate(date);
        if(targetDate == null){
            //no record for today
            //get latest heat index
            DataTable target = new DataTable(0, egg);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date inputDate = dateFormat.parse(date);
            target.setTimestamp(inputDate);
            dataDao.addBaseValue(target);
            targetDate = target;
        } else { 
            //save or update
            targetDate.setEggproduction(egg);
            //targetDate.setTimestamp(targetDate.getTimestamp());
            //dataDao.addBaseValue(targetDate);
            dataDao.updateBaseValue(targetDate);
        }
        return new ResponseEntity<DataTable>(targetDate, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/base/best/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRecommendedValue(){
        DataTable bestValue = dataDao.getBestRecord();
        return new ResponseEntity<DataTable>(bestValue, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/daily/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDailyValue(){
        //get current setting and recommended setting
        DataTable todayRecord = dataDao.getTodayRecord();
        if(todayRecord == null){
            //no record for today
            int baseValue = dataDao.getBaseValue();
            todayRecord = new DataTable(baseValue, 0);
            dataDao.addBaseValue(todayRecord);
        } 
        
        int currentSetting = todayRecord.getHeatindex();
        int recommendedSetting = dataDao.getBestRecord().getHeatindex();
        
        JSONObject result = new JSONObject();
        result.put("current", currentSetting);
        result.put("recommended", recommendedSetting);
        
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }
    
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }
    
    //email notification
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setOptimumHeatIndex(@RequestParam("email") String email){
        User target = dataDao.getEmail();
        target.setEmail(email);
        dataDao.setEmail(target);
        return new ResponseEntity<User>(target, HttpStatus.OK);
    }
}
