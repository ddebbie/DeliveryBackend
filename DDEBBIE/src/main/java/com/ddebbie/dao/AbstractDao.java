package com.ddebbie.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.ddebbie.exception.BusinessException;
import com.ddebbie.exception.ExceptionCodes;
import com.ddebbie.exception.ExceptionMessages;
import com.ddebbie.model.AbstractObject;
import com.ddebbie.model.BaseObject;
import com.ddebbie.model.Objects;
import com.ddebbie.utils.Utils;


/**
 * @author Ram
 * 13-Sep-2017
 */
public abstract class AbstractDao implements BaseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private Objects objects;

    @Autowired
    private Utils utils;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public BaseObject persist(BaseObject entity) {
        entity.setCts(Utils.getGMTCurrentTime());
        entity.setMts(Utils.getGMTCurrentTime());
        getSession().persist(entity);
        return entity;
    }

    public void delete(BaseObject entity) {
        System.out.println("Base Object");
        entity.setDeleted(Boolean.TRUE);
        entity.setMts(Utils.getGMTCurrentTime());
        getSession().update(entity);
    }

    public BaseObject update(BaseObject entity) {
        entity.setMts(Utils.getGMTCurrentTime());
        getSession().update(entity);
        return entity;
    }

    public Criteria createCriteria(Class pojoClass) {
        return getSession().createCriteria(pojoClass);
    }

    public Criteria createCustomCriteria(Class pojoClass) {
        Criteria criteria = getSession().createCriteria(pojoClass);
        criteria.add(Restrictions.eq(AbstractObject.LABEL_IS_DELETED,
                AbstractObject.IS_DELETED_FALSE));
        return criteria;
    }

    public long getTotalCount(Criteria criteria) {
        criteria.setProjection(Projections.rowCount());
        Long total = (Long) criteria.uniqueResult();
        criteria.setProjection(null);
        return total;
    }


    public BaseObject getObjectById(long id, int objectType) throws BusinessException {
        BaseObject persistentObject = null;
        Class dbname = objects.getObjectName(objectType);
        persistentObject = (BaseObject) getSession().get(dbname, id);//(BaseObject) criteria.uniqueResult();
        if (persistentObject == null) {
            throw new BusinessException(ExceptionCodes.OBJECT_NOT_FOUND,
                    ExceptionMessages.OBJECT_NOT_FOUND);
        }
        return persistentObject;
    }


    public boolean delete(int objectType, long id) {
        System.out.println("className" + objects.getObjectName(objectType).getName());
        String hql = "update " + objects.getObjectName(objectType).getName() + " set " + AbstractObject.LABEL_IS_DELETED + " = "
                + AbstractObject.IS_DELETED_TRUE + ", " + AbstractObject.LABEL_MODIFIED_TIME + " = '" + Utils.getGMTCurrentTime() + "' where "
                + AbstractObject.LABEL_ID + " = " + id;

        System.out.println(hql);
        Query query = getSession().createQuery(hql);

        return query.executeUpdate() > 0 ? true : false;
    }

    @Override
    public BaseObject saveOrUpdate(BaseObject entity)
    {
//        getSession().saveOrUpdate(entity);
//        return entity;
        if(entity.getId()>0)
        {
            update(entity);
        }else{
            persist(entity);
        }
        return entity;
    }
}