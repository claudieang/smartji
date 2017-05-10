package com.model.entity;
// Generated Mar 13, 2017 4:22:05 PM by Hibernate Tools 4.3.1


import java.util.Comparator;
import java.util.Date;

/**
 * RawData generated by hbm2java
 */
public class RawData  implements java.io.Serializable {


     private Integer id;
     private Date timestamp;
     private int coopId;
     private int temp1;
     private int temp2;
     private int humid1;
     private int humid2;
     private boolean light;
     private int heatindex1;
     private int heatindex2;
     private boolean email;

    public RawData() {
    }

    public RawData(int coopId, int temp1, int temp2, int humid1, int humid2, boolean light, int heatindex1, int heatindex2, boolean email) {
       this.coopId = coopId;
       this.temp1 = temp1;
       this.temp2 = temp2;
       this.humid1 = humid1;
       this.humid2 = humid2;
       this.light = light;
       this.heatindex1 = heatindex1;
       this.heatindex2 = heatindex2;
       this.email = email;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getTimestamp() {
        return this.timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public int getCoopId() {
        return this.coopId;
    }
    
    public void setCoopId(int coopId) {
        this.coopId = coopId;
    }
    public int getTemp1() {
        return this.temp1;
    }
    
    public void setTemp1(int temp1) {
        this.temp1 = temp1;
    }
    public int getTemp2() {
        return this.temp2;
    }
    
    public void setTemp2(int temp2) {
        this.temp2 = temp2;
    }
    public int getHumid1() {
        return this.humid1;
    }
    
    public void setHumid1(int humid1) {
        this.humid1 = humid1;
    }
    public int getHumid2() {
        return this.humid2;
    }
    
    public void setHumid2(int humid2) {
        this.humid2 = humid2;
    }
    public boolean isLight() {
        return this.light;
    }
    
    public void setLight(boolean light) {
        this.light = light;
    }
    public int getHeatindex1() {
        return this.heatindex1;
    }
    
    public void setHeatindex1(int heatindex1) {
        this.heatindex1 = heatindex1;
    }
    public int getHeatindex2() {
        return this.heatindex2;
    }
    
    public void setHeatindex2(int heatindex2) {
        this.heatindex2 = heatindex2;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public static Comparator<RawData> RawDataSorting = new Comparator<RawData>() {
	public int compare(RawData d1, RawData d2) {
	   Integer data1 = d1.getId();
	   Integer data2 = d2.getId();
	   //ascending order
	   return data1.compareTo(data2);
    }};
    
}


