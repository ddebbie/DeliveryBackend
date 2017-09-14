package com.ddebbie.cache;

import com.ddebbie.exception.CacheException;

/**
 * Implementation class for this interface has the knowledge of creating and updating the cache element
 * for a given cache key
 * @author Ram
 * 13-Sep-2017
 */
public interface CacheElementFactory {

	/**
	 * Loads the Objects for the database
	 * @param key - key for the object
	 * @return loaded object and throws Cache exception, if the object is not found
	 * @throws CacheException if the object is not found
	 */
	public Object loadObject(Object key) throws CacheException;

	/**
	 * Update the object. This Should update the content in the value object that is passed
	 * as reference, it should not replace the value object
	 * @param key - key of the object
	 * @param value - value of the object that needs to be updated
	 * @throws CacheException if the object is not found
	 */
	public void updateObject(Object key, Object value) throws CacheException;


	/**
	 * Delete the object from cache
	 * @param key
	 * @return
	 * @throws CacheException
	 */
	public boolean deleteObject(Object key) throws CacheException;

}
