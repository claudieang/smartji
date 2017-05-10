/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dao;

import com.model.entity.RawData;
import com.model.entity.DataTable;
import com.model.entity.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Claudie
 */
public class DataDAOImpl extends BaseDAO implements DataDAO{
    
    private SessionFactory sessionFactory;

    public DataDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        super.setSessionFactory(sessionFactory);
    }
    
    @Override
    @Transactional
    public List<RawData> getRawData() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from RawData order by id desc");
        List<RawData> list = query.setMaxResults(20).list();
        Collections.sort(list, RawData.RawDataSorting);
        return list;  
        //List<RawData> allData = findAll(RawData.class);
    }

    @Override
    @Transactional
    public RawData getRawDataById(int id) {
        RawData data = null;
        List<Criterion> criterias = new ArrayList<Criterion>();
        Criterion dataId = Restrictions.eq("id", id);
        criterias.add(dataId);
        data = (RawData) findUniqueByCriteria(RawData.class, criterias);

        return data;
    }
    
    
    
    @Override
    @Transactional
    public void addRawData(RawData data) {
        save(data);
    }

    @Override
    @Transactional
    public int getBaseValue() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from DataTable order by id DESC");
        query.setMaxResults(1);
        DataTable last = (DataTable) query.uniqueResult();
        return last==null?0:last.getHeatindex();
    }
    
    @Override
    @Transactional
    public void addBaseValue(DataTable data){
        saveOrUpdate(data);
    }

    @Override
    @Transactional
    public List<RawData> getRawDataByDate(String start, String end) {
        //run HQL query to query based on date
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from RawData where timestamp BETWEEN '"
                + start + "' AND '" + end + "'");
        List<RawData> list = query.list();
        return list;
    }

    @Override
    @Transactional
    public RawData getLatestData() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from RawData order by id DESC").setMaxResults(1);
        RawData last = (RawData) query.uniqueResult();
        return last;
    }

    @Override
    @Transactional
    public DataTable getTodayRecord(){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM DataTable WHERE timestamp >= CURDATE()");
        DataTable last = (DataTable) query.uniqueResult();
        return last;
    }
      
    @Override
    @Transactional
    public DataTable getBestRecord(){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM DataTable WHERE eggproduction = (SELECT max(eggproduction) FROM DataTable)");
        DataTable best = (DataTable) query.uniqueResult();
        return best;
    }

    @Override
    @Transactional
    public DataTable getRecordbyDate(String date) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM DataTable WHERE Date(timestamp) = '" + date + "'");
        DataTable target = (DataTable) query.uniqueResult();
        return target;
    }

    @Override
    @Transactional
    public List<DataTable> getAllBaseValue() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from DataTable order by id desc");
        List<DataTable> list = query.setMaxResults(10).list();
        Collections.sort(list, DataTable.dataTableSorting);
        return list;        
    }

    @Override
    @Transactional
    public void setEmail(User user) {
        saveOrUpdate(user);
    }

    @Override
    @Transactional
    public User getEmail() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User where id = 1");
        User target = (User) query.uniqueResult();
        return target;
    }

    @Override
    @Transactional
    public void updateBaseValue(DataTable data) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("update DataTable set timestamp = timestamp, heatindex = " + data.getHeatindex() 
                + ", eggproduction = " + data.getEggproduction() + " where id = " + data.getId());
        query.executeUpdate();
    }
    
}
