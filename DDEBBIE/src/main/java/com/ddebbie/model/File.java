package com.ddebbie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ram
 *
 */
@Entity
@Table(name = "file")
public class File extends AbstractObject {

	private static final long serialVersionUID = -8480716488181200953L;

	
	public static final String LABEL_PATH = "path";
	public static final String LABEL_NAME="name";

	@Column
	private String name;

	@Column
	private String type;
	
	@Column
	private String oldpath;

	public String getOldpath() {
		return oldpath;
	}

	public void setOldpath(String oldpath) {
		this.oldpath = oldpath;
	}

	@Column
	private String path;
	
  
	
  
    private String filePath	="";// where the actual file is being stored /bucket1/Folder1/key1
    private String fileHomePath	=	"";// where the actual file is being stored /bucket1/Folder1
   
   

	

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileHomePath() {
		return fileHomePath;
	}

	public void setFileHomePath(String fileHomePath) {
		this.fileHomePath = fileHomePath;
	}

	

	
	public File() {
	}

	public File(String name, String type, String path) {
		super();
		this.name = name;
		this.type = type;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
