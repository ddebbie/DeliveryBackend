package com.ddebbie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "FILES_UPLOAD")
public class UploadFile extends AbstractObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6060027869688398512L;
	/**
	 * 
	 */
	
	@Column(name = "FILE_NAME")
    private String fileName;
    
    @Column(name = "FILE_DATA")
    private byte[] data;
 
    
  
 
    
    public String getFileName() {
        return fileName;
    }
 
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
 
    
    public byte[] getData() {
        return data;
    }
 
    public void setData(byte[] data) {
        this.data = data;
    }
}