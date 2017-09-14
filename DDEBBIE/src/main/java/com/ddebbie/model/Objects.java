package com.ddebbie.model;

import org.springframework.stereotype.Service;

/**
 * @author Ram
 *
 *         
 */
@Service("objects")
public class Objects {

	public Class getObjectName(int objectType) {
		Class persistentObjectName = null;
		switch (objectType) {

		case ObjectTypes.USER:
			persistentObjectName = User.class;
			break;
		case ObjectTypes.ROLE:
			persistentObjectName = Role.class;
			break;
			
		default:
			break;
		}
		return persistentObjectName;
	}
}
