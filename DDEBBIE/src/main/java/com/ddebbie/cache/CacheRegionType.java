/**
 * 
 */
package com.ddebbie.cache;

import java.util.HashMap;
import java.util.Map;


/**
 * Used to define different cache region types in the system
 * @author Ram
 * 13-Sep-2017
 */
public class CacheRegionType {

	private static Map<String, CacheRegionType> cacheMaps = new HashMap<>();
	
	public static final CacheRegionType USER_SESSION_CACHE = new CacheRegionType("1", CacheType.CENTRAL_CACHE);

    public static final CacheRegionType LOCK_CACHE = new CacheRegionType("3", null, CacheType.CENTRAL_CACHE, 60);
	
    
    private CacheElementFactory cacheFactory = null;

	private int timeToLiveSeconds = 0;
	
	private int maxElements = 0;
	
	private int cacheType;

	private String id;
	
	private String name;
	
	public static CacheRegionType getCacheRegionType(String id) {
		return cacheMaps.get(id);
	}
	
	public CacheRegionType(String id, int cacheType){
        this.setId(id);
		this.setName(name);
		this.setCacheType(cacheType);
		cacheMaps.put(id, this);
	}

    public CacheRegionType(String id, CacheElementFactory factory, int cacheType, int timeToLiveSeconds){
            this.setId(id);
    		this.setName(name);
    		this.setCacheFactory(factory);
    		this.setCacheType(cacheType);
    		this.setTimeToLiveSeconds(timeToLiveSeconds);
    		cacheMaps.put(id, this);
    	}


	public CacheRegionType(String id, int cacheType, int timeToLiveSeconds){
        this.setId(id);
		this.setName(name);
		this.setCacheType(cacheType);
		this.setTimeToLiveSeconds(timeToLiveSeconds);
		cacheMaps.put(id, this);
	}
	
	public int getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	public void setTimeToLiveSeconds(int timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

	public int getMaxElements() {
		return maxElements;
	}

	public void setMaxElements(int maxElements) {
		this.maxElements = maxElements;
	}

	public int getCacheType() {
		return cacheType;
	}

	public void setCacheType(int cacheType) {
		this.cacheType = cacheType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public CacheElementFactory getCacheFactory() {
        return cacheFactory;
    }

    public void setCacheFactory(CacheElementFactory cacheFactory) {
        this.cacheFactory = cacheFactory;
    }
}
