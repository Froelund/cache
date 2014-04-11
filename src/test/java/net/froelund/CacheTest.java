package net.froelund;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.lang.Thread;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Froelund on 4/7/14.
 */
public class CacheTest {

    Cache cache;

    @Before
    public void setUp() throws Exception {
        cache = new Cache();
        //As @PostConstruct never is run.
        cache.initialize();
    }
    @Test
    public void testSingleObject(){
        String cacheKey = "MY_TEST_KEY";
        String cached = "MY TEST VALUE";
        cache.put(cacheKey, cached);
        Assert.assertEquals("Single object put, wasn't the same as the one get.", cached, cache.get(cacheKey));
    }
    @Test
    public void testObjectOverwrite(){
        String cacheKey = "MY_TEST_KEY";
        String initialCachedObject = "MY TEST VALUE";
        String newCachedObject = "MY NEW TEST VALUE";
        cache.put(cacheKey, initialCachedObject);
        cache.put(cacheKey, newCachedObject);
        Assert.assertEquals("Overwrite of object, doesn't work.", newCachedObject, cache.get(cacheKey));
    }
    @Test
    public void testObjectDelete(){
        String cacheKey = "MY_TEST_KEY";
        String cached = "MY TEST VALUE";
        cache.put(cacheKey, cached);
        cache.delete(cacheKey);
        Assert.assertNull("Object wasn't deleted.", cache.get(cacheKey));
    }
    @Test
    public void testObjectExistence(){
        String cacheKey = "MY_TEST_KEY";
        String cached = "MY TEST VALUE";
        cache.put(cacheKey, cached);
        Assert.assertTrue("Object wasn't in cache.", cache.has(cacheKey));
    }
    @Test
    public void testObjectCount(){
        String cacheKey = "MY_TEST_KEY";
        String cached = "MY TEST VALUE";
        cache.put(cacheKey, cached);
        Assert.assertEquals("Object count was wrong.", 1, cache.count());
    }
    @Test
    public void testObjectExpires() throws Exception{
        String cacheKey = "MY_TEST_KEY";
        String cached = "MY TEST VALUE";
        Calendar expire = cache.put(cacheKey, cached, 50);
        Assert.assertNotNull("Cached object was null", cache.get(cacheKey));
        Thread.sleep(50);
        Assert.assertNull("Cached object wasn't null", cache.get(cacheKey));
        
    }
}
