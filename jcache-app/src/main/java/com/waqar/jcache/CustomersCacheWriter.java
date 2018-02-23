package com.waqar.jcache;

import com.waqar.jcache.repositories.CustomersRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import javax.cache.integration.CacheWriter;
import javax.cache.integration.CacheWriterException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by @shaikhwaqar
 */
@Component
public class CustomersCacheWriter implements CacheWriter<Integer, Customer> {

    private static final Log LOG = LogFactory.getLog(CustomersCacheWriter.class);

    private CustomersRepository customersRepository;

    public CustomersCacheWriter(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public void write(Cache.Entry<? extends Integer, ? extends Customer> entry) throws CacheWriterException {
        LOG.info("Write called with entry " + entry);
        customersRepository.save(entry.getValue());
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends Integer, ? extends Customer>> entries) throws CacheWriterException {
        LOG.info("WriteAll called with entries " + entries);
        Set<Customer> customers = entries
                .stream()
                .map(Cache.Entry::getValue)
                .collect(Collectors.toSet());
        customersRepository.save(customers);
    }

    @Override
    public void delete(Object key) throws CacheWriterException {
        LOG.info("Delete called with key " + key);
        customersRepository.delete((Integer)key);
    }

    @Override
    public void deleteAll(Collection<?> keys) throws CacheWriterException {
        LOG.info("DeleteAll called with keys " + keys);
        customersRepository.deleteAll((Collection<Integer>)keys);
    }
}
