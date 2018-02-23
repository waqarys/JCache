package com.waqar.jcache;

import com.waqar.jcache.repositories.CustomersRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by @shaikhwaqar
 */
@Component
public class CustomersCacheLoader implements CacheLoader<Integer, Customer> {

    private static final Log LOG = LogFactory.getLog(CustomersCacheLoader.class);

    private CustomersRepository customersRepository;

    public CustomersCacheLoader(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public Customer load(Integer key) throws CacheLoaderException {
        LOG.info("load key " + key);
        return customersRepository.findOne(key);
    }

    @Override
    public Map<Integer, Customer> loadAll(Iterable<? extends Integer> keys) throws CacheLoaderException {
        LOG.info("Load all keys " + keys);
        Iterable<Customer> customers = customersRepository.findAll((Iterable<Integer>)keys);
        return StreamSupport
                .stream(customers.spliterator(), false)
                .collect(Collectors.toMap(Customer::getId, Function.identity()));
    }
}
