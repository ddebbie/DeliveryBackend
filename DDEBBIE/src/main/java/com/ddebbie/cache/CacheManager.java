package com.ddebbie.cache;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ddebbie.cache.memcached.MemcachedInitializer;
import com.ddebbie.exception.ExceptionCodes;
import com.ddebbie.exception.ExceptionMessages;
import com.ddebbie.exception.SystemException;

/**
 * Singleton class used to get handle to the individual cache objects. 
 * @author Ram
 * 13-Sep-2017
 */
@Configuration
public class CacheManager {
	
//	@Value("${memcached.server}") 
//	private String memcachedServer;
	
	@Autowired
    private Environment environment;



	public static int DEFAULT_CACHE_TYPE = CacheType.CENTRAL_CACHE;
	
	/**
	 * Used to store the caches based on type of cache. Each instance of Cache represents a 
	 * central cache implemented using memcached 
	 * 
	 */
	private Map<String, Cache> registeredCaches = new ConcurrentHashMap<>();

	public Cache getCache(String cacheName) {
		Cache cache = registeredCaches.get(cacheName);
		if(cache == null){
			CacheRegionType type = CacheRegionType.getCacheRegionType(cacheName);
			if(type == null){
				throw new SystemException(ExceptionCodes.CACHE_REGION_NOT_FOUND, ExceptionMessages.CACHE_REGION_NOT_FOUND);
			}else{
				return getCache(type);
			}
		}
		return cache;
	}
	
	
	/**
	 * Gets the cache for the given cache region type. If not found then registers the one with default value. 
	 */
	public Cache getCache(CacheRegionType cacheRegionType) {
		Cache cache = registeredCaches.get(cacheRegionType.getId());
		if(cache == null){
			cache = addCache(cacheRegionType.getCacheType(), cacheRegionType.getId(), cacheRegionType.getTimeToLiveSeconds());
		}
		return cache;
	}

	public String[] getCacheNames() {
		String[] cacheNames = new String [this.registeredCaches.keySet().size()];
		this.registeredCaches.keySet().toArray(cacheNames);
		return cacheNames;
	}


	private Cache addCache(int cacheType, String cacheName, int timeToLiveSeconds) {
		Cache aCache = AbstractCacheFactory.getCacheFactory(cacheType).getCache(cacheName, timeToLiveSeconds, 0);
		registeredCaches.put(cacheName, aCache);		
		return aCache;		
	}
	
	public void initService() throws IOException{
//		Properties props = ConfigReader.getProperties(Constants.MEMCACHED_FILE);
//		String servers = "localhost:11211";//props.getProperty(Constants.MEMCACHED_SERVERS);
		String servers = environment.getRequiredProperty("memcached.server");
		MemcachedInitializer.getInstance().initialize(servers);
	}
	
}
