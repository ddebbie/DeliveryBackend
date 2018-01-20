package com.ddebbie.dao;

import com.ddebbie.model.UploadFile;

public interface FileUploadDAO  extends BaseDAO {
    UploadFile save(UploadFile uploadFile);

	UploadFile getPicById(long id);
}
