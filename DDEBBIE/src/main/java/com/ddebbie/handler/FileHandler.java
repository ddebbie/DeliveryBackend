package com.ddebbie.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.jets3t.service.S3ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ddebbie.dao.FileDAO;
import com.ddebbie.exception.BusinessException;
import com.ddebbie.model.File;
import com.ddebbie.model.ObjectTypes;
import com.ddebbie.utils.Utils;

@Service("fileHandler")
// @PropertySource(value = {"classpath:fileUpload.properties"})
public class FileHandler {

	@Autowired
	private FileDAO fileDAO;


	public File getFile(long fileId) throws BusinessException {
		File file = null;
		if (fileId > 0) {
			file = (File) fileDAO.getObjectById(fileId, ObjectTypes.FILE);
		}
		return file;
	}

	public List<File> getFilesForIds(Collection<Long> ids) {
		List<File> files = new ArrayList<File>();
		if (CollectionUtils.isNotEmpty(ids)) {
			files = fileDAO.getFilesForIds(ids);
		}
		return files;
	}

	public Map<Long, File> getIdFileMap(Collection<Long> ids) {
		List<File> files = getFilesForIds(ids);
		Map<Long, File> fileMap = new HashMap<Long, File>();
		if (CollectionUtils.isNotEmpty(files)) {
			for (File eachFile : files) {
				if (!fileMap.containsKey(eachFile.getId()))
					fileMap.put(eachFile.getId(), eachFile);
			}
		}
		return fileMap;

	}

	public boolean storeFileMetaData(File fileImpl) throws IOException, S3ServiceException {
		fileImpl.setCts(Utils.getGMTCurrentTime());
		fileImpl.setMts(Utils.getGMTCurrentTime());
		fileDAO.storeFileMeta(fileImpl);
		return true;
	}

	public boolean doesFileExist(long fileId) {
		try {
			fileDAO.getObjectById(fileId, ObjectTypes.FILE);
		} catch (BusinessException onf) {
			return false;
		}
		return true;
	}

	public List<String> getFilePathByid(List<Long> fileIdList) {
		List<String> uRLlist = new ArrayList<String>();
		List<File> fileList = new ArrayList<File>();
		if (CollectionUtils.isNotEmpty(fileIdList)) {
			fileList = fileDAO.getFilesForIds(fileIdList);
			for (File file : fileList) {
				uRLlist.add(file.getPath());
			}
		}
		return uRLlist;
	}

}
