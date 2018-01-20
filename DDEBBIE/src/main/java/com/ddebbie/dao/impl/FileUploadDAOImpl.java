package com.ddebbie.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ddebbie.dao.AbstractDao;
import com.ddebbie.dao.FileUploadDAO;
import com.ddebbie.model.UploadFile;
import com.ddebbie.model.User;
 

 
@SuppressWarnings("unchecked")
@Repository("fileUploadDao")
@Transactional
public class FileUploadDAOImpl extends AbstractDao  implements FileUploadDAO {
    @Autowired
    private SessionFactory sessionFactory;
     
    public FileUploadDAOImpl() {
    }
 
    public FileUploadDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    @Override
    @Transactional
    public UploadFile save(UploadFile uploadFile) {
    	UploadFile obj=(UploadFile) this.persist(uploadFile);
    	return obj;
    }
    
    @Override
    public UploadFile getPicById(long id) {
		Criteria criteria = createCustomCriteria(UploadFile.class);
		criteria.add(Restrictions.eq(User.LABEL_ID, id));
		return (UploadFile) criteria.uniqueResult();

	}
}
