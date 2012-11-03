package ahp.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

import ahp.model.AggregateResult;

public class CacheAccessor {
	private JCS cache = null;
	public static final String ProjectReference = "CacheGroup";
	public static final String Aggregates = "Aggregates";
	
	public CacheAccessor() {
		try {
			cache = JCS.getInstance("DC");
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}
	
	public Object getObjectFromCache(String key, String GroupName ){
		return cache.getFromGroup(key, GroupName);
	}
	
	public void putObjectInCache( String key, Object o , String GroupName){
		try {
			cache.putInGroup(key,GroupName, o);
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}
	
	public boolean putObjectInCacheIfNull( String key, Object o, String GroupName ){
		try {
			if ( getObjectFromCache(key, GroupName) == null ){
				cache.putInGroup(key,GroupName, o);
				return true;
			}
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public void clear(){
		try {
			cache.clear();
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void destroy(String objectName , String groupName){
		cache.remove(objectName,groupName);
		
	}
	
	public Map<String, Integer> getAllObjectsInCache(String GroupName){
		Set<String> keys = cache.getGroupKeys(GroupName);
		Map<String,Integer> keyList = new HashMap<String,Integer>();
		for (String key : keys){
			AggregateResult agg = (AggregateResult) getObjectFromCache(key, GroupName);
			keyList.put(key,agg.getSize());
		}
		return keyList;
	}
	
}
