package com.waqar.jcache;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by @shaikhwaqar
 */
@Service
public class ApplicationContextSingleton {

    private static ApplicationContext applicationContext;

    public ApplicationContextSingleton(ApplicationContext applicationContext) {
        ApplicationContextSingleton.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}