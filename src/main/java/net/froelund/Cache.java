package net.froelund;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Froelund on 4/7/14.
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class Cache {

    ConcurrentHashMap cachedObjects = null;

    @PostConstruct
    public void initialize(){
        cachedObjects = new ConcurrentHashMap();
    }
    public void put(String cacheKey, Object cached){
        cachedObjects.put(cacheKey, cached);
    }
    public Object get(String cacheKey){
        return cachedObjects.get(cacheKey);
    }
    public boolean has(String cacheKey){
        return cachedObjects.containsKey(cacheKey);
    }
    public void delete(String cacheKey){
        cachedObjects.remove(cacheKey);
    }
}
