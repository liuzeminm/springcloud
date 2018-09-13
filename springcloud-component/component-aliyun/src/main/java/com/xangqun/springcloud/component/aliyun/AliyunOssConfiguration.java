/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.aliyun;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;


/**
 * @author laixiangqun
 * @since 2018-8-29
 */
@ConditionalOnClass(OSSClient.class)
@EnableConfigurationProperties(AliyunOssProperties.class)
public class AliyunOssConfiguration {

    @Autowired
    private AliyunOssProperties aliyunOssProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean(name = "clientConfiguration")
    @ConditionalOnMissingBean
    public ClientConfiguration clientConfiguration() {
        AliyunOssProperties.Client client = aliyunOssProperties.getClient();
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setMaxConnections(client.getMaxConnections());
        configuration.setSocketTimeout(client.getSocketTimeout());
        configuration.setConnectionTimeout(client.getConnectionTimeout());
        configuration.setRequestTimeoutEnabled(true);
        configuration.setConnectionRequestTimeout(client.getConnectionRequestTimeout());
        client.setIdleConnectionTime(client.getIdleConnectionTime());
        configuration.setMaxErrorRetry(client.getMaxErrorRetry());
        configuration.setSupportCname(client.isSupportCname());
        configuration.setSLDEnabled(client.isSldEnabled());
        if (Protocol.HTTP.toString().equals(client.getProtocol())) {
            configuration.setProtocol(Protocol.HTTP);
        } else if (Protocol.HTTPS.toString().equals(client.getProtocol())) {
            configuration.setProtocol(Protocol.HTTPS);
        }
        configuration.setUserAgent(client.getUserAgent());

        return configuration;
    }

    @Bean(name = "credentialsProvider")
    @ConditionalOnMissingBean
    public CredentialsProvider getCredentialsProvider() {
        return new DefaultCredentialProvider(aliyunOssProperties.getAccessKeyId(), aliyunOssProperties
                .getAccessKeySecret());
    }

    @Bean(name = "ossClient")
    @Scope("prototype")
    @Autowired
    public OSSClient ossClient(ClientConfiguration clientConfiguration, CredentialsProvider credentialsProvider) {
        OSSClient ossClient = new OSSClient(aliyunOssProperties.getEndpoint(), credentialsProvider, clientConfiguration);
        return ossClient;
    }

    @Bean
    public OssPoolManager ossPoolManager() {
        OssPoolManager ossPoolManager = new OssPoolManager(applicationContext);
        AliyunOssUtil.setOSSClient(ossPoolManager);
        return ossPoolManager;
    }

}
