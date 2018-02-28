package com.waqar.jcache.audit;

import javax.cache.Cache;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import com.waqar.jcache.Customer;

/**
 * Created by @shaikhwaqar
 */
@ManagedResource(objectName = "JCache:bean=AuditManager")
@Service
public class AuditManager {

    private MutableCacheEntryListenerConfiguration<Integer, Customer> config;
    private Cache<Integer, Customer> cache;


    public AuditManager(MutableCacheEntryListenerConfiguration<Integer, Customer> config,
                        Cache<Integer, Customer> cache) {
        this.config = config;
        this.cache = cache;
    }

    @ManagedOperation
    public void startAudit() {
        cache.registerCacheEntryListener(config);
    }

    @ManagedOperation
    public void stopAudit() {
        cache.deregisterCacheEntryListener(config);
    }
}
