package com.ddebbie.cache.memcached;

import com.ddebbie.cache.AbstractCacheFactory;
import com.ddebbie.cache.Cache;
import com.ddebbie.exception.ExceptionCodes;
import com.ddebbie.exception.SystemException;

/**
 * @author Ram
 * 13-Sep-2017
 */
public class MemcachedCacheFactory extends AbstractCacheFactory {

	private static MemcachedCacheFactory factory = null;
	
    public static synchronized MemcachedCacheFactory getInstance() {
    	if(factory == null){
    		factory = new MemcachedCacheFactory();
    	}
    	return factory;
    }
	
	@Override
	public Cache getCache(String cacheName, int timeToLiveSeconds,
			int maxElementsInCache) {
		
		Cache memedCache = null;		
		if(cacheName == null) {
			throw new SystemException(ExceptionCodes.CACHE_NAME_NOT_PROVIDED);
		}
		
		memedCache = new SimpleCache(cacheName, timeToLiveSeconds);
		
		return memedCache;
	}
}
