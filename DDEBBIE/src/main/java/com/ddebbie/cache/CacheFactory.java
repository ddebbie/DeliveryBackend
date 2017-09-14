package com.ddebbie.cache;


/**
 * @author Ram
 * 13-Sep-2017
 */
public interface CacheFactory {

	Cache getCache(String cacheName);

	Cache getCache(String cacheName, int timeToLiveSeconds, int maxElementsInCache);	
}
