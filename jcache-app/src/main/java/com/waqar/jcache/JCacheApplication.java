package com.waqar.jcache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

/**
 * Created by @shaikhwaqar
 */
@SuppressWarnings({"unused", "UnnecessaryLocalVariable"} )
@SpringBootApplication
public class JCacheApplication {

    public static void main(String... args) {
        SpringApplication.run(JCacheApplication.class, args);
    }

    @Bean(destroyMethod = "close")
    public CachingProvider createCachingProvider() {
        CachingProvider provider = Caching.getCachingProvider();
        return provider;
    }

    @Bean(destroyMethod = "close")
    public CacheManager createCacheManager(CachingProvider cachingProvider) {
        CacheManager manager = cachingProvider.getCacheManager();
        return manager;
    }

    @Bean(name = "customersCacheConfig")
    public MutableConfiguration<Integer, Customer> createCustomersCacheConfig() {
        MutableConfiguration<Integer, Customer> config
                = new MutableConfiguration<Integer, Customer>()
                .setTypes(Integer.class, Customer.class);
        return config;
    }

    @Bean(name = "customersCache", destroyMethod = "close")
    public Cache<Integer, Customer> createCustomersCache(CacheManager cacheManager,
                                             @Qualifier("customersCacheConfig")
                                             MutableConfiguration<Integer, Customer> config) {
        return cacheManager.createCache("customers", config);
    }
}
