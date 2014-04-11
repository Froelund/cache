package net.froelund;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Calendar;

/**
 * Created by Froelund on 4/7/14.
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class Cache {

    ConcurrentHashMap<String, CacheObjectWrapper> cachedObjects = null;

    @PostConstruct
    public void initialize(){
        cachedObjects = new ConcurrentHashMap();
    }
    public void put(String cacheKey, Object cached){
        CacheObjectWrapper cacheObject = new CacheObjectWrapper(cached);
        cachedObjects.put(cacheKey, cacheObject);
    }
    public Calendar put(String cacheKey, Object cached, long millis){
        Calendar timeout = Calendar.getInstance();
        timeout.setTimeInMillis(timeout.getTimeInMillis() + millis);
        CacheObjectWrapper cacheObject = new CacheObjectWrapper(cached, timeout);
        cachedObjects.put(cacheKey, cacheObject);
        return timeout;
    }
    public Object get(String cacheKey){
        if(this.has(cacheKey)){
            CacheObjectWrapper cachedObject = cachedObjects.get(cacheKey);
            if(cachedObject.expire != null){
                if(Calendar.getInstance().before(cachedObject.expire)){
                    return cachedObject.cachedObject;
                }else{
                    return null;
                }
            }
            return cachedObject.cachedObject;
        }
        return null;
    }
    public boolean has(String cacheKey){
        return cachedObjects.containsKey(cacheKey);
    }
    public void delete(String cacheKey){
        cachedObjects.remove(cacheKey);
    }
    public int count(){
        return cachedObjects.size();
    }
}
class CacheObjectWrapper{
    Calendar expire;
    Object cachedObject;
    public CacheObjectWrapper(Object cachedObject){
        this.cachedObject = cachedObject;
    }
    public CacheObjectWrapper(Object cachedObject, Calendar expire){
        this.cachedObject = cachedObject;
        this.expire = expire;
    }
}
