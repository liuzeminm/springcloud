/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.aliyun;


import com.aliyun.oss.OSSClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.ApplicationContext;

/**
 * @author laixiangqun
 * @since 2018-8-30
 */
public class OssPoolManager extends BasePooledObjectFactory<OSSClient> {

    private ApplicationContext applicationContext;

    public GenericObjectPool<OSSClient> pool;

    public OssPoolManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(12);
        AbandonedConfig abandonedConfig = new AbandonedConfig();
        pool = new GenericObjectPool(this, config, abandonedConfig);
    }


    @Override
    public OSSClient create() throws Exception {
        return applicationContext.getBean("ossClient", OSSClient.class);
    }

    @Override
    public PooledObject wrap(OSSClient obj) {
        return new DefaultPooledObject<>(obj);
    }


}
