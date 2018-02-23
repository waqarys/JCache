package com.waqar.jcache;

import javax.cache.configuration.Factory;

/**
 * Created by @shaikhwaqar
 */
public class CustomerCacheWriterFactory implements Factory<CustomersCacheWriter> {

    @Override
    public CustomersCacheWriter create() {
        return ApplicationContextSingleton
                .getApplicationContext()
                .getBean(CustomersCacheWriter.class);
    }

}
