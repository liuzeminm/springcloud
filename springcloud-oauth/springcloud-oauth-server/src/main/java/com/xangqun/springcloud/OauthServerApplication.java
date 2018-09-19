package com.xangqun.springcloud;

//import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;


//@EnableApolloConfig({"product.yaml"}) //或者配置文件里加
@EnableFeignClients
@EnableEurekaClient
//@EnableCircuitBreaker
//@EnableHystrix
@MapperScan("com.xangqun.springcloud")
@SpringBootApplication(scanBasePackages = {"com.xangqun.springcloud"})
//@EnableApolloConfig({"testxt"})
public class OauthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthServerApplication.class, args);
    }


}
