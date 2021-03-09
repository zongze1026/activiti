package com.zongze.config;

import org.springframework.context.ApplicationContext;

/**
 * @Date 2020/12/25 10:17
 * @Created by xiezz
 */
public class ApplicationContextHolder {


    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);

    }

}
