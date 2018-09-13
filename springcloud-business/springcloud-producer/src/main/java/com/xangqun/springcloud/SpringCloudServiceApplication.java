package com.xangqun.springcloud;

//import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;


//@EnableApolloConfig({"product.yaml"}) //或者配置文件里加
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrix
@MapperScan("com.xangqun.springcloud.mapper")
@SpringBootApplication(scanBasePackages = {"com.xangqun.springcloud"})
public class SpringCloudServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudServiceApplication.class, args);
    }


}
