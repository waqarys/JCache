package com.waqar.jcache;


import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

/**
 * Created by Waqar Shaikh @shaikhwaqar
 */
public class Main {

    public static void main(String... args) {

        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<Integer, String> configuration = new MutableConfiguration<>();
        Cache<Integer, String> cache = cacheManager.createCache("names", configuration);

        cache.put(1, "John");
        cache.put(2, "Mary");
        cache.put(3, "Simon");
        cache.put(4, "Jane");
        cache.put(5, "Roger");

//        System.out.println("Hello " + cache.get(1));

        cache.forEach(entry -> System.out.println("Hello: " + entry.getValue()));
    }
}

