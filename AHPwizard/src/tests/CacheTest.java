package tests;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

public final class CacheTest {

	private static JCS cache = null;

	public static Long testCacheObject() {
		try {
			cache = JCS.getInstance("DC");
		} catch (CacheException e) {
			e.printStackTrace();
		}

		String key = "testObject";
		Object o = cache.get(key);

		try {
			// if it isn't null, insert it
			if (o == null) {
				cache.put(key, System.currentTimeMillis());
			}else{
				return (Long)o;
			}
			return  (Long)cache.get(key);
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return null;
	}

}
