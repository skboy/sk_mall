package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TokenCache {
    public static final String TOKEN_PREFIX="token_";
    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    //initialCapacity初始化容量
    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                //默认的数据加载实现,当调用get没有值 就调用这个方法加载
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void setKet(String key, String vallue) {
        localCache.put(key, vallue);
    }
    public static String getKet(String key) {
        String value= null;
        try{
            value=localCache.get(key);
            if ("null".equals(value)){
                return null;
            }
            return value;
        }catch (Exception e){
            logger.error("localCache get error");
        }
       return null;
    }
}
