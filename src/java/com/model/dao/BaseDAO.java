package com.model.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class BaseDAO {

    public BaseDAO() {
    }

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Save/Update object instances by Id
     *
     * @param entity entity to Save/Update
     *
     * @return void
     *
     * @throws HibernateException
     */
    @Transactional
    public void saveOrUpdate(Object entity) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(entity);
        } catch (Exception e) {
            throw new HibernateException(e.toString());
        }
    }

    /**
     * Save object instances by Id
     *
     * @param entity entity to Save/Update
     *
     * @return void
     *
     * @throws HibernateException
     */
    @Transactional
    public void save(Object entity) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(entity);
        } catch (Exception e) {
            throw new HibernateException(e.toString());
        }
    }

    /**
     * Delete object instances by Id
     *
     * @param entity entity to delete
     *
     * @return void
     *
     * @throws HibernateException
     */
    @Transactional
    public void delete(Object entity) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(entity);
        } catch (Exception e) {
            throw new HibernateException(e.toString());
        }
    }

    /**
     * Return object instances by Id
     *
     * @param myClass Class of entity to retrieve
     *
     * @param id id of object.
     *
     * @return matching object instance.
     *
     * @throws HibernateException
     */
    @Transactional
    public Object findById(Class myClass, String id) {
        Object entity = null;
        try {
            Session session = sessionFactory.getCurrentSession();

            entity = session.get(myClass, id);
        } catch (Exception e) {
            throw new HibernateException(e.toString());
        }
        return entity;

    }

    /**
     * Return a unique object by restrictions.
     *
     * @param entityClass Class of entity to retrieve
     *
     * @param restrictions collection of Restrictions.
     *
     * @return unique object instances.
     *
     * @throws HibernateException
     */
    @Transactional
    public Object findUniqueByCriteria(Class entityClass,
            Collection restrictions) {

        //logger.debug("START findUniqueByCriteria...");
        Criteria criteria = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            criteria = session.createCriteria(entityClass);
            // Add criteria
            if (restrictions != null) {
                Iterator criteriaIter = restrictions.iterator();
                while (criteriaIter.hasNext()) {
                    Criterion criterion = (Criterion) criteriaIter.next();
                    criteria.add(criterion);
                }
            }

        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
            throw new HibernateException(e.toString());
        }
        //logger.debug("END findUniqueByCriteria...");
        return criteria.uniqueResult();
    }

    /**
     * Return list of object instances by restrictions
     *
     * @param entityClass Class of entity to retrieve
     *
     * @param restrictions collection of Restrictions.
     *
     * @return list of matching object instances.
     *
     * @throws HibernateException
     */
    @Transactional
    public List findByCriteria(Class entityClass, Collection restrictions) {
        List list = new ArrayList();
        try {
            Session session = sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(entityClass);
            // Add criteria
            if (restrictions != null) {
                Iterator criteriaIter = restrictions.iterator();
                while (criteriaIter.hasNext()) {
                    Criterion criterion = (Criterion) criteriaIter.next();
                    criteria.add(criterion);
                }
            }

            list = criteria.list();
        } catch (HibernateException e) {
            throw new HibernateException(e.toString());
        }
        return list;
    }

    /**
     * The method supports to find a list of entity by a property
     *
     * @param propertyName
     * @param value
     * @return
     */
    @Transactional
    public List findByProperty(Class entityClass, String propertyName,
            Object value) {
        try {
            // Just get entity name
            String className = entityClass.getName();
            className = className.substring(className.lastIndexOf(".") + 1,
                    className.length());

            String queryString = "from " + className + " as model where model."
                    + propertyName + "= ?";
            Session session = sessionFactory.getCurrentSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            throw re;
        }
    }

    /**
     * Get all entities.
     *
     * @return
     */
    @Transactional
    public List findAll(Class entityClass) {
        try {
//            // Just get entity name
            String className = entityClass.getName();
            className = className.substring(className.lastIndexOf(".") + 1,
                    className.length());
//
            String queryString = "from " + className;
            System.out.println("in findAll");
            Session session = sessionFactory.getCurrentSession();
            Query queryObject = session.createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            //logger.error(re.getMessage(), re);
            throw re;
        }

    }

    /**
     * Get total records base on a criteria
     *
     * @param criteria
     * @return
     */
    @Transactional
    public float getTotalRecords(Criteria criteria) {
        criteria.setFirstResult(0);
        List results = criteria.setProjection(
                Projections.projectionList().add(Projections.rowCount()))
                .list();
        return ((Integer) results.get(0)).floatValue();
    }
}
