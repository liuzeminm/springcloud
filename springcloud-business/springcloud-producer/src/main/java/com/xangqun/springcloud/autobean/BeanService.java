/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.autobean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 动态注入，删除bean
 * @author laixiangqun
 * @since 2018-8-16
 */
@Component
public class BeanService implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory();
        //创建bean信息.
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(AuoService.class);
        beanDefinitionBuilder.addPropertyValue("name","张三");
       //动态注册bean.
        defaultListableBeanFactory.registerBeanDefinition("testService", beanDefinitionBuilder.getBeanDefinition());
         //获取动态注册的bean.
        AuoService testService =ctx.getBean(AuoService.class);
        testService.print();

        //删除bean.
        defaultListableBeanFactory.removeBeanDefinition("testService");
    }
}
