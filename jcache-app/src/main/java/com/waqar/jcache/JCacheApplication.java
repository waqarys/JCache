package com.waqar.jcache;

import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;
import javax.cache.spi.CachingProvider;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;

import com.waqar.jcache.annotation.Address;
import com.waqar.jcache.events.CustomerCacheEntryEventFilter;
import com.waqar.jcache.events.CustomerCacheEntryListener;

/**
 * Created by @shaikhwaqar
 */
@SuppressWarnings({"unused", "UnnecessaryLocalVariable"} )
@SpringBootApplication
@EnableCaching
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
    public MutableConfiguration<Integer, Customer> createCustomersCacheConfig(
            MutableCacheEntryListenerConfiguration<Integer, Customer> listenerConfiguration
    ) {
        MutableConfiguration<Integer, Customer> config
                = new MutableConfiguration<Integer, Customer>()
                //.setTypes(Integer.class, Customer.class)
                .setCacheWriterFactory(new CustomerCacheWriterFactory())
                .setWriteThrough(true)
                .setCacheLoaderFactory(new CustomerCacheLoaderFactory())
                .setReadThrough(true)
                .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 20)))
                .addCacheEntryListenerConfiguration(listenerConfiguration)
                ;
        return config;
    }

    @Bean(name = "customersCache", destroyMethod = "close")
    public Cache<Integer, Customer> createCustomersCache(CacheManager cacheManager,
                                             @Qualifier("customersCacheConfig")
                                             MutableConfiguration<Integer, Customer> config) {
        return cacheManager.createCache("customers", config);
    }
    
    @Bean
    public MutableCacheEntryListenerConfiguration<Integer, Customer> cacheEntryListener() {
        return new MutableCacheEntryListenerConfiguration<>(
                FactoryBuilder.factoryOf(CustomerCacheEntryListener.class),
                FactoryBuilder.factoryOf(CustomerCacheEntryEventFilter.class), // filter
                true, // pass old value for audit
                true  // Synchronous/Asynchronous
        );
    }
    
    @Bean
    public JCacheCacheManager cacheCacheManager(CacheManager cacheManager) {
        return new JCacheCacheManager(cacheManager);
    }
    
    @Bean(name = "addressesCacheConfig")
    public MutableConfiguration<Integer, Address> createAddressesCacheConfig() {
        MutableConfiguration<Integer, Address> config
                = new MutableConfiguration<Integer, Address>()
                .setStatisticsEnabled(true)
                .setManagementEnabled(true)
                ;
        return config;
    }
    
    @Bean(name = "addressesCache", destroyMethod = "close")
    public Cache<Integer, Address> createAddressesCache(CacheManager cacheManager,
                                                         @Qualifier("addressesCacheConfig")
                                                         MutableConfiguration<Integer, Address> config) {
        return cacheManager.createCache("addresses", config);
    }
}
