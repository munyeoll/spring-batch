package com.mun.batch.util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BeanUtil {

    private final ApplicationContext applicationContext;

    public Object getBeanByName(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
