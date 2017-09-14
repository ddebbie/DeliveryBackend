package com.ddebbie.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * Defines all the common attributes across all model objects
 * @author Vamsi Kuchi
 *
 * */
public interface BaseObject extends Serializable{

	long getId();

	void setId(long id);
	
	Timestamp getCts();

	void setCts(Timestamp cts);

	long getCreatorId();

	void setCreatorId(long creatorId);

	Timestamp getMts();

	void setMts(Timestamp mts);

	long getModifierId();

	void setModifierId(long modifierId);

	boolean getDeleted();

	void setDeleted(boolean deleted);


}
