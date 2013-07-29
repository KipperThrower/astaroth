package pl.kipperthrower.astaroth.core.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Service;

@Service("cache")
public class ApplicationCacheService {
	
	private static final String GENERAL_USE_CACHE = "generalUseCache";

	public void put(String key, Object item) {
		CacheManager cacheManager = CacheManager.getInstance();
		Cache cache = cacheManager.getCache(GENERAL_USE_CACHE);
		cache.put(new Element(key,item));
	}
	
	public Object get(String key) {
		CacheManager cacheManager = CacheManager.getInstance();
		Cache cache = cacheManager.getCache(GENERAL_USE_CACHE);
		return cache.get(key).getObjectValue();
	}
	
	public void delete(String key) {
		CacheManager cacheManager = CacheManager.getInstance();
		Cache cache = cacheManager.getCache(GENERAL_USE_CACHE);
		cache.remove(key);
	}
	
	public void clear() {
		CacheManager cacheManager = CacheManager.getInstance();
		Cache cache = cacheManager.getCache(GENERAL_USE_CACHE);
		cache.removeAll();
	}
}
