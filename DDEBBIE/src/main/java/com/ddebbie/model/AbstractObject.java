package com.ddebbie.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.TableGenerator;

import com.ddebbie.service.descriptors.IdView;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Abstract Object is the implementation for common attributes of all objects
 * in the system. And also all model objects in the system will inherit this class.
 * @author Vamsi Kuchi
 * 
 **/
@MappedSuperclass
public abstract class AbstractObject implements BaseObject{

	@JsonView(IdView.class)
	@GeneratedValue(generator = "tableGen", strategy = GenerationType.TABLE)
	@GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.enhanced.TableGenerator", parameters = {
	            @Parameter(name = TableGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1")
	    })
	
	@Id
	@Column(name = "id")
	private long id;
	@Column(name = "cts")
	private Timestamp cts;
	@Column(name = "mts")
	private Timestamp mts;
	@Column(name = "createdby")
	private long creatorId;
	@Column(name = "modifiedby")
	private long modifierId;
	@Column(name = "deleted")
	private boolean isDeleted;

	public static final String LABEL_ID = "id";
	public static final String LABEL_CREATED_TIME = "cts";
	public static final String LABEL_CREATOR_ID = "creatorId";
	public static final String LABEL_MODIFIED_TIME = "mts";
	public static final String LABEL_MODIFIER_ID = "modifierId";
	public static final String LABEL_IS_DELETED = "isDeleted";
	public static final boolean IS_DELETED_TRUE = true;
	public static final boolean IS_DELETED_FALSE = false;

	public AbstractObject() {
		super();
	}

	public AbstractObject(AbstractObject abstractObject) {
		this.id = abstractObject.id;
		this.cts = abstractObject.cts;
		this.creatorId = abstractObject.creatorId;
		this.modifierId = abstractObject.modifierId;
		this.mts = abstractObject.mts;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getCts() {
		return cts;
	}

	public void setCts(Timestamp cts) {
		this.cts = cts;
	}

	@Override
	public long getCreatorId() {
		return creatorId;
	}

	@Override
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public Timestamp getMts() {
		return mts;
	}

	public void setMts(Timestamp mts) {
		this.mts = mts;
	}

	@Override
	public long getModifierId() {
		return modifierId;
	}

	@Override
	public void setModifierId(long modifierId) {
		this.modifierId = modifierId;
	}

	@Override
	public boolean getDeleted() {
		return isDeleted;
	}

	@Override
	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
	}
}