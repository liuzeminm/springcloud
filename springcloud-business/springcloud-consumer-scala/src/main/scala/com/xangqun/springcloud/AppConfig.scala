package com.xangqun.springcloud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

/**
  *
  *
  * @author laixiangqun
  * @since 2018-8-14
  */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
@ServletComponentScan //针对@webFilter @WebListener扫描
@ConditionalOnClass(value = Array(classOf[WebMvcAutoConfiguration]))
@ComponentScan(basePackages = Array("com.xangqun.springcloud"))
class AppConfig {

}
