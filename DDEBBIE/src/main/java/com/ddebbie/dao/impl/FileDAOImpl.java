/**
 * 
 */
package com.ddebbie.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ddebbie.dao.AbstractDao;
import com.ddebbie.dao.FileDAO;
import com.ddebbie.model.AbstractObject;
import com.ddebbie.model.File;
import com.ddebbie.pagination.Paginator;
import com.ddebbie.utils.Constants;

/**
 * @author Ram
 * 13-Sep-2017
 */
@Repository("fileDAO")
public class FileDAOImpl extends AbstractDao implements FileDAO {

	
	@Override
	public Paginator<File> getFilesByType(String type, int pageNo, int pageSize) {
		int skipCount = (pageNo - 1) * pageSize;
		Criteria criteria = createCustomCriteria(File.class);
		criteria.add(Restrictions.eq(Constants.LABEL_TYPE, type));	
		long total = getTotalCount(criteria);
		criteria.setFirstResult(skipCount).setMaxResults(pageSize);
		return new Paginator<File>((List<File>)criteria.list(), total);		 
	}

	
	@Override
	public File save(File file) {
		persist(file);
		return file;
	}


	@Override
	public List<File> getFilesForIds(Collection<Long> ids) {
		Criteria criteria = createCustomCriteria(File.class);
		criteria.add(Restrictions.in(AbstractObject.LABEL_ID, ids));
		return criteria.list();	
	}


	@Override
	public void storeFileMeta(File fileImpl) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getTotalFileCount() {
		Criteria criteria = createCustomCriteria(File.class);
		return (int) getTotalCount(criteria);
	}


	@Override
	public Paginator<File> getPaginatedFiles(int pageNumber, int pageSize,long fromid,long toid) {
		int skipCount = (pageNumber - 1) * pageSize;
		Criteria criteria = createCustomCriteria(File.class);
		criteria.add(Restrictions.ge(AbstractObject.LABEL_ID, fromid));
		criteria.add(Restrictions.le(AbstractObject.LABEL_ID, toid));
		long count = getTotalCount(criteria);	
		criteria.setFirstResult(skipCount).setMaxResults(pageSize);
		return new Paginator<File>((List<File>)criteria.list(), count);
	}


	@Override
	public List<File> getFilesForIdsForCnfHomePage(Collection<Long> ids) {
		Criteria criteria = createCustomCriteria(File.class);
		criteria.add(Restrictions.in(AbstractObject.LABEL_ID, ids)); 
			  ProjectionList proList = Projections.projectionList();
			     proList.add(Projections.property(File.LABEL_ID));
			        proList.add(Projections.property(File.LABEL_PATH));
			        proList.add(Projections.property(File.LABEL_NAME));
			        criteria.setProjection(proList);
			  List<File> fileList = new ArrayList<File>();
			  for(final Object o : criteria.list()) {
				  File file = new File();
			   Object[] temp = (Object[]) o;
			   file.setId((long) temp[0]);			   
			   file.setPath((String) temp[1]);
			   file.setName((String) temp[2]);
			   fileList.add(file);
			  }
			  return fileList;
	}


	@Override
	public File getOptimizedFileObject(long id) {
		Criteria criteria = createCustomCriteria(File.class);
		criteria.add(Restrictions.eq(AbstractObject.LABEL_ID, id)); 
			  ProjectionList proList = Projections.projectionList();
			     proList.add(Projections.property(File.LABEL_ID));
			        proList.add(Projections.property(File.LABEL_PATH));
			        criteria.setProjection(proList);
			        File file = new File();
			  for(final Object o : criteria.list()) {
			   Object[] temp = (Object[]) o;
			   file.setId((long) temp[0]);			   
			   file.setPath((String) temp[1]);
			  }
			  return file;
	}
}
