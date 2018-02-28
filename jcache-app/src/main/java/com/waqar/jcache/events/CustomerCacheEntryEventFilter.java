package com.waqar.jcache.events;

import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListenerException;

import com.waqar.jcache.Customer;

/**
 * Created by @shaikhwaqar
 */
public class CustomerCacheEntryEventFilter
    implements CacheEntryEventFilter<Integer, Customer> {

    @Override
    public boolean evaluate(CacheEntryEvent<? extends Integer, ? extends Customer> event)
            throws CacheEntryListenerException {
        return event.getKey() % 2 == 0;
    }
}
