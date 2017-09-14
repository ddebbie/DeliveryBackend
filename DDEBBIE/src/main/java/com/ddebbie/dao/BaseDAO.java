package com.ddebbie.dao;
import com.ddebbie.exception.BusinessException;
import com.ddebbie.model.BaseObject;
import com.ddebbie.model.UserAuthentication;


/**
 * @author Ram
 * 13-Sep-2017
 */
public interface BaseDAO {

    public BaseObject persist(BaseObject entity);

    public void delete(BaseObject entity);

    public BaseObject update(BaseObject entity);

    public BaseObject getObjectById(long id, int objectType) throws BusinessException;

    public boolean delete(int objectType, long id);
    
    public BaseObject saveOrUpdate(BaseObject entity);
}
