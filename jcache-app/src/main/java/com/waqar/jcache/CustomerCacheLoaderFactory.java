package com.waqar.jcache;

import javax.cache.configuration.Factory;

/**
 * Created by @shaikhwaqar
 */
public class CustomerCacheLoaderFactory implements Factory<CustomersCacheLoader> {

    @Override
    public CustomersCacheLoader create() {
        return ApplicationContextSingleton
                .getApplicationContext()
                .getBean(CustomersCacheLoader.class);
    }

}
