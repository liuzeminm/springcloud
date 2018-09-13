package com.ctrip.framework.apollo.portal;

import com.ctrip.framework.apollo.common.ApolloCommonConfig;
import com.ctrip.framework.apollo.openapi.PortalOpenApiConfig;

import org.springframework.boot.SpringApplication;
//import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
//import org.springframework.boot.actuate.system.EmbeddedServerPortFileWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.context.WebServerPortFileWriter;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableDiscoveryClient
@ComponentScan(basePackageClasses = {ApolloCommonConfig.class,
    PortalApplication.class, PortalOpenApiConfig.class})
public class PortalApplication {

  public static void main(String[] args) throws Exception {
    ConfigurableApplicationContext context = SpringApplication.run(PortalApplication.class, args);
    context.addApplicationListener(new ApplicationPidFileWriter());
    context.addApplicationListener(new WebServerPortFileWriter());
  }
}