package com.waqar.jcache.events;

import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryExpiredListener;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryRemovedListener;
import javax.cache.event.CacheEntryUpdatedListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.waqar.jcache.Customer;

/**
 * Created by @shaikhwaqar
 */
public class CustomerCacheEntryListener implements
		CacheEntryCreatedListener<Integer, Customer>,
		CacheEntryUpdatedListener<Integer, Customer>,
		CacheEntryRemovedListener<Integer, Customer>,
		CacheEntryExpiredListener<Integer, Customer> {
	private static final Log AUDIT_LOG = LogFactory.getLog("AUDIT");

	@Override
	public void onCreated(
			Iterable<CacheEntryEvent<? extends Integer, ? extends Customer>> cacheEntryEvents)
			throws CacheEntryListenerException {
		cacheEntryEvents.forEach(entry -> AUDIT_LOG
				.info("Cache entry created, for key: " + entry.getKey()
						+ " new value: " + entry.getValue() + " old value: "
						+ entry.getOldValue()));
	}

	@Override
	public void onExpired(
			Iterable<CacheEntryEvent<? extends Integer, ? extends Customer>> cacheEntryEvents)
			throws CacheEntryListenerException {
		cacheEntryEvents.forEach(entry -> AUDIT_LOG
				.info("Cache entry expired, for key: " + entry.getKey()
						+ " new value: " + entry.getValue() + " old value: "
						+ entry.getOldValue()));
	}

	@Override
	public void onRemoved(
			Iterable<CacheEntryEvent<? extends Integer, ? extends Customer>> cacheEntryEvents)
			throws CacheEntryListenerException {
		cacheEntryEvents.forEach(entry -> AUDIT_LOG
				.info("Cache entry removed, for key: " + entry.getKey()
						+ " new value: " + entry.getValue() + " old value: "
						+ entry.getOldValue()));
	}

	@Override
	public void onUpdated(
			Iterable<CacheEntryEvent<? extends Integer, ? extends Customer>> cacheEntryEvents)
			throws CacheEntryListenerException {
		cacheEntryEvents.forEach(entry -> AUDIT_LOG
				.info("Cache entry updated, for key: " + entry.getKey()
						+ " new value: " + entry.getValue() + " old value: "
						+ entry.getOldValue()));
	}
}
