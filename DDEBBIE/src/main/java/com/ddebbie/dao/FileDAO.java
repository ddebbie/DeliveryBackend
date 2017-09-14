package com.ddebbie.dao;

import java.util.Collection;
import java.util.List;

import com.ddebbie.model.File;
import com.ddebbie.pagination.Paginator;


/**
 * @author Ram
 * 13-Sep-2017
 */
public interface FileDAO extends BaseDAO {
	
	public Paginator<File> getFilesByType(String type, int pageNo, int pageSize);

	public File save(File file);

	public List<File> getFilesForIds(Collection<Long> ids);
	
	public void storeFileMeta(File fileImpl);
	
	public int getTotalFileCount();
	
	public Paginator<File> getPaginatedFiles(int pageNumber,int pageSize,long fromid,long toid);
	
	public List<File> getFilesForIdsForCnfHomePage(Collection<Long> ids);
	
	public File getOptimizedFileObject(long id);
}
